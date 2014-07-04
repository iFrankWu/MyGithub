/*
 * 文件名： DefaultDateWriter.java	 <br>
 * 创建日期: 2011-3-3<br>
 * 包名: com.shinetech.sql.lob.oracle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.type.oracle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.shinetech.sql.DatabaseConst;
import com.shinetech.sql.type.ITypeWriter;

/**
 * 功能描述: 默认处理DATE数据类型 <br>
 * 作者: <a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 */

public class DefaultDateWriter implements ITypeWriter<Date> {

	@Override
	public void free() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int write(Connection conn, PreparedStatement stmt, int index, Date t) throws Exception {
		// TODO Auto-generated method stub
		stmt.setTimestamp(index, new Timestamp(t.getTime()));
		return DatabaseConst.TYPE_WRITE_SUCCESS;
	}

	@Override
	public boolean isFree() {
		// TODO Auto-generated method stub
		return false;
	}

}
