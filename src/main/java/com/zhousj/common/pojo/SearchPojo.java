package com.zhousj.common.pojo;


/**
 * @author zhousj
 * @date 2020/12/8
 */
@SuppressWarnings("unused")
public class SearchPojo {

    /**
     * 查询字段
     */
    private String operCode;

    /**
     * 查询值
     */
    private String operValue;

    /**
     * 查询符号
     */
    private Operator operator;


    public static SearchPojo of(String filed) {
        return of(filed, null);
    }


    public static SearchPojo of(String filed, String value) {
        return of(filed, value, null);
    }


    public static SearchPojo of(String filed, String value, Operator operator) {
        return new SearchPojo(filed, value, operator);
    }


    public SearchPojo(String operCode) {
        this.operCode = operCode;
    }

    public SearchPojo(String operCode, String operValue) {
        this.operCode = operCode;
        this.operValue = operValue;
    }

    public SearchPojo(String operCode, String operValue, Operator operator) {
        this.operCode = operCode;
        this.operValue = operValue;
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getOperValue() {
        return operValue;
    }

    public void setOperValue(String operValue) {
        this.operValue = operValue;
    }
}
