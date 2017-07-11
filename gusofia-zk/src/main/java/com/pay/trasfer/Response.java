package com.pay.trasfer;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 支付统一返回封装类
 * 
 * @author chen.jie
 * 
 */
public class Response implements Serializable,Cloneable {

    private static final long serialVersionUID = 12415161L;

    /**
     * 0|处理中,1|成功,-1|业务失败 -2请求失败  @See PaidStatus(退款请参考RefundStatus)
     */
    private Integer status;

    /**
     * 失败具体信息
     */
    private String errorMsg;

    /**
     * 支付系统返回的订单号
     */
    private String payOrderNo;
    
    /** @see com.tongbanjie.pay.facade.consts.PayWay */
    private Integer payWay;
    
    /**
      * 冗余字段，由业务方商量传值,尽量别用
     */
    private Map<String, Object> fields;

    public Response() {
        super();
    }
    
    public Response(Integer status, String errorMsg) {
        super();
        this.status = status;
        this.errorMsg = errorMsg;
    }
    public Response(int status,Integer payWay, String errorMsg) {
        super();
        this.status = status;
        this.errorMsg = errorMsg;
        this.payWay=payWay;
    }

    public Response(Integer status, String errorMsg, Map<String, Object> fields) {
        super();
		this.status = status;
		this.errorMsg = errorMsg;
		this.fields = fields;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the payOrderNo
     */
    public String getPayOrderNo() {
        return payOrderNo;
    }

    /**
     * @param payOrderNo the payOrderNo to set
     */
    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}



	/**
	 * 这里只做浅拷贝，后续如果有需求，可以做深拷贝
	 */
	public Object clone() {
		try {
			return (Response)super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

	
}