package com.shinetech.sql;

import java.util.List;

/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：SqlParameter.java<br>
 * 包名：com.shinetech.sql<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/23<br>
 * 版本：1.0.0 <br>
 * 功能描述：sql预编译信息类 <br>
 * 
 */
public class SqlParameter {
	/**
	 * 要执行的sql语句
	 */
	private String sql;
	/**
	 * sql占位符对应绑定值的集合
	 */
	private List<FieldParameter> paraList;

	public SqlParameter() {

	}

	public SqlParameter(String sql, List<FieldParameter> paraList) {
		this.sql = sql;
		this.paraList = paraList;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<FieldParameter> getParaList() {
		return paraList;
	}

	public void setParaList(List<FieldParameter> paraList) {
		this.paraList = paraList;
	}
}
