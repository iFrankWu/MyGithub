/*
 * QueryBridalGownsProduct.java	 <br>
 * 2011-11-8<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shinetech.dao.DressDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class QueryBridalGownsProduct {
	private static Logger logger = Logger.getLogger(QueryBridalGownsProduct.class);
	public static void main(String[] args) throws Exception {
		Const.initLogger();
		getBigCatalogIsNull();
		
	}
//	private static FileOperate op = new FileOperate();
	public static void getBigCatalogIsNull() throws DBException{
		final DressDAO dao = new DressDAO();
		dao.getProductInfo(new ResultSetHandler() {
			
			@Override
			public void handle(ResultSet rs) {
				try {
					TagCatatogHandle handle = new TagCatatogHandle();
					dao.getTagInfoByPId(rs.getLong("id"), handle);
					Map<String,ArrayList<String>> map = handle.getCatalogTagMap();
//					if(map.get("图片描述")== null || !map.get("图片描述").contains("不确定裙长")){
//						List<String> list = map.get("Hemline/Train");
//						if(list == null || list.isEmpty()){
//							logger.info(rs.getLong("id"));
//						} 
//					}
//					if(map.get("图片评级") != null && map.get("图片评级").size() > 1){
//						System.out.println(rs.getLong("id")+":"+map.get("图片评级"));
//					}
					String catalogs[] = "Silhouette,Neckline,Hemline/Train,Style".split(",");
					for(String catalog: catalogs){
						List<String> list = map.get(catalog);
						if(list == null || list.isEmpty()){
							logger.info(catalog+"\t"+rs.getLong("id"));
						} 
					}
					
//					catalogs = "Petticoat,Sleeve Length,Waistline,图片评级".split(",");
//					for(String catalog: catalogs){
//						List<String> list = map.get(catalog);
//						if(list != null && list.size() > 1){
//							logger.info(catalog+"\t"+rs.getLong("id"));
//						} 
//					}
					
//				 
//					if(map.get("Wedding Dresses") == null && map.get("Special Occasion Dresses") == null){
//							logger.info("Wedding and Occasion is null:"+rs.getLong("id"));
//					}else{
//						if(map.get("Wedding Dresses") != null && map.get("Wedding Dresses").contains("Bridal Gowns")){
//							if(map.get("Wedding Dresses").size() > 1 || map.get("Special Occasion Dresses")!= null){
//								System.out.println("Bridal Gowns:"+rs.getLong("id"));
//							}
//						}
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void getBadyShapeIsNull() throws DBException{
		final DressDAO dao = new DressDAO();
		dao.getProductInfo(new ResultSetHandler() {
			
			@Override
			public void handle(ResultSet rs) {
				try {
					TagCatatogHandle handle = new TagCatatogHandle();
					dao.getTagInfoByPId(rs.getLong("id"), handle);
					Map<String,ArrayList<String>> map = handle.getCatalogTagMap();
					List<String> list = map.get("Body Shape");
					if(list == null || list.isEmpty()){
						System.out.println(rs.getLong("id"));
					} 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static  void deleteProTag() throws DBException{
		final DressDAO dao = new DressDAO();
		dao.getProductInfo(new ResultSetHandler() {
			
			@Override
			public void handle(ResultSet rs) {
				try {
					TagCatatogHandle handle = new TagCatatogHandle();
					dao.getTagInfoByPId(rs.getLong("id"), handle);
					Map<String,ArrayList<String>> map = handle.getCatalogTagMap();
					List<String> list = map.get("Wedding Dresses");
					if(list != null){
						if(list.contains("Bridal Gowns")){
							if(list.size() > 1 || map.get("Special Occasion Dresses") != null){
								System.out.println(rs.getLong("id"));
//								dao.deleteProTag(rs.getLong("id"), 165);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
