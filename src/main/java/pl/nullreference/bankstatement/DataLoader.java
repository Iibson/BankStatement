package pl.nullreference.bankstatement;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.seed.ProviderSeed;
import pl.nullreference.bankstatement.services.repositories.ProviderRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProviderRepository providerRepository;

    @Override
    public void run(String... args) {
        if(providerRepository.count() != 0) return;
        List<Provider> providers = ProviderSeed.getProviderSeed();
        this.providerRepository.saveAll(providers);
    }
}
