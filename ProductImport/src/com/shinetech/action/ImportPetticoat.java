/*
 * ImportPetticoat.java	 <br>
 * 2011-11-14<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.shinetech.dao.DressDAO;
import com.shinetech.handle.PetticoatHandle;
import com.shinetech.handle.TagCatatogHandle;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * 导入衬裙
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportPetticoat {

	/**
	 * 功能：导入到231 product_dress中 product_tag关系表
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		System.err.println("这步已经过时，是用导入衬群产品的tag");
//		ImportPetticoat pett = new ImportPetticoat();
//		pett.importPetticoat();
	}
	private DressDAO dao = new DressDAO();
	public void importPetticoat(){
		try {
			dao.getProductInfo(new ResultSetHandler() {
				@Override
				public void handle(ResultSet rs) {
						try {
							Long productId = rs.getLong("id");
							TagCatatogHandle handle =new TagCatatogHandle();
							dao.getTagInfoByPId(productId, handle);
							Map<String,ArrayList<String>> map = handle.getCatalogTagMap();
							PetticoatHandle pettHandle = new PetticoatHandle();
							Set<String> pettSet = pettHandle.getPett(map);
							for(String pett : pettSet){
								dao.svaeProductTag(productId, PetticoatHandle.PetticoatsIdMap.get(pett));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			});
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

}
