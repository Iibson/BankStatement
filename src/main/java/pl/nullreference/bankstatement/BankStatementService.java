package pl.nullreference.bankstatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BankStatementService {

    @Autowired
    private BankStatementRepository bankStatementRepository;

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
