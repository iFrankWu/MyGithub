/*
 * GenericProductProxy.java	 <br>
 * 2011-10-13<br>
 * com.shinetech.proxy <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.proxy;

import com.shinetech.service.GenericProductService;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class GenericProductProxy {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		GenericProductService service = new GenericProductService();
		service.service(true);
	}

}
