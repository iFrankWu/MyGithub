/*
 * ColorBean.java	 <br>
 * 2011-10-12<br>
 * com.shinetech.bean <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.bean;


/**
 * 关于自定义的custom options
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CustomOption {
	private  String _custom_option_store;
	private  String _custom_option_type;
	//type: Field,Area,File,Drop-down,Radio Buttons,Checkbox,Multiple Select,Date,Date &amp; Time,Time
	private  String _custom_option_title ;
	private  String _custom_option_is_required = "1";
	private  String _custom_option_price;
	private  String _custom_option_sku = "";
	private  String _custom_option_max_characters = "0";
	private  String _custom_option_sort_order = "0";
	//private  String _custom_option_row_title;
	//private List<CustomRow> customRowList = new ArrayList<CustomRow>();
	private  String _custom_option_row_title;
	private String _custom_option_row_price;
	private String _custom_option_row_sku;
	private String _custom_option_row_sort;
	/*
	 * @return the _custom_option_store
	 */
	public String get_custom_option_store() {
		return _custom_option_store;
	}
	/*
	 * @return the _custom_option_type
	 */
	public String get_custom_option_type() {
		return _custom_option_type;
	}
	/*
	 * @return the _custom_option_title
	 */
	public String get_custom_option_title() {
		return _custom_option_title;
	}
	/*
	 * @return the _custom_option_is_required
	 */
	public String get_custom_option_is_required() {
		return _custom_option_is_required;
	}
	/*
	 * @return the _custom_option_price
	 */
	public String get_custom_option_price() {
		return _custom_option_price;
	}
	/*
	 * @return the _custom_option_sku
	 */
	public String get_custom_option_sku() {
		return _custom_option_sku;
	}
	/*
	 * @return the _custom_option_max_characters
	 */
	public String get_custom_option_max_characters() {
		return _custom_option_max_characters;
	}
	/*
	 * @return the _custom_option_sort_order
	 */
	public String get_custom_option_sort_order() {
		return _custom_option_sort_order;
	}
	/*
	 * @return the _custom_option_row_title
	 */
	public String get_custom_option_row_title() {
		return _custom_option_row_title;
	}
	/*
	 * @return the _custom_option_row_price
	 */
	public String get_custom_option_row_price() {
		return _custom_option_row_price;
	}
	/*
	 * @return the _custom_option_row_sku
	 */
	public String get_custom_option_row_sku() {
		return _custom_option_row_sku;
	}
	/*
	 * @return the _custom_option_row_sort
	 */
	public String get_custom_option_row_sort() {
		return _custom_option_row_sort;
	}
	/*
	 * @param _custom_option_store the _custom_option_store to set
	 */
	public void set_custom_option_store(String _custom_option_store) {
		this._custom_option_store = _custom_option_store;
	}
	/*
	 * @param _custom_option_type the _custom_option_type to set
	 */
	public void set_custom_option_type(String _custom_option_type) {
		this._custom_option_type = _custom_option_type;
	}
	/*
	 * @param _custom_option_title the _custom_option_title to set
	 */
	public void set_custom_option_title(String _custom_option_title) {
		this._custom_option_title = _custom_option_title;
	}
	/*
	 * @param _custom_option_is_required the _custom_option_is_required to set
	 */
	public void set_custom_option_is_required(String _custom_option_is_required) {
		this._custom_option_is_required = _custom_option_is_required;
	}
	/*
	 * @param _custom_option_price the _custom_option_price to set
	 */
	public void set_custom_option_price(String _custom_option_price) {
		this._custom_option_price = _custom_option_price;
	}
	/*
	 * @param _custom_option_sku the _custom_option_sku to set
	 */
	public void set_custom_option_sku(String _custom_option_sku) {
		this._custom_option_sku = _custom_option_sku;
	}
	/*
	 * @param _custom_option_max_characters the _custom_option_max_characters to set
	 */
	public void set_custom_option_max_characters(
			String _custom_option_max_characters) {
		this._custom_option_max_characters = _custom_option_max_characters;
	}
	/*
	 * @param _custom_option_sort_order the _custom_option_sort_order to set
	 */
	public void set_custom_option_sort_order(String _custom_option_sort_order) {
		this._custom_option_sort_order = _custom_option_sort_order;
	}
	/*
	 * @param _custom_option_row_title the _custom_option_row_title to set
	 */
	public void set_custom_option_row_title(String _custom_option_row_title) {
		this._custom_option_row_title = _custom_option_row_title;
	}
	/*
	 * @param _custom_option_row_price the _custom_option_row_price to set
	 */
	public void set_custom_option_row_price(String _custom_option_row_price) {
		this._custom_option_row_price = _custom_option_row_price;
	}
	/*
	 * @param _custom_option_row_sku the _custom_option_row_sku to set
	 */
	public void set_custom_option_row_sku(String _custom_option_row_sku) {
		this._custom_option_row_sku = _custom_option_row_sku;
	}
	/*
	 * @param _custom_option_row_sort the _custom_option_row_sort to set
	 */
	public void set_custom_option_row_sort(String _custom_option_row_sort) {
		this._custom_option_row_sort = _custom_option_row_sort;
	}
	public CustomOption(String _custom_option_store,
			String _custom_option_type, String _custom_option_title,
			String _custom_option_is_required, String _custom_option_price,
			String _custom_option_sku, String _custom_option_max_characters,
			String _custom_option_sort_order, String _custom_option_row_title,
			String _custom_option_row_price, String _custom_option_row_sku,
			String _custom_option_row_sort) {
		super();
		this._custom_option_store = _custom_option_store;
		this._custom_option_type = _custom_option_type;
		this._custom_option_title = _custom_option_title;
		this._custom_option_is_required = _custom_option_is_required;
		this._custom_option_price = _custom_option_price;
		this._custom_option_sku = _custom_option_sku;
		this._custom_option_max_characters = _custom_option_max_characters;
		this._custom_option_sort_order = _custom_option_sort_order;
		this._custom_option_row_title = _custom_option_row_title;
		this._custom_option_row_price = _custom_option_row_price;
		this._custom_option_row_sku = _custom_option_row_sku;
		this._custom_option_row_sort = _custom_option_row_sort;
	}
	public String toString(){
		return _custom_option_store+","+_custom_option_type+","+_custom_option_title+","+_custom_option_is_required+","+_custom_option_price+","+_custom_option_sku+","+_custom_option_max_characters+","+_custom_option_sort_order+","+_custom_option_row_title+","+_custom_option_row_price+","+_custom_option_row_sku+","+_custom_option_row_sort+",";
	}
	public String to2String(){
		return _custom_option_store+","+""+","+""+","+""+","+""+","+""+","+""+","+_custom_option_sort_order+","+_custom_option_row_title+","+_custom_option_row_price+","+_custom_option_row_sku+","+_custom_option_row_sort+",";
	}
	
}
