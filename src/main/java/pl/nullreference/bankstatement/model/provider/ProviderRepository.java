package pl.nullreference.bankstatement.model.provider;

import org.springframework.data.repository.CrudRepository;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {

    Provider findByNameAndExtension(String name, String extension);
}
