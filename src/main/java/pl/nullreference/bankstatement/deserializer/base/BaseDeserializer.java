package pl.nullreference.bankstatement.deserializer.base;

import pl.nullreference.bankstatement.deserializer.IDeserializer;
import pl.nullreference.bankstatement.model.BankStatementItem;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseDeserializer implements IDeserializer {

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
                .sum(Double.parseDouble(data[this.mappings.get("sum")].replace(',', '.')))
                .cardAccountNumber(data[this.mappings.get("cardAccountNumber")])
                .currency(data[this.mappings.get("currency")])
                .balance(Double.parseDouble(data[this.mappings.get("balance")].replace(',', '.')))
                .operationDate(new SimpleDateFormat(this.settings.get("dateFormat")).parse(data[this.mappings.get("date")]))
                .operationDescription(data[this.mappings.get("operationDescription")])
                .build();
    }
}
