package pl.nullreference.bankstatement.sourceObserver.handler;

import javafx.util.Pair;
import org.apache.commons.lang3.NotImplementedException;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.model.source.SourceType;
import pl.nullreference.bankstatement.sourceObserver.SourceObserver;
import pl.nullreference.bankstatement.sourceObserver.dto.SourceObserverResultDto;
import pl.nullreference.bankstatement.sourceObserver.rest.RestSourceObserver;
import rx.Observable;
import java.util.*;
import java.util.stream.Collectors;

public class SourceObserverHandler {

    private final Map<SourceType, SourceObserver> observers;

    public SourceObserverHandler(List<BankStatementSource> bankStatementSources) {
        this.observers = initializeObservers(bankStatementSources);
    }

    public void addBankStatementSource(BankStatementSource bankStatementSource) {
        observers.get(bankStatementSource.getType())
                .addBankStatementSource(bankStatementSource);
    }

    public Observable<SourceObserverResultDto> getFilesAsObservable() {
        return Observable.merge(
                observers.values()
                        .stream()
                        .map(SourceObserver::getSourceObservable)
                        .collect(Collectors.toList()));
    }

    public void refreshSourceObserves() {
        observers.values()
                .forEach(SourceObserver::refresh);
    }

    public void resetList(List<BankStatementSource> bankStatementSources) {
        bankStatementSources.stream()
                .collect(Collectors.groupingBy(BankStatementSource::getType))
                .forEach((key, value) -> observers.get(key).resetList(value));

    }

    private Map<SourceType, SourceObserver> initializeObservers(List<BankStatementSource> bankStatementSources) {
        return bankStatementSources
                .stream()
                .collect(Collectors.groupingBy(BankStatementSource::getType))
                .entrySet()
                .stream()
                .map(x -> new Pair<>(x.getKey(), createSourceObserver(x.getKey(), x.getValue())))
                .filter(x -> x.getValue() != null)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private SourceObserver createSourceObserver(SourceType type, List<BankStatementSource> bankStatementSources) {
        return switch (type) {
            case ENDPOINT -> new RestSourceObserver(bankStatementSources);
            case DIRECTORY -> null;
        };
    }

}
