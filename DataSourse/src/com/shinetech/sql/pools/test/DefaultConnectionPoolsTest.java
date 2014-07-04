/*
 * 文件名：DefaultConnectionPoolsTest.java	 <br>
 * 创建日期:Mar 4, 2011<br>
 * 包名:com.shinetech.sql.pools.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.pools.test;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.pools.ConnectionPoolsBuilder;
import com.shinetech.sql.pools.DBConfigBean;
import com.shinetech.sql.pools.DBConfigManager;
import com.shinetech.sql.pools.IConnectionPools;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DefaultConnectionPoolsTest extends TestCase {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(this.getClass());
	IConnectionPools pools = null;
	String poolName = "default";
	public DefaultConnectionPoolsTest(){
		initLogger();
		pools = ConnectionPoolsBuilder.getConnectionPools();
	}
	public void testGetConnection() throws DBException{
		drawLine("得到一个连接");
		Connection conn =  pools.getConnection(poolName);
		pools.freeConnection(poolName, conn);
		//logger.debug(conn == null);
		Assert.assertTrue(conn != null);
	}
	public void testRelease1() throws DBException{
		drawLine("测试清空连接池");
		//Connection conn =  pools.getConnection(poolName);
		//Connection con1 =  pools.getConnection("oracle1");
		//Connection conn2 =  pools.getConnection("oracle1");
		//Connection conn3 =  pools.getConnection(poolName);
		//Connection conn4 =  pools.getConnection(poolName);
		//pools.freeConnection(poolName, conn);
		//pools.freeConnection(poolName, conn3);
		//pools.freeConnection(poolName, conn4);
		pools.release();
		pools.getConnection(poolName);
		//System.exit(1);
	}
	 
	public void testFreeConnection() throws DBException{
		drawLine("释放一个连接");
		Connection conn =  pools.getConnection(poolName);
		pools.freeConnection(poolName, conn);
			
	}
	public void testRelease() throws DBException{
		drawLine("测试清空连接池");
		pools.release();
	}
	public void testCloseConnection() throws DBException{
		drawLine("测试关闭一个连接");
		Connection conn =  pools.getConnection(poolName);
		pools.closeConnection(poolName, conn);
	}
	public void testInit(){
		drawLine("测试重新初始化");
		List<DBConfigBean> list =  DBConfigManager.getInstance().getDbConfigList();
		pools.init(list);
	}
	public void initLogger() {
		String log4j = System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j.xml";
 		DOMConfigurator.configure(log4j);
	}
	public void testDrives() throws DBException{
		drawLine("测试数据库驱动注册");
		Connection conn =  pools.getConnection("default");
		Assert.assertTrue(conn != null);
		//Connection conn1 =  pools.getConnection("oracle1");
		//Assert.assertTrue(conn1 != null);
	}
	public void drawLine(String testName){
		System.out.println("----------"+ testName +"--------------");
	}

}
