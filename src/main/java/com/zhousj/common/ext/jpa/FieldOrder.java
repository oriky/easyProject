package com.zhousj.common.ext.jpa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author zhousj
 * @date 2020/12/15
 */
@SuppressWarnings("unused")
public class FieldOrder implements Serializable {

    private static final long serialVersionUID = 6033906849565898651L;

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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(field);
        s.writeBoolean(desc);

    }


    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.field = (String) s.readObject();
        this.desc = s.readBoolean();
    }
}
