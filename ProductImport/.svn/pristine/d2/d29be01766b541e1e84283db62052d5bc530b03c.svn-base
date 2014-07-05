/*
 * ImportProduct.java	 <br>
 * 2011-10-10<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.util.List;
import java.util.Map;

import com.shinetech.bean.ImageRelated;
import com.shinetech.bean.Product;
import com.shinetech.bean.SuperProducts;
import com.shinetech.service.ProductService;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.DateUtil;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportProduct {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 * @throws DBException 
	 */
	public static void main(String[] args) throws DBException {
		ImportProduct importProdcut = new ImportProduct(1l);
		importProdcut.genericConfigurableProduct();
	}
	private static ProductService service ;
	public ImportProduct(Long productId){
		service	= new ProductService(productId);
	}
	/**
	 * 功能：生产可配置产品
	 * 
	 *  自定义属性和图片属性 稍后处理<br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 * @throws DBException 
	 */
	public void genericConfigurableProduct() throws DBException{
		Product product = new Product();
		service.isConfigurable = true;
		product.sku = service.getProductSku();//这是根据产品名生成的
		product.required_options = "1";
		product.has_options = "1";
		product._attribute_set = service.getAttributeSet();
		product._type = "configurable";
		service.getCategory(product.categoryList);
		product.created_at = DateUtil.getNowDate();
		product.description = service.getDescription();
		product.discount  = service.getDiscount();
		product.has_options = service.getHasOption();
		
		product.meta_description = service.getMetaDescription();
		product.meta_keyword = service.getMetaKeyword();
		product.meta_title = service.getMetaTitle();
		product.name = service.getName();
		product.options_container = service.getOptionContainer();
		product.price = service.getPrice();
		product.required_options = service.getRequiredOptions();
		product.short_description = service.getShortDescription();
		product.size = service.getSize(-1);
		product.tax_class_id = service.getTaxClassId();
		product.url_key = service.getUrlKey();
		product.url_path = service.getUrlPath();
		product.weight = service.getWeight();
		product.qty = service.getQty();
		
		/**
		 * 增加自定义颜色的处理，自定义 选项 custom option 可能有多个，都写如这个list中
		 * */
		service.genericCustonOptionColor(product.customOptionList);
		
		
		/**
		 * 图片相关操作
		 * */
		ImageRelated imgRelated = service.genericImageRelated();
		product.imgRelated = imgRelated;
		
		/**
		 * 对属性及其可选项的操作，全部写入map中
		 * */
		Map<String,List<String>> attrMap =  service.genericAttributeOptions();
		product.attrMap = attrMap;
		
		/**由可配置产品生产的简单产品*/
		List<SuperProducts> superProductsList = service.genericSupperProducts();
		product.superProductsList = superProductsList;
		
		product.genericConfigPro();
		
		/**
		 * 生成简单产品
		 * */
		for(SuperProducts superPro:superProductsList){
			Product pro = genericSimpleProduct(superPro);
			pro.genericSimplePro();
		}
		System.exit(1);
	}
	/**
	 * 功能：生产简单产品 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	public Product genericSimpleProduct(SuperProducts superProducts){
		Product product = new Product();
		service.isConfigurable = false;
		product.sku = superProducts.get_super_products_sku();//这是根据产品名生成的
		product.required_options = "1";
		product.has_options = "0";
		product._attribute_set = service.getAttributeSet();
		product._type = "simple";
		//service.getCategory(product.categoryList);
		product.created_at = DateUtil.getNowDate();
		product.description = service.getDescription();
		product.discount  = service.getDiscount();
		product.has_options = service.getHasOption();
		product.meta_description = service.getMetaDescription();
		product.meta_keyword = service.getMetaKeyword();
		product.meta_title = service.getMetaTitle();
		product.name = superProducts.get_super_products_sku();//service.getName();
		product.options_container = service.getOptionContainer();
		
		int p = Integer.parseInt(superProducts.get_super_attribute_price_corr())+Integer.parseInt(service.getPrice());
		product.price = p+"";
		product.required_options = service.getRequiredOptions();
		product.short_description = service.getShortDescription();
		product.size = superProducts.get_super_attribute_option();
		product.tax_class_id = service.getTaxClassId();
		product.url_key = service.getUrlKey();
		product.url_path = service.getUrlPath();
		product.weight = service.getWeight();
		product.qty = service.getQty();
		return product;
	}
}
