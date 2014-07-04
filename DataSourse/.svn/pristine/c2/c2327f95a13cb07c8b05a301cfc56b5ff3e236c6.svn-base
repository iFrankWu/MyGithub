/*
 * 文件名： OracleDialect.java	 <br>
 * 创建日期: 2011-3-3<br>
 * 包名: com.shinetech.sql.dialect <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.dialect;

import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.type.oracle.DefaultBlobReader;
import com.shinetech.sql.type.oracle.DefaultBlobWriter;
import com.shinetech.sql.type.oracle.DefaultClobReader;
import com.shinetech.sql.type.oracle.DefaultClobWriter;
import com.shinetech.sql.type.oracle.DefaultDateWriter;

/**
 * 功能描述: Oracle特定方言实现 <br>
 * 作者: <a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 */

public class OracleDialect extends Dialect {

	public OracleDialect() {
		super();
		registerDefaultTypeReader(FieldTypes.CLOB, new DefaultClobReader());
		registerDefaultTypeReader(FieldTypes.BLOB, new DefaultBlobReader());
		registerDefaultTypeWriter(FieldTypes.CLOB, new DefaultClobWriter());
		registerDefaultTypeWriter(FieldTypes.BLOB, new DefaultBlobWriter());
		registerDefaultTypeWriter(FieldTypes.DATE, new DefaultDateWriter());
	}

	@Override
	public String getLimitString(String sql) {
		// TODO Auto-generated method stub

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (supportsLimitOffset()) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (supportsLimitOffset()) {
			pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
		} else {
			pagingSelect.append(" ) where rownum <= ?");
		}

		return pagingSelect.toString();
	}

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
