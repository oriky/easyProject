package com.zhousj.common.pojo;

/**
 * @author zhousj
 * @date 2021/2/7
 */
@SuppressWarnings("unused")
public class SimplePage {

    private int pageSize;

    private int pageNum;

    public SimplePage(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public static SimplePage of() {
        return of(1, 20);
    }

    public static SimplePage of(int pageSize, int pageNum) {
        return new SimplePage(pageSize, pageNum);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
