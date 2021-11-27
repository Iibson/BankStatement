package pl.nullreference.bankstatement.deserializer;

import pl.nullreference.bankstatement.deserializer.base.BaseDeserializer;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.provider.Provider;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

    @Override
    public BankStatement deserialize() {
        List<BankStatementItem> items = new ArrayList<>();

        try {
            for (String[] line: csvReader.readAll()) {
                items.add(new BankStatementItem(line, this.mappings));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                this.csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return BankStatement.builder()
                .items(items)
                .build();
    }
}
