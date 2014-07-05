/*
 * TagCatatogService.java	 <br>
 * 2011-10-12<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;


/**
 * 这个类兼容了TagHandle类
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TagCatatogHandle implements ResultSetHandler{
	/**
	 * mainFabricList:存放主布料所选的标签
	 */ 
	private List<String> mainFabricList = new ArrayList<String>();
	/**
	 * fabrice1List:材质1
	 */ 
	private List<String> fabrice1List = new ArrayList<String>();
	/*
	 * @return the fabrice1List
	 */
	public List<String> getFabrice1List() {
		return fabrice1List;
	}
	/*
	 * @return the mainFabricList
	 */
	public List<String> getMainFabricList() {
		return mainFabricList;
	}
	/**
	 * map:从数据库中得出产品的tag和分类 存放于此map中
	 */ 
	private Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>(); 
	@Override
	public void handle(ResultSet rs) {
		String tagName;
		try {
			tagName = rs.getString("tag_name").trim();
			String catalogName = rs.getString("catalog_name").trim();
			if(catalogName.equals(Const.FABRIC_CATALOGS.get(0))){
				return;
			}
			//这行代码 必须在下面的那个判断语句之前 否则为空
			if(catalogName.equals(Const.FABRIC_CATALOGS.get(1))){
				mainFabricList.add(tagName);
			}
			if(catalogName.equals(Const.FABRIC_CATALOGS.get(2))){
				fabrice1List.add(tagName);
			}
			if(Const.FABRIC_CATALOGS.contains(catalogName)){
				catalogName = Const.FABRIC_CATALOGS.get(0);
			}
			ArrayList<String> list = map.get(catalogName);
			if(list == null){
				list = new ArrayList<String>();
				map.put(catalogName, list);
			}
			list.add(tagName);
			tags.add(tagName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * tags:存放一个产品的所有Tag
	 */ 
	private List<String> tags = new ArrayList<String>();
	/*
	 * @return 返回TagHandle中的getTags()
	 */
	public List<String> getTags() {
		return tags;
	}
	public Map<String,ArrayList<String>> getCatalogTagMap(){
		return map;
	} 
}
