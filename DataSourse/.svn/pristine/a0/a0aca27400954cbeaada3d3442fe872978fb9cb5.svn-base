/*
 * 文件名：DefaultClobReaderTest.java	 <br>
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

import com.shinetech.sql.type.oracle.DefaultClobReader;

/**
 * 功能描述: DefaultClobReader类的测试用例 <br>
 * 作者:<a href="mailto:husw11@hotmail.com">husw</a>
 * <br>
 * 版本: 1.0.0<br>
 */

public class DefaultClobReaderTest {
	private DefaultClobReader clobReader = null;

	/**
	 * 功能：测试前初始化 <br>
	 * 
	 * @throws java.lang.Exception
	 *             void
	 */
	@Before
	public void setUp() throws Exception {
		clobReader = new DefaultClobReader();
	}

	/**
	 * 功能：测试Read方法
	 *  Test method for
	 * {@link com.shinetech.sql.type.oracle.DefaultClobReader#read(java.sql.ResultSet, java.lang.String)}.
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
			String sql = "select cc,clob from test3";

			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String cc = rs.getString("cc");
				System.out.println("cc= " + cc);
				String clob = clobReader.read(rs, "clob");
				System.out.println("clob= " + clob);
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
