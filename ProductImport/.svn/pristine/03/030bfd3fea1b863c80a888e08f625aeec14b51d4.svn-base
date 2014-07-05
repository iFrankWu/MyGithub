/*
 * UpdateCatalogProductSort.java	 <br>
 * 2011-11-1<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.io.File;
import java.util.List;

import com.shinetech.dao.CatagoryDAO;
import com.shinetech.handle.UpdateCatalogProductSortHandle;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class UpdateCatalogProductSort {
	private CatagoryDAO dao = new CatagoryDAO();
	private CatalogUtil catalogUtil = new CatalogUtil();
	
	public static void main(String[] args) throws Exception {
		Const.initLogger();
		UpdateCatalogProductSort test = new UpdateCatalogProductSort();
//		String catagoryName = "Default Category/Special Occasion Dresses";
//		test.update(catagoryName);
		test.action();
	}
	public void action(){
		FileOperate op = new FileOperate();
		op.getLine(new File(Const.CATALOG_IMPORT_FORMAT), new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				if(!line.startsWith("#")){
					String catalogName = line.split(";")[0];
					try {
						update(catalogName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
	}
	public void update(String catagoryName) throws Exception{
		String entityTypeCode = "catalog_product";
	
		String attributeCode = "product_score";//这个属性在magento中暂未新建
		attributeCode = "sort_score";
//		attributeCode = "itemcode";
		int entityTypeId = dao.getEntityTypeId();
		int cAttributeId = dao.getAttributeId("name", entityTypeId);
		List<Integer> list = catalogUtil.getCategoryId(catagoryName, entityTypeId, cAttributeId);
		int categoryId = list.get(list.size()-1);
		
	
		entityTypeId = dao.getEntityTypeId(entityTypeCode);
		int attributeId = dao.getAttributeId(attributeCode, entityTypeId);
		UpdateCatalogProductSortHandle handle = new UpdateCatalogProductSortHandle();
		dao.getCatalogProdcuts(categoryId, attributeId, entityTypeId, handle);
		handle.sortCatagoryProducts(categoryId);
	}
}
