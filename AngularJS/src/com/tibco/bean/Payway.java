/*
 * Payways.java
 * May 13, 2013
 * com.tibco.bean
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
public class Payway {
	private Integer id;
	private String payway;
	private Float discount;
	private Date modifyDate;
	private Date createDate;
	private Integer cid;

	/**
	 * return the value of this cid
	 * 
	 * @return
	 */
	public Integer getCid() {
		return cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 * 
	 */
	public void setCid(Integer cid) {
		this.cid = cid;
	}

	/**
	 * return the value of this modifyDate
	 * 
	 * @return
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate
	 *            the modifyDate to set
	 * 
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * return the value of this createDate
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 * 
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * return the value of this id
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * return the value of this payway
	 * 
	 * @return
	 */
	public String getPayway() {
		return payway;
	}

	/**
	 * @param payway
	 *            the payway to set
	 * 
	 */
	public void setPayway(String payway) {
		this.payway = payway;
	}

	/**
	 * return the value of this discount
	 * 
	 * @return
	 */
	public Float getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 * 
	 */
	public void setDiscount(Float discount) {
		this.discount = discount;
	}

}
