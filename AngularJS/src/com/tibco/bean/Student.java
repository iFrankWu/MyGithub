/*
 * Customer.java
 * Mar 28, 2013
 * org.timecontrol.domain
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class Student {
	private Integer id;
	 private String name;// student name 
	 private String sex;
	 private Date age;
	 private String address;
	 private String contact;
	 private String mobilePhone;
	 
	 
	 private String email;
	 private String telephone;// it should be an array
	 
	 private String dataFrom; //where the data from?
	 private String status; // the status of student
	 private Boolean doesBill; // whether have done bill 
	 private Date createDate;
	 private Date modifyDate;
	 private List<Clazz> clazzes = new ArrayList<Clazz>();
	 private List<Payment> paymentHistorys = new ArrayList<Payment>(); 
	 private List<Communication> communications = new ArrayList<Communication>();
	 private Integer uid;
	 
	 private String qq;
	 private String school;
	 private String otherTelephone;
	 private float accountBalance;
	 private String remark;
	 
	
	 
	 
 
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
	 * return the value of this accountBalance
	 * @return 
	 */
	public float getAccountBalance() {
		return accountBalance;
	}
	/**
	 * @param accountBalance the accountBalance to set  
	 *
	 */
	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}
	/**
	 * return the value of this otherTelephone
	 * @return 
	 */
	public String getOtherTelephone() {
		return otherTelephone;
	}
	/**
	 * @param otherTelephone the otherTelephone to set  
	 *
	 */
	public void setOtherTelephone(String otherTelephone) {
		this.otherTelephone = otherTelephone;
	}
	/**
	 * return the value of this qq
	 * @return 
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq the qq to set  
	 *
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * return the value of this school
	 * @return 
	 */
	public String getSchool() {
		return school;
	}
	/**
	 * @param school the school to set  
	 *
	 */
	public void setSchool(String school) {
		this.school = school;
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
	 * return the value of this clazzes
	 * @return 
	 */
	public List<Clazz> getClazzes() {
		return clazzes;
	}
	/**
	 * @param clazzes the clazzes to set  
	 *
	 */
	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}
	/**
	 * return the value of this name
	 * @return 
	 */
	public String getName() {
		return name;
	}
	/**
	 * return the value of this createDate
	 * @return 
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set  
	 *
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * return the value of this modifyDate
	 * @return 
	 */
	public Date getModifyDate() {
		return modifyDate;
	}
	/**
	 * @param modifyDate the modifyDate to set  
	 *
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	/**
	 * @param name the name to set  
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * return the value of this sex
	 * @return 
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set  
	 *
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * return the value of this address
	 * @return 
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set  
	 *
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * return the value of this contact
	 * @return 
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set  
	 *
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * return the value of this mobilePhone
	 * @return 
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * @param mobilePhone the mobilePhone to set  
	 *
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 * return the value of this email
	 * @return 
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set  
	 *
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * return the value of this telephone
	 * @return 
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set  
	 *
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * return the value of this dataFrom
	 * @return 
	 */
	public String getDataFrom() {
		return dataFrom;
	}
	/**
	 * @param dataFrom the dataFrom to set  
	 *
	 */
	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
	/**
	 * return the value of this status
	 * @return 
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set  
	 *
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * return the value of this doesBill
	 * @return 
	 */
	public Boolean getDoesBill() {
		return doesBill;
	}
	/**
	 * @param doesBill the doesBill to set  
	 *
	 */
	public void setDoesBill(Boolean doesBill) {
		this.doesBill = doesBill;
	}
	/**
	 * return the value of this paymentHistorys
	 * @return 
	 */
	public List<Payment> getPaymentHistorys() {
		return paymentHistorys;
	}
	/**
	 * @param paymentHistorys the paymentHistorys to set  
	 *
	 */
	public void setPaymentHistorys(List<Payment> paymentHistorys) {
		this.paymentHistorys = paymentHistorys;
	}
	/**
	 * return the value of this communications
	 * @return 
	 */
	public List<Communication> getCommunications() {
		return communications;
	}
	/**
	 * @param communications the communications to set  
	 *
	 */
	public void setCommunications(List<Communication> communications) {
		this.communications = communications;
	}
	/**
	 * return the value of this age
	 * @return 
	 */
	public Date getAge() {
		return age;
	}
	/**
	 * @param age the age to set  
	 *
	 */
	public void setAge(Date age) {
		this.age = age;
	}
	 
	 

}

