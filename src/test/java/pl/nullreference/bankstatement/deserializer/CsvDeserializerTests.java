package pl.nullreference.bankstatement.deserializer;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.provider.ProviderMappingValue;
import pl.nullreference.bankstatement.model.provider.ProviderSetting;

import javax.persistence.OneToMany;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CsvDeserializerTests {

    @Test
    public void testIngCsvDeserializer() throws Exception {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("wyciag_ing.csv")).toURI());
        List<ProviderSetting> settings =  List.of(
                new ProviderSetting(1, "separator", ";"),
                new ProviderSetting(2, "skipLines", "20"),
                new ProviderSetting(3, "dateFormat", "dd.mm.yyyy")
        );
        List<ProviderMappingValue> mappingValues = List.of(
                new ProviderMappingValue(1, 0, "date"),
                new ProviderMappingValue(1, 4, "cardAccountNumber"),
                new ProviderMappingValue(1, 8, "sum"),
                new ProviderMappingValue(1, 9, "currency"),
                new ProviderMappingValue(1, 15, "balance"),
                new ProviderMappingValue(1, 6, "operationDescription")
        );
        Provider provider = new Provider(1, "name", "csv", settings, mappingValues);

        CsvDeserializer csvDeserializer = new CsvDeserializer(provider, file);

        List<BankStatementItem> items = csvDeserializer.deserialize().getItems();

        Assert.isTrue(items.size() == 91);
    }

    @Test
    public void testMillenniumCsvDeserializer() throws Exception {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("wyciąg_milenium.csv")).toURI());
        List<ProviderSetting> settings =  List.of(
                new ProviderSetting(1, "separator", ","),
                new ProviderSetting(2, "skipLines", "1"),
                new ProviderSetting(3, "dateFormat", "yyyy-mm-dd")
        );
        List<ProviderMappingValue> mappingValues = List.of(
                new ProviderMappingValue(1, 1, "date"),
                new ProviderMappingValue(1, 0, "cardAccountNumber"),
                new ProviderMappingValue(1, 7, "sum"),
                new ProviderMappingValue(1, 10, "currency"),
                new ProviderMappingValue(1, 9, "balance"),
                new ProviderMappingValue(1, 6, "operationDescription")
        );
        Provider provider = new Provider(1, "name", "csv", settings, mappingValues);

        CsvDeserializer csvDeserializer = new CsvDeserializer(provider, file);

        List<BankStatementItem> items = csvDeserializer.deserialize().getItems();

        Assert.isTrue(items.size() == 38);
    }
}
