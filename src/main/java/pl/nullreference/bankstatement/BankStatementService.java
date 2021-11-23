package pl.nullreference.bankstatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nullreference.bankstatement.model.BankStatement;
import pl.nullreference.bankstatement.model.BankStatementRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BankStatementService {

    @Autowired
    private BankStatementRepository bankStatementRepository;

    private List<BankStatement> bankStatementsFinal = new ArrayList<>(Arrays.asList(
            new BankStatement(1, new Date(), 1.23, "Millenium"),
            new BankStatement(2, new Date(), 2.23, "Millenium")
    ));

    public List<BankStatement> getAllBankStatements() {
        List<BankStatement> bankStatements = new ArrayList<>();
        bankStatementRepository.findAll()
                .forEach(bankStatements::add);
        return bankStatements;
    }

    public void addBankStatement(BankStatement bankStatement) {
        bankStatementRepository.save(bankStatement);
    }
}
