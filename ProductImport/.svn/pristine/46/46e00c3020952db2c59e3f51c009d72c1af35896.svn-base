/*
 * CheckTagIsNull.java	 <br>
 * 2011-11-24<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
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

public class CheckTagIsNull {
	private static Logger logger = Logger.getLogger(QueryBridalGownsProduct.class);
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		try {
			CheckTagIsNull.check();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	public static void check() throws DBException{
		final DressDAO dao = new DressDAO();
		logger.info("begin check...");
		dao.getProductInfo(new ResultSetHandler() {
			
			@Override
			public void handle(ResultSet rs) {
				try {
					CheckTagCatalogHandle handle = new CheckTagCatalogHandle();
					dao.getTagInfoByPId(rs.getLong("id"), handle);
					Map<String,ArrayList<String>> map = handle.getCatalogTagMap();
//					String[] catalogs = "Silhouette,Neckline,Sleeve Length,Hemline/Train,Waistline,Fabric,Style".split(",");//SimpleProductConfig.Catalogs
//					catalogs = "Silhouette,Neckline,Sleeve Length,Hemline/Train,Waistline,Fabric,Style,Body Shape,Season".split(",");
//					catalogs = "Waistline".split(",");
					//检查大类{Silhouette,Neckline,Sleeve Length,Hemline/Train,Waistline,Fabric,Style}下是否有没有被选中的子标签
					//2011/11/11 14:51检查 存在三个
					//Hemline/Train	1059
					//Hemline/Train	1424
					//Hemline/Train	10850
//					catalogs = "Silhouette,Neckline,Waistline,Hemline/Train,Fabric,Style,Sleeve Length".split(",");
					//2011/11/11 15:29检查已经没有了
//					catalogs = "Silhouette,Neckline,Sleeve Length,Back Details,Fabric,Hemline/Train,Embellishment,Waistline,Body Shape,Season,Style".split(",");
//					for(String catalog: catalogs){
//						List<String> list = map.get(catalog);
//						if(list == null || list.isEmpty()){
//							logger.info(catalog+"\t"+rs.getLong("id"));
//						} 
//					}
//					List<String> list = map.get("图片描述");
//					if(list != null && !list.isEmpty()){
//						System.out.println(rs.getLong("id"));
//					}
					//检查Wedding Dresses下的Venues是否为空。
					//2011/11/11 14：46检查，全部产品的Venues都不为空
//					if(map.get("Wedding Dresses") != null){
//						if(map.get("Wedding Dresses").contains("Bridal Gowns")){
//							if(map.get("Venues") == null || map.get("Venues").isEmpty())
//								logger.info(rs.getLong("id"));
//						}
//					}
					//检查一个产品是否属于2个品类，即既属于Bridal Gowns和Special Occasion Dresses
					//2011/11/11 14：49检查，存在75个产品出现在2个品类中
//					if(map.get("Wedding Dresses") != null){
//						if(map.get("Wedding Dresses").contains("Bridal Gowns") && map.get("Special Occasion Dresses") != null){
//								logger.info(rs.getLong("id"));
//						}else{
//							if(!map.get("Wedding Dresses").contains("Bridal Gowns") && map.get("Special Occasion Dresses") == null){
//								logger.info(rs.getLong("id")+"\t选中Wedding Venues下非Bridal Gowns标签的产品，Special Occasion Dresses大类为空");
//							}
//						}
//					}
					
					//一个产品不属于Wedding Dresses 又不属于 Special Occasion Dresses，会导致计算错误
					//2011/11/11 14：57 检查没有符合该条件的产品了
//					if(map.get("Wedding Dresses") == null && map.get("Special Occasion Dresses") == null){
//							logger.info(rs.getLong("id"));
//					}
				
					//要检查Frabic
					List<String> mainFabrics = map.get("The Main Fabric");
					List<String> fabric1s = map.get("Fabric1");
					List<String> fabric2s = map.get("Fabric2");
					if(mainFabrics == null && fabric1s == null && fabric2s == null){
						//System.out.println(rs.getLong("id"));
						return;
					}
					if(mainFabrics != null && mainFabrics.size() == 1){
						
					}else{
						System.out.println(rs.getLong("id"));
//						System.out.println("The value of The Main Fabric is null or size > 1,id:"+rs.getLong("id"));
						return ;
					}
					
					if(fabric1s != null && fabric1s.size() > 1){
						System.out.println(rs.getLong("id"));
//						System.out.println("The value of Fabric1 is null or size > 1,id:"+rs.getLong("id"));
						return;
					}
					
					if(fabric2s != null && fabric2s.size() > 1){
						System.out.println(rs.getLong("id"));
//						System.out.println("The value of Fabric2 is null or size > 1,id:"+rs.getLong("id"));
						return;
					}
					if(mainFabrics != null && fabric1s != null){
						if(!Collections.disjoint(mainFabrics,fabric1s)){
							System.out.println(rs.getLong("id"));
							return;
						}
					}
					if(mainFabrics != null && fabric2s != null)
						if(!Collections.disjoint(mainFabrics,fabric2s)){
							System.out.println(rs.getLong("id"));
							return;
						}
					if(fabric2s != null && fabric1s != null)
						if(!Collections.disjoint(fabric2s,fabric1s)){
							System.out.println(rs.getLong("id"));
							return;
						}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}

}
