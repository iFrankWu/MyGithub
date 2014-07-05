/*
 * MagentoProductDAO.java	 <br>
 * 2011-11-7<br>
 * com.shinetech.dao <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.dao;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class MagentoProductDAO {
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	
	public MagentoProductDAO(){
		//db.setDatabase("debug");//这是本地环境的magento库
//		db.setDatabase("231");//这是231上的magento数据库
//		db.setDatabase("magento");//这是230的magento数据库
//		db.setDatabase("debug");
//		db.setDatabase("219");
		db.setDatabase(Const.now_use_magento_database);
		Const.PREFIX = "";
	}
	@SuppressWarnings("unchecked")
	public void deleteProduct(String sku) throws DBException{
		String sql = "delete from "+Const.PREFIX+"catalog_product_entity where sku = ? ";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sku,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void deleteProduct(int entityId) throws DBException{
		String sql = "delete from "+Const.PREFIX+"catalog_product_entity where entity_id = ? ";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityId,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getProductId(String sku) throws DBException{
		String sql = "select entity_id from "+Const.PREFIX+"catalog_product_entity where sku = ? ";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sku,FieldTypes.VARCHAR));
		return (Integer)db.queryPrepareFirst(Integer.class,sql,fpList);
	}
	public void deleteAll() throws DBException{
		String sql = "delete from "+Const.PREFIX+"catalog_product_entity where 1 = 1";
//		sql = "truncate table "+Const.PREFIX+"catalog_product_entity";
		db.execute(sql);
	}
	/**
	 * 功能：插入产品关系，一共有四种Type：relation,super,up_sell,cross_sell <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param productId
	 * @param linkedProductId
	 * @param linkTypeId
	 * @throws DBException
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void saveProductLink(int productId,int linkedProductId,int linkTypeId) throws DBException{
		String sql = "insert into "+Const.PREFIX+"catalog_product_link(product_id,linked_product_id,link_type_id) values(?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,linkedProductId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,linkTypeId,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void saveProductLink(int productId,String linkedProductSku,String linkTypeCode) throws DBException{
//		String sql = "insert into`mg_catalog_product_link`(`product_id`,`linked_product_id`,`link_type_id`) values(4129,(select entity_id from mg_catalog_product_entity where sku = 47),(select link_type_id from mg_catalog_product_link_type where code = 'relation'))";
		String sql = "insert into`"+Const.PREFIX+"catalog_product_link`(`product_id`,`linked_product_id`,`link_type_id`) values(?,(select entity_id from "+Const.PREFIX+"catalog_product_entity where sku = ?),(select link_type_id from "+Const.PREFIX+"catalog_product_link_type where code = ?))";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,linkedProductSku,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,linkTypeCode,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void saveProductLink(String sku,String linkedProductSku,String linkTypeCode) throws DBException{
//		String sql = "insert into`mg_catalog_product_link`(`product_id`,`linked_product_id`,`link_type_id`) values(4129,(select entity_id from mg_catalog_product_entity where sku = 47),(select link_type_id from mg_catalog_product_link_type where code = 'relation'))";
		String sql = "insert into`"+Const.PREFIX+"catalog_product_link`(`product_id`,`linked_product_id`,`link_type_id`) values((select entity_id from "+Const.PREFIX+"catalog_product_entity where sku = ?),(select entity_id from "+Const.PREFIX+"catalog_product_entity where sku = ?),(select link_type_id from "+Const.PREFIX+"catalog_product_link_type where code = ?))";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sku,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,linkedProductSku,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,linkTypeCode,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	/**
	 * 功能：onenote:///\\SERVER2\Onenote协作区\IT开发\2新软件站\9dresses.one#导入产品的相关产品等信息&section-id={8FF6DC42-F18E-4379-ADD7-3D4CB53A4579}&page-id={C804261F-9404-44EB-9653-12BF5B4BECEB}&object-id={45D4CCAE-EA91-4309-941F-A9116B3639A8}&F
	 * 注意事项：<font color="red">保存int型的值，主要是位置信息</font> <br>
	 * void
	 * @throws DBException 
	 */
	@SuppressWarnings("unchecked")
	public void insertProductLinkInt(int productLinkAttrId,int linkId,int value) throws DBException{
		String sql = "insert into "+Const.PREFIX+"catalog_product_link_attribute_int(product_link_attribute_id,link_id,value)values(?,?,?)"; 
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productLinkAttrId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,linkId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,value,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	 
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param linkTypeCode	relation
	 * @param linkAttrCode	position
	 * @param linkId
	 * @param value
	 * @throws DBException
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void insertProductLinkInt(String linkTypeCode,String linkAttrCode,int linkId,int value) throws DBException{
		// SELECT product_link_attribute_id FROM `mg_catalog_product_link_attribute` where link_type_id = (select link_type_id from mg_catalog_product_link_type where code = 'relation') and product_link_attribute_code = 'position'
		String sql = "insert into "+Const.PREFIX+"catalog_product_link_attribute_int(product_link_attribute_id,link_id,value)values((SELECT product_link_attribute_id FROM "+Const.PREFIX+"catalog_product_link_attribute where link_type_id = (select link_type_id from "+Const.PREFIX+"catalog_product_link_type where code = ?) and product_link_attribute_code = ?),?,?)"; 
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,linkTypeCode,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,linkAttrCode,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,linkId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,value,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getLastId()throws DBException{
		String sql = "select @@IDENTITY";
		return (Integer)db.queryFirst(Integer.class, sql);
	}
	@SuppressWarnings("unchecked")
	public void deleteProductLink(String linkTypeCode) throws DBException{
		String sql = "delete from "+Const.PREFIX+"catalog_product_link where link_type_id = (select link_type_id from "+Const.PREFIX+"catalog_product_link_type where code = ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,linkTypeCode,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void deleteProductLinkAttributeInt(String linkTypeCode,String linkAttrCode) throws DBException{
		String sql = "delete from "+Const.PREFIX+"catalog_product_link_attribute_int where product_link_attribute_id = (select product_link_attribute_id from "+Const.PREFIX+"catalog_product_link_attribute where link_type_id = (select link_type_id from "+Const.PREFIX+"catalog_product_link_type where code = ?) and product_link_attribute_code = ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,linkTypeCode,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,linkAttrCode,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	public static void main(String[] args) throws DBException {
		com.shinetech.util.Const.initLogger();
		MagentoProductDAO dao = new MagentoProductDAO();
		dao.insertProductLinkInt("relation", "position", 3, 1);
		dao.insertProductLinkInt(1, 4, 4);
	}
	@SuppressWarnings("unchecked")
	public void insertProductVarchar(int entityTypeId,int attributeId,int storeId,int entityId,String value) throws DBException{
		String sql = "insert into catalog_product_entity_varchar(entity_type_id,attribute_id,store_id,entity_id,value)value(?,?,?,?,?) ON DUPLICATE KEY UPDATE value = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,attributeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,storeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,entityId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(5,value,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6,value,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
//	/ INSERT INTO table (a,b,c) VALUES (1,2,3),(4,5,6) 
//    -> ON DUPLICATE KEY UPDATE c=VALUES(a)+VALUES(b); 
	@SuppressWarnings("unchecked")
	public void insertProductDecimal(int entityTypeId,int attributeId,int storeId,int entityId,float value) throws DBException{
		String sql = "insert into catalog_product_entity_decimal(entity_type_id,attribute_id,store_id,entity_id,value)value(?,?,?,?,?) ON DUPLICATE KEY UPDATE value = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,attributeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,storeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,entityId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(5,value,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(6,value,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void insertProductInt(int entityTypeId,int attributeId,int storeId,int entityId,int value) throws DBException{
		String sql = "insert into catalog_product_entity_int(entity_type_id,attribute_id,store_id,entity_id,value)value(?,?,?,?,?) ON DUPLICATE KEY UPDATE value = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,attributeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,storeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,entityId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(5,value,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(6,value,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void insertProductMedia(int attributeId,int entityId,String value) throws DBException{
		String sql = "insert into catalog_product_entity_int(attribute_id,entity_id,value)value(?,?,?,?) ON DUPLICATE KEY UPDATE value = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,entityId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,value,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,value,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	public void insertProductMediaValue(int storeId,String label,int position,int disable) throws DBException{
//		String sql = "insert into catalog_product_entity_int(entity_type_id,attribute_id,store_id,entity_id,value)value(?,?,?,?,?) ON DUPLICATE KEY UPDATE value = ?";
//		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
//		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(2,attributeId,FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(3,storeId,FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(4,entityId,FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(5,value,FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(6,value,FieldTypes.INTEGER));
//		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void insertProductText(int entityTypeId,int attributeId,int storeId,int entityId,String value) throws DBException{
		String sql = "insert into catalog_product_entity_text(entity_type_id,attribute_id,store_id,entity_id,value)value(?,?,?,?,?) ON DUPLICATE KEY UPDATE value = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,attributeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,storeId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,entityId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(5,value,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6,value,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public void getMainImageBySku(String sku,ResultSetHandler handle) throws DBException{
		String sql = "select value_id,attribute_id,value from catalog_product_entity_varchar t1,catalog_product_entity t2 where t1.entity_id = t2.entity_id and t2.sku = ? and attribute_id in(77,78,79)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sku,FieldTypes.VARCHAR));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public void updateMainImage(int valueId,String value) throws DBException{
		String sql = "update catalog_product_entity_varchar set value = ? where value_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,valueId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,value,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public void getImageBySku(String sku,ResultSetHandler handle) throws DBException{
		String sql = "SELECT t1.value_id,t1.value FROM catalog_product_entity_media_gallery t1, catalog_product_entity t2 WHERE t1.entity_id = t2.entity_id AND t2.sku = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sku,FieldTypes.VARCHAR));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public void updateMediaImage(int valueId,String value) throws DBException{
		String sql = "update catalog_product_entity_media_gallery set value = ? where value_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,valueId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,value,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void updateVarcharImage(int valueId,String value) throws DBException{
		String sql = "update catalog_product_entity_varchar set value = ? where value_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,valueId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,value,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void getMediaImageByName(String imgName,int entityId,ResultSetHandler handle) throws DBException{
		String sql = "select * from catalog_product_entity_media_gallery where entity_id = ? and value like '%"+imgName+"'";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityId,FieldTypes.INTEGER));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public void getVarcharImageByName(String imgName,int entityId,ResultSetHandler handle) throws DBException{
		String sql = "select * from catalog_product_entity_varchar where entity_id = ? and value like '%"+imgName+"'";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityId,FieldTypes.INTEGER));
		db.handleList(sql, fpList, handle);
	}
}
