/*
 * OnlyPriceHandle.java	 <br>
 * 2011-11-8<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.util.List;

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

public class OnlyPriceHandle  {
	private Logger logger = Logger.getLogger(this.getClass());
	public static void main(String[] args) {
		Const.initLogger();
		OnlyPriceHandle onlyPriceHandle = new OnlyPriceHandle();
		onlyPriceHandle.updatePrice();
	}
	private DressDAO dao = new DressDAO();
	public void updatePrice(){
		
		try {
			dao.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					TagHandle handle = new TagHandle();
					try {
						dao.getTagInfoByPId(rs.getLong("id"), handle);
						List<String> tags = handle.getTags();
						PriceHandle priceHandle = new PriceHandle();
						String []temp = priceHandle.computePrice(rs.getLong("id"), tags);
						int costPrice = Integer.parseInt(temp[3]);
						float _9dMarketPrice = 0f,_9dRealPrice =0f;
						_9dRealPrice  = Float.parseFloat(temp[0]);
						_9dMarketPrice = 100*_9dRealPrice/(100-rs.getFloat("discount"));
						if(costPrice == 0 ){
							logger.info(rs.getLong("id")+"\t"+tags);
						}
						dao.update9Dresses(rs.getLong("id"), rs.getString("9dresses_producut_name"), rs.getString("9dress_itemcode"), _9dMarketPrice, _9dRealPrice, rs.getFloat("discount"), rs.getString("weight"), rs.getInt("sort_score"), 
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
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
