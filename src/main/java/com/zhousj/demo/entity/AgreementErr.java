package com.zhousj.demo.entity;

import com.zhousj.common.ext.jpa.BaseEntity;

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
@Table(name = "agreement_err")
@Entity
public class AgreementErr extends BaseEntity implements Serializable{


    private static final long serialVersionUID = -5145731654106420859L;
    /**
     * 协议标识
     */
    @Id
    @Column(name = "AGREEMENT_ID")
    private String agreementId;

    /**
     * 客户id
     */
    @Column(name = "CUST_ID")
    private String custId;


    /**
     * 接入时间
     */
    @Column(name = "AGREEMENT_TYPE")
    private String agreementType;

    /**
     * 渠道id
     */
    @Column(name = "SIGN_DATE")
    private String signDate;

    /**
     * 处理完成时间
     */
    @Column(name = "COMPLETED_DATE")
    private String completedDate;

    /**
     * 处理人工号
     */
    @Column(name = "ACCEPT_STAFF_ID")
    private String acceptStaffId;

    /**
     * 状态
     */
    @Column(name = "STATE")
    private String state;

    /**
     * 描述
     */
    @Column(name = "DESCRIPT")
    private String descript;

    /**
     * 处理次数
     */
    @Column(name = "PROC_CNT")
    private String procCnt;

    /**
     * 业务单号
     */
    @Column(name = "ACC_NBR")
    private String accNbr;

    /**
     * 错误id
     */
    @Column(name = "ERROR_ID")
    private String errorId;


    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getAcceptStaffId() {
        return acceptStaffId;
    }

    public void setAcceptStaffId(String acceptStaffId) {
        this.acceptStaffId = acceptStaffId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getProcCnt() {
        return procCnt;
    }

    public void setProcCnt(String procCnt) {
        this.procCnt = procCnt;
    }

    public String getAccNbr() {
        return accNbr;
    }

    public void setAccNbr(String accNbr) {
        this.accNbr = accNbr;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    @Override
    public String toString() {
        return "AgreementErr{" +
                "agreementId='" + agreementId + '\'' +
                ", custId='" + custId + '\'' +
                ", agreementType='" + agreementType + '\'' +
                ", signDate='" + signDate + '\'' +
                ", completedDate='" + completedDate + '\'' +
                ", acceptStaffId='" + acceptStaffId + '\'' +
                ", state='" + state + '\'' +
                ", descript='" + descript + '\'' +
                ", procCnt='" + procCnt + '\'' +
                ", accNbr='" + accNbr + '\'' +
                ", errorId='" + errorId + '\'' +
                '}';
    }
}
