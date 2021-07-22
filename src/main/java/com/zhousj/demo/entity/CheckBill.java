package com.zhousj.demo.entity;


import com.zhousj.common.ext.jpa.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhousj
 * @date 2021/5/7
 */
@Entity
@Table(name = "check_bill")
public class CheckBill extends BaseEntity {

    @Id
    @Column(name = "seq")
    private String seq;

    @Column(name = "cust_id")
    private String custId;

    @Column(name = "ext_cust_order_id")
    private String extCustOrderId;

    @Column(name = "deal_time")
    private String dealTime;

    @Column(name = "broad_account")
    private String broadAccount;

    @Column(name = "flag")
    private int flag;

    @Column(name = "yuanyin")
    private String yuanyin;

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getExtCustOrderId() {
        return extCustOrderId;
    }

    public void setExtCustOrderId(String extCustOrderId) {
        this.extCustOrderId = extCustOrderId;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getBroadAccount() {
        return broadAccount;
    }

    public void setBroadAccount(String broadAccount) {
        this.broadAccount = broadAccount;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getYuanyin() {
        return yuanyin;
    }

    public void setYuanyin(String yuanyin) {
        this.yuanyin = yuanyin;
    }
}
