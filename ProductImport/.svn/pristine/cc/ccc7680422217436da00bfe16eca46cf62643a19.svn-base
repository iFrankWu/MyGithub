/*
 * ImportAttributeSet.java	 <br>
 * 2011-10-9<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.sql.ResultSet;

import com.shinetech.dao.AttributeDAO;
import com.shinetech.dao.AttributeSetDAO;
import com.shinetech.dao.CatagoryDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportAttributeSet {
	private AttributeSetDAO attrSetDAO = new AttributeSetDAO();
	private CatagoryDAO categoryDAO = new CatagoryDAO();
	private AttributeDAO attrDAO = new AttributeDAO();
	private FileOperate op = new FileOperate();
	/**
	 * sortOrder:group排序
	 */ 
	private int sortOrder = 0;
	/**
	 * evaEntityAttrSort:属性排序
	 */ 
	private int evaEntityAttrSort = 0;
	public static void main(String[] args) {
		Const.initLogger();
		ImportAttributeSet attrSet = new ImportAttributeSet();
		attrSet.createAttributeSet();
	}
	private int newAddGroupId = 0;
	public void createAttributeSet(){
		try {
			final int entityTypeId = categoryDAO.getEntityTypeId(ConstConfig.ENTITY_TYPE_CODES[3]);
//			System.out.println("entityTypeId:"+entityTypeId);
			//默认属性集的id
			int defauleAttributeSetId = attrSetDAO.getAttributeSetId(entityTypeId, "Default");
			
			final int attributeSetId = attrSetDAO.saveAttributeSet(entityTypeId, ConstConfig.ATTRIBUTE_SET_NAME);
//			System.out.println("attributeSetId:"+attributeSetId);
			
			attrSetDAO.getDefaultGroup(defauleAttributeSetId, new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try {
						attrSetDAO.saveAttributeGroup(attributeSetId, rs.getString("attribute_group_name"), rs.getInt("sort_order"));
						sortOrder = Math.max(sortOrder, rs.getInt("sort_order"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			evaEntityAttrSort = 0;
			attrSetDAO.getDefaultEntityAttr(defauleAttributeSetId, new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
//						System.out.println(rs.getInt("entity_type_id")+":"+ attributeSetId+":"+rs.getInt("attribute_group_id")+":"+rs.getInt("attribute_id")+":"+sortOrder);
						int newerGroupId = attrSetDAO.getNewerGroupId(rs.getInt("attribute_group_id"), attributeSetId);
						attrSetDAO.saveEntityAttribute(rs.getInt("entity_type_id"), attributeSetId, newerGroupId, rs.getInt("attribute_id"), sortOrder);
						evaEntityAttrSort = Math.max(evaEntityAttrSort, rs.getInt("sort_order"));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
//	/	System.exit(1);
		    attrSetDAO.saveAttributeGroup(attributeSetId, ConstConfig.CUSTOM_GROUP_NAMES[0], sortOrder++);
			newAddGroupId = attrSetDAO.getGroupId(attributeSetId,  ConstConfig.CUSTOM_GROUP_NAMES[0]);
			op.getLine(Const.ATTRIBUTE_FILE[0], new ILineHandle<String>() {
				@Override
				public String lineHandle(String aLineAttribute) {
					try{
						if(aLineAttribute.startsWith("#"))return null;
						aLineAttribute = removeBraces(aLineAttribute);
						String temp [] = aLineAttribute.split(":");
						String attributeName = temp[0];
						if(attributeName.endsWith("[T]")){
							attributeName = attributeName.substring(0, attributeName.indexOf("[T]"));
						}
						String attributeCode = attributeName.toLowerCase().replace(" ", "_");
						int attrId = attrDAO.getAttributeId(entityTypeId, attributeCode);
						attrSetDAO.saveEntityAttribute(entityTypeId, attributeSetId, newAddGroupId, attrId, evaEntityAttrSort++);
					}catch(Exception e){
						e.printStackTrace();
					}
					return null;
				}
			});
			
			attrSetDAO.saveAttributeGroup(attributeSetId, ConstConfig.CUSTOM_GROUP_NAMES[1], sortOrder++);
			newAddGroupId = attrSetDAO.getGroupId(attributeSetId,  ConstConfig.CUSTOM_GROUP_NAMES[1]);
			op.getLine(Const.OTHER_ATTRIBUTE, new ILineHandle<String>() {

				@Override
				public String lineHandle(String aLineAttribute) {
					try{
						if(aLineAttribute.startsWith("#")){
							return null;
						}
						String temp [] = aLineAttribute.split(":");
						if(temp.length != 13){
							throw new RuntimeException("其他属性配置文件，列数目不对"+temp.length);
						}
						String attributeName = temp[0];
						String attributeCode = attributeName.toLowerCase().replace(" ", "_");
						int attrId = attrDAO.getAttributeId(entityTypeId, attributeCode);
//						int newerGroupId = attrSetDAO.getNewerGroupId(rs.getInt("attribute_group_id"), attributeSetId);
						attrSetDAO.saveEntityAttribute(entityTypeId, attributeSetId, newAddGroupId, attrId, evaEntityAttrSort++);
					}catch(Exception e){
						e.printStackTrace();
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String removeBraces(String str){
		String s = str;
		while(s.contains("{")){
			int start =  s.indexOf("{");
			int end = s.indexOf("}", start+1);
			s = s.substring(0, start)+s.substring(end+1);
		}
		return s;
	}
}
