package pl.nullreference.bankstatement.services.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;

public interface BankStatementItemRepository extends CrudRepository<BankStatementItem, Integer> {
    
}
