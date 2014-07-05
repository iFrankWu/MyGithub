/*
 * ImportProDAO.java	 <br>
 * 2011-10-9<br>
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
import com.shinetech.sql.exception.DBException;
import static  com.shinetech.dao.Const.PREFIX;;


/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class AttributeDAO {
	public AttributeDAO(){
//		db.setDatabase("231");
//		db.setDatabase("debug");
//		Const.PREFIX = "";
//		db.setDatabase("219");//导入219 magento数据
		db.setDatabase(Const.now_use_magento_database);
		Const.PREFIX = "";
	}
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	@SuppressWarnings("unchecked")
	public Long getNextAttributeId() throws DBException{
		String sql = "select auto_increment from information_schema.`TABLES` where table_name='"+PREFIX+"eav_attribute'";
		return (Long)db.queryFirst(Long.class, sql);
	}
	/**
	 * 功能：插入基本属性 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param code
	 * @param backendModel
	 * @param backendType
	 * @param frontendInput
	 * @param frontendLabel
	 * @param sourceModel
	 * @param isRequired
	 * @param isUserDefined
	 * @param isUnique
	 * @throws DBException
	 * void
	 */
	@SuppressWarnings("unchecked")
	public Long saveAttribute(String code,String backendModel,String backendType,String frontendInput,String frontendLabel,String sourceModel,String isRequired,String isUserDefined,String isUnique) throws DBException{
//		db.setDatabase("magento");
		Long entityTypeId = getEntityTypeId();
		if(entityTypeId == null){
			throw new DBException("返回的entity_type_id 为空");
		}else{
			String sql = "delete from "+PREFIX+"eav_attribute where entity_type_id = ? and attribute_code =?";
			List<FieldParameter> fpList = new ArrayList<FieldParameter>();
			fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.NUMERIC));
			fpList.add(new FieldParameter(2,code,FieldTypes.VARCHAR));
			db.execute(sql,fpList);
			
			sql = "INSERT INTO `"+PREFIX+"eav_attribute` (`entity_type_id`, `attribute_code`, `backend_model`, `backend_type`, " +
					"`frontend_input`, `frontend_label`, `source_model`, `is_required`, `is_user_defined`, `is_unique`)" +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			fpList.add(new FieldParameter(3,backendModel,FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(4,backendType,FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(5,frontendInput,FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(6,frontendLabel,FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(7,sourceModel,FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(8,isRequired,FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(9,isUserDefined,FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(10,isUnique,FieldTypes.VARCHAR));
			db.execute(sql, fpList);
			sql = "select @@IDENTITY";
			return (Long)db.queryFirst(Long.class, sql);
		}
	}
	@SuppressWarnings("unchecked")
	public Long getEntityTypeId() throws DBException{
		String sql = "SELECT `"+PREFIX+"eav_entity_type`.entity_type_id FROM `"+PREFIX+"eav_entity_type` WHERE (`"+PREFIX+"eav_entity_type`.`entity_type_code`='catalog_product')";
		return (Long)db.queryFirst(Long.class, sql);
	}
	@SuppressWarnings("unchecked")
	public void saveAttributeLabel(Long attributeId,Long storeId,String value) throws DBException{
		String sql = "INSERT INTO `"+PREFIX+"eav_attribute_label` (`attribute_id`, `store_id`, `value`) VALUES (?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,storeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,value,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void saveCatalogAttribute(Long attributeId,String isGlobal,String isSearchable,String isFilterable,String isComparable,String isVisibleOnFront,String isHtmlAllowedOnFont,String isFilterableInSeacrch,String usedInProductListing,String isConfiguable,String applyTo,String isVisibleInAdvacedSeach,String position,String isUserdForPromoRules,String used_for_sort_by) throws DBException{
//		db.setDatabase("magento");
		String sql = "INSERT INTO `"+PREFIX+"catalog_eav_attribute` (`attribute_id`, `is_global`, `is_searchable`, `is_filterable`, `is_comparable`, `is_visible_on_front`, `is_html_allowed_on_front`, `is_filterable_in_search`, `used_in_product_listing`, `is_configurable`, `apply_to`, `is_visible_in_advanced_search`, `position`, `is_used_for_promo_rules`,`used_for_sort_by`) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,isGlobal,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,isSearchable,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(4,isFilterable,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(5,isComparable,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6,isVisibleOnFront,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(7,isHtmlAllowedOnFont,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(8,isFilterableInSeacrch,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(9,usedInProductListing,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(10,isConfiguable,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(11,applyTo,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(12,isVisibleInAdvacedSeach,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(13,position,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(14,isUserdForPromoRules,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(15,used_for_sort_by,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public Long saveAttributeOption(Long attributeId,String sortOrder) throws DBException{
		String sql = "INSERT INTO `"+PREFIX+"eav_attribute_option` (`attribute_id`, `sort_order`) VALUES (?, ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,sortOrder,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
		sql = "select @@IDENTITY";
		return (Long)db.queryFirst(Long.class, sql);
	}
	@SuppressWarnings("unchecked")
	public Long getNextOptionId() throws DBException{
		String sql = "select auto_increment from information_schema.`TABLES` where table_name='"+PREFIX+"eav_attribute_option'";
		return (Long)db.queryFirst(Long.class, sql);
	}
	@SuppressWarnings("unchecked")
	public void saveAttributeOptionValue(Long optionId,Long storeId,String value) throws DBException{
		String sql = "INSERT INTO `"+PREFIX+"eav_attribute_option_value` (`option_id`, `store_id`, `value`) VALUES (?, ?, ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,optionId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,storeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,value,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void lastUpdate(Long attributeId) throws DBException{
		String sql = "UPDATE `"+PREFIX+"eav_attribute` SET `default_value` = '' WHERE (attribute_id =?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeId,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void deleteAttribute(String attributeCode) throws DBException{
		String sql = "delete from "+Const.PREFIX+"eav_attribute where attribute_code = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeCode,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getAttributeId(int entityTypeId,String attributeCode) throws DBException{
		String sql = "select attribute_id from "+Const.PREFIX+"eav_attribute where entity_type_id = ? and attribute_code = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attributeCode,FieldTypes.VARCHAR));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	/**
	 * 功能：返回option id用于 插入 catalog_product_entity_varchar表中value 字段，若有多个 用，分隔 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param attrId
	 * @param tag
	 * @return
	 * @throws DBException
	 * Integer
	 */
	@SuppressWarnings("unchecked")
	public Integer getOptionId(int attrId,String tag) throws DBException{
		String sql = "SELECT distinct option_id FROM eav_attribute_option_value where option_id in (SELECT option_id FROM eav_attribute_option where attribute_id = ?) and value = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attrId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,tag,FieldTypes.VARCHAR));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
}
