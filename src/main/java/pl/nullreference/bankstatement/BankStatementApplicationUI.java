package pl.nullreference.bankstatement;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.controller.BankStatementController;

public class BankStatementApplicationUI extends Application {

    private BankStatementController appController;

    BankStatementService statementService = new BankStatementService();
    @Override
    public void start(Stage primaryStage) {
        this.appController = new BankStatementController();
        this.appController.setPrimaryStage(primaryStage);
        this.appController.initRootLayout();
    }
}
