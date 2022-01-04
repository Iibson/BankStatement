package pl.nullreference.bankstatement.statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;

import java.util.List;

public class ExpensesAndIncomes {
    private static final String EXPENSES_CHART_LABEL = "Expenses";
    private static final String INCOMES_CHART_LABEL = "Incomes";

    public static void generateExpensesIncomesChart(BarChart<String, Double> expensesIncomesChart, List<BankStatementItem> bankStatementItems) {
        double expenses = getTotalExpenses(bankStatementItems);
        double incomes = getTotalIncomes(bankStatementItems);
        insertDataIntoChart(expenses, incomes, expensesIncomesChart);
    }

    public static double getTotalExpenses(List<BankStatementItem> bankStatementItems) {
        return bankStatementItems.stream()
                .map(BankStatementItem::getSum)
                .filter(transactionSum -> transactionSum < 0)
                .map(Math::abs)
                .reduce(0.0, Double::sum);
    }

    public static double getTotalIncomes(List<BankStatementItem> bankStatementItems) {
        return bankStatementItems.stream()
                .map(BankStatementItem::getSum)
                .filter(transactionSum -> transactionSum > 0)
                .reduce(0.0, Double::sum);
    }

    private static void insertDataIntoChart(double expenses, double incomes, BarChart<String, Double> expensesIncomesChart) {
        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();

        XYChart.Data<String, Double> expensesData = new XYChart.Data<>(EXPENSES_CHART_LABEL, expenses);
        dataSeries.getData().add(expensesData);
        XYChart.Data<String, Double> incomesData = new XYChart.Data<>(INCOMES_CHART_LABEL, incomes);
        dataSeries.getData().add(incomesData);

        ObservableList<XYChart.Series<String, Double>> expensesIncomesData = FXCollections.observableArrayList();
        expensesIncomesData.add(dataSeries);
        expensesIncomesChart.setData(expensesIncomesData);

        String expensesTooltipText = "Expenses: " + String.format("%.2f", expenses) + " PLN";
        String incomesTooltipText = "Incomes: " +  String.format("%.2f", incomes) + " PLN";
        StatisticsUtils.configureTooltip(expensesData.getNode(), expensesTooltipText);
        StatisticsUtils.configureTooltip(incomesData.getNode(), incomesTooltipText);
    }


}
