/*
 * ProdcutPriceProxy.java	 <br>
 * 2011-10-18<br>
 * com.shinetech.proxy <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.proxy;

import com.shinetech.service.ProductPriceService;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProdcutPriceProxy {
	public static void main(String[] args) {
		Const.initLogger();
		ProductPriceService service = new ProductPriceService();
		service.service();
	}
}
