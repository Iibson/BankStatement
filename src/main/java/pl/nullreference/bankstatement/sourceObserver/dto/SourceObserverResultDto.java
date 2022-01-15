package pl.nullreference.bankstatement.sourceObserver.dto;

import lombok.Builder;
import lombok.Getter;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.io.File;

@Builder
@Getter
public class SourceObserverResultDto {
    private Provider provider;
    private File file;
}
