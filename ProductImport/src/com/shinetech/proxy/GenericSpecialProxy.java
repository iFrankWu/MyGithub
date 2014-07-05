/*
 * GenericPetticoatProxy.java	 <br>
 * 2011-11-15<br>
 * com.shinetech.proxy <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.proxy;

import java.sql.ResultSet;

import com.shinetech.bean.Statics9DAttribute;
import com.shinetech.dao.DressDAO;
import com.shinetech.simple.special.SpecialImport;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class GenericSpecialProxy {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		DressDAO dao = new DressDAO();
		try {
			dao.getSpecialInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try {
						//这2个产品已经导入了
//						if(rs.getLong("id") == 14987 || rs.getLong("id") == 14988){
//							return;
//						}
						Statics9DAttribute statics9DAttribute = 
						new Statics9DAttribute(
								rs.getString("9dresses_producut_name"), 
								rs.getString("9dress_itemcode"), 
								rs.getFloat("9dresses_market_price"), 
								rs.getFloat("9dresses_real_price"), 
								rs.getFloat("discount"),
								rs.getString("weight"),
								rs.getLong("id"), 
								rs.getInt("sort_score"),
								rs.getInt("cost_price"),
								rs.getString("mg_description"),
								rs.getString("mg_short_description"),
								rs.getString("mg_meta_title"),
								rs.getString("mg_meta_keywords"),
								rs.getString("mg_meta_description")
								);
						SpecialImport imports = new SpecialImport(statics9DAttribute);
						imports.genericSimpleProduct();
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
