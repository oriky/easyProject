package com.zhousj.common.ext.jpa;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author zhousj
 * @date 2020/12/8
 */
@SuppressWarnings("unused")
public class SearchPojo implements Serializable {

    private static final long serialVersionUID = 4154285636671566135L;
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
        Operator operator = Operator.EQUAL;
        if (value == null) {
            operator = Operator.IS_NULL;
        }
        return of(filed, value, operator);
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


    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(operCode);
        s.writeObject(operValue);
        s.writeObject(operator);
    }


    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.operCode = (String) s.readObject();
        this.operValue = (String) s.readObject();
        this.operator = (Operator) s.readObject();
    }
}
