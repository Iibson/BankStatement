package pl.nullreference.bankstatement.deserializer;

import pl.nullreference.bankstatement.model.BankStatement;
import pl.nullreference.bankstatement.model.provider.Provider;

import java.io.File;

public class XlsxDeserializer implements IDeserializer {

    private Provider provider;

    public XlsxDeserializer(Provider provider, File file) {
        this.provider = provider;
    }
    @Override
    public BankStatement deserialize() {
        return null;
    }
}
//    private final Provider provider;
//
//    public XLSXSerializer(Provider provider) {
//        this.provider = provider;
//    }
//
//    public List<ProofOfConceptModel> serialize(String fileName) {
//        try {
//            FileInputStream file = new FileInputStream(new File(fileName));
//            Workbook workbook = new XSSFWorkbook(file);
//            DataFormatter dataFormatter = new DataFormatter();
//            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
//            while(sheetIterator.hasNext()) {
//                Sheet sheet = sheetIterator.next();
//                System.out.println("Name: " + sheet.getSheetName());
////                sheet.
//                Iterator<Row> rowIterator = sheet.rowIterator();
//                while(rowIterator.hasNext()) {
//                    Row row = rowIterator.next();
//                    Iterator<Cell> cellIterator = row.cellIterator();
//                    while (cellIterator.hasNext()) {
//                        Cell cell = cellIterator.next();
//                        String cellValue = dataFormatter.formatCellValue(cell);
//                        System.out.println(cellValue);
//                    }
//                }
//            }
//            workbook.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }