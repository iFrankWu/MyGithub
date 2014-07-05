/*
 * DeleteAttribute.java	 <br>
 * 2011-11-10<br>
 * com.shinetech.init <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.init;

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

public class DeleteAttribute {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		DeleteAttribute attribute = new DeleteAttribute();
		attribute.deleteAttribute(0);
		attribute.deleteAttribute(1);
		attribute.deleteOtherAttribute();
	}
	private AttributeDAO dao = new AttributeDAO();
	private FileOperate op = new FileOperate();
//	private String prefixAttr = SimpleProductConfig.PREFIX_ATTRIBUTE[0];
	public void deleteAttribute(int index){
//		prefixAttr = SimpleProductConfig.PREFIX_ATTRIBUTE[index];
		op.getLine(new File(Const.ATTRIBUTE_FILE[index]),new ILineHandle<String>(){

			@Override
			public String lineHandle(String line) {
				try {
//					System.out.println(line);
					handleAttribute(line);
				} catch (DBException e) {
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	public void deleteOtherAttribute(){
		op.getLine(new File(Const.OTHER_ATTRIBUTE),true,new ILineHandle<String>(){

			@Override
			public String lineHandle(String line) {
				try {
//					System.out.println(line);
					handleOtherAttribute(line);
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
		if(attributeName.endsWith("[T]")){
			attributeName = attributeName.substring(0, attributeName.indexOf("[T]"));
		}
		String attributeCode = attributeName.toLowerCase().replace(" ", "_");
//		attributeCode = prefixAttr+aLineAttribute;
		dao.deleteAttribute(attributeCode);
	}
	public void handleOtherAttribute(String aLineAttribute) throws DBException{
		aLineAttribute = removeBraces(aLineAttribute);
		String temp [] = aLineAttribute.split(":");
		String attributeName = temp[0];
		if(attributeName.endsWith("[T]")){
			attributeName = attributeName.substring(0, attributeName.indexOf("[T]"));
		}
		String attributeCode = attributeName.toLowerCase().replace(" ", "_");
		dao.deleteAttribute(attributeCode);
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
