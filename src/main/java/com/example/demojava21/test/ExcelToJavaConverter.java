package com.example.demojava21.test;

/**
 * ExcelToJavaConverter
 *
 * <p>创建人：hrniu 创建日期：2024/6/3
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelToJavaConverter {

    public static void convertExcelToJava(String excelFilePath, String outputJavaFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        List<List<String>> data = new ArrayList<>();
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(cell.toString());
            }
            data.add(rowData);
        }

        // 读取名称管理器
        List<String[]> namedRanges = new ArrayList<>();
        for (Name name : workbook.getAllNames()) {
            String nameName = name.getNameName();
            String refersToFormula = name.getRefersToFormula();
            namedRanges.add(new String[]{nameName, refersToFormula});
        }

        FileWriter writer = new FileWriter(outputJavaFilePath);
        writer.write("public class GeneratedExcelData {\n");
        writer.write("    public static String[][] getData() {\n");
        writer.write("        return new String[][] {\n");

        for (List<String> row : data) {
            writer.write("            {");
            for (String cell : row) {
                writer.write("\"" + cell + "\", ");
            }
            writer.write("},\n");
        }

        writer.write("        };\n");
        writer.write("    }\n");

        writer.write("    public static String[][] getNamedRanges() {\n");
        writer.write("        return new String[][] {\n");

        for (String[] namedRange : namedRanges) {
            writer.write("            {\"" + namedRange[0] + "\", \"" + namedRange[1] + "\"},\n");
        }

        writer.write("        };\n");
        writer.write("    }\n");

        writer.write("}\n");
        writer.close();

        workbook.close();
        fis.close();
    }



    public static void main(String[] args) throws IOException {
    convertExcelToJava(
        "C:\\Users\\87028\\Desktop\\aa.xlsx", "C:\\Users\\87028\\Desktop\\GeneratedExcelData.java");
    }
}

