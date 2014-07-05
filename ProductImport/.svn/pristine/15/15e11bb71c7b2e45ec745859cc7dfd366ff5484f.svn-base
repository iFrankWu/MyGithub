/*
 * GenericProductProxy.java	 <br>
 * 2011-10-13<br>
 * com.shinetech.proxy <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.proxy;

import java.io.File;

import org.apache.log4j.Logger;

import com.shinetech.service.GenericProductService;
import com.shinetech.simple.SimpleProductImport;
import com.shinetech.util.Const;
import com.shinetech.util.DateUtil;
import com.shinetech.util.FileOperate;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class GenericSimpleProductProxy {
	private static Logger logger = Logger.getLogger(GenericProductProxy.class);
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		logger.info("Begin:"+DateUtil.getNowDate());
		Long start = System.currentTimeMillis();
		GenericProductService service = new GenericProductService();
		service.service(false);
		FileOperate op = new FileOperate();
		File f = new File("data/category.tsv");
		if(f.exists()){
			f.delete();
		}
		op.addContentToEndOfFile2("data/category.tsv", "#记录每个分类下的产品数目");
		for(String category : SimpleProductImport.categoriesMap.keySet()){
			op.addContentToEndOfFile2("data/category.tsv", category+"\t"+SimpleProductImport.categoriesMap.get(category));
		}
		Long end = System.currentTimeMillis();
		Long minute = (end - start)/1000/60;
		logger.info("End:"+DateUtil.getNowDate());
		logger.info("All times in minute:"+minute);
		
	}

}
