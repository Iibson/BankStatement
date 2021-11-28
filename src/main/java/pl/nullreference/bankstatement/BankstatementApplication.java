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
				BankStatementItem.builder()
						.operationDate(new Date())
						.operationDescription("operacja1")
						.cardAccountNumber("1234123412341234")
						.sum(123.23)
						.currency("PLN")
						.balance(420.21)
						.build(),
				BankStatementItem.builder()
						.operationDate(new Date())
						.operationDescription("operacja2")
						.cardAccountNumber("432143214321")
						.sum(112.23)
						.currency("PLN")
						.balance(110.21)
						.build()
		));
		System.out.println("Items: " + bankStatementsItemFixed);

		BankStatement bankStatementFixed = BankStatement.builder()
				.date(new Date())
				.beginningbalance(21.12)
				.endbalance(24.24)
				.bankname("AliorBank")
				.items(bankStatementsItemFixed)
				.build();

		System.out.println("BankStatement:" + bankStatementFixed);
		bankStatementService.addBankStatement(bankStatementFixed);
	}
}
