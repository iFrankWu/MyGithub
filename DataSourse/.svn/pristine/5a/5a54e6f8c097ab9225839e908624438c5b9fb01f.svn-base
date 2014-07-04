/*
 * 文件名：DefaultClobWriterTest.java	 <br>
 * 创建日期:Mar 4, 2011<br>
 * 包名:com.shinetech.sql.lob.oracle.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.type.oracle.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.shinetech.sql.type.oracle.DefaultClobWriter;

/**
 * 功能描述:测试DefaultClobWriter类的测试用例
 * 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */

public class DefaultClobWriterTest {
	private DefaultClobWriter clobWriter = null;
	/**
	 * 功能：测试前的初始化 <br>
	 * @throws java.lang.Exception
	 * void
	 */
	@Before
	public void setUp() throws Exception {
		clobWriter = new DefaultClobWriter();
	}

	/**
	 * Test method for {@link com.shinetech.sql.type.oracle.DefaultClobWriter#write(java.sql.Connection, java.sql.PreparedStatement, int, java.lang.String)}.
	 */
	@Test
	public void testWrite() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.2.230:1521:site", "reply",
					"reply");
			String sql = "insert into test3 values(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "e");
			String a="asdf";
			clobWriter.write(conn, ps, 2, a);
			ps.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Test method for {@link com.shinetech.sql.type.oracle.DefaultClobWriter#free()}.
	 */
	@Test
	public void testFree() {
		try {
			clobWriter.free();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
