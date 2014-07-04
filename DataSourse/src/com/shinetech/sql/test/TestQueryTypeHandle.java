package com.shinetech.sql.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.dialect.Dialect;
import com.shinetech.sql.dialect.DialectFactory;

public class TestQueryTypeHandle {

	public void query() throws Exception {
		// Field[] fieldss = FieldTypes.class.getDeclaredFields();
		// for (Field field : fieldss) {
		// // if (field.isAccessible())
		// System.out.println(field.getName());
		// System.out.println(field.getInt(FieldTypes.class));
		// }
		// System.out.println("BLOB = " +
		// FieldTypes.class.getField("BLOB").getInt(FieldTypes.class));

		Field[] fieldss = FieldTypes.class.getFields();
		for (Field field : fieldss) {
			String fieldName = field.getName();
			if (FieldTypes.class.getField(fieldName).getInt(FieldTypes.class) == 2004)
				System.out.println(fieldName);
		}

		System.out.println("find");

		// Method[] methods = ResultSet.class.getDeclaredMethods();
		// for (Method method : methods) {
		// String m = method.getName();
		// if (m.startsWith("get"))
		// System.out.println(m);
		// }

		// TestQueryLobHandle test = new TestQueryLobHandle();

		String url = "jdbc:oracle:thin:@192.168.2.230:1521:site";
		String user = "translate";
		String password = "translate";
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		Connection conn = DriverManager.getConnection(url, user, password);

		Statement statement = conn.createStatement();

		String sql = "select * from LOB_TEST";
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData rsd = rs.getMetaData();
		int columnCount = rsd.getColumnCount();
		Map<String, Integer> metaMap = new HashMap<String, Integer>(columnCount);
		for (int i = 1; i <= columnCount; i++) {
			metaMap.put(StringUtils.upperCase(rsd.getColumnName(i)), rsd.getColumnType(i));
		}

		printMap(metaMap);

		Field[] fields = TestBean.class.getDeclaredFields();
		Map<String, Class<?>> fieldMap = new HashMap<String, Class<?>>(fields.length);
		for (int i = 0; i < fields.length; i++) {
			fieldMap.put(StringUtils.upperCase(fields[i].getName()), fields[i].getType());
		}

		printMap(fieldMap);

		while (rs.next()) {
			TestBean t = new TestBean();
			System.out.println("=====================");
			for (Iterator<String> iter = metaMap.keySet().iterator(); iter.hasNext();) {
				// 结果集列名称
				String fieldName = iter.next();
				int fieldType = metaMap.get(fieldName);
				// 检查对象实例是否包含此结果集名称的对应的属性名称
				if (fieldMap.containsKey(fieldName)) {
					// 检查是否默认类型处理
					Object object = null;
					Dialect dialect = DialectFactory.getDialect("oracle");
					if (!dialect.containDefaultTypeReader(fieldType)) {
						String method = "get" + dialect.getReflectType(fieldType);
						System.out.println("mehtod = " + method);
						Method m = ResultSet.class.getDeclaredMethod(method, new Class[] { String.class });
						object = m.invoke(rs, new Object[] { fieldName });
					} else {
						object = dialect.getDefaultTypeReader(fieldType).read(rs, fieldName);
					}

					// 获取该属性setter方法
					String method = "set" + WordUtils.capitalizeFully(fieldName);
					System.out.println("method = " + method);
					System.out.println("type = " + fieldMap.get(fieldName));
					System.out.println("object = " + object);
					Method setMethod = TestBean.class.getMethod(method, new Class[] { fieldMap.get(fieldName) });
					// 调用该方法
					setMethod.invoke(t, new Object[] { object });
				}
			}

			System.out.println("=====================");
			System.out.println("ID = " + t.getId());
			System.out.println("TS = " + t.getT_s());
			System.out.println("TC = " + t.getT_c());
			System.out.println("TB = " + new String(t.getT_b()));
			System.out.println("TD = " + t.getT_d());
		}

		rs.close();
		statement.close();
		conn.close();

		conn = null;
	}

	private void printMap(Map<?, ?> map) {
		System.out.println("==============================");
		for (Iterator<?> iter = map.keySet().iterator(); iter.hasNext();) {
			Object obj = iter.next();
			System.out.println("[" + obj + ", " + map.get(obj) + "]");
		}
	}

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param args
	 *            void
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TestQueryTypeHandle t = new TestQueryTypeHandle();
		t.query();
	}

}
