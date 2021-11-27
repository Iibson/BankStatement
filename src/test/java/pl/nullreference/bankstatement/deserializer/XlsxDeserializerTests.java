package pl.nullreference.bankstatement.deserializer;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import pl.nullreference.bankstatement.model.BankStatementItem;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.provider.ProviderMappingValue;
import pl.nullreference.bankstatement.model.provider.ProviderSetting;

import java.io.File;
import java.util.Objects;
import java.util.Set;

public class XlsxDeserializerTests {
    @Test
    public void testIngXlsxDeserializer() throws Exception {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("wyciag_ing.xlsx")).toURI());
        Set<ProviderSetting> settings =  Set.of(
                new ProviderSetting(2, "skipLines", "20"),
                new ProviderSetting(3, "dateFormat", "dd.mm.yyyy")
        );
        Set<ProviderMappingValue> mappingValues = Set.of(
                new ProviderMappingValue(1, 0, "date"),
                new ProviderMappingValue(1, 4, "cardAccountNumber"),
                new ProviderMappingValue(1, 8, "sum"),
                new ProviderMappingValue(1, 9, "currency"),
                new ProviderMappingValue(1, 15, "balance"),
                new ProviderMappingValue(1, 6, "operationDescription")
        );
        Provider provider = new Provider(1, "name", "xlsx", settings, mappingValues);

        XlsxDeserializer xlsxDeserializer = new XlsxDeserializer(provider, file);

        Set<BankStatementItem> items = xlsxDeserializer.deserialize().getItems();

        Assert.isTrue(items.size() == 91);
    }
    @Test
    public void testMillenniumXlsxDeserializer() throws Exception {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("wyciąg_milenium.xlsx")).toURI());
        Set<ProviderSetting> settings =  Set.of(
                new ProviderSetting(2, "skipLines", "1"),
                new ProviderSetting(3, "dateFormat", "yyyy-mm-dd")
        );
        Set<ProviderMappingValue> mappingValues = Set.of(
                new ProviderMappingValue(1, 1, "date"),
                new ProviderMappingValue(1, 0, "cardAccountNumber"),
                new ProviderMappingValue(1, 7, "sum"),
                new ProviderMappingValue(1, 10, "currency"),
                new ProviderMappingValue(1, 9, "balance"),
                new ProviderMappingValue(1, 6, "operationDescription")
        );
        Provider provider = new Provider(1, "name", "xlsx", settings, mappingValues);

        XlsxDeserializer xlsxDeserializer = new XlsxDeserializer(provider, file);

        Set<BankStatementItem> items = xlsxDeserializer.deserialize().getItems();

        Assert.isTrue(items.size() == 38);
    }
}
