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

    public List<BankStatementItem> getAllBankStatementItems() {
        BankStatementItem item1 = new BankStatementItem();
        BankStatementItem item2 = new BankStatementItem();
        List<BankStatementItem> list = new ArrayList<>();
        item1.setCardAccountNumber("123456789");
        item1.setOperationDescription("description 1");
        item1.setSum(123.456);
        item1.setOperationDate(new Date());
        item1.setBalance(432.1);
        item1.setCurrency("PLN");
        list.add(item1);
        item2.setCardAccountNumber("123456789");
        item2.setOperationDescription("description 1");
        item2.setSum(123.456);
        item2.setOperationDate(new Date());
        item2.setBalance(432.1);
        item2.setCurrency("PLN");
        list.add(item2);
        return list;

    }

    public List<String> getAllProviders() {
        List<String> list = new ArrayList<>();
        list.add("ING");
        list.add("Millennium");
        return list;
    }
}
