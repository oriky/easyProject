package com.zhousj.common.ext.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

/**
 * @author zhousj
 * @date 2021/6/25
 */
@SuppressWarnings("unused")
public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    private static final int MAX_ROW_NUM = 65000;


    @SafeVarargs
    public static <T> void createExcel(List<T> list, String[] title, String filePath, Function<T, ?>... values) {
        createExcel(list, null, title, filePath, values);
    }

    @SafeVarargs
    public static <T> void createExcel(List<T> list, String[] title, int splitSize, String filePath, Function<T, ?>... values) {
        createExcel(list, null, title, null, splitSize, filePath, values);
    }


    @SafeVarargs
    public static <T> void createExcel(List<T> list, String sheetName, String[] title, String filePath, Function<T, ?>... values) {
        createExcel(list, sheetName, title, null, MAX_ROW_NUM, filePath, values);
    }


    @SafeVarargs
    public static <T> void createExcel(List<T> list, String[] title, int[] tileWidth, String filePath, Function<T, ?>... values) {
        createExcel(list, null, title, tileWidth, MAX_ROW_NUM, filePath, values);
    }

    @SafeVarargs
    public synchronized static <T> void createExcel(List<T> list, String sheetName, String[] title, int[] titleWidth, int splitSize, String filePath, Function<T, ?>... values) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet;
        OutputStream os = null;
        if (StringUtil.isEmpty(sheetName)) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(sheetName);
        }
        try {
            createHead(workbook, sheet, title, titleWidth);
            create(list, workbook, sheet, title, titleWidth, splitSize, values);
            Path path = Paths.get(filePath);
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            os = new FileOutputStream(path.toFile());
            workbook.write(os);
        } catch (IOException e) {
            LOGGER.error("创建excel失败，失败原因：{}", e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                workbook.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }


    public synchronized static <T> void createExcel(ExcelCreator<T> excelCreator) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet;
        OutputStream os = null;
        if (StringUtil.isEmpty(excelCreator.getSheetName())) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(excelCreator.getSheetName());
        }
        try {
            createHead(workbook, sheet, excelCreator);
            create(workbook, sheet, excelCreator);
            Path path = Paths.get(excelCreator.getFilePath());
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            os = new FileOutputStream(path.toFile());
            workbook.write(os);
        } catch (IOException e) {
            LOGGER.error("创建excel失败，失败原因：{}", e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                workbook.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }


    @SafeVarargs
    protected static <T> void create(List<T> list, HSSFWorkbook workbook, HSSFSheet sheet, String[] title, int[] titleWidth, Function<T, ?>... values) {
        create(list, workbook, sheet, title, titleWidth, MAX_ROW_NUM, values);
    }

    protected static <T> void create(HSSFWorkbook workbook, HSSFSheet sheet, ExcelCreator<T> excelCreator) {
        int splitSize = excelCreator.getSplitSize();
        if (splitSize > MAX_ROW_NUM) {
            throw new IllegalArgumentException("sheet页行数不能超过：" + MAX_ROW_NUM);
        }
        if (splitSize <= 0) {
            splitSize = MAX_ROW_NUM;
        }
        List<List<T>> lists = ListUtil.splitList(excelCreator.getData(), splitSize);
        for (int i = 0; i < lists.size(); i++) {
            if (i == 0) {
                createRow(lists.get(i), workbook, sheet, excelCreator.getValues());
            } else {
                HSSFSheet hssfSheet = workbook.createSheet(sheet.getSheetName() + "_" + (i + 1));
                createHead(workbook, hssfSheet, excelCreator);
                createRow(lists.get(i), workbook, hssfSheet, excelCreator.getValues());
            }
        }
    }


    @SafeVarargs
    protected static <T> void create(List<T> list, HSSFWorkbook workbook, HSSFSheet sheet, String[] title, int[] titleWidth, int splitSize, Function<T, ?>... values) {
        if (splitSize > MAX_ROW_NUM) {
            throw new IllegalArgumentException("sheet页行数不能超过：" + MAX_ROW_NUM);
        }
        if (splitSize == 0) {
            splitSize = MAX_ROW_NUM;
        }
        List<List<T>> lists = ListUtil.splitList(list, splitSize);
        for (int i = 0; i < lists.size(); i++) {
            if (i == 0) {
                createRow(lists.get(i), workbook, sheet, values);
            } else {
                HSSFSheet hssfSheet = workbook.createSheet(sheet.getSheetName() + "_" + (i + 1));
                createHead(workbook, hssfSheet, title, titleWidth);
                createRow(lists.get(i), workbook, hssfSheet, values);
            }
        }
    }


    @SafeVarargs
    protected static <T> void createRow(List<T> list, HSSFWorkbook workbook, HSSFSheet sheet, Function<T, ?>... values) {
        int rowNum = 1;
        CellStyle cellStyle = commonCellStyle(workbook);
        for (T data : list) {
            int columnNum = 0;
            HSSFRow row = sheet.createRow(rowNum++);
            for (Function<T, ?> function : values) {
                Object value = function.apply(data);
                createCommonCell(row, columnNum++, String.valueOf(value), cellStyle);
            }
        }
    }


    protected static void createHead(HSSFWorkbook workbook, HSSFSheet sheet, String[] title) {
        createHead(workbook, sheet, title, null);
    }

    protected static <E> void createHead(HSSFWorkbook workbook, HSSFSheet sheet, ExcelCreator<E> excelCreator) {
        CellStyle cellType = headCellStyle(workbook);
        // 设置表头
        Row headRow = sheet.createRow(0);
        for (int i = 0; i < excelCreator.getTitle().length; i++) {
            Cell cell = headRow.createCell(i);
            cell.setCellStyle(cellType);
            cell.setCellValue(excelCreator.getTitle()[i]);
            if (excelCreator.getTitleWidth() != null && excelCreator.getTitleWidth().length > 0) {
                sheet.setColumnWidth(i, excelCreator.getTitleWidth()[i]);
            } else {
                sheet.autoSizeColumn(i + 1, true);
            }
        }
    }


    protected static void createHead(HSSFWorkbook workbook, HSSFSheet sheet, String[] title, int[] columnWidth) {
        CellStyle cellType = headCellStyle(workbook);
        // 设置表头
        Row headRow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            Cell cell = headRow.createCell(i);
            cell.setCellStyle(cellType);
            cell.setCellValue(title[i]);
            if (columnWidth != null && columnWidth.length > 0) {
                sheet.setColumnWidth(i, columnWidth[i]);
            } else {
                sheet.autoSizeColumn(i + 1, true);
            }
        }
    }

    public static CellStyle headCellStyle(HSSFWorkbook workbook) {
        Font headFont = workbook.createFont();
        headFont.setBold(true);
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setLocked(true);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setFont(headFont);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setLocked(true);
        headStyle.setWrapText(true);
        return headStyle;
    }

    public static CellStyle commonCellStyle(HSSFWorkbook workbook) {
        Font headFont = workbook.createFont();
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setLocked(true);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setFont(headFont);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setLocked(true);
        headStyle.setWrapText(true);
        return headStyle;
    }


    public static void createCommonCell(HSSFRow row, int index, String value, CellStyle cellStyle) {
        HSSFCell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        if (value != null) {
            cell.setCellValue(value);
        }
    }

    public static void createCommonCell(HSSFRow row, int index, double value, CellStyle cellStyle) {
        HSSFCell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }


    static class UserLog {
        private String userName;

        private int age;

        private String code;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
