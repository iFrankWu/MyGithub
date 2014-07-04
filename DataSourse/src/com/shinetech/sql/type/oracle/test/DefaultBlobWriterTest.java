package com.shinetech.sql.type.oracle.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.type.oracle.DefaultBlobWriter;

/**
 * 功能描述:DefaultBlobWriter类的测试用例
 * 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */
public class DefaultBlobWriterTest {

	private DefaultBlobWriter blobWriter = null;

	@Before
	public void setUp() throws Exception {
		blobWriter = new DefaultBlobWriter();
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * 功能：测试write方法 <br>
	 * void
	 */
	@Test
	public void testWrite() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.2.230:1521:site", "reply",
					"reply");
			String sql = "insert into test values(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "e");
			byte[] bytes = { 0, 1, 1, 0 };
			blobWriter.write(conn, ps, 2, bytes);
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
	 * 功能：测试free方法 <br>
	 * void
	 */
	@Test
	public void testFree() {
		if (blobWriter != null) {
			try {
				blobWriter.free();
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("success");
		}else{
			System.out.println("error");
		}
	}

}
