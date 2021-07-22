package com.zhousj.common.pojo;

/**
 * @author zhousj
 * @date 2020/12/15
 */
@SuppressWarnings("unused")
public class FieldOrder {

    private String field;

    private boolean desc;

    public FieldOrder(String field) {
        this.field = field;
    }

    public FieldOrder(String field, boolean desc) {
        this.field = field;
        this.desc = desc;
    }

    public static FieldOrder of(String field) {
        return new FieldOrder(field);
    }

    public static FieldOrder of(String field, boolean desc){
        return new FieldOrder(field, desc);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
