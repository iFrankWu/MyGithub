/*
 * ExportQualifiedProductImageService.java	 <br>
 * 2011-11-10<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.service;

import com.shinetech.action.ExportQualifiedProductImage;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ExportQualifiedProductImageService {
	public static void main(String[] args) {
		Const.initLogger();
		ExportQualifiedProductImage export = new ExportQualifiedProductImage();
		try {
			export.exportQualifiedProductImage();
		} catch (DBException e) {
			e.printStackTrace();
		}
		System.out.println("所有有效图片："+export.count);
	}
}
