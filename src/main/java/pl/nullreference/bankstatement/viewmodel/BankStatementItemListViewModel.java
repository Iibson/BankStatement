package pl.nullreference.bankstatement.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BankStatementItemListViewModel {
    private ObservableList<BankStatementItemViewModel> list;
    public BankStatementItemListViewModel(){
        this.list = FXCollections.emptyObservableList();
    }
    public void addBankStatementItemViewModel(BankStatementItemViewModel item){
        this.list.add(item);
    }
    public ObservableList<BankStatementItemViewModel> getList(){
        return this.list;
    }
}
