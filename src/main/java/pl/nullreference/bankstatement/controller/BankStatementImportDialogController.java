package pl.nullreference.bankstatement.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.BankStatementService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class BankStatementImportDialogController {

    private Stage dialogStage;
    private File loadedFile;
    private File confirmedFile;
    private String confirmedBankName;
    @FXML
    private TextField fileName;
    @FXML
    private ComboBox<String> providersBox;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProvidersBox(List<String> providers) {
        ObservableList<String> observableProviderList = FXCollections.observableList(providers);
        this.providersBox.setItems(observableProviderList);
    }

    @FXML
    private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(dialogStage);
        if (file != null) {
            this.fileName.setText(file.getName());
            this.loadedFile = file;
        }
    }

    @FXML
    private void handleConfirm() {
        this.confirmedFile = this.loadedFile;
        this.confirmedBankName = this.providersBox.getValue();
        this.dialogStage.close();
    }

    public File getConfirmedFile() {
        return this.confirmedFile;
    }

    public String getConfirmedBankName() {
        return this.confirmedBankName;
    }

}
