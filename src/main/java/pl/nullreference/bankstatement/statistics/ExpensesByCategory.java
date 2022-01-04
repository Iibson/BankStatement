package pl.nullreference.bankstatement.statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.bankstatement.Category;

import java.util.HashMap;
import java.util.List;

public class ExpensesByCategory {
    public static void generateExpensesByCategoryChart(PieChart expensesByCategoryChart, List<BankStatementItem> bankStatementItems) {
        HashMap<Category, Double> expensesByCategory = new HashMap<>();
        groupExpensesByCategory(bankStatementItems, expensesByCategory);
        insertDataIntoChart(expensesByCategory, expensesByCategoryChart);
    }

    public static void groupExpensesByCategory(List<BankStatementItem> bankStatementItems, HashMap<Category, Double> expensesByCategory) {
        bankStatementItems.stream()
                .filter(bankStatementItem -> bankStatementItem.getSum() < 0 && bankStatementItem.getCategory() != Category.NONE)
                .forEach(bankStatementItem -> {
                    if (!expensesByCategory.containsKey(bankStatementItem.getCategory())) {
                        expensesByCategory.put(bankStatementItem.getCategory(), 0.0);
                    }
                    expensesByCategory.put(bankStatementItem.getCategory(), expensesByCategory.get(bankStatementItem.getCategory()) + Math.abs(bankStatementItem.getSum()));
                });
    }

    private static void insertDataIntoChart(HashMap<Category, Double> expensesByCategory, PieChart expensesByCategoryChart) {
        ObservableList<PieChart.Data> expensesByCategoryData = FXCollections.observableArrayList();
        for(Category category : expensesByCategory.keySet()) {
            PieChart.Data data = new PieChart.Data(category.toString(), expensesByCategory.get(category));
            expensesByCategoryData.add(data);
        }
        expensesByCategoryChart.setData(expensesByCategoryData);

        for (PieChart.Data data : expensesByCategoryChart.getData()) {
            String chartTooltipText = data.getName() + ": " + String.format("%.2f", data.getPieValue()) + " PLN";
            StatisticsUtils.configureTooltip(data.getNode(), chartTooltipText);
        }
    }
}
