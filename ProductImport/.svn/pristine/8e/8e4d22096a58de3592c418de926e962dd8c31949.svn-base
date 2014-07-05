/*
 * ImportOtherCategory.java	 <br>
 * 2011-11-19<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.shinetech.dress.tag.GetShowroomQ;
import com.shinetech.handle.ShowroomCategoryHandle;
import com.shinetech.util.Const;

/**
 * 这个类主要用来导入商城分类的showroom分类下的子类
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportOtherCategory {
	private static Logger logger = Logger.getLogger(ImportOtherCategory.class);
	public static void main(String[] args) {
		Const.initLogger();
		Properties pro = new Properties();
		try{
			pro.load(new FileInputStream(new File("assets/now_use_showroom.txt")));
			String showroom = pro.getProperty("now_use_showroom_file","assets/in.tsv");
			logger.info("Now Import New Add Showroom,File is :"+showroom);
			GetShowroomQ gq = new GetShowroomQ(showroom);
			//正式使用时 值为false，否则将不会插入数据库
			ShowroomCategoryHandle handle = new ShowroomCategoryHandle(false);
			gq.excute(handle, true);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
