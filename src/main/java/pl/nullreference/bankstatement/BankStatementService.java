package pl.nullreference.bankstatement;

import lombok.SneakyThrows;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementRepository;
import pl.nullreference.bankstatement.deserializer.IDeserializer;
import pl.nullreference.bankstatement.deserializer.factory.DeserializerFactory;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.provider.ProviderMappingValue;
import pl.nullreference.bankstatement.model.provider.ProviderRepository;
import pl.nullreference.bankstatement.model.provider.ProviderSetting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BankStatementService {

    @Autowired
    private BankStatementRepository bankStatementRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private DeserializerFactory deserializerFactory;

    public List<BankStatement> getAllBankStatements() {
        List<BankStatement> bankStatements = new ArrayList<>();
        bankStatementRepository.findAll()
                .forEach(bankStatements::add);
        return bankStatements;
    }

    @SneakyThrows
    @Transactional
    public void parseAndSave(File file, String bankName) {
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        Provider provider = providerRepository.findByNameAndExtension(bankName, extension);
        IDeserializer deserializer = deserializerFactory.getDeserializer(provider, file);
        BankStatement bankStatement = deserializer.deserialize();
        bankStatementRepository.save(bankStatement);
    }

    @TestOnly
    public void addBankStatement(BankStatement bankStatement) {
        bankStatementRepository.save(bankStatement);
    }

    public List<String> getAllProviders() {
        List<String> list = new ArrayList<>();
        providerRepository.findAll().forEach(el -> {
            if (!list.contains(el.getName())) list.add(el.getName());
        });
        return list;
    }
}
