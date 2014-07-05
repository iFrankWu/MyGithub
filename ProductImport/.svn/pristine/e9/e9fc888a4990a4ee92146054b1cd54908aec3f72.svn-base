/*
 * DeleteAllProduct.java	 <br>
 * 2011-11-14<br>
 * com.shinetech.init <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.init;

import com.shinetech.dao.MagentoProductDAO;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DeleteAllProduct {
	public static void main(String[] args) {
		Const.initLogger();
		DeleteAllProduct product = new DeleteAllProduct();
		product.deleteAll();
	}
	public void deleteAll(){
		MagentoProductDAO dao = new MagentoProductDAO();
		try {
			dao.deleteAll();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
