/*
 * 文件名：DefaultConnectionPools.java	 <br>
 * 创建日期:Mar 4, 2011<br>
 * 包名:com.shinetech.sql.pools.impl <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.pools.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.shinetech.sql.DatabaseConst;
import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.pools.DBConfigBean;
import com.shinetech.sql.pools.DBConfigManager;
import com.shinetech.sql.pools.IConnectionPools;

/**
 * 实现了 接口 IConnectionPools ，完成了对数据库连接池的相关操作<br>
 * 连接池： 要处理好两个量：时间和数<br>
 * 时间像等待时间，空闲时间
 * 数量像初始连接数、最大连接数、最小空闲数<br>
 * 同步控制线程获得连接
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.3
 * @see IConnectionPools.java
 */
public class DefaultConnectionPools implements IConnectionPools {
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * WAIT_RELEASE:当前是否正在清空所有连接池，如果是 不允许在申请连接
	 */
	private boolean WAIT_RELEASE = false;
	/**
	 * freeConns:存放所有池 以及 对应的空闲连接
	 */
	private HashMap<String, List<Connection>> freeConns = new HashMap<String, List<Connection>>();
	/**
	 * connCounts:用来对每个连接 计数
	 */
	private HashMap<String, Integer> connCounts = new HashMap<String, Integer>();

	/**
	 * drivers:存放所有注册的驱动,目的是为了避免一种驱动，生成多个实例
	 */
	private HashMap<String, Driver> drivers = new HashMap<String, Driver>();

	private static DefaultConnectionPools instance = null;

	private DefaultConnectionPools() {
		// 加载默认配置文件的配置
		List<DBConfigBean> list = DBConfigManager.getInstance()
				.getDbConfigList();
		init(list); // 初始化连接池
	}

	public synchronized static IConnectionPools getInstance() {
		if (instance == null) {
			instance = new DefaultConnectionPools();
		}
		return instance;
	}

