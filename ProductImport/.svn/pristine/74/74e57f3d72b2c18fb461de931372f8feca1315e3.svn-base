/*
 * GenericProductService.java	 <br>
 * 2011-10-12<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.service;

import com.shinetech.dao.DressDAO;
import com.shinetech.handle.ProductInfoHandle;
import com.shinetech.sql.ResultSetHandler;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class GenericProductService {
	private DressDAO dao = new DressDAO();
	
	public void service(boolean isConfigurable){
		ResultSetHandler productInfoHandle = new ProductInfoHandle(isConfigurable,false);
		try {
			dao.getProductInfo(productInfoHandle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
