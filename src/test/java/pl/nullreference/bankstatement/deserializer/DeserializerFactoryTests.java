package pl.nullreference.bankstatement.deserializer;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import pl.nullreference.bankstatement.deserializer.factory.DeserializerFactory;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.provider.ProviderSetting;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DeserializerFactoryTests {
    @Test
    public void testDeserializerFactory() throws Exception {

        DeserializerFactory deserializerFactory = new DeserializerFactory();
        Set<ProviderSetting> settings =  Set.of(
                new ProviderSetting(1, "separator", ","),
                new ProviderSetting(2, "skipLines", "1"),
                new ProviderSetting(3, "dateFormat", "yyyy-mm-dd")
        );
        Provider csvProvider = new Provider(1, "name", "csv", settings, new HashSet<>());
        Provider xlsxProvider = new Provider(1, "name", "xlsx", settings, new HashSet<>());

        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("wyciąg_milenium.csv")).toURI());

        Assert.isTrue(CsvDeserializer.class == deserializerFactory.getDeserializer(csvProvider, file).getClass());
        Assert.isTrue(XlsxDeserializer.class == deserializerFactory.getDeserializer(xlsxProvider, file).getClass());
    }
}
