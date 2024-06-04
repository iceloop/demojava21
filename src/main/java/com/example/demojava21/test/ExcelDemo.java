package com.example.demojava21.test;

/**
 * ExcelDemo
 *
 * <p>创建人：hrniu 创建日期：2024/6/3
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDemo {
    public static void main(String[] args) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet dataSheet = workbook.createSheet("DataSheet");
        XSSFSheet mainSheet = workbook.createSheet("MainSheet");

        // 数据
        String[][] data = {
                {"省份", "市", "区", "县"},
                {"北京", "北京市", "东城区", "东华门街道"},
                {"北京", "北京市", "东城区", "景山街道"},
                {"北京", "北京市", "西城区", "西长安街道"},
                {"广东", "广州市", "越秀区", "北京街道"},
                {"广东", "广州市", "越秀区", "六榕街道"}
        };

        // 写入DataSheet
        int rowNum = 0;
        for (String[] rowData : data) {
            Row row = dataSheet.createRow(rowNum++);
            int colNum = 0;
            for (String field : rowData) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(field);
            }
        }

        // 自动调整列宽
        for (int i = 0; i < data[0].length; i++) {
            dataSheet.autoSizeColumn(i);
        }

        // 在MainSheet中添加标题
        Row headerRow = mainSheet.createRow(0);
        headerRow.createCell(0).setCellValue("省份");
        headerRow.createCell(1).setCellValue("市");
        headerRow.createCell(2).setCellValue("区");
        headerRow.createCell(3).setCellValue("县");

        // 创建命名范围
        Name provinces = workbook.createName();
        provinces.setNameName("Provinces");
        provinces.setRefersToFormula("DataSheet!$A$2:$A$6");

        Name beijing = workbook.createName();
        beijing.setNameName("Beijing");
        beijing.setRefersToFormula("DataSheet!$B$2:$B$4");

        Name guangdong = workbook.createName();
        guangdong.setNameName("Guangdong");
        guangdong.setRefersToFormula("DataSheet!$B$5:$B$6");

        Name beijingCity = workbook.createName();
        beijingCity.setNameName("北京市");
        beijingCity.setRefersToFormula("DataSheet!$C$2:$C$4");

        Name guangzhou = workbook.createName();
        guangzhou.setNameName("广州市");
        guangzhou.setRefersToFormula("DataSheet!$C$5:$C$6");

        Name dongcheng = workbook.createName();
        dongcheng.setNameName("东城区");
        dongcheng.setRefersToFormula("DataSheet!$D$2:$D$3");

        Name xicheng = workbook.createName();
        xicheng.setNameName("西城区");
        xicheng.setRefersToFormula("DataSheet!$D$4:$D$4");

        Name yuexiu = workbook.createName();
        yuexiu.setNameName("越秀区");
        yuexiu.setRefersToFormula("DataSheet!$D$5:$D$6");

        // 创建数据验证
        createDataValidation(mainSheet, "A2:A100", "Provinces", 1, 0);
        createDataValidation(mainSheet, "B2:B100", "INDIRECT($A2)", 1, 1);
        createDataValidation(mainSheet, "C2:C100", "INDIRECT($B2)", 1, 2);
        createDataValidation(mainSheet, "D2:D100", "INDIRECT($C2)", 1, 3);

        // 生成Excel文件
        try (FileOutputStream outputStream = new FileOutputStream("LocationDataWithValidation.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel 文件已生成！");
    }

    private static void createDataValidation(Sheet sheet, String range, String formula, int firstRow, int firstCol) {
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(formula);
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, firstRow + 99, firstCol, firstCol);
        DataValidation validation = validationHelper.createValidation(constraint, addressList);
        validation.setSuppressDropDownArrow(true);
        sheet.addValidationData(validation);
    }


}
