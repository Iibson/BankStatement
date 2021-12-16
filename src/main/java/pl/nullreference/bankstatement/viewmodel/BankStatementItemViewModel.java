package pl.nullreference.bankstatement.viewmodel;

import javafx.beans.property.*;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.bankstatement.Category;

import java.util.Date;

public class BankStatementItemViewModel {
    private IntegerProperty id;
    private ObjectProperty<Date> operationDate;
    private StringProperty operationDescription;
    private StringProperty cardAccountNumber;
    private DoubleProperty sum;
    private StringProperty currency;
    private DoubleProperty balance;
    private ObjectProperty<Category> category;

    public BankStatementItemViewModel(BankStatementItem bankStatementItem) {
        this.id = new SimpleIntegerProperty(bankStatementItem.getId());
        this.operationDate = new SimpleObjectProperty<>(bankStatementItem.getOperationDate());
        this.operationDescription = new SimpleStringProperty(bankStatementItem.getOperationDescription());
        this.cardAccountNumber = new SimpleStringProperty(bankStatementItem.getCardAccountNumber());
        this.sum = new SimpleDoubleProperty(bankStatementItem.getSum());
        this.currency = new SimpleStringProperty(bankStatementItem.getCurrency());
        this.balance = new SimpleDoubleProperty(bankStatementItem.getBalance());
        this.category = new SimpleObjectProperty<>(bankStatementItem.getCategory());
    }


    public Category getCategory() {
        return category.get();
    }

    public ObjectProperty<Category> categoryProperty() {
        return category;
    }

    public void setCategory(Category category) {
        this.category.set(category);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public Date getOperationDate() {
        return operationDate.get();
    }

    public ObjectProperty<Date> operationDateProperty() {
        return operationDate;
    }

    public String getOperationDescription() {
        return operationDescription.get();
    }

    public StringProperty operationDescriptionProperty() {
        return operationDescription;
    }

    public String getCardAccountNumber() {
        return cardAccountNumber.get();
    }

    public StringProperty cardAccountNumberProperty() {
        return cardAccountNumber;
    }

    public double getSum() {
        return sum.get();
    }

    public DoubleProperty sumProperty() {
        return sum;
    }

    public String getCurrency() {
        return currency.get();
    }

    public StringProperty currencyProperty() {
        return currency;
    }

    public double getBalance() {
        return balance.get();
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate.set(operationDate);
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription.set(operationDescription);
    }

    public void setCardAccountNumber(String cardAccountNumber) {
        this.cardAccountNumber.set(cardAccountNumber);
    }

    public void setSum(double sum) {
        this.sum.set(sum);
    }

    public void setCurrency(String currency) {
        this.currency.set(currency);
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }
}
