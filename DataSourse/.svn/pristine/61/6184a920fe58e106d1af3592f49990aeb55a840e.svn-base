/*
 * 文件名： DB2Dialect.java	 <br>
 * 创建日期: 2011-3-3<br>
 * 包名: com.shinetech.sql.dialect <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.dialect;

/**
 * 功能描述: DB2特定方言实现 <br>
 * 作者: <a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 */

public class DB2Dialect extends Dialect {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shinetech.sql.dialect.Dialect#getLimitString(java.lang.String,
	 * boolean)
	 */
	@Override
	public String getLimitString(String sql) {
		// TODO Auto-generated method stub
		int startOfSelect = sql.toLowerCase().indexOf("select");

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100).append(sql.substring(0, startOfSelect))
				.append("select * from ( select ").append(getRowNumber(sql));
		if (hasDistinct(sql)) {
			pagingSelect.append(" row_.* from ( ").append(sql.substring(startOfSelect)).append(" ) as row_");
		} else {
			pagingSelect.append(sql.substring(startOfSelect + 6));
		}

		pagingSelect.append(" ) as temp_ where rownumber_ ");

		// add the restriction to the outer select
		if (supportsLimitOffset()) {
			pagingSelect.append("between ?+1 and ?");
		} else {
			pagingSelect.append("<= ?");
		}

		return pagingSelect.toString();
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

	private String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50).append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}

	private boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
	}

}
