package pl.nullreference.bankstatement.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import pl.nullreference.bankstatement.model.source.SourceType;

public class BankStatementSourceListViewModel {
    @FXML
    private final ObjectProperty<ObservableList<BankStatementSourceViewModel>> directorySourcesList;
    @FXML
    private final ObjectProperty<ObservableList<BankStatementSourceViewModel>> urlSourcesList;
    public BankStatementSourceListViewModel() {
        this.directorySourcesList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        this.urlSourcesList = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    }

    public void addBankStatementItemViewModel(BankStatementSourceViewModel item) {
        if(item.getType().equals(SourceType.DIRECTORY)){
            this.directorySourcesList.getValue().add(item);
        }
        else{
            this.urlSourcesList.getValue().add(item);
        }
    }

    public ObjectProperty<ObservableList<BankStatementSourceViewModel>> getDirectoryListProperty() {
        return this.directorySourcesList;
    }
    public ObjectProperty<ObservableList<BankStatementSourceViewModel>> getUrlListProperty() {
        return this.urlSourcesList;
    }
    public void deleteSource(BankStatementSourceViewModel source){
        var list = source.getType().equals(SourceType.DIRECTORY) ? this.directorySourcesList : this.urlSourcesList;
        list.getValue().remove(source);
    }
}
