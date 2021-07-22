package com.zhousj.common.ext;

/**
 * 分页信息
 * @author zhousj
 * @date 2021/1/4
 */
@SuppressWarnings("unused")
public class Page {

    private int pageNum;

    private int pageSize;


    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static Page of(int pageNum, int pageSize) {
        return new Page(pageNum, pageSize);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
