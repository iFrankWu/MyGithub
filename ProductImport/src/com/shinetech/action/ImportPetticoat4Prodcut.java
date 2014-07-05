/*
 * ImportPetticoat4Prodcut.java	 <br>
 * 2011-11-15<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shinetech.dao.DressDAO;
import com.shinetech.dao.MagentoProductDAO;
import com.shinetech.handle.TagCatatogHandle;
import com.shinetech.simple.petticoat.PetticoatInfo;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportPetticoat4Prodcut {
	public static void main(String[] args) {
		Const.initLogger();
		ImportPetticoat4Prodcut petticoatProduct = new ImportPetticoat4Prodcut();
		petticoatProduct.importUpSellProduct();
	}
	private DressDAO dao = new DressDAO();
	private Map<String,Long> tag2skuMap = PetticoatInfo.petticoatTagProductSku();
	private MagentoProductDAO magentoDAO = new MagentoProductDAO();
	public void importUpSellProduct(){
		try {
			dao.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
						Long productId = rs.getLong("id");
						TagCatatogHandle handle = new TagCatatogHandle();
						dao.getTagInfoByPId(productId, handle);
						Map<String,ArrayList<String>> map = handle.getCatalogTagMap();
						if(map.get("Petticoat") != null){
							List<String> list = map.get("Petticoat");
							int post = 1;
							for(String tag : list){
								String upSellSku = tag2skuMap.get(tag)+"";
//								System.out.println(productId+":"+upSellSku+":"+post++);
								magentoDAO.saveProductLink(productId+"", upSellSku, ConstConfig.product_link_type_code[2]);
								int linkId = magentoDAO.getLastId();
								magentoDAO.insertProductLinkInt(ConstConfig.product_link_type_code[2], ConstConfig.product_link_attribute_code[0], linkId, post++);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
