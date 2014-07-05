/*
 * ImageRelated.java	 <br>
 * 2011-10-12<br>
 * com.shinetech.bean <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinetech.util.Const;
import com.shinetech.util.ImageUtil;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImageRelated {
	private  String image;
	private  String image_label = "";
	private  String small_image;
	private  String small_image_label = "";
	private  String thumbnail;
	private  String thumbnail_label = "";
	private List<Image> imgList = new ArrayList<Image>();
	
	/**
	 * 目的和功能：将一个产品和图片相关的属性初始化，全部写入这个类中
	 * 注意事项：该类可提供一个自定义的toString方法
	 * @param imgList
	 */
	public ImageRelated(List<Image> imgList){
		List<Integer> list = new ArrayList<Integer>();
		Map<Integer,Image> map = new HashMap<Integer, Image>();
		Integer posi = 0;
		for(Image imageBean : imgList){
			 posi = Integer.parseInt(imageBean.get_media_position());
			list.add(posi);
			map.put(posi,imageBean);
		}
		Collections.sort(list);
		if(!list.isEmpty()){
			Image imageBean = map.get(list.get(0));
			this.image = imageBean.get_media_image();
			String temp = image.substring(7);
			this.image_label = imageBean.get_media_lable();
			int start = image.lastIndexOf("/");
			this.small_image = "/small/s_"+image.substring(start+1);
			this.small_image_label = image_label;
			this.thumbnail = "/thumb/t_"+image.substring(start+1);
			this.thumbnail_label = image_label;
			
			/* 图片先不生产*/
			 try {
				ImageUtil.thumbnailator(Const.ENTRY_DIR+temp, Const.ENTRY_DIR+small_image, Const.WIDTH_SMAll, Const.HIGHT_SMALL);
				ImageUtil.thumbnailator(Const.ENTRY_DIR+temp, Const.ENTRY_DIR+thumbnail, Const.WIDTH_THUMB, Const.HIGHT_THUMB);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.imgList = imgList;
			imgList.add(new Image("80", small_image, small_image_label, ++posi+"", "1"));//这种缩写图是不可见的
			imgList.add(new Image("80", thumbnail, thumbnail_label, ++posi+"", "1"));
		}
	}
	public ImageRelated(){
		this.image = "";
		this.image_label = "";
		this.small_image = image;
		this.small_image_label = image_label;
		this.thumbnail = image;
		this.thumbnail_label = image_label;
	}
	/*
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/*
	 * @return the image_label
	 */
	public String getImage_label() {
		return image_label;
	}
	/*
	 * @return the small_image
	 */
	public String getSmall_image() {
		return small_image;
	}
	/*
	 * @return the small_image_label
	 */
	public String getSmall_image_label() {
		return small_image_label;
	}
	/*
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}
	/*
	 * @return the thumbnail_label
	 */
	public String getThumbnail_label() {
		return thumbnail_label;
	}
	/*
	 * @return the imgList
	 */
	public List<Image> getImgList() {
		return imgList;
	}
	/*
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/*
	 * @param image_label the image_label to set
	 */
	public void setImage_label(String image_label) {
		this.image_label = image_label;
	}
	/*
	 * @param small_image the small_image to set
	 */
	public void setSmall_image(String small_image) {
		this.small_image = small_image;
	}
	/*
	 * @param small_image_label the small_image_label to set
	 */
	public void setSmall_image_label(String small_image_label) {
		this.small_image_label = small_image_label;
	}
	/*
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	/*
	 * @param thumbnail_label the thumbnail_label to set
	 */
	public void setThumbnail_label(String thumbnail_label) {
		this.thumbnail_label = thumbnail_label;
	}
	/*
	 * @param imgList the imgList to set
	 */
	public void setImgList(List<Image> imgList) {
		this.imgList = imgList;
	}
	public String toString(){
		return image+","+image_label+","+small_image+","+small_image_label+","+thumbnail+","+thumbnail_label+",";
	}
}
