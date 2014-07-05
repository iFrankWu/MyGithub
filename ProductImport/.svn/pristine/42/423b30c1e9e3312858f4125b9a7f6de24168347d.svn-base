/*
 * PetticoatImport.java	 <br>
 * 2011-11-15<br>
 * com.shinetech.simple.petticoat <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple.petticoat;

import org.apache.log4j.Logger;

import com.shinetech.bean.ImageRelated;
import com.shinetech.bean.Statics9DAttribute;
import com.shinetech.dao.DressDAO;
import com.shinetech.simple.SimpleProduct;
import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.DateUtil;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class PetticoatImport { 
	private PetticoatService service ;
	private Statics9DAttribute statics9DAttribute ;
	private static int index = 0;
	private DressDAO dao = new DressDAO();
	public PetticoatImport(Statics9DAttribute statics9DAttribute){
		this.statics9DAttribute = statics9DAttribute;
		service	= new PetticoatService(index++);
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
			boolean needUpdate = false;
			product.required_options = "1";
			product.has_options = "0";
			product._attribute_set = SimpleProductConfig.AttributeSets[0];
			product._type = "simple";
			product.created_at = DateUtil.getNowDate();
		
			if(statics9DAttribute.getDiscount() != null && statics9DAttribute.getDiscount() > 0){
				product.discount = statics9DAttribute.getDiscount()+"";
			}else{
				 throw new RuntimeException("衬裙折扣值为空");
			}
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
				 throw new RuntimeException("衬裙名为空");
			}else{
				product.name = statics9DAttribute.get_9dName();
			}
			service.setName(product.name);
			product.sku = statics9DAttribute.getProductId()+"";
			
			if(statics9DAttribute.get_9dMarketPrice() != null && statics9DAttribute.get_9dMarketPrice() > 0){
				product.price = statics9DAttribute.get_9dMarketPrice()+"";
			}else{
				throw new RuntimeException("衬裙名市场价和实际价空");
			}
//			product.meta_description = service.getMetaDescription();//这里包含价格，估计描述不正确
//			product.meta_keyword = statics9DAttribute.get_9dName();
//			product.meta_title = service.getMetaTitle();
			
			if(statics9DAttribute.getMg_meta_description() == null || statics9DAttribute.getMg_meta_description().trim().equals("")){
				String meta_description = service.getMetaDescription();
				statics9DAttribute.setMg_meta_description(meta_description);
				needUpdate = true;
				product.meta_description = statics9DAttribute.getMg_meta_description();
			}else{
				product.meta_description = "\""+statics9DAttribute.getMg_meta_description()+"\"";
			}
			
			
//			if(statics9DAttribute.getMg_meta_keywords() == null){
//				 needUpdate = true;
//				 statics9DAttribute.setMg_meta_keywords(service.getMetaKeyword());
//			}
//			product.meta_keyword = statics9DAttribute.getMg_meta_keywords();
			product.meta_keyword = "";
			if(statics9DAttribute.getMg_meta_title() == null || statics9DAttribute.getMg_meta_title().trim().equals("")){
				needUpdate = true;
				statics9DAttribute.setMg_meta_title(service.getMetaTitle());
			}
			product.meta_title = statics9DAttribute.getMg_meta_title();
			
			
			/**
			 * 图片相关操作
			 * */
			ImageRelated imgRelated = service.genericImageRelated();
			product.imgRelated = imgRelated;
			
			if(statics9DAttribute.get_9dItemCode() == null || statics9DAttribute.get_9dItemCode().trim().equals("")){
				throw new RuntimeException("衬裙itemcode为空");
			}
			product.itemcode = statics9DAttribute.get_9dItemCode();
			product.url_key = "p/"+statics9DAttribute.get_9dName().replace(" ", "-").toLowerCase().replace("/", "-")+"-"+product.sku;
			product.url_path = statics9DAttribute.get_9dName().replace(" ", "-").toLowerCase().replace("/", "-")+"-"+product.sku+".html";
			if(statics9DAttribute.getWeight() == null){
				throw new RuntimeException("衬裙weight为空");
			}
			product.weight = statics9DAttribute.getWeight();
			if(statics9DAttribute.getMg_description() == null || statics9DAttribute.getMg_description().trim().equals("")){
				needUpdate = true;
				statics9DAttribute.setMg_description(service.getDescription());
				product.description = statics9DAttribute.getMg_description();
			}else{
				product.description = "\""+statics9DAttribute.getMg_description()+"\"";
			}
			
			
			service.getCategory(product.categoryList);	
//			product.short_description = statics9DAttribute.get_9dName();
			if(statics9DAttribute.getMg_short_description() == null || statics9DAttribute.getMg_short_description().trim().equals("")){
				needUpdate =true;
				statics9DAttribute.setMg_short_description(service.getShortDescription());
				product.short_description = statics9DAttribute.getMg_short_description();
			}else{
				product.short_description = "\""+statics9DAttribute.getMg_short_description()+"\"";
			}
			product.genericSimpleProduct();
			if(needUpdate){
				dao.update9Dresses(statics9DAttribute.getProductId(), statics9DAttribute.get_9dName(), 
						statics9DAttribute.get_9dItemCode(),
						statics9DAttribute.get_9dMarketPrice(), 
						statics9DAttribute.get_9dRealPrice(), statics9DAttribute.getDiscount(),statics9DAttribute.getWeight(),
						statics9DAttribute.getSortScore(),
//						statics9DAttribute.getCostPrice(),
						statics9DAttribute.getMg_description(),statics9DAttribute.getMg_short_description(),statics9DAttribute.getMg_meta_title(),
						statics9DAttribute.getMg_meta_keywords(),statics9DAttribute.getMg_meta_description());
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("产品："+statics9DAttribute.getProductId()+"\t"+e.getMessage());
		}
	}

}
