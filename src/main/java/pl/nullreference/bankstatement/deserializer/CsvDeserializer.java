package pl.nullreference.bankstatement.deserializer;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.SneakyThrows;
import pl.nullreference.bankstatement.deserializer.base.BaseDeserializer;
import pl.nullreference.bankstatement.deserializer.staticData.SettingsData;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvDeserializer extends BaseDeserializer implements Deserializer {

    private final CSVParser parser;
    private final int linesToSkip;

    public CsvDeserializer(Provider provider, File file) throws Exception {
        super(provider, file);

        String separator = this.settings.get(SettingsData.separator);
        String skipLines = this.settings.get(SettingsData.skipLines);

        if (separator == null || skipLines == null) throw new Exception();

        this.linesToSkip = Integer.parseInt(skipLines);
        this.parser = new CSVParserBuilder()
                .withSeparator(separator.charAt(0))
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreQuotations(true)
                .build();
    }

    @Override
    public BankStatement deserialize() {
        List<BankStatementItem> items = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(this.file))
                .withCSVParser(this.parser)
                .withSkipLines(this.linesToSkip)
                .build()) {

            List<String[]> parsedData = csvReader.readAll();
            for (String[] line : parsedData) {
                try {
                    items.add(createBankStatementItemFromMetaData(line));
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

            return BankStatement.builder()
                    .items(items)
                    .build();
        }
    }
