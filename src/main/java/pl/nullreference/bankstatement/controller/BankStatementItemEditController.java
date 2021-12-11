package pl.nullreference.bankstatement.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
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


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBankStatementService(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
    }

    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }

    @FXML
    private void handleOkAction() {
        // validate() needed
        updateModel();
        dialogStage.close();
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
