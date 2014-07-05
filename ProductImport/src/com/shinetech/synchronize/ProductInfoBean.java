/*
 * ProductInfoBean.java	 <br>
 * 2012-1-5<br>
 * com.shinetech.synchronize <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.synchronize;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProductInfoBean {
	private String name,meta_titile,meta_description,itemcode,discount,description,short_description,sku;
	private float marketPrice;
	/*
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/*
	 * @return the meta_titile
	 */
	public String getMeta_titile() {
		return meta_titile;
	}
	/*
	 * @return the meta_description
	 */
	public String getMeta_description() {
		return meta_description;
	}
	/*
	 * @return the itemcode
	 */
	public String getItemcode() {
		return itemcode;
	}
	/*
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}
	/*
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/*
	 * @return the short_description
	 */
	public String getShort_description() {
		return short_description;
	}
	/*
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}
	/*
	 * @return the marketPrice
	 */
	public float getMarketPrice() {
		return marketPrice;
	}
	/*
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/*
	 * @param meta_titile the meta_titile to set
	 */
	public void setMeta_titile(String meta_titile) {
		this.meta_titile = meta_titile;
	}
	/*
	 * @param meta_description the meta_description to set
	 */
	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}
	/*
	 * @param itemcode the itemcode to set
	 */
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	/*
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	/*
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/*
	 * @param short_description the short_description to set
	 */
	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}
	/*
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}
	/*
	 * @param marketPrice the marketPrice to set
	 */
	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = sku+"\t"+name+"\t"+meta_titile+"\t"+meta_description+"\t"+itemcode+"\t"+discount+"\t"+description+"\t"+short_description;
		float dis = Float.parseFloat(discount);
		float realprice = marketPrice*(100-dis)/100;
		s += "\t"+marketPrice+"\t"+String.format("%.2f", realprice);;
		return s;
	}
	
	
	
}
