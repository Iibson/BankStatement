package pl.nullreference.bankstatement.model.provider;

import org.springframework.data.repository.CrudRepository;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;

public interface ProviderRepository extends CrudRepository<BankStatement, Integer > {
}
