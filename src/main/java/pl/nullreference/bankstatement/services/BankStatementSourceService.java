package pl.nullreference.bankstatement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.model.source.SourceType;
import pl.nullreference.bankstatement.services.repositories.BankStatementSourceRepository;
import pl.nullreference.bankstatement.sourceObserver.SourceObserver;
import pl.nullreference.bankstatement.sourceObserver.dto.SourceObserverResultDto;
import pl.nullreference.bankstatement.sourceObserver.handler.SourceObserverHandler;
import rx.Observable;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankStatementSourceService {

    private final BankStatementSourceRepository bankStatementSourceRepository;
    private final SourceObserverHandler handler;

    public BankStatementSourceService(BankStatementSourceRepository bankStatementSourceRepository) {
        this.bankStatementSourceRepository = bankStatementSourceRepository;
        this.handler = new SourceObserverHandler(getAllBanksStatementSources());
    }

    public Observable<SourceObserverResultDto> getFilesAsObservable() {
        return handler.getFilesAsObservable();
    }

    public void refreshSourceObserves() {
        handler.refreshSourceObserves();
    }

    public void resetObservedBankStatementSources() {
        handler.resetList(getAllBanksStatementSources());
    }

    @Transactional
    public void addBankStatementSource(BankStatementSource bankStatementSource) {
        var source = bankStatementSourceRepository.save(bankStatementSource);
        handler.addBankStatementSource(source);
    }

    public List<BankStatementSource> getAllBanksStatementSources() {
        return (List<BankStatementSource>) bankStatementSourceRepository.findAll();
    }
}
