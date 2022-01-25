package pl.nullreference.bankstatement.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.Category;
import pl.nullreference.bankstatement.services.BankStatementService;
import pl.nullreference.bankstatement.viewmodel.BankStatementItemListViewModel;
import pl.nullreference.bankstatement.viewmodel.BankStatementItemViewModel;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Component
public class BankStatementOverviewController {

    private static final String BANK_STATEMENT_DIALOG_FXML = "/view/ImportBankStatementDialog.fxml";
    private static final String BANK_STATEMENT_STATISTICS_FXML = "/view/BankStatementStatistics.fxml";
    private static final String BANK_STATEMENT_ITEM_EDIT_FXML = "/view/EditBankStatementItem.fxml";
    private static final String BANK_STATEMENT_DATA_SOURCES_FXML = "/view/DataSourcesDialog.fxml";

    private BankStatementService bankStatementService;
    private Stage primaryStage;

    private BankStatementItemListViewModel statementItemList;

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
    private TableColumn<BankStatementItemViewModel, ComboBox<Category>> categoryColumn;

    private boolean isAutoRefreshWorking;

    public BankStatementOverviewController(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
        this.statementItemList = new BankStatementItemListViewModel();
    }

    @FXML
    private void initialize() {
        this.initData();
        this.initializeDataTable();
        this.configureAutoRefresh();

    }

    private void initializeDataTable() {
        statementsTable.itemsProperty().bindBidirectional(statementItemList.getListProperty());
        statementsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        categoryColumn.setCellValueFactory(this::createComboBoxProperty);
        operationDescriptionColumn.setCellValueFactory(dataValue -> dataValue.getValue().operationDescriptionProperty());
        cardAccountNumberColumn.setCellValueFactory(dataValue -> dataValue.getValue().cardAccountNumberProperty());
        currencyColumn.setCellValueFactory(dataValue -> dataValue.getValue().currencyProperty());
        sumColumn.setCellValueFactory(dataValue -> dataValue.getValue().sumProperty().asObject());
        balanceColumn.setCellValueFactory(dataValue -> dataValue.getValue().balanceProperty().asObject());
    }

    private ObservableValue<ComboBox<Category>> createComboBoxProperty(TableColumn.CellDataFeatures<BankStatementItemViewModel, ComboBox<Category>> dataValue) {
        ComboBox<Category> box = new ComboBox<>();
        box.setItems(FXCollections.observableArrayList(Category.values()));
        box.setValue(dataValue.getValue().getCategory());
        box.getSelectionModel().selectedItemProperty().addListener(
                (options, oldValue, newValue) -> onCategoryChange(dataValue.getValue(), newValue)
        );
        return new SimpleObjectProperty<>(box);
    }

    private void onCategoryChange(BankStatementItemViewModel dataValue, Category newValue) {
        dataValue.setCategory(newValue);
        bankStatementService.updateBankStatementItem(dataValue);
    }

