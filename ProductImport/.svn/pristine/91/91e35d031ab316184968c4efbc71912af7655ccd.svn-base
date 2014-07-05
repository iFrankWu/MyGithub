/*
 * DeleteCategory.java	 <br>
 * 2011-11-10<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.init;

import java.io.File;
import java.util.List;

import com.shinetech.action.CatalogUtil;
import com.shinetech.dao.CatagoryDAO;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 *  
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DeleteCategory {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		//由于分类表存在脏数据，所以变更新的分类删除的方法
//		new DeleteCategory().readCataegory();
		new DeleteCategory().deleteCustomCategory();
	}
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	public void deleteCustomCategory(){
		try {
			dao.deleteCustomCategory();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	private CatagoryDAO dao = new CatagoryDAO();
	private CatalogUtil catalogUtil = new CatalogUtil();
	public void readCataegory(){
		FileOperate op = new FileOperate();
		op.getLine(new File(Const.CATALOG_IMPORT_FORMAT), new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				if(!line.startsWith("#")){
					String catalogName = line.split(";")[0];
					try {
						deleteCategory(catalogName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
	}
	public void deleteCategory(String catagoryName) throws Exception{
		int entityTypeId = dao.getEntityTypeId();
		int cAttributeId = dao.getAttributeId("name", entityTypeId);
		List<Integer> list = catalogUtil.getCategoryId(catagoryName, entityTypeId, cAttributeId);
		int categoryId = list.get(list.size()-1);
		dao.deleteCategory(categoryId);
	}
}
