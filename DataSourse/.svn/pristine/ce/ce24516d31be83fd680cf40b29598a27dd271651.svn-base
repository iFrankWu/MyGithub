package com.shinetech.sql.pools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;

import com.shinetech.sql.dialect.Dialect;
import com.shinetech.sql.dialect.DialectFactory;
import com.shinetech.sql.exception.DBException;

/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：DBConfig.java<br>
 * 包名：com.shinetech.sql.pool<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/24<br>
 * 版本：1.0.0 <br>
 * 功能描述：数据库配置加载类 <br>
 * 
 * 作者 : loong<br>
 * 修改日期 : 2011/3/2<br>
 * 版本：1.0.1 <br>
 * 修改原因：具体类实现<br>
 */
public class DBConfigManager {

	Logger logger = Logger.getLogger(getClass());
	/**
	 * dbConfigMap: 数据源配置信息集合
	 */
	Map<String, DBConfigBean> dbConfigMap = null;
	/**
	 * dialectMap: 数据库类型与方言映射集合
	 */
	Map<String, Dialect> dialectMap = null;

	private static final class Singleton {
		public static final DBConfigManager singleton = new DBConfigManager();
	}

	private DBConfigManager() {
		initConfig();
		initDialect();
	}

	/**
	 * 功能：得到一个单例 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @return DBConfigManager
	 */
	public static DBConfigManager getInstance() {
		return Singleton.singleton;
	}

	/**
	 * 功能：返回数据源配置信息集合 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @return List<DBConfigBean>
	 */
	public List<DBConfigBean> getDbConfigList() {
		return new ArrayList<DBConfigBean>(dbConfigMap.values());
	}

	/**
	 * 功能：返回指定连接池名称的数据库配置信息对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param poolName
	 * @return
	 * @throws DBException
	 *             DBConfigBean
	 */
	public DBConfigBean getDBConfigBean(String poolName) throws DBException {
		if (dbConfigMap.containsKey(poolName)) {
			return dbConfigMap.get(poolName);
		}
		throw new DBException("连接池[" + poolName + "]对应数据库配置信息不存在");
	}

	/**
	 * 功能：返回指定连接池名称的数据库特定方言处理对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param poolName
	 * @return
	 * @throws DBException
	 *             Dialect
	 */
	public Dialect getDialect(String poolName) throws DBException {
		String dbType = getDbType(poolName);
		if (dialectMap.containsKey(dbType)) {
			return dialectMap.get(dbType);
		}
		throw new DBException("连接池[" + poolName + "]对应数据库类型特定方言对象不存在");
	}

	/**
	 * 功能：初始化配置信息 <br>
	 * 注意事项：<font color="red">未处理动态加载</font> <br>
	 * 
	 * @return int
	 */
	private int initConfig() {

		String dbConfig = SystemUtils.USER_DIR + "/config/db.properties";
		List<String> dbList = new ArrayList<String>(Arrays.asList(new String[] { "TYPE", "DRIVER", "USERNAME",
				"PASSWORD", "URL" }));

		try {
			this.dbConfigMap = initConfig(dbConfig, DBConfigBean.class, dbList);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("加载数据库配置文件失败：" + e);
			System.exit(0);
		}

		return 0;
	}

	/**
	 * 功能：初始化数据库特定方言对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	private void initDialect() {
		this.dialectMap = new HashMap<String, Dialect>(dbConfigMap.size());
		for (DBConfigBean dbConfigBean : dbConfigMap.values()) {
			String dbType = dbConfigBean.getType();
			if (!dialectMap.containsKey(dbType)) {
				try {
					dialectMap.put(dbType, DialectFactory.getDialect(dbType));
				} catch (DBException e) {
					// TODO Auto-generated catch block
					logger.error("加载数据库类型[" + dbType + "]特定方言处理对象失败：" + e);
					System.exit(0);
				}
			}
		}
	}

	/**
	 * 功能：分析配置文件返回集合 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param file
	 *            String 配置文件名称
	 * @param ConfigBean
	 *            ConfigBean 配置信息类名称
	 * @param fieldList
	 *            List 包含setter的属性集合
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             Map<String,ConfigBean>
	 * @throws IOException
	 */
	private Map<String, DBConfigBean> initConfig(String file, Class<?> configBean, List<String> fieldList)
			throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException,
			IllegalArgumentException, InvocationTargetException, IOException {

		Properties props = new Properties();
		InputStream is = new FileInputStream(file);
		props.load(is);
		is.close();

		Map<String, DBConfigBean> configMap = new HashMap<String, DBConfigBean>();

		Enumeration<?> propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			int pos = name.lastIndexOf('.');
			String prefixName = name.substring(0, pos);
			String paraName = name.substring(pos + 1);
			String paraValue = props.getProperty(name);

			DBConfigBean cfBean = null;
			if (configMap.containsKey(prefixName)) {
				cfBean = configMap.get(prefixName);
			} else {
				cfBean = (DBConfigBean) configBean.newInstance();
				Method paraMethod = configBean.getDeclaredMethod("setName", new Class[] { String.class });
				paraMethod.invoke(cfBean, prefixName);
				configMap.put(prefixName, cfBean);
			}

			if (fieldList != null && fieldList.contains(StringUtils.upperCase(paraName))) {
				Method paraMethod = configBean.getDeclaredMethod("set" + WordUtils.capitalizeFully(paraName),
						new Class[] { String.class });
				paraMethod.invoke(cfBean, paraValue);
			} else {
				cfBean.setParamMap(paraName, paraValue);
			}
		}

		return configMap;
	}

	/**
	 * 功能：返回指定连接池名称的数据库类型 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param poolName
	 * @return String
	 * @throws DBException
	 */
	private String getDbType(String poolName) throws DBException {
		if (dbConfigMap.containsKey(poolName)) {
			return dbConfigMap.get(poolName).getType();
		}
		throw new DBException("连接池[" + poolName + "]对应数据库配置信息不存在");
	}
}
