package pl.nullreference.bankstatement.services.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.nullreference.bankstatement.model.provider.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {

    Provider findByNameAndExtension(String name, String extension);

    Provider findByName(String name);
}
