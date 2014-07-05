/*
 * UpdateCatalogProductSortHandle.java	 <br>
 * 2011-11-1<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.shinetech.bean.CatalogProduct;
import com.shinetech.dao.CatagoryDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class UpdateCatalogProductSortHandle implements ResultSetHandler{
	private CatagoryDAO dao = new CatagoryDAO();
	private Logger logger = Logger.getLogger(this.getClass());
	private List<CatalogProduct> products = new ArrayList<CatalogProduct>();
	@Override
	public void handle(ResultSet rs) {
		try {
//			System.out.println(sort);
			CatalogProduct product = new CatalogProduct(Integer.parseInt(rs.getString("value")),rs.getInt("product_id"));
			products.add(product);
//			dao.updateSort(sort++, rs.getInt("category_id"), rs.getInt("product_id"));
//			logger.info(rs.getInt("category_id")+"\t"+rs.getInt("product_id")+"\t"+rs.getString("value"));
		} catch (Exception e) {
			logger.error("读取商城中产品得分信息出错");
			e.printStackTrace();
		}
	}
	public void sortCatagoryProducts(int categoryId){
		final Random random = new Random();
		Collections.sort(products,new Comparator<CatalogProduct>() {
			@Override
			public int compare(CatalogProduct o1, CatalogProduct o2) {
				if(o1.getScore() > o2.getScore()){
					return 1;
				}else if(o1.getScore() == o2.getScore()){
					return random.nextInt(3)-1;
				}else{
					return -1;
				}
			}
		});
		int sort = 1;
		for(CatalogProduct p : products){
			try {
				dao.updateSort(sort++,categoryId, p.getEntityId());
//				logger.info(categoryId+"\t"+p.getEntityId()+"\t"+sort);
			} catch (DBException e) {
				logger.error("更新产品"+p.toString()+"时，出错");
				e.printStackTrace();
			}
		}
	}
}
