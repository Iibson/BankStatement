package pl.nullreference.bankstatement;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankstatementApplication {


	public static void main(String[] args) {

		Application.launch(BankStatementApplicationUI.class,args);

	}


}
