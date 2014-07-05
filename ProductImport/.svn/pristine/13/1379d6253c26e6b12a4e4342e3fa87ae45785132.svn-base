/*
 * ImportProduct.java	 <br>
 * 2011-10-9<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.io.File;

import com.shinetech.dao.AttributeDAO;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportAttribute {
	private Long storeId = 1l;
	private AttributeDAO dao = new AttributeDAO();
	private FileOperate op = new FileOperate();
	public static void main(String[] args) {
		Const.initLogger();
		ImportAttribute product = new ImportAttribute();
		//String s = "Venues[T]:Beach/Destination{Beach;Destination}[T];Church/Hall{Church;Hall}[T];Garden/Outdoor{Garden;Outdoor}[T]";
		//product.removeBraces(s);
		product.parseAttributeFile(0);
//		product.parseAttributeFile(1);
	}
//	private String prefixAttr = SimpleProductConfig.PREFIX_ATTRIBUTE[0];
	public void parseAttributeFile(int prefix){
//		prefixAttr = SimpleProductConfig.PREFIX_ATTRIBUTE[prefix];
		op.getLine(new File(Const.ATTRIBUTE_FILE[prefix]),new ILineHandle<String>(){

			@Override
			public String lineHandle(String line) {
				try {
//					System.out.println(line);
					if(line.startsWith("#"))return null;
					handleAttribute(line);
				} catch (DBException e) {
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	public void handleAttribute(String aLineAttribute) throws DBException{
		aLineAttribute = removeBraces(aLineAttribute);
		String temp [] = aLineAttribute.split(":");
		String attributeName = temp[0];
		String options[] = temp[1].split(";");
		
		String isFilterable = "0";//no ==> 0
		String isFilterableInSeacrch = "0";//no ==> 0
		String isVisibleOnFront = "1"; 
		//根据一个属性后面是否有[T] 来确定 下面三个属性
		if(attributeName.endsWith("[T]")){
			attributeName = attributeName.substring(0, attributeName.indexOf("[T]"));
			isFilterable = "1";//no ==> 0
			isFilterableInSeacrch = "1";//no ==> 0
			//这个地方 还需要赋值
			isVisibleOnFront = "0"; 
		}
		
		String code = attributeName.toLowerCase().replace(" ", "_");
//		code = prefixAttr+code;
		
		String backendModel = "eav/entity_attribute_backend_array";
		String backendType = "varchar";
		String frontendInput = "multiselect";
		String frontendLabel = attributeName;
		String sourceModel = null;
		String isRequired = "0";
		String isUserDefined = "1";
		String isUnique = "0";
		Long attributeId =  dao.saveAttribute(code, backendModel, backendType, frontendInput, frontendLabel, sourceModel, isRequired, isUserDefined, isUnique);
		String value = attributeName;
		dao.saveAttributeLabel(attributeId, storeId, value);
		
		String isGlobal = "1";
		String isSearchable = "0";
		String isComparable = "0";
		String isHtmlAllowedOnFont = "1";
		String usedInProductListing = "0";
		String isConfiguable = "0";
		String applyTo = null;
		String isVisibleInAdvacedSeach = "0";
		String position = "0";
		String isUserdForPromoRules = "0";
		String used_for_sort_by = "0";
		
		dao.saveCatalogAttribute(attributeId, isGlobal, isSearchable, isFilterable, isComparable, isVisibleOnFront, isHtmlAllowedOnFont, isFilterableInSeacrch,
				usedInProductListing, isConfiguable, applyTo, isVisibleInAdvacedSeach, position, isUserdForPromoRules,used_for_sort_by);
		
		int i = 1;
		for(String option:options){
			if(option.endsWith("[T]")){
				//这里需求还没有十分清楚，故暂时不处理
				option = option.substring(0, option.indexOf("[T]"));
			}
			Long optionId = dao.saveAttributeOption(attributeId, i+"");
//			for(long storeId = 0;storeId<2;storeId++){//两个店铺id/ admin ==> 0; default ==> 1
				Long storeId = 0l;
				dao.saveAttributeOptionValue(optionId, storeId, option);
//			}
			i++;
		}
		dao.lastUpdate(attributeId);
	}
	/**
	 * 功能： 去除花括号及其包含的字符 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param str
	 * @return
	 * String
	 */
	public String removeBraces(String str){
		String s = str;
		while(s.contains("{")){
			int start =  s.indexOf("{");
			int end = s.indexOf("}", start+1);
			s = s.substring(0, start)+s.substring(end+1);
		}
		return s;
	}
}
