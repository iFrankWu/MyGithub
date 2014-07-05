/*
 * RelatedProductHandle.java	 <br>
 * 2011-11-10<br>
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

import org.apache.log4j.Logger;

import com.shinetech.action.ConstConfig;
import com.shinetech.bean.CatalogProduct;
import com.shinetech.dao.DressDAO;
import com.shinetech.sql.ResultSetHandler;

/**
 * 正对每一个产品进行的处理
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class RelatedProductHandle implements ResultSetHandler{
	private Logger logger = Logger.getLogger(this.getClass());
	private CatalogProduct product;
	private Long productId;
	private DressDAO dressDAO = new DressDAO();
	/**
	 * tagPidsMap:tag对应的产品id列表
	 */ 
	public static Map<String,ArrayList<CatalogProduct>> tagPidsMap = new HashMap<String, ArrayList<CatalogProduct>>(); 
	/**
	 * productList:产品列表
	 */ 
	public static List<CatalogProduct> productList = new ArrayList<CatalogProduct>();
	/**
	 * catalogTagsMap:大类别对应的tag集合
	 */ 
	//private Map<String,ArrayList<String>> catalogTagsMap = new HashMap<String, ArrayList<String>>(); 
	@Override
	public void handle(ResultSet res) {
			try {
				productId = res.getLong("id");
				product = new CatalogProduct();
				product.setSku(productId+"");
				product.setScore(res.getInt("sort_score"));
				if(res.getString("weight") == null){
					return;
				}
				final Map<String,ArrayList<String>> catalogTagsMap = new HashMap<String, ArrayList<String>>(); 
				
				dressDAO.getTagInfoByPId(productId, new ResultSetHandler() {
					
					@Override
					public void handle(ResultSet rs) {
						try {
							String catalogName = rs.getString("catalog_name");
							if(ConstConfig.catalogs.contains(catalogName)){
								String tagName = rs.getString("tag_name");
								ArrayList<String> list = catalogTagsMap.get(catalogName);
								if(list == null){
									list = new ArrayList<String>();
									catalogTagsMap.put(catalogName, list);
								}
								list.add(tagName);
								ArrayList<CatalogProduct> pids = tagPidsMap.get(tagName);
								if(pids == null){
									pids = new ArrayList<CatalogProduct>();
									tagPidsMap.put(tagName, pids);
								}
								pids.add(product);
							}
						} catch (SQLException e) {
							logger.error("");
							e.printStackTrace();
						}
					}
				});
				product.setCatalogTagsMap(catalogTagsMap);
				productList.add(product);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
