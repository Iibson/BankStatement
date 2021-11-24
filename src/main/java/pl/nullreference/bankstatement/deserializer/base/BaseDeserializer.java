package pl.nullreference.bankstatement.deserializer.base;

import pl.nullreference.bankstatement.deserializer.IDeserializer;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.io.File;
import java.util.Map;

public abstract class BaseDeserializer implements IDeserializer {

    protected File file;
    protected Map<String, String> settings;
    protected Map<Integer, String> mappings;

    public BaseDeserializer(Provider provider, File file) {
        this.file = file;
        this.settings = provider.getSettingsAsMap();
        this.mappings = provider.getMappingValuesAsMap();
    }
}
