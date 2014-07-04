/*
 * 文件名：DefaultBlobReaderTest.java	 <br>
 * 创建日期:Mar 4, 2011<br>
 * 包名:com.shinetech.sql.lob.oracle.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.type.oracle.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.shinetech.sql.type.oracle.DefaultBlobReader;

/**
 * 功能描述:DefaultBlobReader测试用例
 * 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */

public class DefaultBlobReaderTest {
	private DefaultBlobReader blobReader = null;
	/**
	 * 功能：测试开始前的初始化过程 <br>
	 * @throws java.lang.Exception
	 * void
	 */
	@Before
	public void setUp() throws Exception {
		blobReader = new DefaultBlobReader();
	}

	/**
	 * Test method for {@link com.shinetech.sql.type.oracle.DefaultBlobReader#read(java.sql.ResultSet, java.lang.String)}.
	 */
	@Test
	public void testRead() {
		Connection conn = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.2.230:1521:site", "reply",
					"reply");
			String sql = "select cc,blob from test";

			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String cc = rs.getString("cc");
				System.out.println("cc= " + cc);
				byte[] bytes = blobReader.read(rs, "blob");
				System.out.println("blob= "+bytes);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
