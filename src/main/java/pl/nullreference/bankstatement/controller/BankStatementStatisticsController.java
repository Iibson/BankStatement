package pl.nullreference.bankstatement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.services.BankStatementService;
import pl.nullreference.bankstatement.statistics.ExpensesAndIncomes;
import pl.nullreference.bankstatement.statistics.ExpensesByCategory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class BankStatementStatisticsController {
    private static final String INCORRECT_DATE_INPUT_MESSAGE = "Wrong date format. Correct date format is: DD.MM.YYYY";

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
        loseFocusOnElement((DatePicker)event.getSource());
        for (CheckBox presetCheckBox: presetCheckBoxes) {
            presetCheckBox.setSelected(false);
        }
    }

    @FXML
    public void handlePresetChosen(ActionEvent event) {
        loseFocusOnElement((CheckBox)event.getSource());

        CheckBox selectedPresetCheckBox = (CheckBox)event.getSource();
        boolean selectedPresetCheckBoxState = selectedPresetCheckBox.isSelected();

        setPresetDateValues(selectedPresetCheckBox);
        togglePresetSelection(selectedPresetCheckBox, selectedPresetCheckBoxState);
    }

    @FXML
    public void handleGenerateCharts(ActionEvent event) {
        loseFocusOnElement((Button)event.getSource());
        errorMessageLabel.setVisible(false);
        if (startingDatePicker.getValue() == null || endingDatePicker.getValue() == null) {
            showIncorrectInputMessage();
            return;
        }

        Date startingDate = convertDate(startingDatePicker.getValue());
        Date endingDate = convertDate(endingDatePicker.getValue());
        List<BankStatementItem> bankStatementItems = bankStatementService.getBankStatementItemsBetweenDates(startingDate, endingDate);

        ExpensesAndIncomes.generateExpensesIncomesChart(expensesIncomesChart, bankStatementItems);
        ExpensesByCategory.generateExpensesByCategoryChart(expensesByCategoryChart, bankStatementItems);
        expensesIncomesChart.getParent().layout();
        expensesByCategoryChart.getParent().layout();
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

    private void togglePresetSelection(CheckBox selectedPresetCheckBox, boolean selectedPresetCheckBoxState) {
        for (CheckBox presetCheckBox: presetCheckBoxes) {
            presetCheckBox.setSelected(presetCheckBox.equals(selectedPresetCheckBox) && selectedPresetCheckBoxState);
        }
    }

    private static void loseFocusOnElement(Node node) {
        node.getParent().requestFocus();
    }

    private void showIncorrectInputMessage() {
        errorMessageLabel.setText(INCORRECT_DATE_INPUT_MESSAGE);
        errorMessageLabel.setVisible(true);
    }

    private static Date convertDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
}
