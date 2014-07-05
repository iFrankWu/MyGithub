/*
 * CatagoryDAO.java	 <br>
 * 2011-10-28<br>
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

public class CatagoryDAO {
	public CatagoryDAO(){
//		db.setDatabase("debug");
//		Const.PREFIX = "";
//		db.setDatabase("231");//这是231上的magento数据库
//		db.setDatabase("magento");//这是230的magento数据库
//		db.setDatabase("219");//219 magento数据库
		db.setDatabase(Const.now_use_magento_database);
		Const.PREFIX = "";
	}
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	
	public Long getCategoryEntityId(){
		return 35l;
	}
	@SuppressWarnings("unchecked")
	public int getEntityTypeId() throws DBException{
		String sql = "SELECT entity_type_id FROM "+Const.PREFIX+"eav_entity_type WHERE(entity_type_code='catalog_category')";
		return (Integer)db.queryFirst(Integer.class, sql);
	}
	@SuppressWarnings("unchecked")
	public void getCategoryEntity(int categoryEntityId,ResultSetHandler handle) throws DBException{
		String sql = "SELECT * FROM "+Const.PREFIX+"catalog_category_entity WHERE(entity_id=?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,categoryEntityId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList, handle);
	}
	
	@SuppressWarnings("unchecked")
	public int getMaxPosition(int parentId) throws DBException{
		String sql = "SELECT MAX(`position`) FROM `"+Const.PREFIX+"catalog_category_entity` WHERE parent_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,parentId,FieldTypes.NUMERIC));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	public void updateChildren(List<Integer> entityIdList) throws DBException{
		StringBuilder sql =  new StringBuilder();
		sql.append("UPDATE`"+Const.PREFIX+"catalog_category_entity` SET `children_count`=children_count+1 WHERE (entity_id IN ('',");
		for(Integer entityId : entityIdList){
			sql.append(entityId+",");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append("))");
		db.execute(sql.toString());
	}
	@SuppressWarnings("unchecked")
	public void saveCatalogEntity(int entityTypeId,int attributeSetId,int parentId,String path,int position,int level) throws DBException{
		String sql = "INSERT INTO `"+Const.PREFIX+"catalog_category_entity`(`entity_type_id`,`attribute_set_id`,`parent_id`,`created_at`,`updated_at`,`path`,`position`,`level`,`children_count`)" +
				"values(?,?,?,now(),now(),?,?,?,0)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, entityTypeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2, attributeSetId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3, parentId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4, path, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(5, position, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(6, level, FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void saveCatalogEntityVarchar(int entityTypeId,int attributeSetId,int storeId,int entityId,String value) throws DBException{
		String sql = "INSERT INTO`"+Const.PREFIX+"catalog_category_entity_varchar`(`entity_type_id`,`attribute_id`,`store_id`,`entity_id`,`value`)" +
					 "values(?,?,?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, entityTypeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2, attributeSetId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3, storeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4, entityId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(5, value, FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
 
	@SuppressWarnings("unchecked")
	public void saveCatalogEntityText(int entityTypeId,int attributeSetId,int storeId,int entityId,String value) throws DBException{
		String sql = "INSERT INTO`"+Const.PREFIX+"catalog_category_entity_text`(`entity_type_id`,`attribute_id`,`store_id`,`entity_id`,`value`)" +
					 "values(?,?,?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, entityTypeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2, attributeSetId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3, storeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4, entityId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(5, value, FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public void saveCatalogEntityInt(int entityTypeId,int attributeSetId,int storeId,int entityId,int value) throws DBException{
		String sql = "INSERT INTO`"+Const.PREFIX+"catalog_category_entity_int`(`entity_type_id`,`attribute_id`,`store_id`,`entity_id`,`value`)" +
					 "values(?,?,?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, entityTypeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2, attributeSetId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3, storeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4, entityId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(5, value, FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public void saveCatalogEntityDatetime(int entityTypeId,int attributeSetId,int storeId,int entityId,String value) throws DBException{
		String sql = "INSERT INTO`"+Const.PREFIX+"catalog_category_entity_datetime` (`entity_type_id`,`attribute_id`,`store_id`,`entity_id`,`value`)" +
					 "values(?,?,?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, entityTypeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2, attributeSetId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3, storeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4, entityId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(5, value, FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void updatePath(int entityId) throws DBException{
		String sql = "UPDATE `"+Const.PREFIX+"catalog_category_entity` SET `path` = CONCAT(path,'/"+entityId+"') WHERE (entity_id=?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, entityId, FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getAttributeId(String attributeCode,int entityTypeId) throws DBException{
		String sql = "SELECT attribute_id FROM `"+Const.PREFIX+"eav_attribute` where `attribute_code` = ? and `entity_type_id` = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, attributeCode, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2, entityTypeId, FieldTypes.NUMERIC));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getAtributeSetId(String attributeSetName,int entityTypeId) throws DBException{
		attributeSetName = "Default";
		String sql = "SELECT `attribute_set_id` FROM `"+Const.PREFIX+"eav_attribute_set` where `entity_type_id` = ? and `attribute_set_name` = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, entityTypeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2, attributeSetName, FieldTypes.VARCHAR));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getEntityTypeId(String entityTypeCode) throws DBException{
		String sql = "SELECT  `entity_type_id` FROM  `"+Const.PREFIX+"eav_entity_type` WHERE  `entity_type_code` =  ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, entityTypeCode, FieldTypes.VARCHAR));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void getCatalogByParentId(int parentId,ResultSetHandler handle) throws DBException{
		String sql = "select * from "+Const.PREFIX+"catalog_category_entity where parent_id = ? order by position";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,parentId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public String getCatagotyValue(int entityTypeId,int attributeId,int entityId) throws DBException{
		String sql = "select value from "+Const.PREFIX+"catalog_category_entity_varchar where entity_type_id = ? and attribute_id = ? and entity_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attributeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,entityId,FieldTypes.NUMERIC));
		return (String)db.queryPrepareFirst(String.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getLastId()throws DBException{
		String sql = "select @@IDENTITY";
		return (Integer)db.queryFirst(Integer.class, sql);
	}
	@SuppressWarnings("unchecked")
	public void saveCatagoryProduct(int catalogId,int productId,int position) throws DBException{
		String sql = "INSERT INTO `"+Const.PREFIX+"catalog_category_product` (`category_id`,`product_id`,`position`) VALUES (?, ?, ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,catalogId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,productId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,position,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	/**
	 * 功能： 因为magento中的产品sku即231产品库中的id，所以此处可以简单处理 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param catalogId
	 * @param sku
	 * @param position
	 * void
	 * @throws DBException 
	 */
	public void saveCatagoryProduct(int catalogId,String sku,int position) throws DBException{
		int productId = getProductId(sku);
		if(productId > 0){
			saveCatagoryProduct(catalogId, productId, position);
		}
	}
	@SuppressWarnings("unchecked")
	public int getProductId(String sku) throws DBException{
		String sql = "select entity_id from "+Const.PREFIX+"catalog_product_entity where sku = ? ";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sku,FieldTypes.VARCHAR));
		return (Integer)db.queryPrepareFirst(Integer.class,sql,fpList);
	}
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">返回某个分类下的产品已经按属性值倒序排序</font> <br>
	 * @param categoryId	 分类id
	 * @param attributeId	属性id，是指产品对应的属性，如score，itemcode
	 * @param entityTypeId	实体类型id，此处为4，指catalog_product
	 * @param handle	
	 * @throws DBException
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void getCatalogProdcuts(int categoryId,int attributeId,int entityTypeId,ResultSetHandler handle) throws DBException{
//		String sql = "SELECT * FROM `mg_catalog_category_product` where `category_id` = ?";
		// SELECT mg_catalog_category_product.*,value FROM `mg_catalog_category_product`,`mg_catalog_product_entity_varchar` where `category_id` = 161 and attribute_id = 236 and store_id = 0 and entity_type_id = 4 and entity_id = product_id
		String sql = "SELECT "+Const.PREFIX+"catalog_category_product.*,value " +
					 "FROM `"+Const.PREFIX+"catalog_category_product`,`"+Const.PREFIX+"catalog_product_entity_varchar` " +
					 "where `category_id` = ? and attribute_id = ? and store_id = 0 and entity_type_id = ? and entity_id = product_id " +
					 "order by CAST(value as signed) desc";//cast方法是将varchar字段按照int型类排序
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,categoryId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attributeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,entityTypeId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList, handle);
	}
	/**
	 * 功能：更新分类下产品的序号 <br>
	 * 注意事项：<font color="red">前提是该分类已经有产品</font> <br>
	 * @param position
	 * @param categoryId
	 * @param productId
	 * @throws DBException
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void updateSort(int position,int categoryId,int productId) throws DBException{
		String sql = "update "+Const.PREFIX+"catalog_category_product set position = ?  where category_id = ? and product_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,position,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,categoryId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,productId,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	/**
	 * 功能：删除指定分类 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param categoryId
	 * @throws DBException
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void deleteCategory(int categoryId) throws DBException{
		String sql = "delete from "+Const.PREFIX+"catalog_category_entity where entity_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,categoryId,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	
	/**
	 * 功能： <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param categoryId
	 * @return
	 * @throws DBException
	 * List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> getProductSkus(int categoryId) throws DBException{
		String sql = "SELECT sku FROM  `"+Const.PREFIX+"catalog_product_entity` where entity_id in (SELECT `product_id` FROM `catalog_category_product` where `category_id` = ? order by `position`)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,categoryId,FieldTypes.NUMERIC));
		return db.queryPrepareList(String.class, sql, fpList);
	}
	/**
	 * 功能：发现时常分类表存在脏数据，导致检查导入数据出错<br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @throws DBException
	 * void
	 */
	public void deleteCustomCategory() throws DBException{
		String sql = "delete from "+Const.PREFIX+"catalog_category_entity where entity_id > 2";
		db.execute(sql);
	}
	public static void main(String[] args) throws DBException {
		com.shinetech.util.Const.initLogger();
		CatagoryDAO dao = new CatagoryDAO();
		dao.getProductId("134");
	}
}