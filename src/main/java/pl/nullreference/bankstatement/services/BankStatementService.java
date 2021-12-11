package pl.nullreference.bankstatement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nullreference.bankstatement.deserializer.Deserializer;
import pl.nullreference.bankstatement.deserializer.factory.DeserializerFactory;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.services.repositories.BankStatementItemRepository;
import pl.nullreference.bankstatement.services.repositories.BankStatementRepository;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.services.repositories.ProviderRepository;
import pl.nullreference.bankstatement.viewmodel.BankStatementItemViewModel;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BankStatementService {

    private final BankStatementRepository bankStatementRepository;

    private final BankStatementItemRepository bankStatementItemRepository;

    private final ProviderRepository providerRepository;

    private final DeserializerFactory deserializerFactory;

    public List<BankStatement> getAllBankStatements() {
        List<BankStatement> bankStatements = new ArrayList<>();
        bankStatementRepository.findAll()
                .forEach(bankStatements::add);
        return bankStatements;
    }

    @Transactional
    public BankStatement parseAndSave(File file, String bankName) {
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        Provider provider = providerRepository.findByNameAndExtension(bankName, extension);
        try{
            Deserializer deserializer = deserializerFactory.getDeserializer(provider, file);
            BankStatement bankStatement = deserializer.deserialize();
            return bankStatementRepository.save(bankStatement);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateBankStatementItem(BankStatementItemViewModel item) {
        Optional<BankStatementItem> bankStatementItem = bankStatementItemRepository.findById(item.getId());
        bankStatementItem.ifPresent(itemDB -> {
            itemDB.setOperationDescription(item.getOperationDescription());
            itemDB.setCardAccountNumber(item.getCardAccountNumber());
            itemDB.setSum(item.getSum());
            itemDB.setCurrency(item.getCurrency());
            itemDB.setBalance(item.getBalance());
            bankStatementItemRepository.save(itemDB);
        });
    }

    public List<String> getAllProviders() {
        return StreamSupport.stream(providerRepository.findAll().spliterator(), false)
                .map(Provider::getName)
                .distinct()
                .toList();
    }
}
