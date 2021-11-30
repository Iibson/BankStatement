package pl.nullreference.bankstatement.deserializer.factory;

import org.springframework.stereotype.Service;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.deserializer.CsvDeserializer;
import pl.nullreference.bankstatement.deserializer.IDeserializer;
import pl.nullreference.bankstatement.deserializer.XlsxDeserializer;

import java.io.File;

@Service
public class DeserializerFactory {
    public IDeserializer getDeserializer(Provider provider, File file) throws Exception {
        switch (provider.getExtension().toUpperCase()) {
            case "XLSX":
                return new XlsxDeserializer(provider, file);
            case "CSV":
                return new CsvDeserializer(provider, file);
            default:
                throw new Exception("Invalid extension");
        }
    }
}
