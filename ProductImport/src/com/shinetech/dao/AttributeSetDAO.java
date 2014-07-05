/*
 * AttributeSetDAO.java	 <br>
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
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;

//import static  com.shinetech.dao.Const.PREFIX;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class AttributeSetDAO {
	public AttributeSetDAO(){
		db.setDatabase(Const.now_use_magento_database);
		Const.PREFIX = "";
	}
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	@SuppressWarnings("unchecked")
	public Integer saveAttributeSet(int entityTypeId,String attributeSetName) throws DBException{
		String sql = "INSERT INTO `eav_attribute_set` (`entity_type_id`, `attribute_set_name`) VALUES (?, ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attributeSetName,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
		sql = "select @@IDENTITY";
		return (Integer)db.queryFirst(Integer.class, sql);
	}
	@SuppressWarnings("unchecked")
	public int saveAttributeGroup(int attributeSetId,String attributeGroupName,int sortOrder) throws DBException{
		String sql = "INSERT INTO `eav_attribute_group` (`attribute_set_id`, `attribute_group_name`, `sort_order`) VALUES (?, ?, ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeSetId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attributeGroupName,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,sortOrder,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
		sql = "select @@IDENTITY";
		return (Integer)db.queryFirst(Integer.class, sql);
	}
	
	@SuppressWarnings("unchecked")
	public void saveEntityAttribute(int entityTypeId,int   attributeSetId,int attributeGroupId,int attributeId,int sortOrder ) throws DBException{
		String sql = "INSERT INTO `eav_entity_attribute` (`entity_type_id`, `attribute_set_id`, `attribute_group_id`, `attribute_id`, `sort_order`) VALUES (?, ?, ?, ?, ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attributeSetId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,attributeGroupId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4, attributeId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(5, sortOrder, FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getAttributeSetId(int entityTypeId,String attributeSetName) throws DBException{
		String sql = "select attribute_set_id from eav_attribute_set where entity_type_id = ? and attribute_set_name = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attributeSetName,FieldTypes.VARCHAR));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void getDefaultGroup(int attributeSetId,ResultSetHandler handle) throws DBException{
		String sql = "select * from eav_attribute_group where `attribute_set_id` =?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeSetId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public void getDefaultEntityAttr(int attributeSetId,ResultSetHandler handle) throws DBException{
		String sql = "select * from eav_entity_attribute where `attribute_set_id` =?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attributeSetId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public int getNewerGroupId(int attribute_group_id,int attribute_set_id) throws DBException{
		String sql = "select attribute_group_id from eav_attribute_group where attribute_group_name in (SELECT attribute_group_name FROM `eav_attribute_group` where `attribute_group_id` = ? ) and `attribute_set_id` = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,attribute_group_id,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attribute_set_id,FieldTypes.NUMERIC));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public int getGroupId(int attribute_set_id,String groupName) throws DBException{
		String sql = "select attribute_group_id from eav_attribute_group where attribute_group_name = ? and `attribute_set_id` = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,groupName,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,attribute_set_id,FieldTypes.NUMERIC));
		return (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void deleteAttrSet(int entityTypeId,String attrSetName) throws DBException{
		String sql = "delete from eav_attribute_set where entity_type_id = ? and attribute_set_name = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,entityTypeId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,attrSetName,FieldTypes.VARCHAR));		
		db.execute(sql, fpList);
	}
	
}

