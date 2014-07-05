/*
 * TestMagento.java	 <br>
 * 2011-11-9<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import com.shinetech.action.ConstConfig;
import com.shinetech.dao.CatagoryDAO;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TestMagento {
	public static void main(String[] args) {
		test1();
	}
	public static void test1(){
		CatagoryDAO dao = new CatagoryDAO();
		try {
			int entityTypeId = dao.getEntityTypeId("catalog_product");
			int attrId = dao.getAttributeId(ConstConfig.costomAttrCode[0], entityTypeId);
			System.out.println(attrId);
//			dao.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
