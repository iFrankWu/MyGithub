/*
 * ImportOtherAttribute.java	 <br>
 * 2011-11-7<br>
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

public class ImportOtherAttribute {

	private Long storeId = 1l;
	private AttributeDAO dao = new AttributeDAO();
	private FileOperate op = new FileOperate();
	public static void main(String[] args) {
		Const.initLogger();
		ImportOtherAttribute product = new ImportOtherAttribute();
		//String s = "Venues[T]:Beach/Destination{Beach;Destination}[T];Church/Hall{Church;Hall}[T];Garden/Outdoor{Garden;Outdoor}[T]";
		//product.removeBraces(s);
		product.parseAttributeFile();
	}
	public void parseAttributeFile(){
		op.getLine(new File(Const.OTHER_ATTRIBUTE),true,new ILineHandle<String>(){

			@Override
			public String lineHandle(String line) {
				try {
					handleAttribute(line);
				} catch (DBException e) {
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	public void handleAttribute(String aLineAttribute)throws DBException{
//		System.out.println(aLineAttribute);
		String temp [] = aLineAttribute.split(":");
		if(temp.length != 13){
			throw new RuntimeException("其他属性配置文件，列数目不对");
		}
		String attributeName = temp[0];
		String frontendInput = temp[1];
		String isUnique = temp[2];
		String isSearchable = temp[3];
		String isVisibleInAdvacedSeach = temp[4];
		String isComparable = temp[5];
		String isFilterable = temp[6];
		String isFilterableInSeacrch = temp[7];//no ==> 0
		String isUserdForPromoRules = temp[8];
		String isHtmlAllowedOnFont = temp[9];
		String isVisibleOnFront = temp[10];
		String usedInProductListing = temp[11];
		String used_for_sort_by = temp[12];
		
		String code = attributeName.toLowerCase().replace(" ", "_");
		String backendModel = "eav/entity_attribute_backend_array";
		String backendType = "varchar";
		
		String frontendLabel = attributeName;
		String sourceModel = null;
		String isRequired = "0";
		String isUserDefined = "1";
		
		Long attributeId =  dao.saveAttribute(code, backendModel, backendType, frontendInput, frontendLabel, sourceModel, isRequired, isUserDefined, isUnique);
		String value = attributeName;
		dao.saveAttributeLabel(attributeId, storeId, value);
		String isGlobal = "1";
		String isConfiguable = "0";
		String applyTo = null;
		String position = "0";
		dao.saveCatalogAttribute(attributeId, isGlobal, isSearchable, isFilterable, isComparable, isVisibleOnFront, isHtmlAllowedOnFont, isFilterableInSeacrch,
				usedInProductListing, isConfiguable, applyTo, isVisibleInAdvacedSeach, position, isUserdForPromoRules,used_for_sort_by);
		
		dao.lastUpdate(attributeId);
	}
}
