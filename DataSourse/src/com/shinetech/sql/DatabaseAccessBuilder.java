package com.shinetech.sql;

import com.shinetech.sql.impl.DefaultDatabaseAccess;
import com.shinetech.sql.pools.DBConfigManager;

/**
 * <font color="red">注意：如果是用来查询，此类实例化时必须指定泛型</font><br>
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：DatabaseAccessBuilder.java<br>
 * 包名：com.shinetech.sql<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/23<br>
 * 版本：1.0.0 <br>
 * 功能描述：数据库操作实例创建工厂 <br>
 * 
 * 作者：loong<br>
 * 版本：1.0.1<br>
 * 修改日期：2011/3/2<br>
 * 修改原因：具体类实现<br>
 * 
 */
public class DatabaseAccessBuilder<T> {

	/**
	 * 构造方法，加载配置
	 */
	public DatabaseAccessBuilder() {
		DBConfigManager.getInstance();
	}

	/**
	 * 得到默认连接池数据库操作对象
	 * 
	 * @return
	 */
	public IDatabaseAccess<T> getDatabaseAccess() {
		return getDatabaseAccess(DatabaseConst.DEFAULT_POOL_NAME);
	}

	/**
	 * 得到指定连接池数据库操作对象
	 * 
	 * @param poolName
	 *            连接池名
	 * @return
	 */
	public IDatabaseAccess<T> getDatabaseAccess(String poolName) {
		IDatabaseAccess<T> databaseAccess = new DefaultDatabaseAccess<T>();
		databaseAccess.setDatabase(poolName);

		return databaseAccess;
	}
}
