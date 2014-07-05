/*
 * ProductService.java	 <br>
 * 2011-10-11<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shinetech.bean.CustomOption;
import com.shinetech.bean.Image;
import com.shinetech.bean.ImageRelated;
import com.shinetech.bean.SuperProducts;
import com.shinetech.dao.DressDAO;
import com.shinetech.handle.ColorHandle;
import com.shinetech.handle.CustonAttributeHandle;
import com.shinetech.handle.ImageHandle;
import com.shinetech.handle.TagHandle;
import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.exception.DBException;

/**
 * 将导入产品时需要用到一些相关方法集中写道这里
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProductService {
	private Long productId ;
	public ProductService(Long productId){
		this.productId = productId;
	}
	
	private DressDAO dao = new DressDAO();
	
	public boolean isConfigurable = true;
	public String getProductSku(){
		if(isConfigurable)
			return "test_import_product";
		return "test_import_product-1";
	}
	public String getAttributeSet(){
		return "Attribute Set Test";
	}
	public void getCategory(List<String> categoryList){
		if(isConfigurable){
			if(categoryList.isEmpty()){
				genericCategory(categoryList);
			}
		}
	}
	/**
	 * 测试时使用数据
	 * */
	public void genericCategory(List<String> categoryList){
		String []s = "Tools,Dress/Girl Dress,Dress/Boy Dress,Furniture".split(",");
		categoryList.addAll(Arrays.asList(s));
	}
	
	public String getDescription(){
		return "Description";
	}
	public String getDiscount(){
		return "20";
	}
	public String getHasOption(){
		if(isConfigurable)
			return "1";
		return "0";
	}
	public String getImage(int posi){
		if(posi == 1 && isConfigurable){
			return "/0/6/065fa818a0fc8fa14aedbc87.jpg";
		}
		return "";
	}
	public String getImageLabel(int posi){
		if(posi == 1 && isConfigurable){
			return "image label 1";
		}
		return "";
	}
	public String getMetaDescription(){
		return "Meta Description";
	}
	public String getMetaKeyword(){
		return "Meta Keyword";
	}
	public String getMetaTitle(){
		return "Meta Title";
	}
	public String getName(){
		return "Test Product";
	}
	/**
	 *  返回二者之一
	 * */
	public String getOptionContainer(){
		String s = "Product Info Column";
		s = "Block after Info Column";
		return s;
	}
	public String getPrice(){
		return "1000";
		/*if(isConfigurable){
			return "1000";
		}
		return "1010";*/
	}
	public String getRequiredOptions(){
		if(isConfigurable)
			return "1";
		return "0";
	}
	public String getShortDescription(){
		return "Short Description";
	}
	private String sizes[] = {"10","15","20","25","custom"};
	public String getSize(int i){
		if(i == -1){
			return "";
		}else{
			if( i < sizes.length)
				return sizes[i];
			return "";
		}
	}
	public String getSmallImage(){
		return "/a/c/acabe45fc58033bd6b67c06c29f0b828.jpg";
	}
	public String getSmallImageLabel(){
		return "image label 3";
	}
	public String getTaxClassId(){
		/*
		 * 	0==>None
			2==>Taxable Goods
			4==>Shipping
		 * */
		return "0";
	}
	public String getThumbnail(){
		if(isConfigurable)
			return "/0/0/008.jpg";
		return "";
	}
	public String getThumbnailLabel(){
		if(isConfigurable)
			return "image label 2";
		return "";
	}
	public String getUrlKey(){
		if(isConfigurable)
			return "test-product-sku";
		return "test-product-sku-1";
	}
	public String getUrlPath(){
		if(isConfigurable)
			return "test-product-sku";
		return "test-product-sku-1";
	}
	public String getWeight(){
		if(isConfigurable){
			return "";
		}
		return "0.4";
	}
	public String getQty(){
		if(isConfigurable)
				return "0";
		return "10000000";
	}
	private List<String> customOptionRowTitleList = new ArrayList<String>();
	public String getCustomOptionRowTitle(){
		if(isConfigurable){
			String s = "";
			if(customOptionRowTitleList.isEmpty()){
				genericCustomOptionRowTitle();
			}
			for(String title: customOptionRowTitleList){
				s += title+",";
			}
			if(s.endsWith(",")){
				s = s.substring(0,s.length()-1);
			}
			return s; 
		}
		return "";
	}
	public void genericCustomOptionRowTitle(){
		customOptionRowTitleList = Arrays.asList("Blue,Green,Red,White,Yello".split(","));
	}
	/**
	 * 通过尺寸大小，生产简单产品，产品名由配置产品名和尺寸组成，如：${sku}_${size_option}
	 * */
	public List<SuperProducts> genericSupperProducts(){
		List<SuperProducts> superProductsList = new ArrayList<SuperProducts>();
		for(String sz :sizes){
			SuperProducts superPro = new SuperProducts(getProductSku(), "size", sz, "5");
			superProductsList.add(superPro);
		}
		return superProductsList; 
	}
	 
	/**
	 * 功能：对图片相关赋值全在这里 <br>
	 * 注意事项：<font color="red">描述信息还没有正确生产</font> <br>
	 * @return
	 * @throws DBException
	 * ImageRelated
	 */
	public ImageRelated genericImageRelated() throws DBException{
		ImageHandle handle = new ImageHandle("");
		dao.getImageByProId(productId, handle);
		List<Image> imageList = handle.getImageList();
		ImageRelated imgRelated = new ImageRelated(imageList);
		return imgRelated;
		/*
		imageList.add(new ImgaeBean("80", "/1/2/1253333261036.jpg", "image label 1", "1", "0"));
		imageList.add(new ImgaeBean("80","/0/6/065fa818a0fc8fa14aedbc87.jpg","image label 1","1","0"));
		imageList.add(new ImgaeBean("80","/a/c/acabe45fc58033bd6b67c06c29f0b828.jpg","image label 3","3","1"));
		imageList.add(new ImgaeBean("80","/0/0/008.jpg","image label 2","2","1"));
		imageList.add(new ImgaeBean("80","/1/2/1253333261036.jpg","image label 4","4","0"));
		imageList.add(new ImgaeBean("80","/1/2/1280725787211.jpg","image label 5","5","0"));
		imageList.add(new ImgaeBean("80","/1/2/1280725994559.jpg","image label 6","6","0"));
		imageList.add(new ImgaeBean("80","/1/2/1253332412177.jpg","image label 7","7","0"));
		imageList.add(new ImgaeBean("80","/1/2/1253332884508.jpg","image label 8","8","0"));
		imageList.add(new ImgaeBean("80","/1/2/1280725955691.jpg","image label 9","9","0"));
		 */
	}
	/**
	 * 属性以及对应的选中项，都存放一个map中
	 * */
	public Map<String,List<String>> genericAttributeOptions() throws DBException{
		CustonAttributeHandle handle = new CustonAttributeHandle(SimpleProductConfig.productTypes[0]);
		dao.getTagInfoByPId(productId, handle);
		Map<String,List<String>> map = handle.getAttrOptionMap();
		return map;
	}
	private List<String> tags;
	private List<String> getTags() throws DBException{
		if(tags == null){
			tags = new ArrayList<String>();
			TagHandle tagHandle = new TagHandle();
			dao.getTagInfoByPId(productId, tagHandle);
			tags = tagHandle.getTags();
		}
		return tags;
	}
	public void genericCustonOptionColor(List<CustomOption> customList) throws DBException{
		ColorHandle handle = new ColorHandle();
		Set<String> colors = handle.getColorSet(getTags());
		int i = 0;
		for(String color : colors){
			CustomOption colorCustom ;
			if(i == 0 ){
				 colorCustom = new CustomOption("", "drop_down", "Color", "1", "", "", "0", "", color, "0", "", "0");
			}else{
				 colorCustom = new CustomOption("", "", "", "", "", "", "", "", color, "0", "", "0");
			}
			customList.add(colorCustom);
			i++;
		}
		i = 0;
		for(String color : colors){
			if(i > 0){
				CustomOption colorCustom = new CustomOption("default", "", "", "", "", "", "", "", color, "", "", "");
				customList.add(colorCustom);
			}
			i++;
		}
	}
}
