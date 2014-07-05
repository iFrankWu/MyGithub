/*
 * ImportPetticoatCategory.java	 <br>
 * 2011-11-15<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportPetticoatCategory {
	public static void main(String[] args) {
		Const.initLogger();
		ImportCategory category = new ImportCategory(false);
		try {
			category.saveCalegory(SimpleProductConfig.NotActiveCategory.get(0));
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
