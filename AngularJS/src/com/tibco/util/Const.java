/*
 * Const.java
 * May 4, 2013
 * com.tibco.util
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.util;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class Const {
	public static boolean doesLoggerInited = false;
	
	public static String STATUS_USEABLE = "可用";
	public static String STATUS_UNUSEABLE = "禁用";
	
	public static String TYPE_ADMIN = "管理员";
	public static String TYPE_NORMAL = "普通用户";
	public static String TYPE_SUPER = "超级管理员";
	
	public static String PAYWAY_REFUND = "存入账户";
	public static String LOGIN_SUCCESS = "loginSuccess";
	public static String LOGIN_PATH = "user";
	public static String METHOD_POST = "POST";
	public static String METHOD_GET = "GET";
	public static String CURRENT_USER_TYPE = "type";
	public static String CURRENT_USER_ID = "currentUserId";
	public static String CURRENT_USER_NAME = "currentUserName";
	
	
	public static String ACTION_CheckStudent = "doesExistStudent";
	public static String ACTION_SearchStudent = "searchStudent";
	public static String ACTION_GetStuNameById = "getStuNameById";
	public static String ACTION_SerachPaymentByUser = "serachPaymentByUser";
	public static String ACTION_AddCommunicate = "addCommunicate";
	public static String ACTION_SettingDiscount = "settingDiscount";
	public static String ACTION_GetSettingDiscount = "getSettedDiscount";
	public static String ACTION_DeleteSettingDiscount = "deleteSettedDiscount";
	public static String ACTION_GetDiscountForStudentByClass = "getDiscountForStudentByClass";
	public static String ACTION_GetAllRefunds = "getAllRefunds";
	public static String ACTION_AddRefund = "addRefund";
	public static String ACTION_DeleteRefund = "deleteRefund";
	
	
	public  static void initLogger() {
		if(!doesLoggerInited){
			String log4j = System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j.xml";
	 		System.out.println(log4j);
			DOMConfigurator.configure(log4j);
	 		doesLoggerInited = true;
 		}
	}
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			params.put(key, request.getParameter(key));
		}
//		UnmodifiableMap.decorate(params);
		return params;
	}
//	private static MessageDigest md;
//	public static String cryptWithMD5(String pass){
//	    try {
//	        md = MessageDigest.getInstance("MD5");
//	        byte[] passBytes = pass.getBytes();
//	        md.reset();
//	        byte[] digested = md.digest(passBytes);
//	        StringBuffer sb = new StringBuffer();
//	        for(int i=0;i<digested.length;i++){
//	            sb.append(Integer.toHexString(0xff & digested[i]));
//	        }
//	        return sb.toString();
//	    } catch (NoSuchAlgorithmException ex) {
//	    	ex.printStackTrace();
//	    }
//	        return null;
//	   }
}

