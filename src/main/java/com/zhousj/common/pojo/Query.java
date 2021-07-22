package com.zhousj.common.pojo;

import java.io.Serializable;
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
    private static final long serialVersionUID = 4165017315440164112L;
    /**
     * 查询条件
     */
    private List<SearchPojo> searchPojo;

    /**
     * 排序
     */
    private List<FieldOrder> fieldOrders;

    public Query() {
    }

    public Query(List<SearchPojo> searchPojo) {
        this.searchPojo = searchPojo;
    }

    public Query(List<SearchPojo> searchPojo, List<FieldOrder> fieldOrders) {
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
        addSearch(field, value, null);
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


}
