/*
 * 文件名：DefaultClobWriter.java	 <br>
 * 创建日期:Feb 26, 2011<br>
 * 包名:com.shinetech.sql.lob.oracle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.type.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracle.sql.BLOB;
import oracle.sql.CLOB;
import com.shinetech.sql.DatabaseConst;
import com.shinetech.sql.type.ITypeWriter;

/**
 * 功能描述:默认写Oracle大字段Clob 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */

public class DefaultClobWriter implements ITypeWriter<String> {

	CLOB clob = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shinetech.sql.lob.ILobWriter#write(java.sql.Connection,
	 * java.sql.PreparedStatement, int, java.lang.Object)
	 */

	public int write(Connection conn, PreparedStatement stmt, int index, String str) throws Exception {
		try {

			clob = CLOB.createTemporary(conn, false, BLOB.DURATION_SESSION);
			clob.putString(1, str);
			stmt.setClob(index, clob);
			return DatabaseConst.TYPE_WRITE_SUCCESS;
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public void free() throws Exception {
		try {
			if (clob != null) {
				clob.freeTemporary();
			}
		} catch (SQLException e) {
			throw e;
		}

	}

	@Override
	public boolean isFree() {
		// TODO Auto-generated method stub
		return true;
	}

}
