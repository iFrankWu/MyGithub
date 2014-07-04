package com.shinetech.sql.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.SqlParameter;
import com.shinetech.sql.exception.DBException;

public class TestDatabaseAccessBuilder {
	static {
		System.out.println(TestDatabaseAccessBuilder.class.getResource("/com/shinetech/sql/test/log4j.xml"));
		System.setProperty("log4j.configuration",
				TestDatabaseAccessBuilder.class.getResource("/com/shinetech/sql/test/log4j.xml").toString());
	}

	public void print(TestBean b) {
		byte[] bytes = b.getT_b();

		t("======================================");
		t("T_I = " + b.getId());
		t("T_S = " + b.getT_s());
		t("T_C = " + b.getT_c());
		t("T_B = " + (bytes == null ? "" : new String(bytes)));
		t("T_D = " + b.getT_d());
	}

	public void t(String msg) {
		System.out.println(msg);
	}

	public void l(List<TestBean> list) {
		for (TestBean b : list) {
			print(b);
		}
	}

	public void ti(int i) {
		System.out.println("result = " + i);
	}

	public List<List<FieldParameter>> getList() {
		List<List<FieldParameter>> lllist = new ArrayList<List<FieldParameter>>();

		for (int i = 0; i < 3; i++) {
			List<FieldParameter> flist = new ArrayList<FieldParameter>();
			flist.add(new FieldParameter(1, "测试字符串参数绑定批量插入" + (i + 1), FieldTypes.VARCHAR));
			flist.add(new FieldParameter(2, "测试CLOB参数绑定批量插入" + (i + 1), FieldTypes.CLOB));
			flist.add(new FieldParameter(3, new String("测试BLOB参数绑定批量插入" + (i + 1)).getBytes(), FieldTypes.BLOB));
			flist.add(new FieldParameter(4, new Date(System.currentTimeMillis()), FieldTypes.DATE));
			lllist.add(flist);
		}

		return lllist;
	}

	public List<SqlParameter> getSqlList() {
		List<SqlParameter> list = new ArrayList<SqlParameter>();
		SqlParameter s1 = new SqlParameter();
		s1.setSql("insert into lob_test values(seq_lob_test.nextval, ?, ?, ?, ?)");
		List<FieldParameter> flist = new ArrayList<FieldParameter>();
		flist.add(new FieldParameter(1, "测试多表更新", FieldTypes.VARCHAR));
		flist.add(new FieldParameter(2, "测试多表更新CLOB", FieldTypes.CLOB));
		flist.add(new FieldParameter(3, "测试多表更新BLOB".getBytes(), FieldTypes.BLOB));
		flist.add(new FieldParameter(4, new Date(System.currentTimeMillis()), FieldTypes.DATE));
		s1.setParaList(flist);
		list.add(s1);

		SqlParameter s2 = new SqlParameter();
		s2.setSql("insert into lob_test_1 (id) values(?)");
		flist = new ArrayList<FieldParameter>();
		flist.add(new FieldParameter(1, 1, FieldTypes.NUMERIC));
		s2.setParaList(flist);
		list.add(s2);

		SqlParameter s3 = new SqlParameter();
		s3.setSql("insert into lob_test_1 (name) values(?)");
		flist = new ArrayList<FieldParameter>();
		flist.add(new FieldParameter(1, "my name is loong", FieldTypes.VARCHAR));
		s3.setParaList(flist);
		list.add(s3);

		SqlParameter s4 = new SqlParameter();
		s4.setSql("insert into lob_test_1 (id, name) values(?, ?)");
		flist = new ArrayList<FieldParameter>();
		flist.add(new FieldParameter(1, 3, FieldTypes.NUMERIC));
		flist.add(new FieldParameter(2, "my name is loong too", FieldTypes.VARCHAR));
		s4.setParaList(flist);
		list.add(s4);

		return list;
	}

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param args
	 *            void
	 * @throws DBException
	 */
	public static void main(String[] args) throws DBException {
		// TODO Auto-generated method stub

		TestDatabaseAccessBuilder t = new TestDatabaseAccessBuilder();

		IDatabaseAccess<TestBean> accessBuilder = new DatabaseAccessBuilder<TestBean>().getDatabaseAccess("oracle");

		t.t("");
		t.t("======================================");
		t.t("测试更新部分：");
		t.t("======================================");
		t.t("");

		t.t("测试execute：delete");
		String sql = "delete from lob_test where id > 4";

		t.t("测试execute：");
		sql = "insert into lob_test(ID, T_S, T_C, T_B, T_D) values(seq_lob_test.nextval, '测试插入语句', empty_clob(),empty_blob(), sysdate)";

		t.t("测试execute：");
		sql = "insert into lob_test(ID, T_S, T_C, T_B) values(seq_lob_test.nextval, ?, ?, ?)";
		List<FieldParameter> flist = new ArrayList<FieldParameter>();
		flist.add(new FieldParameter(1, "测试字符串参数绑定插入", FieldTypes.VARCHAR));
		flist.add(new FieldParameter(2, "测试CLOB参数绑定插入", FieldTypes.CLOB));
		flist.add(new FieldParameter(3, "测试BLOB参数绑定插入".getBytes(), FieldTypes.BLOB));

		t.t("测试executeBatch：");
		List<String> blist = new ArrayList<String>();
		blist.add("insert into lob_test(ID, T_S, T_C, T_B, T_D) values(seq_lob_test.nextval, '测试批量插入语句1', empty_clob(),empty_blob(), sysdate)");
		blist.add("insert into lob_test(ID, T_S, T_C, T_B) values(seq_lob_test.nextval, '测试批量插入语句2', empty_clob(),empty_blob())");

		t.t("测试executePrepareBatch：");
		sql = "insert into lob_test(ID, T_S, T_C, T_B, T_D) values(seq_lob_test.nextval, ?, ?, ?, ?)";

		t.t("测试executePrepareMultiBath：");

		t.t("");
		t.t("======================================");
		t.t("测试查询部分：");
		t.t("======================================");
		t.t("");

		// 部分查询
		t.t("测试queryFirst：");
		sql = "select id, t_c, t_d from lob_test";
		TestBean tb = accessBuilder.queryFirst(TestBean.class, sql);
		t.print(tb);

		// 全部查询
		t.t("测试queryFirst：");
		sql = "select * from lob_test";
		t.print(accessBuilder.queryFirst(TestBean.class, sql));

		// 返回集合
		t.t("测试queryList：");
		t.l(accessBuilder.queryList(TestBean.class, sql));

		//
		t.t("测试queryPrepareFirst：");
		sql = "select * from lob_test where id = ?";
		flist = new ArrayList<FieldParameter>();
		flist.add(new FieldParameter(1, 4, FieldTypes.NUMERIC));
		t.print(accessBuilder.queryPrepareFirst(TestBean.class, sql, flist));

		//
		t.t("测试queryPrepareList：");
		sql = "select * from lob_test where id > ?";
		t.l(accessBuilder.queryPrepareList(TestBean.class, sql, flist));

		//
		t.t("测试queryPage：");
		sql = "select * from lob_test order by id";
		t.l(accessBuilder.queryPage(TestBean.class, sql, 2, 1));

		//
		t.t("测试queryPreparePage：");
		sql = "select * from lob_test where id > ?";
		t.l(accessBuilder.queryPreparePage(TestBean.class, sql, flist, 2, 2));
	}

}
