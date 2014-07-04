/*
 * 文件名：TestExecute.java	 <br>
 * 创建日期:Mar 8, 2011<br>
 * 包名:com.shinetech.sql.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.test;

import java.util.List;

import org.apache.log4j.Logger;

import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.SqlParameter;
import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.impl.DefaultDatabaseAccess;

/**
 * 功能描述:测试Execute方法 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */

public class TestExecute {
	Logger logger = Logger.getLogger(TestExecute.class);

	/**
	 * 目的和功能：构造方法,构造TestExecute类的一个实例
	 * 
	 * @param
	 */
	public TestExecute() {
		init();
	}

	/**
	 * 功能： 初始化<br>
	 * void
	 */
	private void init() {

	}

	/**
	 * 功能：测试dml操作 <br>
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void testExecute(String sql) {
		String handlerStr = getHandleStr(sql);// 得到操作方式
		logger.info("开始测试execute方法的" + handlerStr + "操作");
		DefaultDatabaseAccess access = new DefaultDatabaseAccess();
		try {
			access.execute(sql);
			logger.info("execute方法测试" + handlerStr + "成功");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			logger.error("execute方法测试" + handlerStr + "失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 功能：测试预编译dml操作 <br>
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void testPexecute(String sql, List<FieldParameter> paraList) {
		String isLobStr = getLobStr(paraList);
		String handlerStr = getHandleStr(sql);
		logger.info("开始测试execute方法" + isLobStr + handlerStr + "预编译");
		DefaultDatabaseAccess access = new DefaultDatabaseAccess();
		try {
			access.execute(sql, paraList);
			logger.info("预编译execute方法测试" + isLobStr + handlerStr + "成功");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			logger.error("预编译execute方法测试" + isLobStr + handlerStr + "失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 功能：测试相同sql的多次操作 <br>
	 * 
	 * @param sqlList
	 *            void
	 */
	@SuppressWarnings("unchecked")
	public void testExecuteBatch(List<String> sqlList) {
		logger.info("开始测试executeBatch方法的事务操作");
		DefaultDatabaseAccess access = new DefaultDatabaseAccess();
		try {
			access.executeBatch(sqlList);
			logger.info("executeBatch方法的事务测试成功");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			logger.error("executeBatch方法的事务测试失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 功能：测试预编译事物 <br>
	 * 
	 * @param sql
	 * @param listParaList
	 *            void
	 */
	@SuppressWarnings("unchecked")
	public void testPExecuteBatch(String sql,
			List<List<FieldParameter>> listParaList) {
		logger.info("开始测试executePrepareBatch方法");
		DefaultDatabaseAccess access = new DefaultDatabaseAccess();
		try {
			access.executePrepareBatch(sql, listParaList);
			logger.info("executePrepareBatch方法测试成功");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			logger.error("executePrepareBatch方法测试失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 功能：TODO <br>
	 * 
	 * @param list
	 *            void
	 */
	@SuppressWarnings("unchecked")
	public void testExecutePrepareMultiBath(List<SqlParameter> list) {
		logger.info("开始测试executePrepareMultiBath方法");
		DefaultDatabaseAccess access = new DefaultDatabaseAccess();
		try {
			access.executePrepareMultiBath(list);
			logger.info("executePrepareMultiBath方法测试成功");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			logger.error("executePrepareMultiBath方法测试失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 功能：检查参数中是否有大字段 <br>
	 * 
	 * @param paraList
	 * @return String
	 */
	private String getLobStr(List<FieldParameter> paraList) {
		String handler = "无大字段";
		boolean result = false;
		for (FieldParameter parameter : paraList) {
			int type = parameter.getType();
			if (type == FieldTypes.BLOB || type == FieldTypes.CLOB) {
				result = true;
			}
		}
		if (result) {
			handler = "含大字段";
		}
		return handler;
	}

	/**
	 * 
	 * 功能：得到操作方式 <br>
	 * 
	 * @param sql
	 * @return String
	 */
	private String getHandleStr(String sql) {
		String handle = "";
		if (sql.startsWith("i")) {
			handle = "插入";
		} else if (sql.startsWith("u")) {
			handle = "修改";
		} else if (sql.startsWith("d")) {
			handle = "删除";
		}
		return handle;
	}
}
