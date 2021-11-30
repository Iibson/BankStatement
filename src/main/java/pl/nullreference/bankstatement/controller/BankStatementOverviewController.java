package pl.nullreference.bankstatement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.services.BankStatementService;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.viewmodel.BankStatementItemViewModel;

import java.io.IOException;
import java.util.List;

@Component
public class BankStatementOverviewController {

    private BankStatementService bankStatementService;
    private Stage primaryStage;
    private ObservableList<BankStatementItemViewModel> data;

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


    public BankStatementOverviewController(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
    }

    @FXML
    private void initialize() {
        setData(bankStatementService.getAllBankStatements());
        statementsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        operationDescriptionColumn.setCellValueFactory(dataValue -> dataValue.getValue().operationDescriptionProperty());
        cardAccountNumberColumn.setCellValueFactory(dataValue -> dataValue.getValue().cardAccountNumberProperty());
        currencyColumn.setCellValueFactory(dataValue -> dataValue.getValue().currencyProperty());
        sumColumn.setCellValueFactory(dataValue -> dataValue.getValue().sumProperty().asObject());
        balanceColumn.setCellValueFactory(dataValue -> dataValue.getValue().balanceProperty().asObject());
    }

    @FXML
    private void handleImport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BankStatementOverviewController.class.getResource("/view/ImportBankStatementDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Import new statement");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            BankStatementImportDialogController controller = loader.getController();
            controller.setProvidersBox(this.bankStatementService.getAllProviders());
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            if (controller.getConfirmedBankName() != null && controller.getConfirmedFile() != null) {
                bankStatementService.parseAndSave(controller.getConfirmedFile(), controller.getConfirmedBankName());
                setData(bankStatementService.getAllBankStatements());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(List<BankStatement> statementsList) {
        this.data = FXCollections.observableArrayList();
        statementsList.forEach(statement -> statement.getItems().forEach(item -> data.add(new BankStatementItemViewModel(item))));
        statementsTable.setItems(data);
    }
}
