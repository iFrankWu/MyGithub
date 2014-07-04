package com.shinetech.sql.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.dialect.Dialect;
import com.shinetech.sql.dialect.DialectFactory;
import com.shinetech.sql.type.ITypeWriter;

public class TestUpdateTypeHandle {

	private Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@192.168.2.230:1521:site";
		String user = "translate";
		String password = "translate";
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update() throws Exception {
		Connection conn = getConnection();
		Statement statement = conn.createStatement();

		String sql = "insert into LOB_TEST values(SEQ_LOB_TEST.nextval, 'insert test', empty_clob(), empty_blob(), sysdate)";
		int lr = statement.executeUpdate(sql);
		System.out.println("lr = " + lr);

		statement.close();

		sql = "insert into LOB_TEST values(SEQ_LOB_TEST.nextval, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		List<FieldParameter> list = new ArrayList<FieldParameter>();
		list.add(new FieldParameter(1, "I have a dream", FieldTypes.VARCHAR));
		list.add(new FieldParameter(2, "I hava a clob dream", FieldTypes.CLOB));
		list.add(new FieldParameter(3, "I have a blob dream".getBytes(), FieldTypes.BLOB));
		list.add(new FieldParameter(4, new Date(System.currentTimeMillis()), FieldTypes.DATE));

		Dialect dialect = DialectFactory.getDialect("oracle");

		List<ITypeWriter<?>> lobList = new ArrayList<ITypeWriter<?>>();

		for (FieldParameter parameter : list) {
			int index = parameter.getIndex();
			int type = parameter.getType();
			Object value = parameter.getValue();

			if (value == null) {
				pstmt.setNull(index, type);
			} else {
				if (!dialect.containDefaultTypeWriter(type))
					pstmt.setObject(index, value, type);
				else {
					ITypeWriter lobWriter = dialect.getDefaultTypeWriter(type);
					lobWriter.write(conn, pstmt, index, value);
					lobList.add(lobWriter);
				}
			}
		}

		lr = pstmt.executeUpdate();
		System.out.println("lr = " + lr);

		for (ITypeWriter<?> lw : lobList) {
			lw.free();
		}

		pstmt.close();
		conn.close();
	}

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param args
	 *            void
	 * @throws Exception
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			TestUpdateTypeHandle tu = new TestUpdateTypeHandle();
			tu.update();
			TestQueryTypeHandle tq = new TestQueryTypeHandle();
			tq.query();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
