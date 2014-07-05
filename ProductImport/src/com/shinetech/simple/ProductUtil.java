/*
 * ProductUtil.java	 <br>
 * 2011-11-1<br>
 * com.shinetech.simple <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProductUtil {
	/**
	 * 功能：计算一个产品的分类，是Wedding Dresses 还是 Special occasion dresses <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param catalogTagMap
	 * @return
	 * String
	 */
	public static String getProductType(Map<String,ArrayList<String>> catalogTagMap){
		//if(catalogTagMap.get("Wedding Dresses") != null && catalogTagMap.get("Wedding Dresses").contains("Bridal Gowns")){
		String productType = SimpleProductConfig.productTypes[0];
		if(catalogTagMap.get("Wedding Dresses") != null && !catalogTagMap.get("Wedding Dresses").isEmpty()){
			productType =  SimpleProductConfig.productTypes[0];//婚纱
		}else{
			productType =  SimpleProductConfig.productTypes[1];//不是婚纱
		}
		return productType;
	}
}
