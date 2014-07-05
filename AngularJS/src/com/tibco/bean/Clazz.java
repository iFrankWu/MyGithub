/*
 * Class.java
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
public class Clazz {

 private Integer id;
 private String className;
 private String classNotice;
 private String teacher;
 private Float payAmount;
 private Date modifyDate;
 private Date createDate;
 private List<Payway> payways = new ArrayList<Payway>();//付款方式设定
 private List<Student> students = new ArrayList<Student>();
 
 
 
/**
 * return the value of this payways
 * @return 
 */
public List<Payway> getPayways() {
	return payways;
}
/**
 * @param payways the payways to set  
 *
 */
public void setPayways(List<Payway> payways) {
	this.payways = payways;
}
/**
 * return the value of this students
 * @return 
 */
public List<Student> getStudents() {
	return students;
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
 * @param students the students to set  
 *
 */
public void setStudents(List<Student> students) {
	this.students = students;
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
 * return the value of this className
 * @return 
 */
public String getClassName() {
	return className;
}
/**
 * @param className the className to set  
 *
 */
public void setClassName(String className) {
	this.className = className;
}
/**
 * return the value of this classNotice
 * @return 
 */
public String getClassNotice() {
	return classNotice;
}
/**
 * @param classNotice the classNotice to set  
 *
 */
public void setClassNotice(String classNotice) {
	this.classNotice = classNotice;
}
/**
 * return the value of this teacher
 * @return 
 */
public String getTeacher() {
	return teacher;
}
/**
 * @param teacher the teacher to set  
 *
 */
public void setTeacher(String teacher) {
	this.teacher = teacher;
}
/**
 * return the value of this payAccount
 * @return 
 */
public Float getPayAmount() {
	return payAmount;
}
/**
 * @param payAccount the payAccount to set  
 *
 */
public void setPayAmount(Float payAmount) {
	this.payAmount = payAmount;
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

