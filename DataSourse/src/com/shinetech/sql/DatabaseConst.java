/*
 * 文件名：DatabaseConst.java	 <br>
 * 创建日期:Feb 26, 2011<br>
 * 包名:com.shinetech.sql <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 功能描述:常量 作者:<a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 * 
 * 作者 ： frank.wu 版本 ： 1.0.1 修改日期 ： 2011/3/4 修改内容 ： 增加几个数据库连接池的常量
 */
public final class DatabaseConst {

	/**
	 * DEFAULT_POOL_NAME: 默认连接池名称
	 */
	public final static String DEFAULT_POOL_NAME = "default";

	/**
	 * RDBMS_ORACLE: 关系型数据名称：Oracle
	 */
	public final static String RDBMS_ORACLE = "oracle";// @1.0.1 begin
	/**
	 * RDBMS_MYSQL: 关系型数据名称：MySQL
	 */
	public final static String RDBMS_MYSQL = "mysql";
	/**
	 * RDBMS_SQLSERVER: 关系型数据名称：SQLServer
	 */
	public final static String RDBMS_SQLSERVER = "sqlserver";
	/**
	 * RDBMS_DB2: 关系型数据名称：DB2
	 */
	public final static String RDBMS_DB2 = "db2";
	/**
	 * RDBMS_POSTGRESQL: 关系型数据名称：PostgreSQL
	 */
	public final static String RDBMS_POSTGRESQL = "postgresql";

	/**
	 * TYPE_WRITE_SUCCESS: 自定义写字段数据成功
	 */
	public final static int TYPE_WRITE_SUCCESS = 0;
	
	/**
	 * MAX_CONNECTION_COUNT:默认数据库连接池的最大连接数
	 */
	public final static int MAX_CONNECTION_COUNT = 50;

	/**
	 * MAX_IDLE:最大空闲连接数
	 */
	public final static int MAX_IDLE = 50;
	/**
	 * MAX_WAIT:最大等待时间
	 */
	public final static int MAX_WAIT = 30000;// @1.0.1 end

	/**
	 * LINE_SEPARATOR: 通用换行符
	 */
	public static final String LINE_SEPARATOR;
	static {
		// avoid security issues
		StringWriter buf = new StringWriter(4);
		PrintWriter out = new PrintWriter(buf);
		out.println();
		LINE_SEPARATOR = buf.toString();
	}
}
