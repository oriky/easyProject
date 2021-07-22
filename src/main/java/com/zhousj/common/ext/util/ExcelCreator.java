package com.zhousj.common.ext.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

/**
 * @author zhousj
 * @date 2021/6/28
 */
@SuppressWarnings("unused")
public class ExcelCreator<E> {

    private List<E> data;

    private String sheetName;

    private String filePath;

    private int splitSize;

    private String[] title;

    private int[] titleWidth;

    private Function<E, ?>[] values;

    private CellStyle headStyle;

    private CellStyle commonStyle;


    private ExcelCreator() {
    }

    public ExcelCreator(ExcelBuilder<E> excelBuilder) {
        this.data = excelBuilder.data;
        this.filePath = excelBuilder.filePath;
        this.sheetName = excelBuilder.sheetName;
        this.splitSize = excelBuilder.splitSize;
        this.title = excelBuilder.title;
        this.titleWidth = excelBuilder.titleWidth;
        this.values = excelBuilder.values;
        this.headStyle = excelBuilder.headStyle;
        this.commonStyle = excelBuilder.commonStyle;
    }

    public void generate() {
        generate(this.filePath);
    }

    public void generate(String filePath) {
        this.filePath = filePath;
        Assert.notNull(this.data, "填充数据不能为空.");
        Assert.notNull(this.filePath, "文件路径不能为空.");
        Assert.notNull(this.title, "excel表头不能为空.");
        Assert.notNull(this.values, "excel字段取值function不能为空.");
        ExcelUtil.createExcel(this);
    }

    public List<E> getData() {
        return data;
    }

    public String getSheetName() {
        return sheetName;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getSplitSize() {
        return splitSize;
    }

    public String[] getTitle() {
        return title;
    }

    public int[] getTitleWidth() {
        return titleWidth;
    }

    public Function<E, ?>[] getValues() {
        return values;
    }

    public CellStyle getHeadStyle() {
        return headStyle;
    }

    public CellStyle getCommonStyle() {
        return commonStyle;
    }

    public static class ExcelBuilder<E> {

        private List<E> data;

        private String sheetName;

        private String filePath;

        private int splitSize;

        private String[] title;

        private int[] titleWidth;

        private Function<E, ?>[] values;

        private CellStyle headStyle;

        private CellStyle commonStyle;

        public ExcelBuilder() {
        }


        public ExcelBuilder<E> data(List<E> data) {
            this.data = data;
            return this;
        }

        public ExcelBuilder<E> sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public ExcelBuilder<E> filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public ExcelBuilder<E> splitSize(int splitSize) {
            this.splitSize = splitSize;
            return this;
        }

        public ExcelBuilder<E> title(String[] title) {
            this.title = title;
            return this;
        }

        @SafeVarargs
        public final ExcelBuilder<E> values(Function<E, ?>... values) {
            this.values = values;
            return this;
        }

        public ExcelBuilder<E> headStyle(CellStyle headStyle) {
            this.headStyle = headStyle;
            return this;
        }

        public ExcelBuilder<E> commonStyle(CellStyle commonStyle) {
            this.commonStyle = commonStyle;
            return this;
        }

        public ExcelCreator<E> build() {
            return new ExcelCreator<>(this);
        }
    }

}
