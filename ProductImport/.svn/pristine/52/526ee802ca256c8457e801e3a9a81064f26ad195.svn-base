/*
 * TagService.java	 <br>
 * 2011-10-12<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;


/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TagHandle implements ResultSetHandler{
	/**
	 * tags:存放一个产品的所有Tag
	 */ 
	private List<String> tags = new ArrayList<String>();
	/*
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}
	public void handle(ResultSet rs){
		try {
			//此时，因为现在Fabric大类是
			if(rs.getString("catalog_name").equals(Const.FABRIC_CATALOGS.get(0))){
				return;
			}
			tags.add(rs.getString("tag_name").trim());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
