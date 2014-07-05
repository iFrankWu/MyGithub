/*
 * PaymentHistory.java
 * Mar 28, 2013
 * org.timecontrol.domain
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.bean;

import java.util.Date;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class Payment {
	 private Integer id;
	 private Float payAmount;
	 private String purpose;
	 private Boolean doesNeedReminder;// if ture, when the date expired, need remind customer renewals
	 private Date startDate; //是指这笔付款的开始日期
	 private Date endDate;//这笔付款的到期时间
	 private String remark;
	 private Date payDate;
	 private Integer uid;//user id
	 private Integer sid;//student id
	 private Integer pwayid; // payway id
	 
	 private String payer;  //those two parameter is not in db tbale
	 private String receiver;
	 
	 private Integer status;//1 is norml, -1 is return, 0 delete
	 private String cashOrCard;
	 private Float useBalance;
	 private String selectedClass ;
     private String selectedPayway;
     private float discount;
	 
	 
	 
	/**
	 * return the value of this discount
	 * @return 
	 */
	public float getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set  
	 *
	 */
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	/**
	 * return the value of this selectedClass
	 * @return 
	 */
	public String getSelectedClass() {
		return selectedClass;
	}
	/**
	 * @param selectedClass the selectedClass to set  
	 *
	 */
	public void setSelectedClass(String selectedClass) {
		this.selectedClass = selectedClass;
	}
	/**
	 * return the value of this selectedPayway
	 * @return 
	 */
	public String getSelectedPayway() {
		return selectedPayway;
	}
	/**
	 * @param selectedPayway the selectedPayway to set  
	 *
	 */
	public void setSelectedPayway(String selectedPayway) {
		this.selectedPayway = selectedPayway;
	}
	/**
	 * return the value of this cashOrCard
	 * @return 
	 */
	public String getCashOrCard() {
		return cashOrCard;
	}
	/**
	 * @param cashOrCard the cashOrCard to set  
	 *
	 */
	public void setCashOrCard(String cashOrCard) {
		this.cashOrCard = cashOrCard;
	}
	/**
	 * return the value of this useBalance
	 * @return 
	 */
	public Float getUseBalance() {
		return useBalance;
	}
	/**
	 * @param useBalance the useBalance to set  
	 *
	 */
	public void setUseBalance(Float useBalance) {
		this.useBalance = useBalance;
	}
	 
	 
	/**
	 * return the value of this status
	 * @return 
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set  
	 *
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * return the value of this payer
	 * @return 
	 */
	public String getPayer() {
		return payer;
	}
	/**
	 * @param payer the payer to set  
	 *
	 */
	public void setPayer(String payer) {
		this.payer = payer;
	}
	/**
	 * return the value of this receiver
	 * @return 
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set  
	 *
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/**
	 * return the value of this startDate
	 * @return 
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set  
	 *
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * return the value of this pwayid
	 * @return 
	 */
	public Integer getPwayid() {
		return pwayid;
	}
	/**
	 * @param pwayid the pwayid to set  
	 *
	 */
	public void setPwayid(Integer pwayid) {
		this.pwayid = pwayid;
	}
	/**
	 * return the value of this uid
	 * @return 
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set  
	 *
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * return the value of this sid
	 * @return 
	 */
	public Integer getSid() {
		return sid;
	}
	/**
	 * @param sid the sid to set  
	 *
	 */
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	/**
	 * return the value of this id
	 * @return 
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set  
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * return the value of this payAmount
	 * @return 
	 */
	public Float getPayAmount() {
		return payAmount;
	}
	/**
	 * @param payAmount the payAmount to set  
	 *
	 */
	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}
	/**
	 * return the value of this purpose
	 * @return 
	 */
	public String getPurpose() {
		return purpose;
	}
	/**
	 * @param purpose the purpose to set  
	 *
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	 
	 
	/**
	 * return the value of this doesNeedReminder
	 * @return 
	 */
	public Boolean getDoesNeedReminder() {
		return doesNeedReminder;
	}
	/**
	 * @param doesNeedReminder the doesNeedReminder to set  
	 *
	 */
	public void setDoesNeedReminder(Boolean doesNeedReminder) {
		this.doesNeedReminder = doesNeedReminder;
	}
	/**
	 * return the value of this endDate
	 * @return 
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set  
	 *
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * return the value of this remark
	 * @return 
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set  
	 *
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * return the value of this payDate
	 * @return 
	 */
	public Date getPayDate() {
		return payDate;
	}
	/**
	 * @param payDate the payDate to set  
	 *
	 */
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	 
	 
}

