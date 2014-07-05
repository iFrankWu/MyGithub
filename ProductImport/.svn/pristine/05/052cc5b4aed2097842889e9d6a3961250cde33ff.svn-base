/*
 * PetticoatService.java	 <br>
 * 2011-11-15<br>
 * com.shinetech.simple.petticoat <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple.petticoat;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.bean.Image;
import com.shinetech.bean.ImageRelated;
import com.shinetech.bean.Petticoat;
import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class PetticoatService {

	private int index = 0;
	private List<Petticoat> list;
	public PetticoatService(int index){
		this.index = index;
		try {
			list = PetticoatInfo.getInfo(SimpleProductConfig.petticoatXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * name:产品名
	 */ 
	private String name ;
	public void setName(String name){
		this.name = name;
	}
 
	/**
	 * 功能：这里通过属性计算出来的 <br>
	 * 注意事项：<font color="red">未实现</font> <br>
	 * @return
	 * String
	 */
	public String getAttributeSet(){
		return SimpleProductConfig.AttributeSets[1];
	}
	/**
	 * 功能：得到产品的类别 <br>
	 * 注意事项：<font color="red">未实现</font> <br>
	 * @param categoryList
	 * void
	 */
	public void getCategory(List<String> categoryList){
//		categoryList.add("Default Category");
		categoryList.add(SimpleProductConfig.NotActiveCategory.get(0).split("/")[1]);
	}
	
	/**
	 * 功能：得到产品描述 <br>
	 * 注意事项：<font color="red">有些产品的某些分类下的tag没有值，在此处没有报错</font> <br>
	 * @return
	 * String
	 */
	public String getDescription(){
		return "\""+list.get(index).getDecription()+"\"";
	}
	 
	public String getShortDescription(){
		return list.get(index).getShort_description();
	}
	 
	public String getHasOption(){
		return "1";
	}
	 
	 
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @return
	 * String
	 */
	public String getMetaDescription(){
		return "\""+list.get(index).getMetaDesci()+"\"";
	}
	public String getMetaKeyword(){
		return "";
	}
	public String getMetaTitle(){
		return "\""+list.get(index).getMetaTitle()+"\"";
	}
	 
	/**
	 *  返回二者之一
	 * */
	public String getOptionContainer(){
		String s = "Product Info Column";
		s = "Block after Info Column";
		return s;
	}
 
	 
	 
	public String getRequiredOptions(){
		return "1";
	}
	public String getTaxClassId(){
		/*
		 * 	0==>None
			2==>Taxable Goods
			4==>Shipping
		 * */
		return "4";
	}
	 
 
	public String getQty(){
		return "10000000";
	}
	 
	/**
	 * 功能：对图片相关赋值全在这里 <br>
	 * 注意事项：<font color="red">描述信息还没有正确生产</font> <br>
	 * @return
	 * @throws DBException
	 * ImageRelated
	 */
	public ImageRelated genericImageRelated() throws DBException{
		String imagePath = "/image/"+list.get(index).getImage();
		List<Image> imageList = new ArrayList<Image>();
		imageList.add(new Image("80",imagePath,name ,1+"","0"));
		ImageRelated imgRelated = new ImageRelated(imageList);
		return imgRelated;
	}


}
