/*
 * CatalogUtil.java	 <br>
 * 2011-11-1<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.dao.CatagoryDAO;
import com.shinetech.handle.CatalogParentHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CatalogUtil {
	private CatagoryDAO dao = new CatagoryDAO();
	public List<Integer> getCategoryId(String catagoryName,int entityTypeId,int attributeId) throws Exception{
		String catagorys[] = catagoryName.split("/");
		int parentId = 1;//找到新插入的分类的父id 
		List<Integer> entityIdList = new ArrayList<Integer>();
		entityIdList.add(1);
		
		for(int index = 0;index < catagorys.length;index++){
			String catalogName = catagorys[index];
			CatalogParentHandle handle = new CatalogParentHandle(catalogName,entityTypeId,attributeId);
			dao.getCatalogByParentId(parentId, handle);
			if(handle.getEntityId() > 0){
				parentId = handle.getEntityId();
			}else{
				if(index <catagorys.length - 1){
					System.err.println(catalogName+"不存在");
					throw new Exception("分类不存在："+catalogName);
				}
			}
			entityIdList.add(handle.getEntityId());
		}
		return entityIdList;
	}
	
}
