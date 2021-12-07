package pl.nullreference.bankstatement.deserializer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.nullreference.bankstatement.deserializer.base.BaseDeserializer;
import pl.nullreference.bankstatement.deserializer.staticData.SettingsData;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class XlsxDeserializer extends BaseDeserializer implements Deserializer {

    private final int linesToSkip;

    public XlsxDeserializer(Provider provider, File file) {
        super(provider, file);

        this.linesToSkip = Integer.parseInt(this.settings.get(SettingsData.skipLines));
    }

    @Override
    public BankStatement deserialize() {
        List<BankStatementItem> items = new ArrayList<>();

        try (FileInputStream stream = new FileInputStream(this.file); Workbook workbook = new XSSFWorkbook(stream)) {
            iterateOverSheet(workbook.getSheetAt(0), items);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BankStatement.builder()
                .items(items)
                .build();
    }

    private void iterateOverSheet(Sheet sheet, List<BankStatementItem> items) {
        for (int i = this.linesToSkip; i < sheet.getLastRowNum() + 1; i++) {
            try {
                items.add(createBankStatementItemFromMetaData(iterateOverRow(sheet.getRow(i)).toArray(String[]::new)));
            } catch (Exception ignored) {
            }
        }
    }

    private List<String> iterateOverRow(Row row) {
        List<String> line = new ArrayList<>();
        for (int j = 0; j < row.getLastCellNum(); j++) {
            Cell cell = row.getCell(j);
            if (cell == null) {
                line.add("");
                continue;
            }
            switch (cell.getCellType()) {
                case STRING -> line.add(cell.getStringCellValue());
                case NUMERIC -> line.add(String.valueOf(cell.getNumericCellValue()));
                default -> line.add("");
            }
        }
        return line;
    }
}
