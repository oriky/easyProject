package com.zhousj.demo.entity;

import com.zhousj.common.ext.jpa.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhousj
 * @date 2020/12/9
 */
@Table(name = "AUTO_SEND_LOG")
@Entity
public class AutoSendLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3795930851836881933L;

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 客户id
     */
    @Column(name = "LOG_TIME")
    private String logTime;


    /**
     * 客户id
     */
    @Column(name = "LOG_CODE")
    private String logCode;


    /**
     * 客户id
     */
    @Column(name = "LOG_RESULT")
    private String logResult;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogCode() {
        return logCode;
    }

    public void setLogCode(String logCode) {
        this.logCode = logCode;
    }

    public String getLogResult() {
        return logResult;
    }

    public void setLogResult(String logResult) {
        this.logResult = logResult;
    }
}
