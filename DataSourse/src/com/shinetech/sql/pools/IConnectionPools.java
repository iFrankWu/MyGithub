package com.shinetech.sql.pools;

import java.sql.Connection;
import java.util.List;

import com.shinetech.sql.exception.DBException;

/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：ConnectionPools.java<br>
 * 包名：com.shinetech.sql.pools<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/23<br>
 * 版本：1.0.0 <br>
 * 功能描述： 连接管理接口<br>
 */
public interface IConnectionPools {
	/**
	 * 重新初始化连接池
	 * 
	 * @param bean
	 */
	public void init(List<DBConfigBean> bean);

	/**
	 * 获得指定连接池的一个空闲连接
	 * 
	 * @param poolName
	 *            连接池名
	 * @return
	 * @throws DBException 
	 */
	public Connection getConnection(String poolName) throws DBException;

	/**
	 * 释放连接回连接池
	 * 
	 * @param poolName
	 *            连接池名
	 * @param conn
	 *            要释放的链接
	 * @throws DBException 
	 */
	public void freeConnection(String poolName, Connection conn) throws DBException;

	/**
	 * 通知连接池关闭连接
	 * 
	 * @param poolName
	 *            连接池名
	 * @param conn
	 *            要关闭的连接
	 * @throws DBException 
	 */
	public void closeConnection(String poolName, Connection conn) throws DBException;

	/**
	 * 清空连接池
	 * @throws DBException 
	 */
	public void release() throws DBException;
}
