/*
 * 文件名： SQLServerDialect.java	 <br>
 * 创建日期: 2011-3-3<br>
 * 包名: com.shinetech.sql.dialect <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.dialect;

/**
 * 功能描述: SQLServer特定方言实现 <br>
 * 作者: <a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 */

public class SQLServerDialect extends Dialect {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shinetech.sql.dialect.Dialect#getLimitString(java.lang.String,
	 * boolean)
	 */
	@Override
	public String getLimitString(String sql) {
		// TODO Auto-generated method stub
		return new StringBuffer(sql.length() + 8).append(sql).insert(getAfterSelectInsertPoint(sql), " top ?")
				.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shinetech.sql.dialect.Dialect#supportsLimitOffset()
	 */
	@Override
	public boolean supportsLimitOffset() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsLimit() {
		// TODO Auto-generated method stub
		return true;
	}

	private int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}
}
