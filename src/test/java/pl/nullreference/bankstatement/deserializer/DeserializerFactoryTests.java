package pl.nullreference.bankstatement.deserializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.nullreference.bankstatement.deserializer.factory.DeserializerFactory;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.provider.ProviderSetting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.mock;

public class DeserializerFactoryTests {
    @Test
    public void DeserializerFactory() throws Exception {
        //given
        DeserializerFactory deserializerFactory = new DeserializerFactory();
        List<ProviderSetting> settings =  List.of(
                new ProviderSetting(1, "separator", ","),
                new ProviderSetting(2, "skipLines", "1"),
                new ProviderSetting(3, "dateFormat", "yyyy-mm-dd")
        );
        Provider csvProvider = new Provider(1, "name", "csv", settings, new ArrayList<>());
        Provider xlsxProvider = new Provider(1, "name", "xlsx", settings, new ArrayList<>());
        File file = mock(File.class);

        //when
        Deserializer csvDeserializer = deserializerFactory.getDeserializer(csvProvider, file);
        Deserializer xlsxDeserializer = deserializerFactory.getDeserializer(xlsxProvider, file);

        //then
        Assertions.assertEquals(CsvDeserializer.class, csvDeserializer.getClass());
        Assertions.assertEquals(XlsxDeserializer.class, xlsxDeserializer.getClass());
    }
}
