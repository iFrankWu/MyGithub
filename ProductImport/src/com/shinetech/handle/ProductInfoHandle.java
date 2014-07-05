/*
 * ProductInfoHandle.java	 <br>
 * 2011-10-12<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.io.File;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.shinetech.action.ImportProduct;
import com.shinetech.bean.Statics9DAttribute;
import com.shinetech.dao.DressDAO;
import com.shinetech.simple.SimpleProductImport;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProductInfoHandle implements ResultSetHandler {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(this.getClass());
	private int validProductCount = 0;
	/**
	 * COUNT_OF_PRODUCNTS:用来控制每次生成的产品数目
	 */ 
	private static int START_PRODUCNT_ID ;
	private static int COUNT;
	private boolean isUpdate = false;
	/**
	 * 目的和功能：构造方法,构造ProductInfoHandle类的一个实例 
	 * 注意事项：
	 * @param isConfigurable 是否是生产可配置产品
	 * @param isUpdate 是否是更新产品
	 */
	public ProductInfoHandle(boolean isConfigurable,boolean isUpdate){
		this.isConfigurable = isConfigurable;
		this.isUpdate = isUpdate;
		if(validProductCount == 0){
			DressDAO dao = new DressDAO();
			try {
				validProductCount = dao.getValidProductCount();
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		if(START_PRODUCNT_ID == 0 || COUNT == 0){
			FileOperate op = new FileOperate();
			op.getLine(new File("assets/count_of_product"),new ILineHandle<String>() {

				@Override
				public String lineHandle(String line) {
					if(line.startsWith("#")){
						return null;
					}
					String temp [] = line.split("=");
					if(temp[0].equals("start_product_id")){
						START_PRODUCNT_ID = Integer.parseInt(temp[1]);
					}else if(temp[0].equals("count_prodcut")){
						COUNT = Integer.parseInt(temp[1]);
					}
					return null;
				}
			});
		}
	}
	private boolean isConfigurable = true;
	private String weight;
	public String getWeight(){
		return weight;
	}
	@Override
	public void handle(ResultSet rs) {
			try {
				Statics9DAttribute statics9DAttribute = 
					new Statics9DAttribute(
							rs.getString("9dresses_producut_name"), 
							rs.getString("9dress_itemcode"), 
							rs.getFloat("9dresses_market_price"), 
							rs.getFloat("9dresses_real_price"), 
							rs.getFloat("discount"),
							rs.getString("weight"),
							rs.getLong("id"), 
							rs.getInt("sort_score"),
							rs.getInt("cost_price"),
							rs.getString("mg_description"),
							rs.getString("mg_short_description"),
							rs.getString("mg_meta_title"),
							rs.getString("mg_meta_keywords"),
							rs.getString("mg_meta_description"),
							rs.getString("color_showncolor"),
							rs.getString("price_status"),
							rs.getInt("old_cost_price"),
							rs.getFloat("price_factor"),
							rs.getInt("color_adjustment")
							);
				handleAProduct(statics9DAttribute);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	private int count = 0;
	public void handleAProduct(Statics9DAttribute statics9DAttribute) throws Exception{
		Long productId = statics9DAttribute.getProductId();
		if(isConfigurable){
			ImportProduct imptProduct = new ImportProduct(productId);
			imptProduct.genericConfigurableProduct();
		}else{
//			if(statics9DAttribute.getProductId() < 500){
//				return;
//			}
//			if(statics9DAttribute.getProductId() > 1){//500
////				return;2685
//				System.exit(1);
//			}
			if(statics9DAttribute.getProductId() < START_PRODUCNT_ID){
				return;
			}
			if(count >= COUNT){
				return;
			}
			SimpleProductImport imptProduct = new SimpleProductImport(statics9DAttribute);
			imptProduct.genericSimpleProduct(this.isUpdate);
			count++;
//			logger.info("ProductId:"+statics9DAttribute.getProductId()+"\tHandled Count:"+count+"\tValid Count:"+validProductCount);
		}
		}
}
