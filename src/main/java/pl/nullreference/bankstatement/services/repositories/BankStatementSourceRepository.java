package pl.nullreference.bankstatement.services.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.model.source.SourceType;

import java.util.List;

public interface BankStatementSourceRepository extends CrudRepository<BankStatementSource, Integer> {

    List<BankStatementSource> findByType(SourceType type);
}
