/*
 * PetticoatImport.java	 <br>
 * 2011-11-15<br>
 * com.shinetech.simple.petticoat <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple.special;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.shinetech.bean.ImageRelated;
import com.shinetech.bean.Statics9DAttribute;
import com.shinetech.simple.SimpleProduct;
import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.DateUtil;

/**
 * onenote:///\\SERVER2\Onenote协作区\IT开发\2新软件站\9dresses.one#特殊产品数据&section-id={8FF6DC42-F18E-4379-ADD7-3D4CB53A4579}&page-id={35F84FD4-1065-485F-9F49-AFF8E88A9872}&object-id={F3A9BF4D-90B9-4F2C-A0CD-7045BC13A1DD}&2A
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class SpecialImport { 
	/**
	 * list:存放需要颜色和尺寸选项的id
	 */ 
	public static List<String> list = Arrays.asList("14989,14990,14991,14994,14995,14996".split(","));
	private SpecialService service = new SpecialService();
	private Statics9DAttribute statics9DAttribute ;
	public SpecialImport(Statics9DAttribute statics9DAttribute){
		this.statics9DAttribute = statics9DAttribute;
	}
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 功能：生产简单产品 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 * @throws DBException 
	 */
	public void genericSimpleProduct(){
		try{
			SimpleProduct product = new SimpleProduct();
			product.required_options = "1";
			product.has_options = "0";
			product._attribute_set = SimpleProductConfig.AttributeSets[0];
			product._type = "simple";
			product.created_at = DateUtil.getNowDate();
			product.has_options = service.getHasOption();
			product.options_container = service.getOptionContainer();
			product.required_options = service.getRequiredOptions();
			//product.size = service.getSize(0);
			product.tax_class_id = service.getTaxClassId();
			
			product.qty = service.getQty();
			/**
			 * 对属性及其可选项的操作，全部写入map中
			 * */
//			Map<String,List<String>> attrMap =  service.genericAttributeOptions();
//			product.attrMap = attrMap;
			
			if(statics9DAttribute.get_9dName() == null || statics9DAttribute.get_9dName().trim().equals("")){
				 throw new RuntimeException("特殊产品名为空");
			}else{
				product.name = statics9DAttribute.get_9dName();
			}
			service.setName(product.name);
			product.sku = statics9DAttribute.getProductId()+"";
			
			if(statics9DAttribute.get_9dMarketPrice() != null && statics9DAttribute.get_9dMarketPrice() > 0){
				product.price = statics9DAttribute.get_9dMarketPrice()+"";
			}else{
				throw new RuntimeException("特殊产品名市场价和实际价空");
			}
			product.meta_description = "\""+statics9DAttribute.getMg_meta_description()+"\"";
			product.meta_keyword = "";
			product.meta_title = statics9DAttribute.getMg_meta_title();
			
			/**
			 * 图片相关操作
			 * */
			ImageRelated imgRelated = service.genericImageRelated(statics9DAttribute.getProductId());
			product.imgRelated = imgRelated;
			
			if(statics9DAttribute.get_9dItemCode() == null || statics9DAttribute.get_9dItemCode().trim().equals("")){
				throw new RuntimeException("特殊产品itemcode为空");
			}
			product.itemcode = statics9DAttribute.get_9dItemCode();
			product.url_key = "p/"+statics9DAttribute.get_9dName().replace(" ", "-").toLowerCase().replace("/", "-")+"-"+product.sku;
			product.url_path = statics9DAttribute.get_9dName().replace(" ", "-").toLowerCase().replace("/", "-")+"-"+product.sku+".html";
//			if(statics9DAttribute.getWeight() == null){
//				throw new RuntimeException("特殊产品weight为空");
//			}
			product.weight = statics9DAttribute.getWeight();
			 
			product.description = "\""+statics9DAttribute.getMg_description()+"\"";
			if(statics9DAttribute.getDiscount() == null){
				product.discount = "0";
			}else{
				product.discount = statics9DAttribute.getDiscount()+"";
			}
			
			service.getCategory(product.categoryList);	
			
			product.short_description = "\""+statics9DAttribute.getMg_short_description()+"\"";
			
			if(list.contains(statics9DAttribute.getProductId()+"")){
				/**
				 * 增加自定义颜色的处理，自定义 选项 custom option 可能有多个，都写如这个list中
				 * */
				service.genericCustonOptionColor(product.customOptionList,statics9DAttribute.getShownColor(),statics9DAttribute.getColorAdjustMent());
				
				/**
				 * 处理自定义尺寸
				 * */
				service.genericCustonOptionSize(product.customOptionList);
	//			service.genericCustonOptionPannier(product.customOptionList);
				service.genericCustonOptionCustomSize(product.customOptionList);
			}
			
			product.genericSimpleProduct();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("产品："+statics9DAttribute.getProductId()+"\t"+e.getMessage());
		}
	}

}
