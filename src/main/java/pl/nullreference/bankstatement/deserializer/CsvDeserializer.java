package pl.nullreference.bankstatement.deserializer;

import lombok.SneakyThrows;
import pl.nullreference.bankstatement.deserializer.base.BaseDeserializer;
import pl.nullreference.bankstatement.model.BankStatement;
import pl.nullreference.bankstatement.model.BankStatementItem;
import pl.nullreference.bankstatement.model.provider.Provider;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CsvDeserializer extends BaseDeserializer implements IDeserializer {

    private final CSVReader csvReader;

    public CsvDeserializer(Provider provider, File file) throws Exception {
        super(provider, file);

        String separator = this.settings.get("separator");
        String skipLines = this.settings.get("skipLines");

        if (separator == null || skipLines == null) throw new Exception();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator.charAt(0))
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreQuotations(true)
                .build();

        this.csvReader = new CSVReaderBuilder(new FileReader(this.file))
                .withCSVParser(parser)
                .withSkipLines(Integer.parseInt(skipLines))
                .build();
    }

    @SneakyThrows
    @Override
    public BankStatement deserialize() {
        Set<BankStatementItem> items = new HashSet<>();

        List<String[]> parsedData = csvReader.readAll();
        for (String[] line : parsedData) {
            try {
                items.add(createBankStatementItemFromMetaData(line));
            } catch (Exception ignored) {
            }
        }

        this.csvReader.close();
        return BankStatement.builder()
                .items(items)
                .build();
    }
}
