package pl.nullreference.bankstatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BankstatementApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BankStatementService bankStatementService;

	public static void main(String[] args) {
		SpringApplication.run(BankstatementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<BankStatementItem> bankStatementsItemFixed = new ArrayList<>(Arrays.asList(
				new BankStatementItem(3, new Date(), "operacja1", "1234123412341234", 123.23, "PLN", 420.21),
				new BankStatementItem(4, new Date(), "operacja2", "432143214321", 321.23, "EUR", 123.21)
		));
		System.out.println("Items: " + bankStatementsItemFixed);
		BankStatement bankStatementFixed = new BankStatement(2, new Date(), 12.12, 24.24, "Alior Bank", bankStatementsItemFixed);

		System.out.println("BankStatement:" + bankStatementFixed);
		bankStatementService.addBankStatement(bankStatementFixed);
	}
}
