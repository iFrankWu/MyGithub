/*
 * 文件名： DialectFactory.java	 <br>
 * 创建日期: 2011-3-3<br>
 * 包名: com.shinetech.sql.dialect <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.dialect;

import org.apache.commons.lang.StringUtils;

import com.shinetech.sql.DatabaseConst;
import com.shinetech.sql.exception.DBException;

/**
 * 功能描述: 特定方言实现类工厂 <br>
 * 作者: <a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 */

public final class DialectFactory {

	/**
	 * 功能：返回指定数据库类型的特定方言实现对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param dbType
	 * @return Dialect
	 * @throws DBException
	 */
	public static Dialect getDialect(String dbType) throws DBException {

		Dialect dialect = null;
		String type = StringUtils.lowerCase(dbType);

		if (type.equals(DatabaseConst.RDBMS_DB2)) {
			dialect = new DB2Dialect();
		} else if (type.equals(DatabaseConst.RDBMS_MYSQL)) {
			dialect = new MySQLDialect();
		} else if (type.equals(DatabaseConst.RDBMS_ORACLE)) {
			dialect = new OracleDialect();
		} else if (type.equals(DatabaseConst.RDBMS_POSTGRESQL)) {
			dialect = new PostgreSQLDialect();
		} else if (type.equals(DatabaseConst.RDBMS_SQLSERVER)) {
			dialect = new SQLServerDialect();
		} else {
			throw new DBException("不支持数据库类型[" + dbType + "]");
		}

		return dialect;
	}
}
