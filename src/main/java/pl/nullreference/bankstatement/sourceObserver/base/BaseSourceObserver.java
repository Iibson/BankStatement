package pl.nullreference.bankstatement.sourceObserver.base;

import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.sourceObserver.SourceObserver;
import pl.nullreference.bankstatement.sourceObserver.dto.SourceObserverResultDto;
import rx.Observable;
import rx.subjects.ReplaySubject;

import java.util.List;

public abstract class BaseSourceObserver implements SourceObserver {
    protected final List<BankStatementSource> bankStatementSources;
    private final ReplaySubject<SourceObserverResultDto> sourceObserverResultDtoReplaySubject = ReplaySubject.create();


    public BaseSourceObserver(List<BankStatementSource> bankStatementSources) {
        this.bankStatementSources = bankStatementSources;
    }

    public void addBankStatementSource(BankStatementSource bankStatementSource) {
        bankStatementSources.add(bankStatementSource);
    }

    public void resetList(List<BankStatementSource> bankStatementSources) {
        this.bankStatementSources.clear();
        this.bankStatementSources.addAll(bankStatementSources);
    }

    @Override
    public Observable<SourceObserverResultDto> getSourceObservable() {
        return sourceObserverResultDtoReplaySubject.asObservable()
                .filter(dto -> dto.getFile() != null && dto.getFile().exists());
    }

    protected void notifySourceObserverSubject(SourceObserverResultDto sourceObserverResultDto) {
        sourceObserverResultDtoReplaySubject.onNext(sourceObserverResultDto);
    }
}
