package com.zhousj.demo.pojo;

/**
 * @author zhousj
 * @date 2020-12-9
 */
@SuppressWarnings("unused")
public enum FlagEnums {
    /**
     * 未稽核
     */
    UNCHECK("0", "未稽核"),

    /**
     * 接收到订单且处理成功
     */
    RECEIVE_DEAL_SUCCESS("1", "接收到订单且处理成功"),

    /**
     * 接收到订单且处理成功
     */
    RECEIVE_DEAL_FAILURE("2", "接收到订单且处理失败"),

    /**
     * 接收到订单失败
     */
    RECEIVE_FAILURE("3", "接收到订单失败"),

    /**
     * 未收到订单
     */
    UN_RECEIVE("4", "未收到订单"),

    /**
     * 未接收到订单已重发成功并接收到订单
     */
    UN_RECEIVE_SEND("5", "未接收到订单已重发成功并接收到订单"),

    /**
     * 未接收到订单重发失败或异常
     */
    UN_RECEIVE_SEND_FAILURE("6", "未接收到订单重发失败或异常"),

    /**
     * 未收到订单已重发待稽核
     */
    UN_RECEIVE_SEND_CHECK("7", "未收到订单已重发待稽核"),

    /**
     * 已收到订单显示未收到
     */
    RECEIVE_SHOW_FAILURE("8", "已收到订单显示未收到"),

    /**
     * 未接收到订单已重发成功仍未接收到订单
     */
    OTHER("9", "未接收到订单已重发成功仍未接收到订单");


    /**
     * 稽核结果编号
     */
    String code;

    /**
     * 稽核结果描述
     */
    String remark;

    FlagEnums(String code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
