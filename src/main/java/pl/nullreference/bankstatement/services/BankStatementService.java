package pl.nullreference.bankstatement.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nullreference.bankstatement.deserializer.Deserializer;
import pl.nullreference.bankstatement.deserializer.factory.DeserializerFactory;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.services.repositories.BankStatementItemRepository;
import pl.nullreference.bankstatement.services.repositories.BankStatementRepository;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.services.repositories.ProviderRepository;
import pl.nullreference.bankstatement.viewmodel.BankStatementItemViewModel;
import rx.Observable;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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

    private final BankStatementSourceService sourceService;

    public List<BankStatement> getAllBankStatements() {
        List<BankStatement> bankStatements = new ArrayList<>();
        bankStatementRepository.findAll()
                .forEach(bankStatements::add);
        return bankStatements;
    }
    public void addBankStatementSource(BankStatementSource newSource){
        this.sourceService.addBankStatementSource(newSource);
    }

    public Observable<BankStatement> getBankStatementsAsObservable() {
        return sourceService.getFilesAsObservable()
                .map(x -> parseAndSave(x.getFile(), x.getProvider().getName()));
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
    public BankStatement parseAndSave(File file, Provider provider) {
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
            itemDB.setCategory(item.getCategory());
            bankStatementItemRepository.save(itemDB);
        });
    }

    public List<String> getAllProviders() {
        return StreamSupport.stream(providerRepository.findAll().spliterator(), false)
                .map(Provider::getName)
                .distinct()
                .toList();
    }
    public Provider getProviderByNameAndExtension(String name, String extension){
        return this.providerRepository.findByNameAndExtension(name,extension);
    }

    public List<BankStatementItem> getBankStatementItemsBetweenDates(Date startingDate, Date endingDate) {
        return bankStatementItemRepository.findByOperationDateBetween(startingDate, endingDate);
    }
    public List<String> getProviderExtensions(String providerName){
        return StreamSupport.stream(providerRepository.findAll().spliterator(), false)
                .filter(p -> p.getName().equals(providerName))
                .map(Provider::getExtension)
                .distinct()
                .toList();
    }
    public List<BankStatementSource> getAllBankStatementSources(){
        return this.sourceService.getAllBanksStatementSources();
    }
    public void deleteBankStatementSource(String path){
        this.sourceService.deleteBankStatementSourcePath(path);
    }
    public void refreshSources(){
        this.sourceService.refreshSourceObserves();
    }
}
