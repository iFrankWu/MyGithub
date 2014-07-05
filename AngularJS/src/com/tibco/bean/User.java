/*
 * User.java
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
public class User {
	 private Integer id;
	 private String username;
	 private String password;
	 private String type;//admin normal
	 private String status; //is active
	 private Date createDate;
	 private Date modifyDate;
	 
	 
 
	
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
	 * return the value of this type
	 * @return 
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set  
	 *
	 */
	public void setType(String type) {
		this.type = type;
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
	 * return the value of this username
	 * @return 
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set  
	 *
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * return the value of this password
	 * @return 
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set  
	 *
	 */
	public void setPassword(String password) {
		this.password = password;
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
	

}

