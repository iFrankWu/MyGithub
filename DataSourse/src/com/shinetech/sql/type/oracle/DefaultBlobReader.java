/*
 * 文件名：DefaultBlobReader.java	 <br>
 * 创建日期:Feb 26, 2011<br>
 * 包名:com.shinetech.sql.lob.oracle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.type.oracle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.type.ITypeReader;

/**
 * 功能描述:Blob读取类，读取结果为byte[]类型<br>
 * 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */
public class DefaultBlobReader implements ITypeReader<byte[]> {
	/**
	 * @param rs
	 *            sql查询返回的ResultSet
	 * @param fieldName
	 *            大字段的名称
	 * @return String 读取blob成的byte[]
	 */
	@Override
	public byte[] read(ResultSet rs, String fieldName) throws DBException {
		BufferedInputStream is = null;
		try {
			byte[] bytes = null;
			Blob blob = rs.getBlob(fieldName);
			if (blob != null) {
				is = new BufferedInputStream(blob.getBinaryStream());
				bytes = new byte[(int) blob.length()];
				int len = bytes.length;
				int offset = 0;
				int read = 0;
				while (offset < len
						&& (read = is.read(bytes, offset, len - offset)) >= 0) {
					offset += read;
				}
			}
			return bytes;
		} catch (SQLException e) {
			throw new DBException("数据库异常：", e);
		} catch (IOException e) {
			throw new DBException("IO异常：", e);
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				return null;
			}

		}
	}
}
