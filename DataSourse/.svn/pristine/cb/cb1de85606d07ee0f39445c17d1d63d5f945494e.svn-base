/*
 * 文件名：TestDefaultDatabaseAccess.java	 <br>
 * 创建日期:Mar 8, 2011<br>
 * 包名:com.shinetech.sql.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.SqlParameter;
import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.impl.DefaultDatabaseAccess;

/**
 * 功能描述: 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */

public class TestDefaultDatabaseAccess {
	Logger logger = Logger.getLogger(TestDefaultDatabaseAccess.class);

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @throws java.lang.Exception
	 *             void
	 */
	@Before
	public void setUp() throws Exception {
		initLog();
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#execute(java.lang.String)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExecuteString() {
		logger.info("开始测试execute(String sql)方法");
		long time = new Date().getTime();
		String insertSql = "insert into test (id,name) values(" + time
				+ ",'张三')";
		logger.info("插入一条数据，数据为：id=1 ;name='张三'");
		IDatabaseAccess access = new DefaultDatabaseAccess();
		try {
			access.execute(insertSql);
			String sql = "select name from test where id=" + time;
			IDatabaseAccess<String> access2 = new DefaultDatabaseAccess<String>();
			List<String> nameList = access2.queryList(String.class, sql);
			if (nameList.size() == 1) {
				String name = nameList.get(0);
				logger.info("查询数据库id=1的数据的name=" + name);
				if (name.equals("张三")) {
					logger.info("测试execute(String sql)方法成功!\r\n\r\n");
				} else {
					logger.error("测试execute(String sql)方法失败!,两个名字不相等");
				}
			} else {
				logger.error("测试execute(String sql)方法失败，数据库存在多条id=1的数据");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			logger.error("测试execute(String sql)方法失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#execute(java.lang.String, java.util.List)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExecuteStringListOfFieldParameter() {
		logger.info("开始测试execute(String sql, List<FieldParameter> paraList)方法");
		long time = new Date().getTime();
		String sql = "insert into test(id,name,info,img) values(?,?,?,?)";
		FieldParameter paraLob1 = new FieldParameter(1, time,
				FieldTypes.NUMERIC);
		FieldParameter paraLob2 = new FieldParameter(2, "王五",
				FieldTypes.VARCHAR);
		FieldParameter paraLob3 = new FieldParameter(3, "王五什么都会做",
				FieldTypes.CLOB);
		byte[] bytes = { 1, 0 };
		FieldParameter paraLob4 = new FieldParameter(4, bytes, FieldTypes.BLOB);
		List<FieldParameter> fieldList = new ArrayList<FieldParameter>();
		fieldList.add(paraLob1);
		fieldList.add(paraLob2);
		fieldList.add(paraLob3);
		fieldList.add(paraLob4);
		logger.info("插入一条数据，id=" + time
				+ ",name='王五',info='王五什么都会做',img为blob {1,0}");
		IDatabaseAccess access = new DefaultDatabaseAccess();
		try {
			access.execute(sql, fieldList);
			String sql2 = "select name,info,img from test where id=" + time;
			IDatabaseAccess<UserBean> access2 = new DefaultDatabaseAccess<UserBean>();
			List<UserBean> userList = access2.queryList(UserBean.class, sql2);
			if (userList.size() == 1) {
				UserBean bean = userList.get(0);
				String name = bean.getName();
				String info = bean.getInfo();
				byte[] img = bean.getImg();
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < img.length; i++) {
					buffer.append(img[i]);
					if (i < img.length - 1) {
						buffer.append(",");
					}
				}
				logger.info("查询id=" + time + "的记录为：name='" + name + "', info='"
						+ info + "', byte[]={" + buffer.toString() + "}");
				if (name.equals("王五") && info.equals("王五什么都会做")
						&& checkBytes(img, bytes)) {
					logger
							.info("测试execute(String sql, List<FieldParameter> paraList)方法成功!\r\n\r\n");
				} else {
					logger
							.error("测试execute(String sql, List<FieldParameter> paraList)方法失败!插入数据跟查询到的数据不一致");
				}
			} else {
				logger
						.error("测试execute(String sql, List<FieldParameter> paraList)方法失败,查询id="
								+ time + "的记录数不等于1");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			logger.error(
					"测试execute(String sql, List<FieldParameter> paraList)方法失败",
					e);
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#executeBatch(java.util.List)}.
	 */
	@Test
	public void testExecuteBatch() {
		logger.info("开始测试executeBatch(List<String> sqlList)方法");
		long time = new Date().getTime();
		String sql1 = "insert into test(id,name) values(" + time + ",'aaa')";
		String sql2 = "insert into testB(departmentId,departmentname) values("
				+ time + ",'人力资源')";
		List<String> sqlList = new ArrayList<String>();
		sqlList.add(sql1);
		sqlList.add(sql2);
		IDatabaseAccess<String> access1 = new DefaultDatabaseAccess<String>();
		try {
			access1.executeBatch(sqlList);
			logger.info("向test表插入一条数据：id=" + time
					+ "; name='aaa'\r\n向testb表中插入一条数据：departmentId=" + time
					+ "; departmentname='人力资源'");
			String querysql1 = "select name from test where id=" + time;
			String querysql2 = "select departmentname from testb where departmentid="
					+ time;
			List<String> nameList = access1.queryList(String.class, querysql1);
			List<String> departmentnameList = access1.queryList(String.class,
					querysql2);
			if (nameList.size() == 1
					&& nameList.size() == departmentnameList.size()) {
				String name = nameList.get(0);
				String departmentName = departmentnameList.get(0);
				logger.info("查询到test中插入的id=" + time + "的数据：name='" + name
						+ "'; \r\n查询到testb中插入的id=" + time
						+ "的数据：departmentName='" + departmentName + "'");
				if (name.equals("aaa") && departmentName.equals("人力资源")) {
					logger
							.info("测试executeBatch(List<String> sqlList)方法成功!\r\n\r\n");
				} else {
					logger
							.error("测试executeBatch(List<String> sqlList)方法失败，插入的数据和查询到的不一致");
				}
			} else {
				logger
						.error("测试executeBatch(List<String> sqlList)方法失败，查询出来的记录数比插入的记录数多");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("测试executeBatch(List<String> sqlList)方法失败", e);
		}
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#executePrepareBatch(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testExecutePrepareBatch() {
		logger
				.info("开始测试executePrepareBatch(String sql, List<List<FieldParameter>> listParaList)方法");
		String sql = "insert into test(id,name,info,img) values(?,?,?,?)";

		List<List<FieldParameter>> listParaList = new ArrayList<List<FieldParameter>>();
		long id1 = new Date().getTime();
		String name1 = "ddd";
		String info1 = "等着中彩票";
		byte[] img1 = { 0, 1, 1, 0 };
		insertTest(id1, name1, info1, img1, listParaList);

		long id2 = new Date().getTime();
		String name2 = "eee";
		String info2 = "石头扎到头";
		byte[] img2 = { 0, 1 };
		insertTest(id2, name2, info2, img2, listParaList);

		IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
		try {
			access.executePrepareBatch(sql, listParaList);
			logger.info("向表test中一次插入两条数据：\r\n 1.id=" + id1
					+ ", name='ddd', info='等着中彩票', img={0,1,1,0} \r\n2.id="
					+ id1 + ", name='eee', info='石头扎到头', img={0,1}");
			String querySql = "select * from test where id in (" + id1 + ","
					+ id2 + ") order by id";
			List<UserBean> userList = access
					.queryList(UserBean.class, querySql);
			logger.info("查询到的数据为：\r\n" + listToString(userList));
			boolean flag = false;
			if (userList.size() == 2) {
				UserBean user1 = userList.get(0);
				String user1name = user1.getName();
				String user1info = user1.getInfo();
				byte[] user1img = user1.getImg();
				if (user1name.equals("ddd") && user1info.equals("等着中彩票")
						&& checkBytes(img1, user1img)) {
					UserBean user2 = userList.get(1);
					String user2name = user2.getName();
					String user2info = user2.getInfo();
					byte[] user2img = user2.getImg();
					if (user2name.equals("eee") && user2info.equals("石头扎到头")
							&& checkBytes(img2, user2img)) {
						flag = true;
					}
				}

			}
			if (flag) {
				logger
						.info("测试executePrepareBatch(String sql, List<List<FieldParameter>> listParaList)方法成功!同时也测试queryList(Class<T> clazz, String sql)方法\r\n\r\n");
			} else {
				logger
						.error("测试executePrepareBatch(String sql, List<List<FieldParameter>> listParaList)方法失败!");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger
					.error(
							"测试executePrepareBatch(String sql, List<List<FieldParameter>> listParaList)方法失败!",
							e);
		}

	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#executePrepareMultiBath(java.util.List)}.
	 */
	@Test
	public void testExecutePrepareMultiBath() {
		logger.info("开始测试executePrepareMultiBath(List<SqlParameter> list)方法");
		String sql1 = "insert into test(id,name,info,img) values(?,?,?,?)";
		long time = new Date().getTime();
		FieldParameter parameter1 = new FieldParameter(1, time,
				FieldTypes.NUMERIC);
		FieldParameter parameter2 = new FieldParameter(2, "ffff",
				FieldTypes.VARCHAR);
		FieldParameter parameter3 = new FieldParameter(3, "傻子", FieldTypes.CLOB);
		byte[] bytes1 = { 0 };
		FieldParameter parameter4 = new FieldParameter(4, bytes1,
				FieldTypes.BLOB);
		List<FieldParameter> paraList1 = new ArrayList<FieldParameter>();
		paraList1.add(parameter1);
		paraList1.add(parameter2);
		paraList1.add(parameter3);
		paraList1.add(parameter4);
		SqlParameter sqlParameter1 = new SqlParameter();
		sqlParameter1.setParaList(paraList1);
		sqlParameter1.setSql(sql1);

		String sql2 = "insert into testb(departmentid,departmentname) values(?,?)";
		FieldParameter para1 = new FieldParameter(1, time, FieldTypes.VARCHAR);
		FieldParameter para2 = new FieldParameter(2, "销售部", FieldTypes.VARCHAR);
		List<FieldParameter> paraList2 = new ArrayList<FieldParameter>();
		paraList2.add(para1);
		paraList2.add(para2);
		SqlParameter sqlParameter2 = new SqlParameter();
		sqlParameter2.setParaList(paraList2);
		sqlParameter2.setSql(sql2);

		List<SqlParameter> sqlParaList = new ArrayList<SqlParameter>();
		sqlParaList.add(sqlParameter1);
		sqlParaList.add(sqlParameter2);
		logger.info("1.向test表中插入的数据为：id=" + time
				+ ", name='ffff', info='傻子', img={0}\r\n2.向testb表中插入的数据为：id="
				+ time + ", name='销售部'");

		String querySql1 = "select * from test where id=" + time;
		String querySql2 = "select * from testb where departmentid=" + time;
		IDatabaseAccess<UserBean> access1 = new DefaultDatabaseAccess<UserBean>();
		IDatabaseAccess<DepartmentBean> access2 = new DefaultDatabaseAccess<DepartmentBean>();
		try {
			access1.executePrepareMultiBath(sqlParaList);
			List<UserBean> userList = access1.queryList(UserBean.class,
					querySql1);
			if (userList.size() == 1) {
				List<DepartmentBean> departmentList = access2.queryList(
						DepartmentBean.class, querySql2);
				if (departmentList.size() == 1) {
					UserBean user = userList.get(0);
					DepartmentBean department = departmentList.get(0);
					logger.info("查询到的testb中的数据：" + user.toString() + "\r\n "
							+ department.toString());
					String name = user.getName();
					String info = user.getInfo();
					byte[] img = user.getImg();
					String departmentName = department.getDepartmentName();
					boolean flag = false;
					if (name.equals("ffff") && info.equals("傻子")
							&& checkBytes(img, bytes1)
							&& departmentName.equals("销售部")) {
						flag = true;
					}
					if (flag) {
						logger
								.info("测试executePrepareMultiBath(List<SqlParameter> list)方法成功\r\n\r\n");
					} else {
						logger
								.error("测试executePrepareMultiBath(List<SqlParameter> list)方法失败，插入结果和查询出来的结果不一致");
					}
				} else {
					logger
							.error("测试executePrepareMultiBath(List<SqlParameter> list)方法失败，查询testb中的数据数量和插入的不一致:");
				}
			} else {
				logger
						.error("测试executePrepareMultiBath(List<SqlParameter> list)方法失败，查询test中的数据比插入的数据多:"
								+ querySql1);
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger
					.error(
							"测试executePrepareMultiBath(List<SqlParameter> list)方法失败",
							e);
		}

	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#queryFirst(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public void testQueryFirst() {
		logger.info("开始queryFirst(Class<T> clazz, String sql)方法");
		long time = new Date().getTime();
		String insertSql = "insert into test(id,name,info,img) values(?,?,?,?)";
		FieldParameter paraLob1 = new FieldParameter(1, time,
				FieldTypes.NUMERIC);
		FieldParameter paraLob2 = new FieldParameter(2, "张三",
				FieldTypes.VARCHAR);
		FieldParameter paraLob3 = new FieldParameter(3, "张三是个老师",
				FieldTypes.CLOB);
		byte[] bytes = { 1, 0 };
		FieldParameter paraLob4 = new FieldParameter(4, bytes, FieldTypes.BLOB);
		List<FieldParameter> fieldList = new ArrayList<FieldParameter>();
		fieldList.add(paraLob1);
		fieldList.add(paraLob2);
		fieldList.add(paraLob3);
		fieldList.add(paraLob4);
		IDatabaseAccess<UserBean> access1 = new DefaultDatabaseAccess<UserBean>();
		try {
			access1.execute(insertSql, fieldList);
			logger.info("插入到test的数据为：id=" + time
					+ ", name='张三', info='张三是个老师', img={1,0}");
			String querySql = "select * from test where id=" + time;
			String querySql2 = "select count(*) from test where id=" + time;
			IDatabaseAccess<Integer> access2 = new DefaultDatabaseAccess<Integer>();
			int count = access2.queryFirst(Integer.class, querySql2);
			UserBean user = access1.queryFirst(UserBean.class, querySql);
			logger.info("查询到的数据为：" + user.toString());
			String name = user.getName();
			String info = user.getInfo();
			byte[] img = user.getImg();
			if (name.equals("张三") && info.equals("张三是个老师")
					&& checkBytes(img, bytes)) {
				logger.info("测试queryFirst(Class<T> clazz, String sql)方法成功");
			} else {
				logger
						.error("测试queryFirst(Class<T> clazz, String sql)方法失败,插入数据和查询的数据不一致");
			}
			logger.info("测试统计查询，插入id=" + time + "的记录为一条");
			if (count == 1) {
				logger
						.info("测试queryFirst(Class<T> clazz, String sql)方法统计查询成功\r\n\r\n");
			} else {
				logger
						.error("测试queryFirst(Class<T> clazz, String sql)方法统计查询失败，插入数据条数和查询数据条数不一致");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("测试queryFirst(Class<T> clazz, String sql)方法失败", e);
		}
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#queryList(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public void testQueryList() {

	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#queryPage(java.lang.Class, java.lang.String, int, int)}.
	 */
	@Test
	public void testQueryPage() {
		logger
				.info("开始测试queryPage(Class<T> clazz, String sql, int pageSize, int currentPage)方法");
		String sql = "insert into test(id,name,info,img) values(?,?,?,?)";

		List<List<FieldParameter>> listParaList = new ArrayList<List<FieldParameter>>();
		long id1 = new Date().getTime();
		String name1 = "小一";
		String info1 = "一等兵";
		byte[] img1 = { 0, 1, 1, 0 };
		insertTest(id1, name1, info1, img1, listParaList);
		UserBean bean1 = new UserBean(id1, name1, info1, img1);

		long id2 = id1 + 1;
		String name2 = "小二";
		String info2 = "二等兵";
		byte[] img2 = { 0 };
		insertTest(id2, name2, info2, img2, listParaList);
		UserBean bean2 = new UserBean(id2, name2, info2, img2);

		long id3 = id1 + 2;
		String name3 = "小三";
		String info3 = "三等兵";
		byte[] img3 = { 0, 1 };
		insertTest(id3, name3, info3, img3, listParaList);
		UserBean bean3 = new UserBean(id3, name3, info3, img3);

		long id4 = id1 + 3;
		String name4 = "小四";
		String info4 = "四等兵";
		byte[] img4 = { 0, 1, 1 };
		insertTest(id4, name4, info4, img4, listParaList);
		UserBean bean4 = new UserBean(id4, name4, info4, img4);

		IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
		try {
			access.executePrepareBatch(sql, listParaList);
			logger.info("插入4条数据:\r\n" + bean1.toString()+"\r\n" + bean2.toString()
					+ bean3.toString()+"\r\n" + bean4.toString());
			String querySql = "select * from test where id in(" + id1 + ","
					+ id2 + "," + id3 + "," + id4 + ") order by id";
			logger.info("查询第二页数据，每页两条数据" + querySql);
			List<UserBean> userList = access.queryPage(UserBean.class,
					querySql, 2, 2);
			if (userList.size() == 2) {
				UserBean user1 = userList.get(0);
				UserBean user2 = userList.get(1);
				if (user1.equals(bean3) && user2.equals(bean4)) {
					logger
							.info("测试queryPage(Class<T> clazz, String sql, int pageSize, int currentPage)方法成功\r\n\r\n");
				} else {
					logger
							.error("测试queryPage(Class<T> clazz, String sql, int pageSize, int currentPage)方法失败，查询的结果不是想要的结果:\r\n"
									+ user1.toString() + user2.toString());
				}
			} else {
				logger
						.error("测试queryPage(Class<T> clazz, String sql, int pageSize, int currentPage)方法失败，查询结果数与预计的数部队");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger
					.error(
							"测试queryPage(Class<T> clazz, String sql, int pageSize, int currentPage)方法失败",
							e);
		}
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#queryPrepareFirst(java.lang.Class, java.lang.String, java.util.List)}.
	 */
	@Test
	public void testQueryPrepareFirst() {
		logger
				.info("开始测试queryPrepareFirst(Class<T> clazz, String sql, List<FieldParameter> paraList)方法");
		String sql = "insert into test(id,name,info,img) values(?,?,?,?)";
		long id = new Date().getTime();
		String name = "小a";
		String info = "字母老大";
		byte[] img = { 1, 1, 1 };
		UserBean bean = new UserBean(id, name, info, img);
		logger.info("插入的数据为：" + bean.toString());
		List<List<FieldParameter>> listParaList = new ArrayList<List<FieldParameter>>();
		insertTest(id, name, info, img, listParaList);
		IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
		try {
			access.executePrepareBatch(sql, listParaList);
			String querySql = "select * from test where id=?";
			List<FieldParameter> paraList = new ArrayList<FieldParameter>();
			FieldParameter para = new FieldParameter(1, id, FieldTypes.NUMERIC);
			paraList.add(para);
			UserBean userBean = access.queryPrepareFirst(UserBean.class,
					querySql, paraList);
			logger.info("查询到的数据：" + userBean.toString());
			if (bean.equals(userBean)) {
				logger
						.info("测试queryPrepareFirst(Class<T> clazz, String sql, List<FieldParameter> paraList)方法成功\r\n\r\n");
			} else {
				logger
						.error("测试queryPrepareFirst(Class<T> clazz, String sql, List<FieldParameter> paraList)方法失败");
			}

			String queryCount = "select count(*) from test where id=?";
			IDatabaseAccess<Integer> access2 = new DefaultDatabaseAccess<Integer>();
			int count = access2.queryPrepareFirst(Integer.class, queryCount,
					paraList);
			logger.info("插入了一条id=" + id + "的记录");
			if (count == 1) {
				logger
						.info("测试queryPrepareFirst(Class<T> clazz, String sql, List<FieldParameter> paraList)方法统计查询成功");
			} else {
				logger
						.error("测试queryPrepareFirst(Class<T> clazz, String sql, List<FieldParameter> paraList)方法统计查询失败，数量不对："
								+ count);
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger
					.error(
							"测试queryPrepareFirst(Class<T> clazz, String sql, List<FieldParameter> paraList)方法失败",
							e);
		}
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#queryPrepareList(java.lang.Class, java.lang.String, java.util.List)}.
	 */
	@Test
	public void testQueryPrepareList() {
		logger
				.info("开始测试queryPrepareList(Class<T> clazz, String sql, List<FieldParameter> paraList)方法");
		String sql = "insert into test(id,name,info,img) values(?,?,?,?)";

		List<List<FieldParameter>> listParaList = new ArrayList<List<FieldParameter>>();
		long id1 = new Date().getTime();
		String name1 = "小b";
		String info1 = "一等兵";
		byte[] img1 = { 0, 1, 1, 0 };
		insertTest(id1, name1, info1, img1, listParaList);
		UserBean bean1 = new UserBean(id1, name1, info1, img1);

		long id2 = new Date().getTime();
		String name2 = "小c";
		String info2 = "二等兵";
		byte[] img2 = { 0 };
		insertTest(id2, name2, info2, img2, listParaList);
		UserBean bean2 = new UserBean(id2, name2, info2, img2);

		long id3 = new Date().getTime();
		String name3 = "小d";
		String info3 = "三等兵";
		byte[] img3 = { 0, 1 };
		insertTest(id3, name3, info3, img3, listParaList);
		UserBean bean3 = new UserBean(id3, name3, info3, img3);
		IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
		try {
			access.executePrepareBatch(sql, listParaList);
			logger.info("插入数据为：\r\n" + bean1.toString() +"\r\n"+ bean2.toString()+"\r\n"
					+ bean3.toString());
			String querySql = "select * from test where id in(?,?,?) order by id";
			List<FieldParameter> paraList = new ArrayList<FieldParameter>();
			FieldParameter param1 = new FieldParameter(1, id1,
					FieldTypes.NUMERIC);
			FieldParameter param2 = new FieldParameter(2, id2,
					FieldTypes.NUMERIC);
			FieldParameter param3 = new FieldParameter(3, id3,
					FieldTypes.NUMERIC);
			paraList.add(param1);
			paraList.add(param2);
			paraList.add(param3);
			List<UserBean> userList = access.queryPrepareList(UserBean.class,
					querySql, paraList);
			logger.info("查询到的结果为：" + listToString(userList));
			if (userList.size() == 3) {
				UserBean user1 = userList.get(0);
				UserBean user2 = userList.get(1);
				UserBean user3 = userList.get(2);
				if (user1.equals(bean1) && user2.equals(bean2)
						&& user3.equals(bean3)) {
					logger
							.info("测试queryPrepareList(Class<T> clazz, String sql, List<FieldParameter> paraList)方法成功\r\n\r\n");
				} else {
					logger
							.error("测试queryPrepareList(Class<T> clazz, String sql, List<FieldParameter> paraList)方法失败,查询的数据和插入的数据不一致");
				}
			} else {
				logger
						.error("测试queryPrepareList(Class<T> clazz, String sql, List<FieldParameter> paraList)方法失败,查询的数据个数跟插入的不一样");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger
					.error(
							"测试queryPrepareList(Class<T> clazz, String sql, List<FieldParameter> paraList)方法失败",
							e);
		}
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#queryPreparePage(java.lang.Class, java.lang.String, java.util.List, int, int)}.
	 */
	@Test
	public void testQueryPreparePage() {
		logger
				.info("开始测试queryPreparePage(Class<T> clazz, String sql, List<FieldParameter> paraList, int pageSize,int currentPage)方法");
		String sql = "insert into test(id,name,info,img) values(?,?,?,?)";

		List<List<FieldParameter>> listParaList = new ArrayList<List<FieldParameter>>();
		long id1 = new Date().getTime();
		String name1 = "小e";
		String info1 = "一等兵";
		byte[] img1 = { 0, 1, 1, 0 };
		insertTest(id1, name1, info1, img1, listParaList);
		UserBean bean1 = new UserBean(id1, name1, info1, img1);

		long id2 = id1 + 1;
		String name2 = "小f";
		String info2 = "二等兵";
		byte[] img2 = { 0 };
		insertTest(id2, name2, info2, img2, listParaList);
		UserBean bean2 = new UserBean(id2, name2, info2, img2);

		long id3 = id1 + 2;
		String name3 = "小g";
		String info3 = "三等兵";
		byte[] img3 = { 0, 1 };
		insertTest(id3, name3, info3, img3, listParaList);
		UserBean bean3 = new UserBean(id3, name3, info3, img3);

		long id4 = id1 + 3;
		String name4 = "小g";
		String info4 = "三等兵";
		byte[] img4 = { 0, 1 };
		insertTest(id4, name4, info4, img4, listParaList);
		UserBean bean4 = new UserBean(id4, name4, info4, img4);
		logger.info("插入的数据为：\r\n" + bean1.toString()+"\r\n" + bean2.toString()+"\r\n"
				+ bean3.toString()+"\r\n" + bean4.toString());
		IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
		try {
			access.executePrepareBatch(sql, listParaList);
			String querySql = "select * from test where id in(?,?,?,?) order by id";
			List<FieldParameter> paraList = new ArrayList<FieldParameter>();
			FieldParameter parameter1 = new FieldParameter(1, id1,
					FieldTypes.NUMERIC);
			FieldParameter parameter2 = new FieldParameter(2, id2,
					FieldTypes.NUMERIC);
			FieldParameter parameter3 = new FieldParameter(3, id3,
					FieldTypes.NUMERIC);
			FieldParameter parameter4 = new FieldParameter(4, id4,
					FieldTypes.NUMERIC);
			paraList.add(parameter1);
			paraList.add(parameter2);
			paraList.add(parameter3);
			paraList.add(parameter4);
			List<UserBean> userList = access.queryPreparePage(UserBean.class,
					querySql, paraList, 2, 2);
			logger.info("每页两条数据，查询第二页数据为：\r\n" + listToString(userList));
			if (userList.size() == 2) {
				UserBean user1 = userList.get(0);
				UserBean user2 = userList.get(1);
				if (user1.equals(bean3) && user2.equals(bean4)) {
					logger
							.info("测试queryPreparePage(Class<T> clazz, String sql, List<FieldParameter> paraList, int pageSize,int currentPage)方法成功\r\n\r\n");
				} else {
					logger
							.error("测试queryPreparePage(Class<T> clazz, String sql, List<FieldParameter> paraList, int pageSize,int currentPage)方法失败,查询的结果跟预期的不一致");
				}
			} else {
				logger
						.error("测试queryPreparePage(Class<T> clazz, String sql, List<FieldParameter> paraList, int pageSize,int currentPage)方法失败,查询得到的结果数量不对:"
								+ userList.size());
			}

		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger
					.error(
							"测试queryPreparePage(Class<T> clazz, String sql, List<FieldParameter> paraList, int pageSize,int currentPage)方法失败",
							e);
		}

	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#registerLobReadHandle(java.lang.String, com.shinetech.sql.type.ITypeReader)}.
	 */
	@Test
	public void testRegisterLobReadHandle() {
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#registerLobWriteHandle(int, com.shinetech.sql.type.ITypeWriter)}.
	 */
	@Test
	public void testRegisterLobWriteHandle() {
	}

	/**
	 * Test method for
	 * {@link com.shinetech.sql.impl.DefaultDatabaseAccess#setDatabase(java.lang.String)}.
	 */
	@Test
	public void testSetDatabase() {
	}

	/**
	 * 功能：初始化日志 <br>
	 * void
	 */
	private void initLog() {
		// PropertyConfigurator.configure("./config/log4j.properties");
		DOMConfigurator.configure("./config/log4j.xml");
	}

	/**
	 * 功能：预编译插入测试 <br>
	 * void
	 */
	private void insertParaExecute() {
		TestExecute execute = new TestExecute();
		// 插入测试
		String sql = "insert into test(id,name) values(?,?)";
		FieldParameter parameter1 = new FieldParameter(1, 1, FieldTypes.NUMERIC);
		FieldParameter parameter2 = new FieldParameter(2, "李四",
				FieldTypes.VARCHAR);
		List<FieldParameter> fieldList = new ArrayList<FieldParameter>();
		fieldList.add(parameter1);
		fieldList.add(parameter2);
		execute.testPexecute(sql, fieldList);
	}

	// /**
	// * 功能：预编译插入大字段测试 <br>
	// * void
	// */
	// private void insertLobParaExecute() {
	// TestExecute execute = new TestExecute();
	// // 插入大字段测试
	// String sql2 = "insert into test(id,name,info,img) values(?,?,?,?)";
	// FieldParameter paraLob1 = new FieldParameter(1, 2, FieldTypes.NUMERIC);
	// FieldParameter paraLob2 = new FieldParameter(2, "王五",
	// FieldTypes.VARCHAR);
	// FieldParameter paraLob3 = new FieldParameter(3, "王五什么都会做",
	// FieldTypes.CLOB);
	// byte[] bytes = { 1, 0, 1, 1, 0, 1 };
	// FieldParameter paraLob4 = new FieldParameter(4, bytes, FieldTypes.BLOB);
	// List<FieldParameter> fieldList = new ArrayList<FieldParameter>();
	// fieldList.add(paraLob1);
	// fieldList.add(paraLob2);
	// fieldList.add(paraLob3);
	// fieldList.add(paraLob4);
	// execute.testPexecute(sql2, fieldList);
	// }
	//
	// /**
	// * 功能：预编译修改测试 <br>
	// * void
	// */
	// private void updateParaExecute() {
	// TestExecute execute = new TestExecute();
	// String sql = "update test set name=? where id=?";
	// FieldParameter paraLob1 = new FieldParameter(1, "aaaa",
	// FieldTypes.VARCHAR);
	// FieldParameter paraLob2 = new FieldParameter(2, 2, FieldTypes.NUMERIC);
	// List<FieldParameter> paraList = new ArrayList<FieldParameter>();
	// paraList.add(paraLob1);
	// paraList.add(paraLob2);
	// execute.testPexecute(sql, paraList);
	//
	// }
	//
	// /**
	// * 功能：预编译大字段修改测试 <br>
	// * void
	// */
	// private void updateLobParaExecute() {
	// TestExecute execute = new TestExecute();
	// String sql = "update test set name=? ,info=? ,img=? where id=?";
	// FieldParameter paraLob1 = new FieldParameter(1, "bbb",
	// FieldTypes.VARCHAR);
	// FieldParameter paraLob2 = new FieldParameter(2, "bbb什么都不会做",
	// FieldTypes.CLOB);
	// byte[] bytes = { 1 };
	// FieldParameter paraLob3 = new FieldParameter(3, bytes, FieldTypes.BLOB);
	// FieldParameter paraLob4 = new FieldParameter(4, 1, FieldTypes.NUMERIC);
	// List<FieldParameter> paraList = new ArrayList<FieldParameter>();
	// paraList.add(paraLob1);
	// paraList.add(paraLob2);
	// paraList.add(paraLob3);
	// paraList.add(paraLob4);
	// execute.testPexecute(sql, paraList);
	// }
	//
	// /**
	// *
	// * 功能：预编译删除测试 <br>
	// * void
	// */
	// private void deleteParaExecute() {
	// TestExecute execute = new TestExecute();
	// String sql = "delete from test where id=?";
	// FieldParameter paraLob1 = new FieldParameter(1, 2, FieldTypes.NUMERIC);
	// List<FieldParameter> paraList = new ArrayList<FieldParameter>();
	// paraList.add(paraLob1);
	// execute.testPexecute(sql, paraList);
	// }
	//
	// /**
	// * 功能：批处理测试 <br>
	// * void
	// */
	// private void executeBatch() {
	// TestExecute execute = new TestExecute();
	// String sql1 = "insert into test(id,name) values(2,'aaa')";
	// String sql2 = "insert into test(id,name) values(3,'ccc')";
	// String sql3 = "update test set name='王五' where id=1";
	// String sql4 = "delete from test where id=2";
	// List<String> sqlList = new ArrayList<String>();
	// sqlList.add(sql1);
	// sqlList.add(sql2);
	// sqlList.add(sql3);
	// sqlList.add(sql4);
	// execute.testExecuteBatch(sqlList);
	// }
	//
	// /**
	// * 功能：测试同一sql的多次操作 <br>
	// * void
	// */
	// private void executeParaBatch() {
	// TestExecute execute = new TestExecute();
	// String sql = "insert into test(id,name,info,img) values(?,?,?,?)";
	//
	// FieldParameter parameter21 = new FieldParameter(1, 5,
	// FieldTypes.NUMERIC);
	// FieldParameter parameter22 = new FieldParameter(2, "eeee",
	// FieldTypes.VARCHAR);
	// FieldParameter parameter23 = new FieldParameter(3, "石头扎到头",
	// FieldTypes.CLOB);
	// byte[] bytes2 = { 0, 1 };
	// FieldParameter parameter24 = new FieldParameter(4, bytes2,
	// FieldTypes.BLOB);
	// List<FieldParameter> paraList2 = new ArrayList<FieldParameter>();
	// paraList2.add(parameter21);
	// paraList2.add(parameter22);
	// paraList2.add(parameter23);
	// paraList2.add(parameter24);
	//
	// List<List<FieldParameter>> listParaList = new
	// ArrayList<List<FieldParameter>>();
	// long id1 = new Date().getTime();
	// String name1 = "ddd";
	// String info1 = "等着中彩票";
	// byte[] img1 = { 0, 1, 1, 0 };
	// insertTest(id1, name1, name1, img1, listParaList);
	// execute.testPExecuteBatch(sql, listParaList);
	// }
	//
	// /**
	// * 功能：测试预编译事物操作 <br>
	// * void
	// */
	// private void executePrepareMultiBath() {
	// TestExecute execute = new TestExecute();
	// String sql1 = "insert into test(id,name,info,img) values(?,?,?,?)";
	// FieldParameter parameter1 = new FieldParameter(1, 11,
	// FieldTypes.NUMERIC);
	// FieldParameter parameter2 = new FieldParameter(2, "ffff",
	// FieldTypes.VARCHAR);
	// FieldParameter parameter3 = new FieldParameter(3, "傻子", FieldTypes.CLOB);
	// byte[] bytes1 = { 0 };
	// FieldParameter parameter4 = new FieldParameter(4, bytes1,
	// FieldTypes.BLOB);
	// List<FieldParameter> paraList1 = new ArrayList<FieldParameter>();
	// paraList1.add(parameter1);
	// paraList1.add(parameter2);
	// paraList1.add(parameter3);
	// paraList1.add(parameter4);
	// SqlParameter sqlParameter1 = new SqlParameter();
	// sqlParameter1.setParaList(paraList1);
	// sqlParameter1.setSql(sql1);
	//
	// String sql2 = "update test set name=? ,info=? ,img=? where id=?";
	// FieldParameter paraLob1 = new FieldParameter(1, "gggg",
	// FieldTypes.VARCHAR);
	// FieldParameter paraLob2 = new FieldParameter(2, "ggg小鸡吃米",
	// FieldTypes.CLOB);
	// byte[] bytes2 = { 1, 1, 1 };
	// FieldParameter paraLob3 = new FieldParameter(3, bytes2, FieldTypes.BLOB);
	// FieldParameter paraLob4 = new FieldParameter(4, 5, FieldTypes.NUMERIC);
	// List<FieldParameter> paraList2 = new ArrayList<FieldParameter>();
	// paraList2.add(paraLob1);
	// paraList2.add(paraLob2);
	// paraList2.add(paraLob3);
	// paraList2.add(paraLob4);
	// SqlParameter sqlParameter2 = new SqlParameter();
	// sqlParameter2.setParaList(paraList2);
	// sqlParameter2.setSql(sql2);
	//
	// List<SqlParameter> sqlParaList = new ArrayList<SqlParameter>();
	// sqlParaList.add(sqlParameter1);
	// sqlParaList.add(sqlParameter2);
	// execute.testExecutePrepareMultiBath(sqlParaList);
	// }
	//
	// /**
	// * 功能：测试查询第一条记录的一个字段 <br>
	// * void
	// */
	// private void queryFirstLong() {
	// logger.info("开始测试查询一条记录的一个字段");
	// String sql = "select name from test where id=1";
	// DefaultDatabaseAccess<Long> access = new DefaultDatabaseAccess<Long>();
	// try {
	// Long id = access.queryFirst(Long.class, sql);
	// logger.info("测试查询一条记录的一个字段成功，查询的字段值为:" + id);
	// } catch (DBException e) {
	// // TODO Auto-generated catch block
	// logger.error("测试查询一条记录的一个字段失败", e);
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * 功能：测试查询一条记录 <br>
	// * void
	// */
	// private void queryFirstBean() {
	// logger.info("开始测试查询一条记录");
	// String sql = "select * from test where id=1";
	// DefaultDatabaseAccess<UserBean> access = new
	// DefaultDatabaseAccess<UserBean>();
	// try {
	// UserBean bean = access.queryFirst(UserBean.class, sql);
	// logger.info("测试查询一条记录成功，查询记录为:" + bean.toString());
	// } catch (DBException e) {
	// // TODO Auto-generated catch block
	// logger.error("测试查询一条记录失败", e);
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * 功能：测试查询多条记录 <br>
	// * void
	// */
	// private void queryList() {
	// logger.info("开始测试查询多条记录");
	// String sql = "select * from test";
	// DefaultDatabaseAccess<UserBean> access = new
	// DefaultDatabaseAccess<UserBean>();
	// try {
	// List<UserBean> userList = access.queryList(UserBean.class, sql);
	// StringBuffer buffer = new StringBuffer();
	// for (UserBean bean : userList) {
	// buffer.append(bean.toString()).append("\r\t");
	// }
	// logger.info("测试查询多条记录成功，查询结果如下：" + buffer.toString());
	// } catch (DBException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// logger.error("测试查询多条记录失败", e);
	// }
	// }
	//
	// /**
	// * 功能：测试分页查询 <br>
	// * void
	// */
	// private void queryPage() {
	// logger.info("开始测试分页查询");
	// String sql = "select * from test order by id";
	// IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
	// try {
	// List<UserBean> userList = access.queryPage(UserBean.class, sql, 2,
	// 2);
	// StringBuffer buffer = new StringBuffer();
	// for (UserBean bean : userList) {
	// buffer.append(bean.toString()).append("\r\t");
	// }
	// logger.info("测试分页查询多条记录成功，查询结果如下：" + buffer.toString());
	//
	// } catch (DBException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// logger.error("测试分页查询多条记录失败", e);
	// }
	// }
	//
	// /**
	// * 功能：测试预编译查询一条记录的一个字段 <br>
	// * void
	// */
	// private void queryPrepareFirstStr() {
	// logger.info("开始测试预编译查询一条记录的一个字段");
	// String sql = "select name from test where id=?";
	// IDatabaseAccess<String> access = new DefaultDatabaseAccess<String>();
	// List<FieldParameter> paraList = new ArrayList<FieldParameter>();
	// FieldParameter param = new FieldParameter(1, 11, FieldTypes.NUMERIC);
	// paraList.add(param);
	// try {
	// String name = access.queryPrepareFirst(String.class, sql, paraList);
	// logger.info("测试预编译查询一条记录的一个字段成功，查询结果为:name=" + name);
	// } catch (DBException e) {
	// // TODO Auto-generated catch block
	// logger.error("测试预编译查询一条记录的一个字段失败", e);
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * 功能：预编译查询一条记录 <br>
	// * void
	// */
	// private void queryPrepareFirst() {
	// logger.info("开始测试预编译查询的一条记录");
	// String sql = "select * from test where id=?";
	// IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
	// List<FieldParameter> paraList = new ArrayList<FieldParameter>();
	// FieldParameter param = new FieldParameter(1, 1, FieldTypes.NUMERIC);
	// paraList.add(param);
	// try {
	// UserBean bean = access.queryPrepareFirst(UserBean.class, sql,
	// paraList);
	// logger.info("测试预编译查询一条记录成功,查询结果为：" + bean.toString());
	// } catch (DBException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// logger.error("测试预编译查询一条记录失败", e);
	// }
	// }
	//
	// /**
	// * 功能：测试预编译查询多条记录 <br>
	// * void
	// */
	// private void queryPrepareList() {
	// logger.info("开始测试预编译查询多条记录");
	// String sql = "select * from test where name=?";
	// IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
	// List<FieldParameter> paraList = new ArrayList<FieldParameter>();
	// FieldParameter param = new FieldParameter(1, "ddd", FieldTypes.VARCHAR);
	// paraList.add(param);
	// try {
	// List<UserBean> userList = access.queryPrepareList(UserBean.class,
	// sql, paraList);
	// StringBuffer buffer = new StringBuffer();
	// for (UserBean bean : userList) {
	// buffer.append(bean.toString()).append("\r\t");
	// }
	// logger.info("测试预编译查询多条记录成功，查询结果如下：" + buffer.toString());
	// } catch (DBException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * 功能：测试预编译分页查询 <br>
	// * void
	// */
	// private void queryPreparePage() {
	// logger.info("开始测试预编译分页查询");
	// String sql = "select * from test where id>?";
	// IDatabaseAccess<UserBean> access = new DefaultDatabaseAccess<UserBean>();
	// List<FieldParameter> paraList = new ArrayList<FieldParameter>();
	// FieldParameter param = new FieldParameter(1, 1, FieldTypes.NUMERIC);
	// paraList.add(param);
	// try {
	// List<UserBean> userList = access.queryPreparePage(UserBean.class,
	// sql, paraList, 2, 3);
	// StringBuffer buffer = new StringBuffer();
	// for (UserBean bean : userList) {
	// buffer.append(bean.toString()).append("\r\t");
	// }
	// logger.info("测试预编译分页查询成功，查询结果：" + buffer.toString());
	// } catch (DBException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// logger.error("测试预编译分页查询失败", e);
	// }
	// }

	/**
	 * 功能：比较两个byte[]是否相等 <br>
	 * 
	 * @param bytes1
	 * @param bytes2
	 * @return boolean
	 */
	private boolean checkBytes(byte[] bytes1, byte[] bytes2) {
		boolean result = true;
		if (bytes1.length == bytes2.length) {
			for (int i = 0; i < bytes1.length; i++) {
				if (bytes1[i] != bytes2[i]) {
					result = false;
					break;
				}
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 功能：UserBean集合转换成String <br>
	 * 
	 * @param userList
	 * @return String
	 */
	private String listToString(List<UserBean> userList) {
		StringBuffer buffer = new StringBuffer();
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				UserBean bean = userList.get(i);
				String name = bean.getName();
				long id = bean.getId();
				String info = bean.getInfo();
				byte[] img = bean.getImg();
				buffer.append("id=" + id + "name='" + name + ", info='" + info
						+ "', img={");
				if (img != null) {
					for (int j = 0; j < img.length; j++) {
						buffer.append(img[j]);
						if (j < img.length - 1) {
							buffer.append(",");
						} else {
							if (i < userList.size() - 1) {
								buffer.append("} \r\n");
							}else{
								buffer.append("}");
							}
						}
					}
				}

			}
		}
		return buffer.toString();
	}

	/**
	 * 功能：byte[]转换成String <br>
	 * 
	 * @param img
	 * @return String
	 */
	private String bytesToString(byte[] img) {
		StringBuffer buffer = new StringBuffer("img={");
		if (img != null) {
			for (int i = 0; i < img.length; i++) {
				buffer.append(img[i]);
				if (i < img.length - 1) {
					buffer.append(",");
				} else {
					buffer.append("} \r\n");
				}
			}
		} else {
			buffer.append("}");
		}
		return buffer.toString();
	}

	/**
	 * 功能：向Test表中插入数据 <br>
	 * void
	 */
	private void insertTest(long id, String name, String info, byte[] img,
			List<List<FieldParameter>> listParaList) {
		FieldParameter parameter1 = new FieldParameter(1, id,
				FieldTypes.NUMERIC);
		FieldParameter parameter2 = new FieldParameter(2, name,
				FieldTypes.VARCHAR);
		FieldParameter parameter3 = new FieldParameter(3, info, FieldTypes.CLOB);
		FieldParameter parameter4 = new FieldParameter(4, img, FieldTypes.BLOB);
		List<FieldParameter> paraList = new ArrayList<FieldParameter>();
		paraList.add(parameter1);
		paraList.add(parameter2);
		paraList.add(parameter3);
		paraList.add(parameter4);
		listParaList.add(paraList);
	}

}
