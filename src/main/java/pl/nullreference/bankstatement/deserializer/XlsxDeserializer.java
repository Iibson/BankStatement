package pl.nullreference.bankstatement.deserializer;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.nullreference.bankstatement.deserializer.base.BaseDeserializer;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.bankstatement.BankStatementItem;
import pl.nullreference.bankstatement.model.bankstatement.BankStatement;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class XlsxDeserializer extends BaseDeserializer implements IDeserializer {

    private int linesToSkip;

    public XlsxDeserializer(Provider provider, File file) {
        super(provider, file);

        this.linesToSkip = Integer.parseInt(this.settings.get("skipLines"));
    }

    @Override
    public BankStatement deserialize() {
        Workbook workbook = null;
        FileInputStream stream = null;
        List<BankStatementItem> items = new ArrayList<>();

        try {
            stream = new FileInputStream(this.file);
            workbook = new XSSFWorkbook(stream);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = this.linesToSkip; i < sheet.getLastRowNum() + 1; i++) {
                List<String> line = new ArrayList<>();
                Row row = sheet.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        line.add("");
                        continue;
                    }
                    switch (cell.getCellType()) {
                        case STRING:
                            line.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            line.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        default:
                            line.add("");
                            break;
                    }
                }
                try {
                    items.add(createBankStatementItemFromMetaData(line.toArray(String[]::new)));
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert workbook != null;
                workbook.close();
                stream.close();
            } catch (IOException ignored) {
            }
        }

        return BankStatement.builder()
                .items(items)
                .build();
    }
}
