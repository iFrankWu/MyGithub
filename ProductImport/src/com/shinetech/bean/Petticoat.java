/*
 * Petticoat.java	 <br>
 * 2011-11-15<br>
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

public class Petticoat {
	private String name;
	private String metaTitle;
	private String metaDesci;
	private String decription;
	private String image;
	private String short_description;
	private String keywords;
	/*
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	/*
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/*
	 * @return the short_description
	 */
	public String getShort_description() {
		return short_description;
	}
	/*
	 * @param short_description the short_description to set
	 */
	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}
	/*
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/*
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/*
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/*
	 * @return the metaTitle
	 */
	public String getMetaTitle() {
		return metaTitle;
	}
	/*
	 * @return the metaDesci
	 */
	public String getMetaDesci() {
		return metaDesci;
	}
	/*
	 * @return the decription
	 */
	public String getDecription() {
		return decription;
	}
	/*
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/*
	 * @param metaTitle the metaTitle to set
	 */
	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}
	/*
	 * @param metaDesci the metaDesci to set
	 */
	public void setMetaDesci(String metaDesci) {
		this.metaDesci = metaDesci;
	}
	/*
	 * @param decription the decription to set
	 */
	public void setDecription(String decription) {
		this.decription = decription;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Petticoat [name=" + name + ", metaTitle=" + metaTitle
				+ ", metaDesci=" + metaDesci + ", decription=" + decription
				+ ", image=" + image + "]";
	}
 
}
