package com.zhousj.common.ext.jpa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * jpa复杂查询pojo类
 *
 * @author zhousj
 * @date 2020/12/15
 */
@SuppressWarnings("unused")
public class Query implements Serializable {

    private static final long serialVersionUID = -6717799162332677515L;
    /**
     * 查询条件
     */
    private List<SearchPojo> searchPojo;

    /**
     * 排序
     */
    private List<FieldOrder> fieldOrders;

    public Query() {
        this.searchPojo = new LinkedList<>();
        this.fieldOrders = new LinkedList<>();
    }

    public Query(List<SearchPojo> searchPojo) {
        this();
        this.searchPojo = searchPojo;
    }

    public Query(List<SearchPojo> searchPojo, List<FieldOrder> fieldOrders) {
        this();
        this.searchPojo = searchPojo;
        this.fieldOrders = fieldOrders;
    }

    public static Query newQuery() {
        return new Query();
    }

    public static Query newQuery(List<SearchPojo> searchPojo) {
        return new Query(searchPojo);
    }

    public static Query newQuery(List<SearchPojo> searchPojo, List<FieldOrder> fieldOrders) {
        return new Query(searchPojo, fieldOrders);
    }

    public void addOrder(String field) {
        addOrder(FieldOrder.of(field, false));
    }

    public void addOrder(String field, boolean desc) {
        addOrder(FieldOrder.of(field, desc));
    }

    public void addOrder(FieldOrder order) {
        if (fieldOrders == null) {
            synchronized (this) {
                if (fieldOrders == null) {
                    fieldOrders = new LinkedList<>();
                }
            }
        }
        fieldOrders.add(order);
    }

    public synchronized void addOrderList(List<FieldOrder> orderList) {
        if (fieldOrders == null) {
            fieldOrders = orderList;
        } else {
            fieldOrders.addAll(orderList);
        }
    }

    public synchronized void addSearch(String field, Operator operator) {
        addSearch(field, null, operator);
    }

    public synchronized void addSearch(String field, String value) {
        addSearch(field, value, Operator.EQUAL);
    }

    public synchronized void addSearch(String field, String value, Operator operator) {
        if (searchPojo == null) {
            searchPojo = new LinkedList<>();
        }
        searchPojo.add(SearchPojo.of(field, value, operator));
    }

    public synchronized void addSearchList(List<SearchPojo> searchPojo) {
        if (this.searchPojo == null) {
            this.searchPojo = searchPojo;
        } else {
            this.searchPojo.addAll(searchPojo);
        }
    }


    public List<SearchPojo> getSearchPojo() {
        return searchPojo;
    }

    public List<FieldOrder> getFieldOrders() {
        return fieldOrders;
    }

    public void setFieldOrders(List<FieldOrder> fieldOrders) {
        this.fieldOrders = fieldOrders;
    }

    public void setSearchPojo(List<SearchPojo> searchPojo) {
        this.searchPojo = searchPojo;
    }


    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        int searchSize = this.searchPojo == null ? 0 : this.searchPojo.size();
        int orderSize = this.fieldOrders == null ? 0 : this.fieldOrders.size();
        s.writeInt(searchSize);
        s.writeInt(orderSize);
        if (searchSize > 0) {
            for (SearchPojo search : this.searchPojo) {
                s.writeObject(search);
            }
        }
        if (orderSize > 0) {
            for (FieldOrder order : this.fieldOrders) {
                s.writeObject(order);
            }
        }

    }


    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int searchSize = s.readInt();
        int orderSize = s.readInt();
        List<SearchPojo> searchPojoList = new ArrayList<>(searchSize);
        List<FieldOrder> fieldOrderList = new ArrayList<>(orderSize);
        for (int i = 0; i < searchSize; i++) {
            SearchPojo pojo = (SearchPojo) s.readObject();
            searchPojoList.add(pojo);
        }
        for (int i = 0; i < orderSize; i++) {
            FieldOrder order = (FieldOrder) s.readObject();
            fieldOrderList.add(order);
        }
        this.searchPojo = searchPojoList;
        this.fieldOrders = fieldOrderList;

    }


}