	@Override
	public synchronized void closeConnection(String poolName, Connection conn)
			throws DBException {// 使用同步 考虑到里面有对连接数 进行计算
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("关闭连接时出错", e);
			throw new DBException("关闭连接时出错", e);
		}
		if (poolName == null) { // 如果是空，则使用默认连接池名
			poolName = DatabaseConst.DEFAULT_POOL_NAME;
		}
		Integer connCount = connCounts.get(poolName);
		if (connCount == null) { // 发生该种情形的可能原因是 关闭连接时 写错了连接池名
			throw new DBException("关闭连接时出错，此时的连接池名为 " + poolName);
		}
		connCounts.put(poolName, --connCount);// 将该池的连接数 减一
		logger.debug("关闭连接，当前数据连接池的状态，已申请连接为 " + getConnCount(poolName)
				+ " 空闲连接为 " + getFreeCount(poolName));
	}

	@Override
	public synchronized void freeConnection(String poolName, Connection conn)
			throws DBException {
		if (poolName == null) { // 如果是空，则使用默认连接池名
			poolName = DatabaseConst.DEFAULT_POOL_NAME;
		}
		List<Connection> frees = freeConns.get(poolName);
		if (frees == null) { // 发生该情形的可能原因是 误写了连接池名
			throw new DBException("释放连接时出错，此时的连接池名为 " + poolName);
		}
		int maxIdle = DatabaseConst.MAX_IDLE;//最大空闲连接数
		DBConfigBean dbCfgBean = DBConfigManager.getInstance().getDBConfigBean(
				poolName);

		String idle = dbCfgBean.getParamMap().get("maxIdle");
		if (idle != null && !idle.trim().equals("")) {
			maxIdle = Integer.parseInt(idle);
		}
		if (frees.size() < maxIdle) { // 如果空闲连接小于最大空闲连接数时，那么释放该连接
			frees.add(conn);
		} else { // 如果空闲连接已达到最大空闲连接数时，关闭该连接
			closeConnection(poolName, conn);
		}
		logger.debug("释放连接，当前数据连接池的状态，已申请连接为 " + getConnCount(poolName)
				+ " 空闲连接为 " + getFreeCount(poolName));
	}

	@Override
	public synchronized Connection getConnection(String poolName)
			throws DBException {// 由于涉及连接数目计算 故同步
		Connection conn = null;
		if (!WAIT_RELEASE) {
			if (poolName == null) {
				poolName = DatabaseConst.DEFAULT_POOL_NAME;
			}
			int maxConn = DatabaseConst.MAX_CONNECTION_COUNT;
			DBConfigBean dbCfgBean = null;
			try {
				dbCfgBean = DBConfigManager.getInstance().getDBConfigBean(
						poolName);
			} catch (DBException e) {
				logger.error("获取DBConfigBean对象时 出错");
				throw new DBException("获取DBConfigBean对象时 出错", e);
			}
			String maxconn = dbCfgBean.getParamMap().get("maxConn");	// 先判断有没有
			if (maxconn != null || !maxconn.trim().equals("")) {
				maxConn = Integer.parseInt(maxconn);
			}
			List<Connection> frees = freeConns.get(poolName);
			if (frees.size() > 0) { 		// 如果空闲池有连接
				conn = frees.remove(0); 	// 从得到第一个空闲连接，并将其从集合中删除
			} else { 						// 没有空闲连接池，那么重新申请一个连接
				Integer connCount = connCounts.get(poolName);
				// 当连接数 没有达到最大连接数时 创建新连接
				if (connCount < maxConn) {
					try {
						conn = newConnection(poolName);
					} catch (Exception e) {
						logger.error("创建一个新连接时出错");
						throw new DBException("创建一个新连接时出错", e);
					}
					connCounts.put(poolName, ++connCount);
				}
			}
		}
		logger.debug("申请连接，当前数据连接池的状态，已申请连接为 " + getConnCount(poolName)
				+ " 空闲连接为 " + getFreeCount(poolName));
		return conn;
	}

	@Override
	public synchronized void init(List<DBConfigBean> list) {
		for (DBConfigBean configBean : list) {
			String poolName = configBean.getName().trim();
			String classname = configBean.getDriver();
			List<Connection> freeList = new LinkedList<Connection>();
			freeConns.put(poolName, freeList); 		// 初始化空闲连接池
			connCounts.put(poolName, new Integer(0));
			if (drivers.get(classname) == null) { 	// 这里主要是避免重复生成对象
				try {
					Driver driver = (Driver) Class.forName(classname)
							.newInstance();
					drivers.put(classname, driver);
					logger.debug("注册JDBC驱动[" + classname + "]成功");
				} catch (Exception e) {
					logger.error("程序退出  在注册JDBC驱动" + classname + "时出现错误: ", e);
					System.exit(0);
				}
			}
		}
		logger.debug("连接池初始化");
	}

	@Override
	public synchronized void release() throws DBException {
		WAIT_RELEASE = true;
		if (instance != null) {
			/* 判断是否所有的连接都已经空闲了 */
			Set<String> poolNameSet = connCounts.keySet();
			boolean flag = true;			// 所有连接是否已经空闲，true不空闲，仍需等待
			while (flag) {
				flag = false;
				for (String tempPool : poolNameSet) {
					int tempCount = connCounts.get(tempPool);
					if (tempCount == 0) { 	// 如果没有连接数
						continue;
					}
					List<Connection> tempList = freeConns.get(tempPool);
					int tempFreeCount = tempList.size();
					if (tempCount != tempFreeCount) {
						flag = true;
					}
				}
				if (flag) {
					try {
						logger.debug("还有连接没有释放，需等待释放全部连接");
						wait(3 * 1000); // 等待几秒钟后再次循环
					} catch (InterruptedException e) {
						logger.error("等待所有连接释放失败", e);
						throw new DBException("等待所有连接释放失败", e);
					}
				}
			}
			/* 关闭 空闲连接池中的连接 */
			Set<String> poolSet = freeConns.keySet();
			for (String pool : poolSet) {
				List<Connection> connList = freeConns.get(pool);
				for (Connection connection : connList) {
					try {
						if (!connection.isClosed()) {
							connection.close(); // 将连接池连接关闭，还需要将连接池清空
						}
					} catch (SQLException e) {
						logger.error("清空连接池时，关闭连接出错", e);
						throw new DBException("清空连接池时，关闭连接出错", e);
					}
				}
				connList.clear();// 清空 空闲连接集合
			}

			for (String poolName : poolNameSet) {
				connCounts.put(poolName, new Integer(0)); // 清空计数
			}
			drivers.clear();//清空驱动
			instance = null;
		}
		WAIT_RELEASE = false;
	}

	/**
	 * 功能：具体得到一个连接 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param poolName
	 * @return
	 * @throws DBException
	 *             Connection
	 */
	private Connection newConnection(String poolName) throws DBException {
		Connection conn = null;
		DBConfigBean dbCfgBean = DBConfigManager.getInstance().getDBConfigBean(
				poolName);
		try {
			if (dbCfgBean.getUsername() == null) {
				conn = DriverManager.getConnection(dbCfgBean.getUrl());
			} else {
				conn = DriverManager.getConnection(dbCfgBean.getUrl(),
						dbCfgBean.getUsername(), dbCfgBean.getPassword());
			}
		} catch (SQLException e) {
			logger.error("创建新连接时出错", e);
			throw new DBException("创建一个新连接时出错，连接池名" + poolName);
		}
		logger.debug("创建新连接，当前数据连接池的状态，已申请连接为 " + getConnCount(poolName)
				+ " 空闲连接为 " + getFreeCount(poolName));
		return conn;
	}

	/**
	 * 功能：得到当前空闲连接数，用来打印调试语句时使用 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param poolName
	 * @return int
	 */
	private int getFreeCount(String poolName) {
		int count = 0;
		if (freeConns.get(poolName) != null) {
			count = freeConns.get(poolName).size();
		}
		return count;
	}

	/**
	 * 功能：得到当前连接数 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param poolName
	 * @return
	 * int
	 */
	private int getConnCount(String poolName) {
		int count = connCounts.get(poolName);
		return count;
	}
}
