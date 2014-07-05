/*
 * ImgaeBean.java	 <br>
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

public class Image {
	private  String _media_attribute_id;//该值要生成
	private  String _media_image;
	private  String _media_lable;
	private  String _media_position;
	private  String _media_is_disabled;//是否显示，1不显示，0显示
	/*
	 * @return the _media_attribute_id
	 */
	public String get_media_attribute_id() {
		return _media_attribute_id;
	}
	/*
	 * @return the _media_image
	 */
	public String get_media_image() {
		return _media_image;
	}
	/*
	 * @return the _media_lable
	 */
	public String get_media_lable() {
		return _media_lable;
	}
	/*
	 * @return the _media_position
	 */
	public String get_media_position() {
		return _media_position;
	}
	/*
	 * @return the _media_is_disabled
	 */
	public String get_media_is_disabled() {
		return _media_is_disabled;
	}
	/*
	 * @param _media_attribute_id the _media_attribute_id to set
	 */
	public void set_media_attribute_id(String _media_attribute_id) {
		this._media_attribute_id = _media_attribute_id;
	}
	/*
	 * @param _media_image the _media_image to set
	 */
	public void set_media_image(String _media_image) {
		this._media_image = _media_image;
	}
	/*
	 * @param _media_lable the _media_lable to set
	 */
	public void set_media_lable(String _media_lable) {
		this._media_lable = _media_lable;
	}
	/*
	 * @param _media_position the _media_position to set
	 */
	public void set_media_position(String _media_position) {
		this._media_position = _media_position;
	}
	/*
	 * @param _media_is_disabled the _media_is_disabled to set
	 */
	public void set_media_is_disabled(String _media_is_disabled) {
		this._media_is_disabled = _media_is_disabled;
	}
	public Image(String _media_attribute_id, String _media_image,
			String _media_lable, String _media_position,
			String _media_is_disabled) {
		super();
		this._media_attribute_id = _media_attribute_id;
		this._media_image = _media_image;
		if(_media_image.startsWith("small_") || _media_image.startsWith("thumb_")){
			
		}
		this._media_lable = _media_lable;
		this._media_position = _media_position;
		this._media_is_disabled = _media_is_disabled;
	}
	public String toString(){
		return _media_attribute_id+","+_media_image+","+_media_lable+","+_media_position+","+_media_is_disabled+",";
	}
}
