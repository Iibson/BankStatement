package pl.nullreference.bankstatement;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nullreference.bankstatement.deserializer.IDeserializer;
import pl.nullreference.bankstatement.deserializer.factory.DeserializerFactory;
import pl.nullreference.bankstatement.model.BankStatement;
import pl.nullreference.bankstatement.model.BankStatementRepository;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.provider.ProviderRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public void parseAndSave(File file, String bankName) {
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf("."));
        Provider provider = providerRepository.findByNameAndExtension(bankName, extension);
        IDeserializer deserializer = deserializerFactory.getDeserializer(provider, file);
        BankStatement bankStatement = deserializer.deserialize();
        bankStatementRepository.save(bankStatement);
    }
}
