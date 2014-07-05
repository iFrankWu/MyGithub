/*
 * PetticoatService.java	 <br>
 * 2011-11-15<br>
 * com.shinetech.simple.petticoat <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple.special;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.bean.CustomOption;
import com.shinetech.bean.Image;
import com.shinetech.bean.ImageRelated;
import com.shinetech.dao.DressDAO;
import com.shinetech.handle.ImageHandle;
import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class SpecialService {

	
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
		categoryList.add(SimpleProductConfig.NotActiveCategory.get(2).split("/")[1]);
	}
	
	 
	 
	public String getHasOption(){
		return "1";
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
	private DressDAO dao = new DressDAO();
	/**
	 * 功能：对图片相关赋值全在这里 <br>
	 * 注意事项：<font color="red">描述信息还没有正确生产</font> <br>
	 * @return
	 * @throws DBException
	 * ImageRelated
	 */
	public ImageRelated genericImageRelated(long productId) throws DBException{
		ImageHandle handle = new ImageHandle(name);
		dao.getImageByProId(productId, handle);
		List<Image> imageList = handle.getImageList();
		ImageRelated imgRelated = new ImageRelated(imageList);
		return imgRelated;
	}
	private String shownColor = "";
	/**
	 * 以下生成自定义的颜色、尺寸以及尺寸的子尺寸
	 * */
	public void genericCustonOptionColor(List<CustomOption> customList,String shownColor,int adjustment) throws DBException{
		this.shownColor = shownColor;
//		ColorHandle handle = new ColorHandle();
//		Set<String> colors = handle.getColorSet(getTags());
		List<String> colors = getColors(adjustment);
		 
		//经过打印结果知道，这里颜色值确实有空值。
		String colorss [][] = new String[colors.size()][];
		int index = 0;
		for(String color : colors){
			colorss[index] = new String[]{color,"0"};
			index++;
		}
		genericCustomOption("Color",colorss,customList,"1");
	}
	public void genericCustonOptionSize(List<CustomOption> customList){
//		List<String> sizes = Arrays.asList("10","20","30","custom");
		genericCustomOption("Size",SimpleProductConfig.sizes,customList,"2");
	}
	
	private void genericCustomOption(String _custom_option_title,String[][] _custom_option_row ,List<CustomOption> customList,String sortOrder){
		int i = 0;
		for(String title[] : _custom_option_row){
			CustomOption colorCustom ;
			String posi = (i+1)+"";//标记各个选项的位置
			if(title[0].trim().equalsIgnoreCase(SimpleProductConfig.THE_SAME_AS_PICTURE)){
				if(this.shownColor != null && !this.shownColor.trim().equals("")){
					title[0] += " ( "+this.shownColor.replace("_", " ")+" )";
				}
				posi = "0";
			}
			if(i == 0 ){
				 colorCustom = new CustomOption("", "drop_down", _custom_option_title, "1", "", "", "0", sortOrder, title[0], title[1], "", posi);
			}else{
				
				 colorCustom = new CustomOption("", "", "", "", "", "", "", "", title[0], title[1], "", posi);
			}
			customList.add(colorCustom);
			i++;
		}
		i = 0;
		for(String title[] : _custom_option_row){
			if(i > 0){
				CustomOption colorCustom = new CustomOption("default", "", "", "", "", "", "", "", title[0], "", "", "");
				customList.add(colorCustom);
			}
			i++;
		}
	}
//	public void genericCustonOptionPannier(List<CustomOption> customList){
//		customList.add(new CustomOption("", "checkbox", "Skirt", "0", "", "", "", "3", "Sleected", "12", "", "1"));
//	}
	public void genericCustonOptionCustomSize(List<CustomOption> customList){
		customList.add(new CustomOption("", "field", "Bust", "0", "0", "", "0", "4", "", "", "", ""));
		customList.add(new CustomOption("", "field", "Waist", "0", "0", "", "0", "5", "", "", "", ""));
		customList.add(new CustomOption("", "field", "Hips", "0", "0", "", "0", "6", "", "", "", ""));
		customList.add(new CustomOption("", "field", "Hollow to Floor", "0", "0", "", "0", "7", "", "", "", ""));
	}
 
	private List<String> getColors(int adjustment){
		List<String> colors = new ArrayList<String>();
		colors.addAll(SimpleProductConfig.otherColors);
		colors.remove("Shown Color");
		return colors;
	}

}
