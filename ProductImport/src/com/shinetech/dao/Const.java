/*
 * Const.java	 <br>
 * 2011-10-9<br>
 * com.shinetech.dao <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class Const {
	public static String PREFIX = "mg_"; 
	public static String now_use_magento_database = "231";
	static{
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream("shell/user.conf"));
			now_use_magento_database = pro.getProperty("now_use_magento_database", now_use_magento_database);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
