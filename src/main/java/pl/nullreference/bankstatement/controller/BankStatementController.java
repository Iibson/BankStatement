package pl.nullreference.bankstatement.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.nullreference.bankstatement.BankStatementService;
import pl.nullreference.bankstatement.BankstatementApplication;

import java.io.File;
import java.io.IOException;
public class BankStatementController {

    private final BankStatementService bankStatementService = new BankStatementService();
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Bank statements app");

            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BankstatementApplication.class.getResource("/view/BankStatementOverview.fxml"));
            BorderPane rootLayout = loader.load();

            // set initial data into controller
            BankStatementOverviewController controller = loader.getController();
            controller.setAppController(this);
            controller.setData(bankStatementService.getAllBankStatementItems());
            controller.setProviders(bankStatementService.getAllProviders());
            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }

    }
    public void showFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File x = fileChooser.showOpenDialog(this.primaryStage);
    }


}
