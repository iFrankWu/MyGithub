package com.shinetech.sql.impl;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shinetech.sql.DatabaseConst;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ParameterCreatorUtil;
import com.shinetech.sql.ReflectUtil;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.SqlParameter;
import com.shinetech.sql.dialect.Dialect;
import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.pools.ConnectionPoolsBuilder;
import com.shinetech.sql.pools.DBConfigManager;
import com.shinetech.sql.type.ITypeReader;
import com.shinetech.sql.type.ITypeWriter;

/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：DefaultDatabaseAccess.java<br>
 * 包名：com.shinetech.sql.impl<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/23<br>
 * 版本：1.0.0 <br>
 * 功能描述：数据库操作接口的默认实现 <br>
 * 
 * 作者 : loong<br>
 * 修改日期 : 2011/3/2<br>
 * 版本：1.0.1 <br>
 * 修改原因：具体类实现<br>
 */
public class DefaultDatabaseAccess<T> implements IDatabaseAccess<T> {

	/**
	 * poolName: 连接池名称
	 */
	private String poolName = DatabaseConst.DEFAULT_POOL_NAME;
	/**
	 * typeReaderMap: 注入字段数据类型读取器集合
	 */
	private Map<String, ITypeReader<?>> typeReaderMap = null;
	/**
	 * typeWriterMap: 注入字段数据类型写入器集合
	 */
	private Map<Integer, ITypeWriter<?>> typeWriterMap = null;
	/**
	 * lobWriterList: 处理字段类型写操作需要释放的临时对象集合
	 */
	private List<ITypeWriter<?>> lobWriterList = null;

