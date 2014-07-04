/*
 * 文件名：DefaultClobReader.java	 <br>
 * 创建日期:Feb 26, 2011<br>
 * 包名:com.shinetech.sql.lob.oracle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.type.oracle;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.type.ITypeReader;

/**
 * 功能描述: 默认读取大字段Clob<br>
 * 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */

public class DefaultClobReader implements ITypeReader<String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shinetech.sql.lob.ILobReader#read(java.sql.ResultSet,
	 *      java.lang.String)
	 */
	@Override
	public String read(ResultSet rs, String fieldName) throws DBException {
		StringBuffer buffer = new StringBuffer();
		if (rs != null) {
			Reader reader = null;
			try {
				Clob clob = rs.getClob(fieldName);
				if (clob != null) {
					reader = clob.getCharacterStream();
					int c = 0;
					while ((c = reader.read()) >= 0) {
						buffer.append((char) c);
					}
				}

			} catch (SQLException e) {
				throw new DBException("数据库异常:",e);
			} catch (IOException e) {
				throw new DBException("IO异常：", e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return buffer.toString();
	}
}
