package com.shinetech.sql.test;

import java.util.Iterator;
import java.util.List;

import com.shinetech.sql.pools.DBConfigBean;
import com.shinetech.sql.pools.DBConfigManager;

public class TestDBConfigManager {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<DBConfigBean> list = DBConfigManager.getInstance().getDbConfigList();
		for (DBConfigBean dbConfigBean : list) {
			System.out.println("===========================");
			System.out.println("name : " + dbConfigBean.getName());
			System.out.println("type : " + dbConfigBean.getType());
			System.out.println("driver : " + dbConfigBean.getDriver());
			System.out.println("username : " + dbConfigBean.getUsername());
			System.out.println("password : " + dbConfigBean.getPassword());
			System.out.println("url : " + dbConfigBean.getUrl());
			for (Iterator<String> iter = dbConfigBean.getParamMap().keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				System.out.println(key + " : " + dbConfigBean.getParamMap().get(key));
			}
		}

		// String[] types = new String[] { "oracle", "mysql", "db2" };
		// for (int i = 0; i < types.length; i++) {
		// LobConfigBean lobConfigBean =
		// DBConfigManager.getInstance().getLobConfigBean(types[i]);
		// if (lobConfigBean != null) {
		// System.out.println("===========================");
		// System.out.println("name : " + lobConfigBean.getName());
		// System.out.println("clobread : " + lobConfigBean.getClobread());
		// System.out.println("clobwrite : " + lobConfigBean.getClobwrite());
		// System.out.println("blobread : " + lobConfigBean.getBlobread());
		// System.out.println("blobwrite : " + lobConfigBean.getBlobwrite());
		// }
		// }
		//
		// for (int i = 0; i < types.length; i++) {
		// TypeConfigBean typeConfigBean =
		// DBConfigManager.getInstance().getTypeConfigBean(types[i]);
		// if (typeConfigBean != null) {
		// System.out.println("===========================");
		// System.out.println("name : " + typeConfigBean.getName());
		// for (Iterator<String> iter =
		// typeConfigBean.getTypeMap().keySet().iterator(); iter.hasNext();) {
		// String key = iter.next();
		// System.out.println(key + " : " +
		// typeConfigBean.getTypeMap().get(key));
		// }
		// }
		// }
	}
}
