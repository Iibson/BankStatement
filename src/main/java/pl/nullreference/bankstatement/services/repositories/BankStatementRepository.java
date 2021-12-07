package pl.nullreference.bankstatement.services.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;

public interface BankStatementRepository extends CrudRepository<BankStatement, Integer> {

}
