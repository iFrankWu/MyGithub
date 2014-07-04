/*
 * 文件名：ConnectionPoolsBuilder.java	 <br>
 * 创建日期:Mar 4, 2011<br>
 * 包名:com.shinetech.sql.pools.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.pools.test;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.xml.DOMConfigurator;

import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.pools.ConnectionPoolsBuilder;
import com.shinetech.sql.pools.IConnectionPools;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ConnectionPoolsBuilderTest extends TestCase{
	public ConnectionPoolsBuilderTest(){
		initLogger();
	}
	public  void initLogger() {
		String log4j = System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j.xml";
 		DOMConfigurator.configure(log4j);
	}
	public void testGetConnectionPools() throws DBException{
		IConnectionPools pools = ConnectionPoolsBuilder.getConnectionPools();
		
		String poolName = "oracle";
		List<Connection> list = new ArrayList<Connection>();
		for(int i = 0;i<20;i++){
			System.out.println("------------------------------------");
			Connection conn  = pools.getConnection(poolName);
			//System.out.println(conn == null);
			Assert.assertTrue(conn != null);
			if(conn != null){
				try {
					list.add(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(i>5 && i != 10)
				pools.freeConnection(poolName, conn);
			if(i == 10 )
			{
				System.out.println("----i = 10-----");
				pools.closeConnection(poolName, conn);
				pools.freeConnection(poolName, list.get(0));
				pools.freeConnection(poolName, list.get(1));
				pools.freeConnection(poolName, list.get(2));
				pools.freeConnection(poolName, list.get(3));
			}
		}
	}
		

}
