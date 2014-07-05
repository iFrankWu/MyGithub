/*
 * Communication.java
 * Mar 30, 2013
 * org.timecontrol.bean
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
public class Communication {
	private Integer id;
//	private String stuContact;  //default is student,contact
//	private String  teaContact; //default is user.name
	private String content;
	private Integer sid;//student id
	private Date communicationDate;
	private Integer uid;
	private String username;
	private Date createDate;
	
	
	
	
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
	 * return the value of this content
	 * @return 
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set  
	 *
	 */
	public void setContent(String content) {
		this.content = content;
	}

	
	 
	/**
	 * return the value of this communicationDate
	 * @return 
	 */
	public Date getCommunicationDate() {
		return communicationDate;
	}
	/**
	 * @param communicationDate the communicationDate to set  
	 *
	 */
	public void setCommunicationDate(Date communicationDate) {
		this.communicationDate = communicationDate;
	}
	
	
}

