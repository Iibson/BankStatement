package pl.nullreference.bankstatement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.model.source.SourceType;
import pl.nullreference.bankstatement.services.repositories.BankStatementSourceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankStatementSourceService {

    private final BankStatementSourceRepository bankStatementSourceRepository;

    @Transactional
    public void addBankStatementSource(BankStatementSource bankStatementSource) {
        bankStatementSourceRepository.save(bankStatementSource);
    }

    @Transactional
    public BankStatementSource addBankStatementSourceAndReturnIt(BankStatementSource bankStatementSource) {
        return bankStatementSourceRepository.save(bankStatementSource);
    }

    public List<BankStatementSource> getBankStatementSourcesForSourceType(SourceType type) {
        return bankStatementSourceRepository.findByType(type);
    }
}
