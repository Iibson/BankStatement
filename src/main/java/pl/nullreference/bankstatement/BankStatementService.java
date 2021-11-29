package pl.nullreference.bankstatement;

import lombok.SneakyThrows;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @TestOnly
    public void addBankStatement(BankStatement bankStatement) {
        bankStatementRepository.save(bankStatement);
    }

    public void addProviders(){
        List<ProviderSetting> settings =  List.of(
                ProviderSetting.builder().name("separator").value(";").build(),
                ProviderSetting.builder().name("skipLines").value("20").build(),
                ProviderSetting.builder().name("dateFormat").value("dd.mm.yyyy").build()
        );
        List<ProviderMappingValue> mappingValues = List.of(
                ProviderMappingValue.builder().mapTo("date").mapFrom(0).build(),
                ProviderMappingValue.builder().mapTo("cardAccountNumber").mapFrom(4).build(),
                ProviderMappingValue.builder().mapTo("sum").mapFrom(8).build(),
                ProviderMappingValue.builder().mapTo("currency").mapFrom(9).build(),
                ProviderMappingValue.builder().mapTo("balance").mapFrom(15).build(),
                ProviderMappingValue.builder().mapTo("operationDescription").mapFrom(6).build()
        );
        Provider provider = Provider.builder().name("ING").extension("csv").settings(settings).mappingValues(mappingValues).build();
        providerRepository.save(provider);
    }
    public List<String> getAllProviders() {
        List<String> list = new ArrayList<>();
        list.add("ING");
        list.add("Millennium");
        return list;
    }
}
