package pl.nullreference.bankstatement.statistics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.bankstatement.Category;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExpensesByCategoryTests {
    @Test
    public void groupExpensesByCategory() {
        // given
        List<BankStatementItem> bankStatementItems = Arrays.asList(
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -10.45, "PLN", 1234.56, Category.NONE),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -150.67, "PLN", 1234.56, Category.TRANSPORT),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", 123.45, "PLN", 1234.56, Category.SALARY),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -12.99, "PLN", 1234.56, Category.FOOD),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -99.97, "PLN", 1234.56, Category.TRANSPORT),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -50.0, "PLN", 1234.56, Category.FOOD),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -20.77, "PLN", 1234.56, Category.ENTERTAINMENT)
        );
        HashMap<Category, Double> expensesByCategory = new HashMap<>();
        // when
        ExpensesByCategory.groupExpensesByCategory(bankStatementItems, expensesByCategory);
        // then
        Assertions.assertEquals(62.99, expensesByCategory.get(Category.FOOD), 0.000001);
        Assertions.assertEquals(250.64, expensesByCategory.get(Category.TRANSPORT), 0.000001);
        Assertions.assertEquals(20.77, expensesByCategory.get(Category.ENTERTAINMENT), 0.000001);
    }

    @Test
    public void groupExpensesByCategoryNoneCategory() {
        // given
        List<BankStatementItem> bankStatementItems = Arrays.asList(
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -10.45, "PLN", 1234.56, Category.NONE),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -150.67, "PLN", 1234.56, Category.TRANSPORT),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", 123.45, "PLN", 1234.56, Category.SALARY),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -12.99, "PLN", 1234.56, Category.FOOD),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -99.97, "PLN", 1234.56, Category.TRANSPORT),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -50.0, "PLN", 1234.56, Category.FOOD),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -20.77, "PLN", 1234.56, Category.ENTERTAINMENT)
        );
        HashMap<Category, Double> expensesByCategory = new HashMap<>();
        // when
        ExpensesByCategory.groupExpensesByCategory(bankStatementItems, expensesByCategory);
        // then
        Assertions.assertNull(expensesByCategory.get(Category.NONE));
    }

    @Test
    public void groupExpensesByCategoryCategoryWithNoExpenses() {
        // given
        List<BankStatementItem> bankStatementItems = Arrays.asList(
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -10.45, "PLN", 1234.56, Category.NONE),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -150.67, "PLN", 1234.56, Category.TRANSPORT),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", 123.45, "PLN", 1234.56, Category.SALARY),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -12.99, "PLN", 1234.56, Category.FOOD),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -99.97, "PLN", 1234.56, Category.TRANSPORT),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -50.0, "PLN", 1234.56, Category.FOOD),
                new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", -20.77, "PLN", 1234.56, Category.ENTERTAINMENT)
        );
        HashMap<Category, Double> expensesByCategory = new HashMap<>();
        // when
        ExpensesByCategory.groupExpensesByCategory(bankStatementItems, expensesByCategory);
        // then
        Assertions.assertNull(expensesByCategory.get(Category.SALARY));
    }
}
