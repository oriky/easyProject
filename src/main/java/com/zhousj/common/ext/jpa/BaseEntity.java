package com.zhousj.common.ext.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author zhousj
 * @date 2020/12/8
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class BaseEntity{

    private static final String CUSTOM_ID_NAME = "custom_id";

    private static final String CUSTOM_ID_STRATEGY_CLASS = "com.zhousj.common.ext.jpa.CustomIdStrategy";

    @Id
    @GeneratedValue(generator = CUSTOM_ID_NAME)
    @GenericGenerator(name = CUSTOM_ID_NAME, strategy = CUSTOM_ID_STRATEGY_CLASS)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
