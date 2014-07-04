/*
 * 文件名：DefaultBlobWriter.java	 <br>
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

import com.shinetech.sql.DatabaseConst;
import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.type.ITypeWriter;

/**
 * 功能描述:默认写Blob方式 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 * 
 * 作者 : loong<br>
 * 修改日期 : 2011/3/4<br>
 * 版本：1.0.1 <br>
 * 修改原因：putBytes参数pos设置错误<br>
 */

public class DefaultBlobWriter implements ITypeWriter<byte[]> {
	BLOB blob = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shinetech.sql.lob.ILobWriter#write(java.sql.Connection,
	 *      java.sql.PreparedStatement, int, java.lang.Object)
	 */
	@Override
	public int write(Connection conn, PreparedStatement stmt, int index,
			byte[] bytes) throws DBException {
		// TODO Auto-generated method stub
		try {
			blob = BLOB.createTemporary(conn, false, BLOB.DURATION_SESSION);
			blob.putBytes(1, bytes);
			stmt.setBlob(index, blob);
			return DatabaseConst.TYPE_WRITE_SUCCESS;
		} catch (SQLException e) {
			throw new DBException("数据库异常:", e);
		}
	}

	@Override
	public void free() throws DBException{
		try {
			if (blob != null) {
				blob.freeTemporary();
			}
		} catch (SQLException e) {
			throw new DBException("大字段释放失败:", e);
		}

	}

	@Override
	public boolean isFree() {
		// TODO Auto-generated method stub
		return true;
	}

}
