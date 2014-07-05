/*
 * GenericOnlineInfo.java	 <br>
 * 2012-1-5<br>
 * com.shinetech.synchronize <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.synchronize;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;

/**
 * 生成线上产品信息，只依赖线上magento库
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class GenericOnlineInfo {
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess  db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	private FileOperate op = new FileOperate();
	private ProductInfoBean productInfoBean;
	private int itemcodeId,discountId;
	
	public static void main(String[] args) {
		Const.initLogger();
		GenericOnlineInfo generic = new GenericOnlineInfo();
		generic.setDB("219");
		generic.init();
		generic.traverseProduct();
	}
	public void setDB(String database){
		db.setDatabase(database);
	}
	public void init(){
		File file = new File("data/product_info.tsv");
		if(file.exists()){
			file.delete();
		}
		try{
			itemcodeId = getAttributeId(4,"itemcode");
			discountId = getAttributeId(4, "discount");
			System.out.println("itemcode:"+itemcodeId+"\tdiscount:"+discountId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public int getAttributeId(int entityTypeId,String attributeCode) throws DBException{
		String sql = "select attribute_id from eav_attribute where entity_type_id = ? and attribute_code = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attributeCode,FieldTypes.VARCHAR));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	public void traverseProduct(){
		String sql = "SELECT * FROM `catalog_product_entity`";
		try{
			db.handleList(sql, new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
						int entityId = rs.getInt("entity_id");
						String sku = rs.getString("sku");
						productInfoBean = new ProductInfoBean();
						productInfoBean.setSku(sku);
						handleProduct(entityId);
						productInfo(entityId);
						productInfo2(entityId);
						op.addContentToEndOfFile2("data/product_info.tsv", productInfoBean.toString());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void handleProduct(int entityId) throws DBException{
		String priceSql = "select value from catalog_product_entity_decimal where entity_id = ? and attribute_id = 67";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityId,FieldTypes.NUMERIC));
		@SuppressWarnings("unchecked")
		Float marketprice = (Float) db.queryPrepareFirst(Float.class, priceSql, fpList);
		productInfoBean.setMarketPrice(marketprice);
	}
	@SuppressWarnings("unchecked")
	private void productInfo(final int entityId){
		String sql = "SELECT attribute_id,value FROM `catalog_product_entity_varchar`where `entity_id` = ? and attribute_id in(63,74,76,"+discountId+","+itemcodeId+")";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityId,FieldTypes.NUMERIC));
		try{
			db.handleList(sql, fpList, new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
						int attributeId = rs.getInt("attribute_id");
						String value = rs.getString("value");
						if(value == null || value.trim().equals("")){
							if(attributeId == discountId){
								value = "0";
							}else{
								throw new RuntimeException("1产品："+entityId+"属性id:"+attributeId+"对应的值不能为空!");
							}
						}
						switch (attributeId) {
							case 63: productInfoBean.setName(value); break;
							case 74: productInfoBean.setMeta_titile(value);break;
							case 76: productInfoBean.setMeta_description(value);break;
							default: break;
						}
						if(attributeId == itemcodeId){
							productInfoBean.setItemcode(value);
						}else if(attributeId == discountId){
							productInfoBean.setDiscount(value);
						}
						
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void productInfo2(final int entityId){
		String sql = "SELECT * FROM `catalog_product_entity_text` where entity_id = ? and attribute_id in(64,65)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityId,FieldTypes.NUMERIC));
		try{
			db.handleList(sql, fpList, new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
						int attributeId = rs.getInt("attribute_id");
						String value = rs.getString("value");
						if(value == null || value.trim().equals("")){
							throw new RuntimeException("2产品："+entityId+"属性id:"+attributeId+"对应的值不能为空!");
						}
						switch (attributeId) {
							case 64: productInfoBean.setDescription(value); break;
							case 65: productInfoBean.setShort_description(value);break;
							default: break;
						}
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
