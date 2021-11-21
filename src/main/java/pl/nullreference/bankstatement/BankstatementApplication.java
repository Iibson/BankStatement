package pl.nullreference.bankstatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

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
		bankStatementService.getAllBankStatements()
				.forEach(System.out::println);
//		String sql = "INSERT INTO bankstatement (id, date, endbalance, bankname) VALUES (2, '2021-12-12', 4.20, 'Nest Bank')";
//		int rows = jdbcTemplate.update(sql);
//		if (rows > 0) {
//			System.out.println("A new row has been inserted.");
//		}
	}
}
