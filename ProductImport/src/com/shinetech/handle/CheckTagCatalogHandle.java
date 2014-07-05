/*
 * CheckTagCatalogHandle.java	 <br>
 * 2011-11-24<br>
 * com.shinetech.handle <br>
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

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CheckTagCatalogHandle implements ResultSetHandler{
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