	@Override
	public void execute(String sql) throws DBException {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行更新失败", e);
		} finally {
			closeStatement(stmt);
			freeConnection(conn);
		}
	}

	@Override
	public void execute(String sql, List<FieldParameter> paraList) throws DBException {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pstmt = getPreparedStatement(conn, sql);
		try {
			bindFieldParameter(conn, pstmt, paraList);
		} catch (DBException e) {
			throw new DBException("绑定参数到PreparedStatement对象失败：" + sql, e);
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行操作失败", e);
		} finally {
			unRegisterTypeMap();
			clearLobWriterList();
			closeStatement(pstmt);
			freeConnection(conn);
		}
	}

	@Override
	public void executeBatch(List<String> sqlList) throws DBException {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		try {
			conn.setAutoCommit(false);
			for (String sql : sqlList) {
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (!conn.getAutoCommit()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new DBException("执行回滚操作失败", e1);
			}
			throw new DBException("执行批量操作失败", e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DBException("设置自动提交操作失败", e);
			}
			closeStatement(stmt);
			freeConnection(conn);
		}

	}

	@Override
	public void executePrepareBatch(String sql, List<List<FieldParameter>> listParaList) throws DBException {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pstmt = getPreparedStatement(conn, sql);
		try {
			conn.setAutoCommit(false);
			for (List<FieldParameter> paraList : listParaList) {
				try {
					bindFieldParameter(conn, pstmt, paraList);
				} catch (DBException e) {
					throw new DBException("绑定参数到PreparedStatement对象失败：" + sql, e);
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (!conn.getAutoCommit()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new DBException("执行回滚操作失败", e1);
			}
			throw new DBException("执行批量操作失败", e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DBException("设置自动提交操作失败", e);
			}
			unRegisterTypeMap();
			clearLobWriterList();
			closeStatement(pstmt);
			freeConnection(conn);
		}

	}

	@Override
	public void executePrepareMultiBath(List<SqlParameter> list) throws DBException {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);
			for (SqlParameter sqlParameter : list) {
				pstmt = getPreparedStatement(conn, sqlParameter.getSql());
				try {
					bindFieldParameter(conn, pstmt, sqlParameter.getParaList());
				} catch (DBException e) {
					throw new DBException("绑定参数到PreparedStatement对象失败：" + sqlParameter.getSql(), e);
				}
				pstmt.executeUpdate();
				closeStatement(pstmt);
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (!conn.getAutoCommit()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new DBException("执行回滚操作失败", e1);
			}
			throw new DBException("执行批量操作失败", e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DBException("设置自动提交操作失败", e);
			}
			unRegisterTypeMap();
			clearLobWriterList();
			closeStatement(pstmt);
			freeConnection(conn);
		}

	}

	@Override
	public T queryFirst(Class<T> clazz, String sql) throws DBException {
		// TODO Auto-generated method stub
		T t = null;
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			Map<String, Integer> metaMap = getMetaDataMap(rs);
			if (rs.next()) {
				t = getClazzBean(clazz, rs, metaMap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行查询失败", e);
		} finally {
			unRegisterTypeMap();
			closeResult(rs);
			closeStatement(stmt);
			freeConnection(conn);
		}

		return t;
	}

	@Override
	public List<T> queryList(Class<T> clazz, String sql) throws DBException {
		// TODO Auto-generated method stub
		List<T> tList = new ArrayList<T>();
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			Map<String, Integer> metaMap = getMetaDataMap(rs);
			while (rs.next()) {
				tList.add(getClazzBean(clazz, rs, metaMap));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行查询失败", e);
		} finally {
			unRegisterTypeMap();
			closeResult(rs);
			closeStatement(stmt);
			freeConnection(conn);
		}

		return tList;
	}

	@Override
	public List<T> queryPage(Class<T> clazz, String sql, int pageSize, int currentPage) throws DBException {
		// TODO Auto-generated method stub
		List<T> tList = new ArrayList<T>();
		Connection conn = getConnection();
		PreparedStatement pstmt = getPreparedStatement(conn, DBConfigManager.getInstance().getDialect(poolName).getLimitString(sql));
		bindFieldParameter(pstmt, 0, pageSize, currentPage);
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			Map<String, Integer> metaMap = getMetaDataMap(rs);
			while (rs.next()) {
				tList.add(getClazzBean(clazz, rs, metaMap));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行查询失败", e);
		} finally {
			unRegisterTypeMap();
			closeResult(rs);
			closeStatement(pstmt);
			freeConnection(conn);
		}

		return tList;
	}

	@Override
	public T queryPrepareFirst(Class<T> clazz, String sql, List<FieldParameter> paraList) throws DBException {
		// TODO Auto-generated method stub
		T t = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = getPreparedStatement(conn, sql);
		try {
			bindFieldParameter(conn, pstmt, paraList);
		} catch (DBException e) {
			throw new DBException("绑定参数到PreparedStatement对象失败：" + sql, e);
		}
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			Map<String, Integer> metaMap = getMetaDataMap(rs);
			if (rs.next()) {
				t = getClazzBean(clazz, rs, metaMap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行查询失败", e);
		} finally {
			unRegisterTypeMap();
			closeResult(rs);
			closeStatement(pstmt);
			freeConnection(conn);
		}

		return t;
	}

	@Override
	public List<T> queryPrepareList(Class<T> clazz, String sql, List<FieldParameter> paraList) throws DBException {
		// TODO Auto-generated method stub
		List<T> tList = new ArrayList<T>();
		Connection conn = getConnection();
		PreparedStatement pstmt = getPreparedStatement(conn, sql);
		try {
			bindFieldParameter(conn, pstmt, paraList);
		} catch (DBException e) {
			throw new DBException("绑定参数到PreparedStatement对象失败：" + sql, e);
		}
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			Map<String, Integer> metaMap = getMetaDataMap(rs);
			while (rs.next()) {
				tList.add(getClazzBean(clazz, rs, metaMap));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行查询失败", e);
		} finally {
			unRegisterTypeMap();
			closeResult(rs);
			closeStatement(pstmt);
			freeConnection(conn);
		}
		return tList;
	}

	@Override
	public List<T> queryPreparePage(Class<T> clazz, String sql, List<FieldParameter> paraList, int pageSize, int currentPage) throws DBException {
		// TODO Auto-generated method stub
		List<T> tList = new ArrayList<T>();
		Connection conn = getConnection();
		PreparedStatement pstmt = getPreparedStatement(conn, DBConfigManager.getInstance().getDialect(poolName).getLimitString(sql));
		try {
			bindFieldParameter(conn, pstmt, paraList);
		} catch (DBException e) {
			throw new DBException("绑定参数到PreparedStatement对象失败：" + sql, e);
		}
		bindFieldParameter(pstmt, paraList.size(), pageSize, currentPage);
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			Map<String, Integer> metaMap = getMetaDataMap(rs);
			while (rs.next()) {
				tList.add(getClazzBean(clazz, rs, metaMap));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行查询失败", e);
		} finally {
			unRegisterTypeMap();
			closeResult(rs);
			closeStatement(pstmt);
			freeConnection(conn);
		}

		return tList;
	}

	@Override
	public void registerTypeReadHandle(String fieldName, ITypeReader<?> typeReader) throws Exception {
		// TODO Auto-generated method stub
		if (this.typeReaderMap == null) {
			this.typeReaderMap = new HashMap<String, ITypeReader<?>>();
		}
		this.typeReaderMap.put(StringUtils.upperCase(fieldName), typeReader);
	}

	@Override
	public void registerTypeWriteHandle(int index, ITypeWriter<?> typeWriter) throws Exception {
		// TODO Auto-generated method stub
		if (this.typeWriterMap == null) {
			this.typeWriterMap = new HashMap<Integer, ITypeWriter<?>>();
		}
		this.typeWriterMap.put(index, typeWriter);
	}

	@Override
	public void setDatabase(String poolName) {
		// TODO Auto-generated method stub
		this.poolName = poolName;
	}

	/**
	 * 功能：返回指定连接池的一个空闲连接 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @return
	 * @throws DBException
	 *             Connection
	 */
	private Connection getConnection() throws DBException {
		Connection conn = ConnectionPoolsBuilder.getConnectionPools().getConnection(poolName);
		if (conn == null)
			throw new DBException("从连接池[" + poolName + "]获取空闲连接失败");
		return conn;
	}

	/**
	 * 功能：释放指定连接 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param conn
	 *            void
	 * @throws DBException
	 */
	private void freeConnection(Connection conn) throws DBException {
		if (conn != null)
			ConnectionPoolsBuilder.getConnectionPools().freeConnection(poolName, conn);
	}

	/**
	 * 功能：返回指定连接的一个Statement对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param conn
	 * @return
	 * @throws DBException
	 *             Statement
	 */
	private Statement getStatement(Connection conn) throws DBException {
		try {
			Statement stmt = conn.createStatement();
			if (stmt == null)
				throw new DBException("创建Statement对象失败");
			return stmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("创建Statement对象失败", e);
		}
	}

	/**
	 * 功能：返回指定连接的一个PreparedStatement对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws DBException
	 *             PreparedStatement
	 */
	private PreparedStatement getPreparedStatement(Connection conn, String sql) throws DBException {
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (pstmt == null)
				throw new DBException("创建PreparedStatement对象失败");
			return pstmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("创建PreparedStatement对象失败, ", e);
		}
	}

	/**
	 * 功能：关闭Statement对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param stmt
	 * @throws DBException
	 *             void
	 */
	private void closeStatement(Statement stmt) throws DBException {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DBException("关闭Statement对象失败", e);
			}
	}

	/**
	 * 功能：关闭ResultSet对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param rs
	 * @throws DBException
	 *             void
	 */
	private void closeResult(ResultSet rs) throws DBException {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DBException("关闭ResultSet对象失败", e);
			}
	}

	/**
	 * 功能：注销已注册的自定义字段数据类型处理器 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	private void unRegisterTypeMap() {
		if (typeReaderMap != null && !typeReaderMap.isEmpty()) {
			typeReaderMap.clear();
		}
		if (typeWriterMap != null && !typeWriterMap.isEmpty()) {
			typeWriterMap.clear();
		}
	}

	/**
	 * 功能：关闭写临时大字段对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @throws DBException
	 *             void
	 */
	private void clearLobWriterList() throws DBException {
		if (lobWriterList != null) {
			while (!lobWriterList.isEmpty()) {
				try {
					lobWriterList.remove(0).free();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new DBException("关闭写临时数据处理器对象失败", e);
				}
			}
		}
	}

	/**
	 * 功能：获取 ResultSet对象中列的类型和属性信息 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param rs
	 * @return
	 * @throws DBException
	 *             Map<String,Integer>
	 */
	private Map<String, Integer> getMetaDataMap(ResultSet rs) throws DBException {
		try {
			ResultSetMetaData rsd = rs.getMetaData();
			int columnCount = rsd.getColumnCount();
			Map<String, Integer> metaMap = new HashMap<String, Integer>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				metaMap.put(StringUtils.upperCase(rsd.getColumnLabel(i)), rsd.getColumnType(i));
			}
			return metaMap;
		} catch (SQLException e) {
			throw new DBException("获取 ResultSet对象中列的类型和属性信息失败", e);
		}
	}

	/**
	 * 功能：从Resultset对象中获取值到指定clazz对象中 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param clazz
	 * @param rs
	 * @param metaMap
	 * @param fieldMap
	 * @return
	 * @throws DBException
	 *             T
	 */
	@SuppressWarnings("unchecked")
	private T getClazzBean(Class<T> clazz, ResultSet rs, Map<String, Integer> metaMap) throws DBException {
		T t = null;
		// 检查是否基本数据类型
		if (ReflectUtil.isPrimitive(clazz)) {
			t = (T) getResult(rs, 1, clazz);
		} else {
			try {
				// 创建对象实例
				t = ReflectUtil.newInstance(clazz);
				// 遍历查询到的结果集
				for (Iterator<String> iter = metaMap.keySet().iterator(); iter.hasNext();) {
					// 结果集列名称
					String fieldName = iter.next();
					// 获取该属性setter方法
					Method method = ReflectUtil.getMethod(clazz, fieldName, "set");
					// 检查对象实例是否包含此结果集名称的对应的属性名称
					if (method != null) {
						// 调用该方法
						method.invoke(t, new Object[] { getResult(rs, fieldName, metaMap.get(fieldName)) });
					}
				}
			} catch (Exception e) {
				throw new DBException("从Resultset对象中获取值到[" + clazz.getName() + "]对象中失败", e);
			}
		}
		return t;
	}

	/**
	 * 功能：从指定RestultSet中获取基本数据类型的值 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param rs
	 * @param columnIndex
	 * @param clazz
	 * @return
	 * @throws DBException
	 *             Object
	 */
	private Object getResult(ResultSet rs, int columnIndex, Class<?> clazz) throws DBException {
		// 直接从ResultSet中返回值
		try {
			String method = "get" + DBConfigManager.getInstance().getDialect(poolName).getReflectType(clazz);
			Method m = ResultSet.class.getDeclaredMethod(method, new Class[] { int.class });
			return m.invoke(rs, new Object[] { columnIndex });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new DBException("从ResultSet中返回字段索引[" + columnIndex + "]值失败", e);
		}
	}

	/**
	 * 功能：从指定RestultSet中获取指定字段名称的值 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param rs
	 * @param fieldName
	 * @param fieldType
	 * @return
	 * @throws DBException
	 *             Object
	 */
	private Object getResult(ResultSet rs, String fieldName, int fieldType) throws DBException {
		Object object = null;
		if (isDefaultTypeByRead(fieldName, fieldType)) {
			// 直接从ResultSet中返回值
			try {
				String method = "get" + DBConfigManager.getInstance().getDialect(poolName).getReflectType(fieldType);
				Method m = ResultSet.class.getDeclaredMethod(method, new Class[] { String.class });
				object = m.invoke(rs, new Object[] { fieldName });
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new DBException("从ResultSet中返回字段[" + fieldName + "]值失败", e);
			}
		} else {
			// 自定义处理
			try {
				object = getTypeReader(fieldName, fieldType).read(rs, fieldName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new DBException("自定义类处理从ResultSet中返回字段[" + fieldName + "]值失败", e);
			}
		}

		return object;
	}

	/**
	 * 功能：绑定参数到PreparedStatement对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param conn
	 * @param pstmt
	 * @param paraList
	 * @throws DBException
	 *             void
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" })
	private void bindFieldParameter(Connection conn, PreparedStatement pstmt, List<FieldParameter> paraList) throws DBException {
		for (FieldParameter parameter : paraList) {
			int index = parameter.getIndex();
			int type = parameter.getType();
			Object value = parameter.getValue();
			try {
				if (value == null) {
					pstmt.setNull(index, type);
				} else {
					if (isDefaultTypeByWrite(index, type))
						pstmt.setObject(index, value, type);
					else {
						ITypeWriter typeWriter = getTypeWriter(index, type);
						typeWriter.write(conn, pstmt, index, value);
						if (typeWriter.isFree()) {
							if (lobWriterList == null) {
								lobWriterList = new ArrayList<ITypeWriter<?>>();
							}
							lobWriterList.add(typeWriter);
						}
					}
				}
			} catch (Exception e) {
				throw new DBException(ParameterCreatorUtil.toString(parameter), e);
			}
		}
	}

	/**
	 * 功能：绑定查询条件参数到PreparedStatement对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param pstmt
	 * @param paraCount
	 * @param pageSize
	 * @param currentPage
	 * @throws DBException
	 *             void
	 */
	private void bindFieldParameter(PreparedStatement pstmt, int paraCount, int pageSize, int currentPage) throws DBException {
		Dialect dbDialect = DBConfigManager.getInstance().getDialect(poolName);
		if (dbDialect.supportsLimit()) {
			try {
				if (pageSize < 1)
					pageSize = 10;
				if (currentPage < 1)
					currentPage = 1;
				pstmt.setInt(paraCount + 1, pageSize * currentPage);
				if (dbDialect.supportsLimitOffset())
					pstmt.setInt(paraCount + 2, pageSize * (currentPage - 1));
			} catch (Exception e) {
				throw new DBException("绑定翻页参数到PreparedStatement对象失败：[" + pageSize + ", " + currentPage + "]", e);
			}
		}
	}

	/**
	 * 功能：判断是否按默认字段数据类型读取数据 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fileName
	 * @param fieldType
	 * @return boolean
	 * @throws DBException
	 */
	private boolean isDefaultTypeByRead(String fieldName, int fieldType) throws DBException {
		if ((typeReaderMap != null && typeReaderMap.containsKey(fieldName))
				|| DBConfigManager.getInstance().getDialect(poolName).containDefaultTypeReader(fieldType)) {
			return false;
		}
		return true;
	}

	/**
	 * 功能：判断是否按默认数据类型写入数据 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param index
	 * @param fieldType
	 * @return boolean
	 * @throws DBException
	 */
	private boolean isDefaultTypeByWrite(int index, int fieldType) throws DBException {
		if ((typeWriterMap != null && typeWriterMap.containsKey(index))
				|| DBConfigManager.getInstance().getDialect(poolName).containDefaultTypeWriter(fieldType)) {
			return false;
		}
		return true;
	}

	/**
	 * 功能：返回自定义读取器处理器对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fileName
	 * @param fieldType
	 * @return ITypeReader<?>
	 * @throws DBException
	 */
	private ITypeReader<?> getTypeReader(String fieldName, int fieldType) throws DBException {
		if (typeReaderMap != null && typeReaderMap.containsKey(fieldName)) {
			return typeReaderMap.get(fieldName);
		}
		return DBConfigManager.getInstance().getDialect(poolName).getDefaultTypeReader(fieldType);
	}

	/**
	 * 功能：返回自定义写入器处理器对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param index
	 * @param fieldType
	 * @return ITypeWriter<?>
	 * @throws DBException
	 */
	private ITypeWriter<?> getTypeWriter(int index, int fieldType) throws DBException {
		if (typeWriterMap != null && typeWriterMap.containsKey(index)) {
			return typeWriterMap.get(index);
		}
		return DBConfigManager.getInstance().getDialect(poolName).getDefaultTypeWriter(fieldType);
	}

	@Override
	public void handleList(String sql, ResultSetHandler handler) throws DBException {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				handler.handle(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行查询失败", e);
		} finally {
			unRegisterTypeMap();
			closeResult(rs);
			closeStatement(stmt);
			freeConnection(conn);
		}
	}

	@Override
	public void handleList(String sql, List<FieldParameter> paraList, ResultSetHandler handler) throws DBException {
		Connection conn = getConnection();
		PreparedStatement pstmt = getPreparedStatement(conn, sql);
		try {
			bindFieldParameter(conn, pstmt, paraList);
		} catch (DBException e) {
			throw new DBException("绑定参数到PreparedStatement对象失败：" + sql, e);
		}
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			while (rs.next()) {
				handler.handle(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("执行查询失败", e);
		} finally {
			unRegisterTypeMap();
			closeResult(rs);
			closeStatement(pstmt);
			freeConnection(conn);
		}
	}
}
