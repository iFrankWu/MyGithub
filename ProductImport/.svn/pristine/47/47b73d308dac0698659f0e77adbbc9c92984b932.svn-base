/*
 * DressProductService.java	 <br>
 * 2011-10-12<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;


/**
 * 在类中集中对采集回来的产品进行处理
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProductHandle implements IHandle<String>{
	private String pSku;
	/*
	 * @return the pSku
	 */
	public String getpSku() {
		return pSku;
	}

	/*
	 * @return the pName
	 */
	public String getpName() {
		return pName;
	}

	/*
	 * @param pSku the pSku to set
	 */
	public void setpSku(String pSku) {
		this.pSku = pSku;
	}

	/*
	 * @param pName the pName to set
	 */
	public void setpName(String pName) {
		this.pName = pName;
	}

	private String pName;

	@Override
	public void handle(String... t) {
		if(t.length > 0){
			this.pName = t[0];
			this.pSku = t[0].replace(" ", "_").toLowerCase();
		}
	}
}
