package pl.nullreference.bankstatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nullreference.bankstatement.model.BankStatement;
import pl.nullreference.bankstatement.model.BankStatementItem;
import pl.nullreference.bankstatement.model.BankStatementRepository;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BankStatementService {

    @Autowired
    private BankStatementRepository bankStatementRepository;

    private List<BankStatement> bankStatementsFinal = new ArrayList<>(Arrays.asList(
//            new BankStatement(1, new Date(), 1.23, "Millenium"),
//            new BankStatement(2, new Date(), 2.23, "Millenium")
    ));

    public List<BankStatement> getAllBankStatements() {
        List<BankStatement> bankStatements = new ArrayList<>();
        bankStatementRepository.findAll()
                .forEach(bankStatements::add);
        return bankStatements;
    }
    public List<BankStatementItem> getAllBankStatementItems() {
        BankStatementItem item1 = new BankStatementItem(1,new Date(),"DESCRIPTION XD", "2137",12.34,"PLN",321.0);
        BankStatementItem item2= new BankStatementItem(1,new Date(),"DESCRIPTION XDD", "2137",-43.21,"USD",123.5);
        List<BankStatementItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        return items;
    }

    public List<String> getAllProviders(){
        List<String> providers = new ArrayList<>();
        providers.add("Millennium");
        providers.add("ING");
        return providers;
    }

    public void addBankStatement(BankStatement bankStatement) {
        bankStatementRepository.save(bankStatement);
    }
}
