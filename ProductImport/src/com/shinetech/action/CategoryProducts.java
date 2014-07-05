/*
 * CategoryProducts.java	 <br>
 * 2011-11-24<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.io.File;
import java.util.List;

import com.shinetech.dao.CatagoryDAO;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CategoryProducts {
	private CatagoryDAO dao = new CatagoryDAO();
	private CatalogUtil catalogUtil = new CatalogUtil();
	public static void main(String[] args) throws Exception {
		Const.initLogger();
		CategoryProducts category = new CategoryProducts();
		List<String> list = category.getCategoryProducts("Default Category/Wedding Dresses");
		System.out.println(list.size());
	}
	public void readCataegory(){
		FileOperate op = new FileOperate();
		op.getLine(new File(Const.CATALOG_IMPORT_FORMAT), new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				if(!line.startsWith("#")){
					String catalogName = line.split(";")[0];
					try {
						getCategoryProducts(catalogName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
	}
	/**
	 * 功能： <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param catagoryName
	 * @throws Exception
	 * void
	 */
	public List<String> getCategoryProducts(String catagoryName) throws Exception{
		int entityTypeId = dao.getEntityTypeId();
		int cAttributeId = dao.getAttributeId("name", entityTypeId);
		List<Integer> list = catalogUtil.getCategoryId(catagoryName, entityTypeId, cAttributeId);
		int categoryId = list.get(list.size()-1);
		return dao.getProductSkus(categoryId);
	}

}
