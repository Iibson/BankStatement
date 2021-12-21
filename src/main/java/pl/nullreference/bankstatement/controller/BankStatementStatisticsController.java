package pl.nullreference.bankstatement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.bankstatement.Category;
import pl.nullreference.bankstatement.services.BankStatementService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
public class BankStatementStatisticsController {

    private BankStatementService bankStatementService;

    @FXML
    private DatePicker startingDatePicker;

    @FXML
    private DatePicker endingDatePicker;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private CheckBox lastMonthCheckBox;

    @FXML
    private CheckBox lastSixMonthsCheckBox;

    @FXML
    private CheckBox lastYearCheckBox;

    @FXML
    private CheckBox allTimeCheckBox;

    private List<CheckBox> presetCheckBoxes;

    @FXML
    private BarChart<String, Double> expensesIncomesChart;

    @FXML
    private PieChart expensesByCategoryChart;

    public void setBankStatementService(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
    }

    @FXML
    public void initialize() {
        presetCheckBoxes = new ArrayList<>(Arrays.asList(
                lastMonthCheckBox,
                lastSixMonthsCheckBox,
                lastYearCheckBox,
                allTimeCheckBox
        ));

        errorMessageLabel.managedProperty().bind(errorMessageLabel.visibleProperty());

        fixLostFocusBehaviourOnDatePicker(startingDatePicker);
        fixLostFocusBehaviourOnDatePicker(endingDatePicker);

        expensesIncomesChart.getXAxis().setAnimated(false);
    }

    @FXML
    public void handleDateChosen(ActionEvent event) {
        ((DatePicker)event.getSource()).getParent().requestFocus();
    }

    @FXML
    public void handlePresetChosen(ActionEvent event) {
        ((CheckBox)event.getSource()).getParent().requestFocus();

        CheckBox selectedPresetCheckBox = (CheckBox)event.getSource();

        toggleInputDisabledState(selectedPresetCheckBox);

        setPresetDateValues(selectedPresetCheckBox);
    }

    @FXML
    public void handleGenerateCharts(ActionEvent event) {
        ((Button)event.getSource()).getParent().requestFocus();
        errorMessageLabel.setVisible(false);

        if (startingDatePicker.getValue() == null || endingDatePicker.getValue() == null) {
            showIncorrectInputMessage();
            return;
        }

        Date startingDate = convertDate(startingDatePicker.getValue());
        Date endingDate = convertDate(endingDatePicker.getValue());
        List<BankStatementItem> bankStatementItems = bankStatementService.getBankStatementItemsBetweenDates(startingDate, endingDate);

        generateExpensesIncomesChart(bankStatementItems);

        generateExpensesByCategoryChart(bankStatementItems);
    }

    private void fixLostFocusBehaviourOnDatePicker(DatePicker datePicker) {
        datePicker.getEditor().focusedProperty().addListener((obj, wasFocused, isFocused)->{
            if (!isFocused) {
                try {
                    datePicker.setValue(datePicker.getConverter().fromString(datePicker.getEditor().getText()));
                } catch (DateTimeParseException e) {
                    datePicker.getEditor().setText(datePicker.getConverter().toString(datePicker.getValue()));
                }
            }
        });
    }

    private void toggleInputDisabledState(CheckBox selectedPresetCheckBox) {
        for (CheckBox presetCheckBox : presetCheckBoxes) {
            if(!presetCheckBox.equals(selectedPresetCheckBox)) {
                presetCheckBox.setDisable(selectedPresetCheckBox.isSelected());
            }
        }
        startingDatePicker.setDisable(selectedPresetCheckBox.isSelected());
        endingDatePicker.setDisable(selectedPresetCheckBox.isSelected());
    }

    private void setPresetDateValues(CheckBox selectedPresetCheckBox) {
        if (selectedPresetCheckBox.isSelected()) {
            LocalDate startingDate = null;
            LocalDate endingDate;

            if (selectedPresetCheckBox.equals(lastMonthCheckBox)) {
                startingDate = LocalDate.now().minusMonths(1);
            }
            else if (selectedPresetCheckBox.equals(lastSixMonthsCheckBox)) {
                startingDate =  LocalDate.now().minusMonths(6);
            }
            else if (selectedPresetCheckBox.equals(lastYearCheckBox)) {
                startingDate = LocalDate.now().minusYears(1);
            }
            else if (selectedPresetCheckBox.equals(allTimeCheckBox)) {
                startingDate = LocalDate.of(1970, 1, 1);
            }
            endingDate = LocalDate.now();

            startingDatePicker.setValue(startingDate);
            endingDatePicker.setValue(endingDate);
        }
    }

    private void generateExpensesIncomesChart(List<BankStatementItem> bankStatementItems) {
        double expenses = bankStatementItems.stream()
                .map(BankStatementItem::getSum)
                .filter(transactionSum -> transactionSum < 0)
                .map(Math::abs)
                .reduce(0.0, Double::sum);

        double incomes = bankStatementItems.stream()
                .map(BankStatementItem::getSum)
                .filter(transactionSum -> transactionSum > 0)
                .reduce(0.0, Double::sum);

        ObservableList<XYChart.Series<String, Double>> expensesIncomesData = FXCollections.observableArrayList();
        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();

        XYChart.Data<String, Double> expensesData = new XYChart.Data<>("Expenses", expenses);
        dataSeries.getData().add(expensesData);
        XYChart.Data<String, Double> incomesData = new XYChart.Data<>("Incomes", incomes);
        dataSeries.getData().add(incomesData);

        expensesIncomesData.add(dataSeries);
        expensesIncomesChart.setData(expensesIncomesData);

        configureTooltip(expensesData.getNode(), "Expenses: " + String.format("%.2f", expenses) + " PLN");
        configureTooltip(incomesData.getNode(), "Incomes: " +  String.format("%.2f", incomes) + " PLN");

        expensesIncomesChart.getParent().layout();
    }

    private void generateExpensesByCategoryChart(List<BankStatementItem> bankStatementItems) {
        HashMap<Category, Double> expensesByCategory = new HashMap<>();
        bankStatementItems.stream()
                .filter(bankStatementItem -> bankStatementItem.getSum() < 0 && bankStatementItem.getCategory() != Category.NONE)
                .forEach(bankStatementItem -> {
                    if (!expensesByCategory.containsKey(bankStatementItem.getCategory())) {
                        expensesByCategory.put(bankStatementItem.getCategory(), 0.0);
                    }
                    expensesByCategory.put(bankStatementItem.getCategory(), expensesByCategory.get(bankStatementItem.getCategory()) + Math.abs(bankStatementItem.getSum()));
                });

        ObservableList<PieChart.Data> expensesByCategoryData = FXCollections.observableArrayList();
        for(Category category : expensesByCategory.keySet()) {
            PieChart.Data data = new PieChart.Data(category.toString(), expensesByCategory.get(category));
            expensesByCategoryData.add(data);
        }
        expensesByCategoryChart.setData(expensesByCategoryData);

        for (PieChart.Data data : expensesByCategoryChart.getData()) {
            configureTooltip(data.getNode(), data.getName() + ": " + String.format("%.2f", data.getPieValue()) + " PLN");
        }

        expensesByCategoryChart.getParent().layout();
    }

    private void showIncorrectInputMessage() {
        errorMessageLabel.setText("Wrong date format. Correct date format is: DD.MM.YYYY");
        errorMessageLabel.setVisible(true);
    }

    private static Date convertDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static void configureTooltip(Node associatedNode, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setShowDelay(Duration.seconds(0.5));
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.setStyle("-fx-font-size: 15");
        Tooltip.install(associatedNode, tooltip);
    }
}
