package pl.nullreference.bankstatement.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.viewmodel.BankStatementItemViewModel;


public class BankStatementOverviewController {

    private ObservableList<BankStatementItemViewModel> data;
    private ObservableList<String> providers;
    private BankStatementController appController;
    @FXML
    private ComboBox<String> comboBox = new ComboBox<>();

    @FXML
    private TableView<BankStatementItemViewModel> statementsTable;

    @FXML
    private TableColumn<BankStatementItemViewModel, String> operationDescriptionColumn;

    @FXML
    private TableColumn<BankStatementItemViewModel, String> cardAccountNumberColumn;

    @FXML
    private TableColumn<BankStatementItemViewModel, Double> sumColumn;

    @FXML
    private TableColumn<BankStatementItemViewModel, String> currencyColumn;

    @FXML
    private TableColumn<BankStatementItemViewModel, Double> balanceColumn;


    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        statementsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        operationDescriptionColumn.setCellValueFactory(dataValue -> dataValue.getValue().operationDescriptionProperty());
        cardAccountNumberColumn.setCellValueFactory(dataValue -> dataValue.getValue().cardAccountNumberProperty());
        currencyColumn.setCellValueFactory(dataValue -> dataValue.getValue().currencyProperty());
        sumColumn.setCellValueFactory(dataValue -> dataValue.getValue().sumProperty().asObject());
        balanceColumn.setCellValueFactory(dataValue -> dataValue.getValue().balanceProperty().asObject());

        deleteButton.disableProperty().bind(comboBox.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void handleImport(ActionEvent event) {
        this.appController.showFileChooser();
        System.out.println(this.comboBox.getValue());
           }

    public void setData(List<BankStatementItem> statementsList) {
        this.data = FXCollections.observableArrayList();
        statementsList.forEach(statement -> data.add(new BankStatementItemViewModel(statement)));
        statementsTable.setItems(data);
    }
    public void setProviders(List<String> providers){
        this.comboBox.setItems(FXCollections.observableList(providers));
    }
    public void setAppController(BankStatementController appController) {
        this.appController = appController;
    }
}
