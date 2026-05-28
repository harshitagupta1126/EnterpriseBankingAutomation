package com.banking.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {

    /**
     * Reusable utility method that opens an Excel sheet and converts its entire matrix
     * into a 2D String Array object that TestNG DataProviders can understand.
     */
    public static String[][] getExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);

        int totalRows = sheet.getPhysicalNumberOfRows();
        int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();

        // We subtract 1 from rows because the first row contains headers (Username, Password)
        String[][] dataMatrix = new String[totalRows - 1][totalCols];
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i < totalRows; i++) { // Starting from 1 to skip header row
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < totalCols; j++) {
                XSSFCell cell = row.getCell(j);
                // Formatting data automatically ensures even numbers/passwords read as clean text
                dataMatrix[i - 1][j] = formatter.formatCellValue(cell);
            }
        }
        workbook.close();
        fileInputStream.close();
        return dataMatrix;
    }
}