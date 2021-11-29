package pl.nullreference.bankstatement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.BankStatementService;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.viewmodel.BankStatementItemViewModel;

import java.io.File;
import java.util.List;

@Component
public class BankStatementOverviewController {

    private BankStatementService bankStatementService;
    private Stage primaryStage;
    private ObservableList<BankStatementItemViewModel> data;

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

    public BankStatementOverviewController(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
    }

    @FXML
    private void initialize() {
        bankStatementService.addProviders();
        ObservableList<String> providers = FXCollections.observableList(bankStatementService.getAllProviders());
        comboBox.setItems(providers);
        setData(bankStatementService.getAllBankStatements());
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(this.primaryStage);
        String bankName = comboBox.getValue();
        bankStatementService.parseAndSave(file, bankName);
    }

    public void setData(List<BankStatement> statementsList) {
        this.data = FXCollections.observableArrayList();
        statementsList.forEach(statement -> statement.getItems().forEach(item -> data.add(new BankStatementItemViewModel(item))));
        statementsTable.setItems(data);
    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
}
