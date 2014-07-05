/*
 * ImportOrderImage.java	 <br>
 * 2011-11-3<br>
 * com.shinetech.order <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.order;

import com.shinetech.dao.DressDAO;
import com.shinetech.order.handle.ProductHandle;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportOrderImage {
	public static void main(String[] args) {
		Const.initLogger();
		ImportOrderImage imports = new ImportOrderImage();
		imports.importImg();
	}
	public void importImg(){
		DressDAO dao  = new DressDAO();
		
		ProductHandle handle = new ProductHandle();
		try {
			dao.getProductInfo(handle);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
