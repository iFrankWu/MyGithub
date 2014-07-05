/*
 * InitProductInfoHandle.java	 <br>
 * 2011-10-26<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.init;

import java.sql.ResultSet;

import com.shinetech.dao.DressDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;

/**
 * 初始化数据库中的产品名等信息
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class InitProductInfoHandle {
	public static void main(String[] args) {
		Const.initLogger();
		InitProductInfoHandle handle = new InitProductInfoHandle();
		handle.init();
//		handle.initProdcutName();
	}
	private DressDAO dao = new DressDAO();
	public void init(){
		try {
			dao.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					String _9dName = null;// _9dItemcode = null;
					Float _9dMarketPrice = 0f, _9dRealPrice = 0f;
					try {
						dao.update9Dresses(rs.getLong("id"),
								_9dName, 
								rs.getString("9dress_itemcode"),
								_9dMarketPrice,
								_9dRealPrice,
//								rs.getFloat("9dresses_market_price"), 
//								rs.getFloat("9dresses_real_price"), 
								rs.getFloat("discount"), 
								rs.getString("weight"), 
								rs.getInt("sort_score"), 
//								rs.getInt("cost_price"),
								null,
								null,
								null,
								null,
								null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initProdcutName(){
		try {
			dao.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					String _9dName = null;
					try {
						dao.update9Dresses(
								rs.getLong("id"), 
								_9dName, 
								rs.getString("9dress_itemcode"), 
								rs.getFloat("9dresses_market_price"), 
								rs.getFloat("9dresses_real_price"), 
								rs.getFloat("discount"), 
								rs.getString("weight"), 
								rs.getInt("sort_score"), 
//								rs.getInt("cost_price"),
								rs.getString("mg_description"),
								rs.getString("mg_short_description"),
								rs.getString("mg_meta_title"),
								rs.getString("mg_meta_keywords"),
								rs.getString("mg_meta_description"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
}
