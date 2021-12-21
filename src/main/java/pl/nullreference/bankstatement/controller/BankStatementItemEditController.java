package pl.nullreference.bankstatement.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import pl.nullreference.bankstatement.services.BankStatementService;
import pl.nullreference.bankstatement.viewmodel.BankStatementItemViewModel;

@RequiredArgsConstructor
public class BankStatementItemEditController {

    private BankStatementService bankStatementService;

    private BankStatementItemViewModel bankStatementItemViewModel;

    private Stage dialogStage;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField cardAccountNumberTextField;

    @FXML
    private TextField sumTextField;

    @FXML
    private TextField currencyTextField;

    @FXML
    private TextField balanceTextField;

    @FXML
    private Label errorMessageLabel;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBankStatementService(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
    }

    public boolean isTextFieldsValid() {
        if(!cardAccountNumberTextField.getText().matches("^[^a-zA-Z]*$")) {
            showErrorMessage("CardAccountNumber can only contain numbers.");
            return false;
        }

        if(!sumTextField.getText().matches("\\A[-]?[0-9]+[,.]?[0-9]*\\Z")) {
            showErrorMessage("Sum can only contain decimal number");
            return false;
        }


        if(!balanceTextField.getText().matches("\\A[-]?[0-9]+[,.]?[0-9]*\\Z")) {
            showErrorMessage("Balance can only contain decimal number");
            return false;
        }
        return true;
    }

    private void showErrorMessage(String errorMessage) {
        errorMessageLabel.setTextFill(Color.web("#FF0000"));
        errorMessageLabel.setText(errorMessage);
    }

    private void hideErrorMessage() {
        errorMessageLabel.setText("");
    }

    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }

    @FXML
    private void handleOkAction() {
        if (isTextFieldsValid()) {
            updateModel();
            hideErrorMessage();
            dialogStage.close();
        }
    }

    public void setData(BankStatementItemViewModel bankStatementItemViewModel) {
        this.bankStatementItemViewModel = bankStatementItemViewModel;
        updateControls();
    }

    private void updateModel() {
        bankStatementItemViewModel.setOperationDescription(descriptionTextField.getText());
        bankStatementItemViewModel.setCardAccountNumber(cardAccountNumberTextField.getText());
        bankStatementItemViewModel.setCurrency(currencyTextField.getText());
        bankStatementItemViewModel.setSum(Double.parseDouble(sumTextField.getText()));
        bankStatementItemViewModel.setBalance(Double.parseDouble(balanceTextField.getText()));
        updateDatabase();
    }

    private void updateDatabase() {
        bankStatementService.updateBankStatementItem(bankStatementItemViewModel);
    }

    private void updateControls() {
        descriptionTextField.setText(bankStatementItemViewModel.getOperationDescription());
        cardAccountNumberTextField.setText(bankStatementItemViewModel.getCardAccountNumber());
        sumTextField.setText(Double.toString(bankStatementItemViewModel.getSum()));
        currencyTextField.setText(bankStatementItemViewModel.getCurrency());
        balanceTextField.setText(Double.toString(bankStatementItemViewModel.getBalance()));
    }


}