    private FXMLLoader getLoader(String resourceName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BankStatementOverviewController.class.getResource(resourceName));
        return loader;
    }

    private Stage createStage(Region page, String stageTitle) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(stageTitle);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    private void initDataInImportController(BankStatementImportDialogController controller, Stage stage) {
        controller.setProvidersBox(this.bankStatementService.getAllProviders());
        controller.setDialogStage(stage);
    }

    private void initDataInEditController(BankStatementItemEditController controller, Stage stage) {
        controller.initValidator();
        controller.setDialogStage(stage);
    }

    @FXML
    private void handleImport(ActionEvent event) {
        ((Button) event.getSource()).getParent().requestFocus();
        try {
            FXMLLoader loader = getLoader(BANK_STATEMENT_DIALOG_FXML);
            BorderPane page = loader.load();
            Stage dialogStage = createStage(page, "Import new statement");
            BankStatementImportDialogController controller = loader.getController();
            initDataInImportController(controller, dialogStage);
            dialogStage.showAndWait();
            if (controller.getConfirmedBankName() != null && controller.getConfirmedFile() != null) {
                new Thread(() -> {
                    BankStatement importedStatement = bankStatementService.parseAndSave(controller.getConfirmedFile(), controller.getConfirmedBankName());
                    Platform.runLater(() -> this.addNewBankStatement(importedStatement));
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStatistics(ActionEvent event) {
        ((Button) event.getSource()).getParent().requestFocus();
        try {
            loadStatisticsDialog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        ((Button) event.getSource()).getParent().requestFocus();
        try {
            BankStatementItemViewModel bankStatementItemViewModel = statementsTable.getSelectionModel()
                    .getSelectedItem();
            if (bankStatementItemViewModel == null) {
                showNoBankStatementItemSelectedAlert();
                return;
            }
            loadEditDialog(bankStatementItemViewModel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void refreshDataSources() {
        Completable.fromAction(() -> {
                    if (!isAutoRefreshWorking) {
                        System.out.println("Manually refreshing start");
                        bankStatementService.refreshSources();
                        System.out.println("Manually refreshing end");
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @FXML
    private void handleDataSources() throws IOException {
        FXMLLoader loader = getLoader(BANK_STATEMENT_DATA_SOURCES_FXML);
        VBox page = loader.load();
        Stage dataSourcesStage = createStage(page, "Manage data sources");
        BankStatementDataSourcesController controller = loader.getController();
        controller.setProvidersBoxes(this.bankStatementService.getAllProviders());
        controller.setBankStatementService(bankStatementService);
        controller.loadData();
        controller.setDialogStage(dataSourcesStage);
        dataSourcesStage.show();
    }

    private void loadStatisticsDialog() throws IOException {
        FXMLLoader loader = getLoader(BANK_STATEMENT_STATISTICS_FXML);
        VBox page = loader.load();
        Stage statisticsStage = createStage(page, "Transaction statistics");
        BankStatementStatisticsController controller = loader.getController();
        controller.setBankStatementService(bankStatementService);
        statisticsStage.show();
    }


    private void loadEditDialog(BankStatementItemViewModel bankStatementItemViewModel) throws IOException {
        FXMLLoader loader = getLoader(BANK_STATEMENT_ITEM_EDIT_FXML);
        BorderPane page = loader.load();
        Stage dialogStage = createStage(page, "Edit Bank Statement Item");
        BankStatementItemEditController controller = loader.getController();
        initDataInEditController(controller, dialogStage);

        controller.setData(bankStatementItemViewModel);
        controller.setBankStatementService(bankStatementService);

        dialogStage.showAndWait();
    }

    private void showNoBankStatementItemSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Proszę wybrać element do edycji.", ButtonType.OK);
        alert.show();
    }

    public void initData() {
        List<BankStatement> allStatements = this.bankStatementService.getAllBankStatements();
        allStatements.forEach(statement -> statement.getItems()
                .forEach(item -> this.statementItemList.addBankStatementItemViewModel(new BankStatementItemViewModel(item))));
        this.bankStatementService.getBankStatementsAsObservable().subscribe(statement -> {
            System.out.println("Received new bank statement: " + statement);
            statement.getItems().forEach(item -> this.statementItemList.addBankStatementItemViewModel(new BankStatementItemViewModel(item)));
        });
    }

    public void addNewBankStatement(BankStatement bankStatement) {
        bankStatement.getItems().forEach(item -> this.statementItemList.addBankStatementItemViewModel(new BankStatementItemViewModel(item)));
    }

    private void configureAutoRefresh() {
        Observable.interval(0, 30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(x -> {
                    isAutoRefreshWorking = true;
                    System.out.println("Auto refreshing start");
                    bankStatementService.refreshSources();
                    System.out.println("Auto refreshing end");
                    isAutoRefreshWorking = false;

                });
    }
}
