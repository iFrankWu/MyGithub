/*
 * CatalogParentHandle.java	 <br>
 * 2011-10-31<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;

import com.shinetech.dao.CatagoryDAO;
import com.shinetech.sql.ResultSetHandler;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CatalogParentHandle implements ResultSetHandler {
	private CatagoryDAO dao = new CatagoryDAO();
	private int entityId;
	private int entityTypeId = 9;
	private int attributeId = 111;
	private String catalogName;
	
	public CatalogParentHandle(String catalogName,int entityTypeId,int attributeId){
			this.catalogName = catalogName;
			this.entityTypeId = entityTypeId;
			this.attributeId = attributeId;
	}

	@Override
	public void handle(ResultSet rs) {
		try {
			String value = dao.getCatagotyValue(entityTypeId, attributeId, rs.getInt("entity_id"));
			if(value != null && value.equals(catalogName)){
				entityId = rs.getInt("entity_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int getEntityId(){
		return entityId;
	}
}
