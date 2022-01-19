package pl.nullreference.bankstatement.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.model.source.SourceType;
import pl.nullreference.bankstatement.services.BankStatementService;

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

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

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
            this.chosenDirectoryPathField.setText(directory.getName());
        }
    }

    @FXML
    private void handleDirectoryConfirm() {
        this.buildBankStatementSourceAndSave(
                this.chosenDirectoryPathField.getText(),
                this.directoryProvidersBox.getValue(),
                this.directoryExtensionsBox.getValue(),
                SourceType.DIRECTORY);
        dialogStage.close();
    }

    @FXML
    private void handleUrlConfirm() {
        this.buildBankStatementSourceAndSave(
                this.urlField.getText(),
                this.urlProvidersBox.getValue(),
                this.urlExtensionsBox.getValue(),
                SourceType.ENDPOINT);
        dialogStage.close();
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
}
