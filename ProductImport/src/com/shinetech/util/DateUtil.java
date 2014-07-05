/*
 * DateUtil.java	 <br>
 * 2011-7-23<br>
 * com.shinetech.util <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DateUtil {
	public static String DATA_FORMAT_TYPE = "yyyy-MM-dd HH:mm:ss";
	private static DateFormat format= new SimpleDateFormat(DATA_FORMAT_TYPE);
	public static String getNowDate(){
		return format.format(Calendar.getInstance().getTime());
	}
	public static String getToday(){
		return format.format(Calendar.getInstance().getTime()).substring(0,10);
	}
	public static String getYestorday(){
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_MONTH, cl.get(Calendar.DAY_OF_MONTH)-1);
		return format.format(cl.getTime()).substring(0, 10);
	}
	public static String getFormatDate(Date date){
		return format.format(date).substring(0,10);
	}
	public static void main(String[] args) {
		System.out.println(getNowDate());
		System.out.println(getToday());
		System.out.println(getYestorday());
	}
}
