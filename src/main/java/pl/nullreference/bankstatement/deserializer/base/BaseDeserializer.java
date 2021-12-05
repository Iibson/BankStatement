package pl.nullreference.bankstatement.deserializer.base;

import pl.nullreference.bankstatement.deserializer.Deserializer;
import pl.nullreference.bankstatement.deserializer.staticData.MappingValuesData;
import pl.nullreference.bankstatement.deserializer.staticData.SettingsData;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public abstract class BaseDeserializer implements Deserializer {

    protected File file;
    protected Map<String, String> settings;
    protected Map<String, Integer> mappings;

    public BaseDeserializer(Provider provider, File file) {
        this.file = file;
        this.settings = provider.getSettingsAsMap();
        this.mappings = provider.getMappingValuesAsMap();
    }

    protected BankStatementItem createBankStatementItemFromMetaData(String[] data) throws ParseException {
        return BankStatementItem.builder()
                .sum(Double.parseDouble(data[this.mappings.get(MappingValuesData.sum)].replace(',', '.')))
                .cardAccountNumber(data[this.mappings.get(MappingValuesData.cardAccountNumber)])
                .currency(data[this.mappings.get(MappingValuesData.currency)])
                .balance(Double.parseDouble(data[this.mappings.get(MappingValuesData.balance)].replace(',', '.')))
                .operationDate(new SimpleDateFormat(this.settings.get(SettingsData.dateFormat)).parse(data[this.mappings.get(MappingValuesData.date)]))
                .operationDescription(data[this.mappings.get(MappingValuesData.operationDescription)])
                .build();
    }
}
