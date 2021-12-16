package pl.nullreference.bankstatement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.services.BankStatementService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
    private CategoryAxis expensesIncomesChartXAxis;

    @FXML
    private NumberAxis expensesIncomesChartYAxis;

    @FXML
    private PieChart expensesCategoryChart;

    public BankStatementStatisticsController(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
    }

    @FXML
    public void initialize() {
        System.out.println("Initializing statistics...");

        presetCheckBoxes = new ArrayList<CheckBox>(Arrays.asList(
                lastMonthCheckBox,
                lastSixMonthsCheckBox,
                lastYearCheckBox,
                allTimeCheckBox
        ));

        errorMessageLabel.managedProperty().bind(errorMessageLabel.visibleProperty());

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Some Month (idk June or sth)");
        series.getData().add(new XYChart.Data<>("Expenses", 123.45));
        series.getData().add(new XYChart.Data<>("Incomes", 159.48));
        expensesIncomesChart.getData().add(series);
        for (Node node : expensesIncomesChart.lookupAll(".default-color0.chart-bar")) {
            node.setStyle("-fx-bar-fill: dodgerblue;");
        }

        ObservableList<PieChart.Data> expensesByCategoryData = FXCollections.observableArrayList(
                new PieChart.Data("Category 1", 12.34),
                new PieChart.Data("Category 2", 15.91),
                new PieChart.Data("Category 3", 21.37)
        );
        expensesCategoryChart.setData(expensesByCategoryData);
    }

    @FXML
    public void handlePresetChosen(ActionEvent event) {
        CheckBox selectedPresetCheckBox = (CheckBox)event.getSource();

        toggleInputDisabledState(selectedPresetCheckBox);

        setPresetDateValues(selectedPresetCheckBox);
    }

    private void toggleInputDisabledState(CheckBox selectedPresetCheckBox) {
        for (CheckBox presetCheckBox : presetCheckBoxes) {
            if(!presetCheckBox.equals(selectedPresetCheckBox)) {
                presetCheckBox.setDisable(selectedPresetCheckBox.isSelected());
            }
            startingDatePicker.setDisable(selectedPresetCheckBox.isSelected());
            endingDatePicker.setDisable(selectedPresetCheckBox.isSelected());
        }
    }

    private void setPresetDateValues(CheckBox selectedPresetCheckBox) {
        LocalDate startingDate = null;
        LocalDate endingDate = null;

        if (selectedPresetCheckBox.isSelected()) {
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
        }

        startingDatePicker.setValue(startingDate);
        endingDatePicker.setValue(endingDate);
    }

    @FXML
    public void handleGenerateCharts(ActionEvent event) {
        errorMessageLabel.setText("Error: shit is not implemented yet!");
        errorMessageLabel.setVisible(true);
    }
}
