package pl.nullreference.bankstatement.services.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;

import java.util.Date;
import java.util.List;

public interface BankStatementItemRepository extends CrudRepository<BankStatementItem, Integer> {

    List<BankStatementItem> findByOperationDateBetween(Date startingDate, Date endingDate);
    
}
