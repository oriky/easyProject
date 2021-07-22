package com.zhousj.common.pojo;

/**
 * @author zhousj
 * @date 2020-12-15
 */
@SuppressWarnings("unused")
public enum Operator {
    /**
     * 相等
     */
    EQUAL,


    /**
     * 大于
     */
    GREAT_THAN,


    /**
     * 大于等于
     */
    GREAT_THAN_EQUAL,


    /**
     * 小于
     */
    LESS_THAN,


    /**
     * 小于等于
     */
    LESS_THAN_EQUAL,


    /**
     * 为null
     */
    IS_NULL,


    /**
     * 不为null
     */
    IS_NOT_NULL,


    /**
     * 为空
     */
    IS_EMPTY,


    /**
     * 不为空
     */
    IS_NOT_EMPTY,


    /**
     * 模糊查询
     */
    LIKE

}
