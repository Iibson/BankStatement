package pl.nullreference.bankstatement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.model.source.SourceType;
import pl.nullreference.bankstatement.services.BankStatementService;
import pl.nullreference.bankstatement.viewmodel.BankStatementSourceListViewModel;
import pl.nullreference.bankstatement.viewmodel.BankStatementSourceViewModel;

import java.io.File;
import java.util.List;

public class BankStatementDataSourcesController {
    private Stage dialogStage;

    private BankStatementService bankStatementService;


    @FXML
    private TextField chosenDirectoryPathField;
    @FXML
    private TextField urlField;
    @FXML
    private ComboBox<String> urlProvidersBox;
    @FXML
    private ComboBox<String> urlExtensionsBox;
    @FXML
    private ComboBox<String> directoryProvidersBox;
    @FXML
    private ComboBox<String> directoryExtensionsBox;
    @FXML
    private Button addEndpointButton;
    @FXML
    private Button addDirectoryButton;
    @FXML
    private TableView<BankStatementSourceViewModel> directoryTable;
    @FXML
    private TableView<BankStatementSourceViewModel> urlTable;
    @FXML
    private TableColumn<BankStatementSourceViewModel, String> directoryTablePathColumn;
    @FXML
    private TableColumn<BankStatementSourceViewModel, String> directoryTableBankNameColumn;
    @FXML
    private TableColumn<BankStatementSourceViewModel, String> directoryTableExtensionColumn;
    @FXML
    private TableColumn<BankStatementSourceViewModel, String> urlTablePathColumn;
    @FXML
    private TableColumn<BankStatementSourceViewModel, String> urlTableBankNameColumn;
    @FXML
    private TableColumn<BankStatementSourceViewModel, String> urlTableExtensionColumn;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private BankStatementSourceListViewModel sourcesList;

    @FXML
    private void initialize() {
        this.addEndpointButton.disableProperty().bind(
                this.urlField.textProperty().length().isEqualTo(0)
                        .or(this.urlExtensionsBox.getSelectionModel().selectedItemProperty().isNull())
                        .or(this.urlProvidersBox.getSelectionModel().selectedItemProperty().isNull())
        );
        this.addDirectoryButton.disableProperty().bind(
                this.chosenDirectoryPathField.textProperty().length().isEqualTo(0)
                        .or(this.directoryExtensionsBox.getSelectionModel().selectedItemProperty().isNull())
                        .or(this.directoryProvidersBox.getSelectionModel().selectedItemProperty().isNull())
        );
    }



    @FXML
    private void handleChoose() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open directory");
        File directory = directoryChooser.showDialog(dialogStage);
        if (directory != null) {
            this.chosenDirectoryPathField.setText(directory.getAbsolutePath());
        }
    }

    @FXML
    private void handleDirectoryConfirm() {
        this.buildBankStatementSourceAndSave(
                this.chosenDirectoryPathField.getText(),
                this.directoryProvidersBox.getValue(),
                this.directoryExtensionsBox.getValue(),
                SourceType.DIRECTORY);
        this.showSuccessDialog();
        this.chosenDirectoryPathField.setText(null);

    }

    @FXML
    private void handleUrlConfirm() {
        this.buildBankStatementSourceAndSave(
                this.urlField.getText(),
                this.urlProvidersBox.getValue(),
                this.urlExtensionsBox.getValue(),
                SourceType.ENDPOINT);
        this.showSuccessDialog();
        this.urlField.setText(null);
    }

    @FXML
    private void deleteDirectorySource() {
        deleteSource(this.directoryTable);
    }

    @FXML
    private void deleteUrlSource() {
        deleteSource(this.urlTable);
    }
    public void loadData() {
        this.initData();
        this.initializeDataTables();
    }
    private void deleteSource(TableView<BankStatementSourceViewModel> table) {
        BankStatementSourceViewModel selectedSource = table.getSelectionModel()
                .getSelectedItem();
        if (selectedSource == null) {
            showNonSelectionDialog();
        } else {
            this.bankStatementService.deleteBankStatementSource(selectedSource.getPath());
            this.sourcesList.deleteSource(selectedSource);
        }
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Added successfully", ButtonType.OK);
        alert.showAndWait();
    }

    private void showNonSelectionDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Please select source to delete.", ButtonType.OK);
        alert.show();
    }

    private void buildBankStatementSourceAndSave(String path, String providerName, String filesExtension, SourceType type) {
        Provider provider = this.bankStatementService
                .getProviderByNameAndExtension(providerName, filesExtension);
        BankStatementSource newSource = BankStatementSource
                .builder()
                .provider(provider)
                .sourcePath(path)
                .type(type)
                .build();
        this.bankStatementService.addBankStatementSource(newSource);
        this.sourcesList.addBankStatementItemViewModel(new BankStatementSourceViewModel(newSource));

    }

    private void initData() {
        this.sourcesList = new BankStatementSourceListViewModel();
        List<BankStatementSource> allSources = this.bankStatementService.getAllBankStatementSources();
        allSources.forEach(source -> this.sourcesList.addBankStatementItemViewModel(new BankStatementSourceViewModel(source)));

    }

    public void setProvidersBoxes(List<String> providers) {
        ObservableList<String> observableProviderList = FXCollections.observableList(providers);
        this.urlProvidersBox.setItems(observableProviderList);
        this.directoryProvidersBox.setItems(observableProviderList);
        this.urlProvidersBox
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {
                    this.setProviderExtensionsBox(this.urlExtensionsBox, newValue);
                });
        this.directoryProvidersBox
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {
                    this.setProviderExtensionsBox(this.directoryExtensionsBox, newValue);
                });
    }

    private void setProviderExtensionsBox(ComboBox<String> box, String provider) {
        ObservableList<String> observableExtensionsList = FXCollections.observableList(this.bankStatementService.getProviderExtensions(provider));
        box.setItems(observableExtensionsList);
    }

    public void setBankStatementService(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
    }

    private void initializeDataTables() {
        directoryTable.itemsProperty().bindBidirectional(sourcesList.getDirectoryListProperty());
        urlTable.itemsProperty().bindBidirectional(sourcesList.getUrlListProperty());
        urlTablePathColumn.setCellValueFactory(dataValue -> dataValue.getValue().getPathProperty());
        urlTableBankNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getBankNameProperty());
        urlTableExtensionColumn.setCellValueFactory(dataValue -> dataValue.getValue().getExtensionProperty());
        directoryTablePathColumn.setCellValueFactory(dataValue -> dataValue.getValue().getPathProperty());
        directoryTableBankNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getBankNameProperty());
        directoryTableExtensionColumn.setCellValueFactory(dataValue -> dataValue.getValue().getExtensionProperty());
    }
}
