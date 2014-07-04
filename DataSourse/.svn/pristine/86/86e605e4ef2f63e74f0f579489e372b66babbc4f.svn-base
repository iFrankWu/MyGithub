/*
 * 文件名： MySQLDialect.java	 <br>
 * 创建日期: 2011-3-3<br>
 * 包名: com.shinetech.sql.dialect <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.dialect;

/**
 * 功能描述: MySQL特定方言实现 <br>
 * 作者: <a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 */

public class MySQLDialect extends Dialect {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shinetech.sql.dialect.Dialect#getLimitString(java.lang.String,
	 * boolean)
	 */
	@Override
	public String getLimitString(String sql) {
		// TODO Auto-generated method stub
		return new StringBuffer(sql.length() + 20).append(sql)
				.append(supportsLimitOffset() ? " limit ?, ?" : " limit ?").toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shinetech.sql.dialect.Dialect#supportsLimitOffset()
	 */
	@Override
	public boolean supportsLimitOffset() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supportsLimit() {
		// TODO Auto-generated method stub
		return true;
	}

}
