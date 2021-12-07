package pl.nullreference.bankstatement.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class BankStatementItemListViewModel {

    @FXML
    private ObjectProperty<ObservableList<BankStatementItemViewModel>> list;

    public BankStatementItemListViewModel() {
        this.list = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    }

    public void addBankStatementItemViewModel(BankStatementItemViewModel item) {
        this.list.getValue().add(item);
    }

    public ObservableList<BankStatementItemViewModel> getList() {
        return this.list.get();
    }

    public ObjectProperty<ObservableList<BankStatementItemViewModel>> getListProperty() {
        return this.list;
    }
}
