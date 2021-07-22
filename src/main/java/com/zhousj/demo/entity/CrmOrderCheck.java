package com.zhousj.demo.entity;

import com.zhousj.common.ext.jpa.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhousj
 * @date 2020/12/8
 */
@SuppressWarnings("unused")
@Table(name = "t_crm_order_check")
@Entity
@DynamicUpdate
public class CrmOrderCheck extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2524561680726002443L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "rowid")
    private String rowId;

    /**
     * 渠道id
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 业务号码
     */
    @Column(name = "acc_nbr")
    private String accNbr;

    /**
     * 外部订单号
     */
    @Column(name = "ext_cust_order_id")
    private String extCustOrderId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 处理时间
     */
    @Column(name = "sync_time")
    private String syncTime;

    /**
     * 稽核状态
     */
    @Column(name = "sync_tag")
    private String syncTag;

    /**
     * 稽核结果
     */
    @Column(name = "deal_flag")
    private String dealFlag;

    /**
     * 结果描述
     */
    @Column(name = "deal_remark")
    private String dealRemark;

    /**
     * 流水号
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 消息session
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 稽核时间
     */
    @Column(name = "deal_time")
    private String dealTime;


    @Column(name = "sign_date")
    private String signDate;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getAccNbr() {
        return accNbr;
    }

    public void setAccNbr(String accNbr) {
        this.accNbr = accNbr;
    }

    public String getExtCustOrderId() {
        return extCustOrderId;
    }

    public void setExtCustOrderId(String extCustOrderId) {
        this.extCustOrderId = extCustOrderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public String getSyncTag() {
        return syncTag;
    }

    public void setSyncTag(String syncTag) {
        this.syncTag = syncTag;
    }

    public String getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(String dealFlag) {
        this.dealFlag = dealFlag;
    }

    public String getDealRemark() {
        return dealRemark;
    }

    public void setDealRemark(String dealRemark) {
        this.dealRemark = dealRemark;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }
}
