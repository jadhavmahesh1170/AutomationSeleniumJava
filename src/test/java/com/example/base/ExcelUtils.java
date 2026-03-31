package com.example.base;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class ExcelUtils {

    public static Stream<Arguments> getExcelData(String filePath, String sheetName) {
        List<Arguments> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter(); // <--- The Magic Wand

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip empty rows

                // formatCellValue() returns a String regardless of cell type
                String browser = (row.getCell(0) == null) ? "" : formatter.formatCellValue(row.getCell(0));
                String user = (row.getCell(1) == null) ? "" : formatter.formatCellValue(row.getCell(1));
                String pass = (row.getCell(2) == null) ? "" : formatter.formatCellValue(row.getCell(2));
                String age  = (row.getCell(3) == null) ? "" : formatter.formatCellValue(row.getCell(3)); // Even if it's a number

                data.add(Arguments.of(browser,user, pass, age));
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read Excel file: " + e.getMessage());
        }
        return data.stream();
    }
}
