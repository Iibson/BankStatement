package pl.nullreference.bankstatement.sourceObserver;

import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.sourceObserver.dto.SourceObserverResultDto;
import rx.Observable;

import java.util.List;

public interface SourceObserver {
    Observable<SourceObserverResultDto> getSourceObservable();
    void addBankStatementSource(BankStatementSource bankStatementSource);
    void resetList(List<BankStatementSource> bankStatementSources);
    void refresh();
}
