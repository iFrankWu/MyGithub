/*
 * UpdateSampleProduct.java	 <br>
 * 2011-12-8<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.service;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.shinetech.dao.AttributeDAO;
import com.shinetech.dao.DressDAO;
import com.shinetech.dao.MagentoProductDAO;
import com.shinetech.handle.EditUpdateHandle;
import com.shinetech.handle.ProductInfoHandle;
import com.shinetech.handle.TagCatatogHandle;
import com.shinetech.magento.entity.Attribute;
import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * 这个类用来更新部分产品
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class UpdateSampleProductService {
	public static void main(String[] args) {
		Const.initLogger();
		UpdateSampleProductService update = new UpdateSampleProductService();
		update.test();
	}
	/**
	 * 功能：更新产品 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	public void updateProduct(){
		try{
			dressDAO.getNeedUpdateProdctIds(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
						EditUpdateHandle handle = new EditUpdateHandle();
						long productId = rs.getLong("product_id");
						System.out.println(productId);
						dressDAO.getProductEditTypeByPid(productId,handle);
						Set<String> editTypeSet = handle.getEditTypeSet();
						if(editTypeSet.containsAll(Arrays.asList(SimpleProductConfig.EDIT_TYPES))){
							//表示该产品 tag pictuce product都修改了，需要大变更
						}else{
							if(editTypeSet.contains(SimpleProductConfig.EDIT_TYPES[0]) || editTypeSet.contains(SimpleProductConfig.EDIT_TYPES[1])){
								//如果是这样的产品，那么直接生产对于的csv文件，但去除图片和自动选择
								ProductInfoHandle pHandle = new ProductInfoHandle(false,true);
								dressDAO.getProductById(productId, pHandle);
								System.exit(1);
							}
							if(editTypeSet.contains(SimpleProductConfig.EDIT_TYPES[2])){
								//需要使用magento api 进行修改
							}
						}
//						dressDAO.updateDrLogUpdateStatus(productId);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void test(){
		try{
//			updateProductVarchar();
//			updateProductDecimal();
			updateProduct();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private DressDAO dressDAO = new DressDAO();
	private MagentoProductDAO pDAO = new MagentoProductDAO();
	private AttributeDAO attrDAO = new AttributeDAO();
	/**
	 * ATTRIBUTE:magento商城的属性
	 */ 
	private Attribute ATTRIBUTE = new Attribute();
	private int STORE_ID = 0;
	private int ENTITY_TYPE_ID = 4;
	
	public void updateProductVarchar() throws DBException{
		dressDAO.getProductInfo(new ResultSetHandler() {
			
			@Override
			public void handle(ResultSet rs) {
				try{
					Long productId = rs.getLong("id");
					String sku = productId+"";
					int entityId = pDAO.getProductId(sku);
					int attrId = attrDAO.getAttributeId(4, SimpleProductConfig.COLOR_CHART);
					TagCatatogHandle handle = new TagCatatogHandle();
					dressDAO.getTagInfoByPId(productId, handle);
					List<String> list = handle.getMainFabricList();
					if(list.size() == 0){
						System.err.println(productId);
					}
					String optionId ="";
					for(String tag : list){
						System.out.println(tag);
						optionId = attrDAO.getOptionId(attrId,tag)+",";
					}
					optionId = optionId.substring(0, optionId.length()-1);
					System.out.println(entityId+":"+attrId+":"+optionId+":"+list);
					pDAO.insertProductVarchar(ENTITY_TYPE_ID, attrId, STORE_ID, entityId, optionId);
					System.exit(1);
				}catch(Exception e){
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
	}
	
	/**
	 * 功能：更新价格和重量 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @throws DBException
	 * void
	 */
	public void updateProductDecimal() throws DBException{
		final int attributeId = attrDAO.getAttributeId(4, ATTRIBUTE._4_price);
		final Random random = new Random();
		dressDAO.getProductInfo(new ResultSetHandler() {

			@Override
			public void handle(ResultSet rs) {
				try{
					Long productId = rs.getLong("id");
					String sku = productId+"";
					int entityId = pDAO.getProductId(sku);
//					if(rs.getInt("old_cost_price") - rs.getInt("cost_price") == 219){
						int usePrice = (rs.getInt("old_cost_price")+ rs.getInt("cost_price"))/2;
						float price = (usePrice*rs.getFloat("price_factor"))/6+5;//得出的是
						int rp = Math.round(price);
						//尾数
						float mantissa = Float.parseFloat(SimpleProductConfig.discount_9[random.nextInt(SimpleProductConfig.discount_9.length)]);
						//实际售价 168.99
						float rprice = rp+mantissa;
						Float marketPrice = rprice*100/(100-rs.getInt("discount"));//这个价格是上传给商城的，商城根据这个值和折扣值计算产品实际售价			
						System.out.println(sku+":"+marketPrice);
//						System.exit(1);
						pDAO.insertProductDecimal(ENTITY_TYPE_ID, attributeId, STORE_ID, entityId, marketPrice);
//					}
				}catch(Exception e){
					
				}
			}
			
		});
	}
}
