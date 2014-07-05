/*
 * DeleteProduct.java	 <br>
 * 2011-11-7<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.init;

import java.sql.ResultSet;

import com.shinetech.dao.MagentoProductDAO;
import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DeleteProduct {
	public static void main(String[] args) {
		Const.initLogger();
		DeleteProduct delete = new DeleteProduct();
		delete.action(false);
//		delete.deleteAllProduct();
	}
	private MagentoProductDAO dao = new MagentoProductDAO();
	/**
	 * 功能：删除指定的商城产品 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param isSql true 代表配置文件为sql，否则为产品sku列表
	 * void
	 */
	public void action(boolean isSql){
		FileOperate op = new FileOperate();
		if(isSql){
			String sql = op.getContent(Const.DELETE_PRO_LIST_FILE);
			@SuppressWarnings("rawtypes")
			IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
			try {
				db.handleList(sql, new ResultSetHandler() {
					
					@Override
					public void handle(ResultSet rs) {
						try {
							String sku = rs.getLong("id")+"";
							dao.deleteProduct(sku);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (DBException e) {
				e.printStackTrace();
			}
		}else{
			op.getLine(Const.DELETE_PRO_LIST_FILE, true,new ILineHandle<String>() {
	
				@Override
				public String lineHandle(String line) {
					try {
						dao.deleteProduct(line);
					} catch (DBException e) {
						e.printStackTrace();
					}
					return null;
				}
			});
		}
	}
	public void deleteAllProduct(){
		try {
			dao.deleteAll();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
