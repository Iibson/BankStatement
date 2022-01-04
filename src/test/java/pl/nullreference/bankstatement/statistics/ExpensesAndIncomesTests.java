package pl.nullreference.bankstatement.statistics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.bankstatement.Category;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpensesAndIncomesTests {
    @Test
    public void getTotalExpenses() {
        // given
        List<Double> positiveTotals = Arrays.asList(
                46.17, 97.55, 123.45, 130.19, 25.15, 5.0, 24.49
        );
        List<Double> negativeTotals = Arrays.asList(
                -10.45, -150.67, -123.45, -12.99, -99.97, -50.0, -20.77

        );
        List<BankStatementItem> bankStatementItems = new ArrayList<>();
        positiveTotals.forEach(total -> bankStatementItems.add(new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", total, "PLN", 1234.56, Category.NONE)));
        negativeTotals.forEach(total -> bankStatementItems.add(new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", total, "PLN", 1234.56, Category.NONE)));
        // when
        double totalExpenses = ExpensesAndIncomes.getTotalExpenses(bankStatementItems);
        // then
        Assertions.assertEquals(468.3, totalExpenses, 0.000001);
    }

    @Test
    public void getTotalIncomes() {
        // given
        List<Double> positiveTotals = Arrays.asList(
                46.17, 97.55, 123.45, 130.19, 25.15, 5.0, 24.49
        );
        List<Double> negativeTotals = Arrays.asList(
                -10.45, -150.67, -123.45, -12.99, -99.97, -50.0, -20.77

        );
        List<BankStatementItem> bankStatementItems = new ArrayList<>();
        positiveTotals.forEach(total -> bankStatementItems.add(new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", total, "PLN", 1234.56, Category.NONE)));
        negativeTotals.forEach(total -> bankStatementItems.add(new BankStatementItem(1, Date.valueOf(LocalDate.now()), "", "123456789", total, "PLN", 1234.56, Category.NONE)));
        // when
        double totalIncomes = ExpensesAndIncomes.getTotalIncomes(bankStatementItems);
        // then
        Assertions.assertEquals(452.0, totalIncomes, 0.000001);
    }
}
