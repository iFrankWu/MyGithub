/*
 * SuperProducts.java	 <br>
 * 2011-10-11<br>
 * com.shinetech.bean <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.bean;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class SuperProducts {
	private  String _super_products_sku = "";
	private  String _super_attribute_code = "";
	private  String _super_attribute_option = "";
	private  String _super_attribute_price_corr = "";
	/*
	 * @return the _super_products_sku
	 */
	public String get_super_products_sku() {
		return _super_products_sku;
	}
	/*
	 * @return the _super_attribute_code
	 */
	public String get_super_attribute_code() {
		return _super_attribute_code;
	}
	/*
	 * @return the _super_attribute_option
	 */
	public String get_super_attribute_option() {
		return _super_attribute_option;
	}
	/*
	 * @return the _super_attribute_price_corr
	 */
	public String get_super_attribute_price_corr() {
		return _super_attribute_price_corr;
	}
	/*
	 * @param _super_products_sku the _super_products_sku to set
	 */
	public void set_super_products_sku(String _super_products_sku) {
		this._super_products_sku = _super_products_sku;
	}
	/*
	 * @param _super_attribute_code the _super_attribute_code to set
	 */
	public void set_super_attribute_code(String _super_attribute_code) {
		this._super_attribute_code = _super_attribute_code;
	}
	/*
	 * @param _super_attribute_option the _super_attribute_option to set
	 */
	public void set_super_attribute_option(String _super_attribute_option) {
		this._super_attribute_option = _super_attribute_option;
	}
	/*
	 * @param _super_attribute_price_corr the _super_attribute_price_corr to set
	 */
	public void set_super_attribute_price_corr(String _super_attribute_price_corr) {
		this._super_attribute_price_corr = _super_attribute_price_corr;
	}
	/**
	 * 目的和功能：构造方法,构造SuperProducts类的一个实例 
	 * 注意事项：此处生成简单产品时，按照默认规则生成sku
	 * @param _super_products_sku
	 * @param _super_attribute_code
	 * @param _super_attribute_option
	 * @param _super_attribute_price_corr
	 */
	public SuperProducts(String _super_products_sku,
			String _super_attribute_code, String _super_attribute_option,
			String _super_attribute_price_corr) {
		super();
		this._super_products_sku = _super_products_sku+"_"+_super_attribute_option;
		this._super_attribute_code = _super_attribute_code;
		this._super_attribute_option = _super_attribute_option;
		this._super_attribute_price_corr = _super_attribute_price_corr;
	}
	public String toString(){
		if("_".equals(_super_products_sku))
			_super_products_sku = "";
		return _super_products_sku+","+_super_attribute_code+","+_super_attribute_option+","+_super_attribute_price_corr+",";
	}
}
