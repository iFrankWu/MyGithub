package com.shinetech.sql.pools;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：DbConfigBean.java<br>
 * 包名：com.shinetech.sql.pools<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/24<br>
 * 版本：1.0.0 <br>
 * 功能描述：数据源配置信息类 <br>
 * 
 * 作者：loong<br>
 * 版本：1.0.1<br>
 * 修改日期：2011/3/2<br>
 * 修改原因：简化了变量名称。配置文件中键值可以忽略大小写，减少加载配置出错机率
 */
public class DBConfigBean {
	/**
	 * 连接池名
	 */
	private String name;
	/**
	 * 数据库类型
	 */
	private String type;
	/**
	 * 驱动名
	 */
	private String driver;
	/**
	 * 连接用户名
	 */
	private String username;
	/**
	 * 连接密码
	 */
	private String password;
	/**
	 * 连接地址
	 */
	private String url;

	/**
	 * 连接池参数集合
	 */
	private Map<String, String> paramMap = new HashMap<String, String>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(String key, String value) {
		this.paramMap.put(key, value);
	}
}
