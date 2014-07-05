/*
 * DeteleRelatedProduct.java	 <br>
 * 2011-11-10<br>
 * com.shinetech.init <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.init;

import com.shinetech.action.ConstConfig;
import com.shinetech.dao.MagentoProductDAO;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DeteleRelatedProduct {
	private MagentoProductDAO dao = new MagentoProductDAO();
	public static void main(String[] args) {
		Const.initLogger();
		DeteleRelatedProduct relatedProduct = new DeteleRelatedProduct();
		relatedProduct.deleteRelatedProduct();
	}
	public void deleteRelatedProduct(){
		try {
			//此处不需要删除位置信息关系，因为表是删除时候会casecate级联删除
			dao.deleteProductLink(ConstConfig.product_link_type_code[0]);
			
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
