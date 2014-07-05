/*
 * ClassStudent.java
 * Apr 9, 2013
 * com.tibco.db
 * StudentManage
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.dao;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class ClassStudentDAO {
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();	
	/**
	 *  TODO it's add an student to a class
	 *  @param cid
	 *  @param sid
	 *  @throws DBException
	 *	void
	 */
	@SuppressWarnings("unchecked")
	public  void add(Integer cid,Integer sid) throws DBException{
		 String sql = "REPLACE into stu_class(sid,cid) values(?,?)";
		 List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		 fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		 fpList.add(new FieldParameter(2,cid,FieldTypes.INTEGER));
		 db.execute(sql, fpList);
	}
	public void getList(ResultSetHandler handle)throws DBException{
		String sql = "select * from stu_class where isDeleteDiscount = false and discount > 0";
		 db.handleList(sql, handle);
	}
	/**
	 *  TODO its for the admininstor set the student discount 
	 *  @param cid
	 *  @param sid
	 *  @param discount
	 *  @throws DBException
	 *	void
	 */
	@SuppressWarnings("unchecked")
	public  void addDiscount(Integer sid,Integer cid,Float discount) throws DBException{
		 String sql = "REPLACE into stu_class(sid,cid,discount,isDeleteDiscount) values(?,?,?,false)";
		 List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		 fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		 fpList.add(new FieldParameter(2,cid,FieldTypes.INTEGER));
		 fpList.add(new FieldParameter(3,discount,FieldTypes.FLOAT));
		 db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public Float getDiscount(Integer sid,Integer cid) throws DBException{
		String sql = "select discount from stu_class where sid = ? and cid = ? and isDeleteDiscount = false";
		 List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		 fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		 fpList.add(new FieldParameter(2,cid,FieldTypes.INTEGER));
//		 return (Payment) db.queryPrepareFirst(Payment.class, sql, fpList);
		 Object o = db.queryPrepareFirst(Float.class, sql, fpList);
		 if(o == null)return 1f;
		 return (Float)o;//(Float.class, fpList);
	}
	@SuppressWarnings("unchecked")
	public  void getClassesByStudent(Integer sid,ResultSetHandler handle) throws DBException{
		String sql = "select * from stu_class where sid = ?";
		 List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		 fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		 db.handleList(sql, fpList, handle);
	}

	@SuppressWarnings("unchecked")
	public  void getStudentsByClass(Integer cid,ResultSetHandler handle) throws DBException{
		String sql = "select * from stu_class where cid = ? ";
		 List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		 fpList.add(new FieldParameter(1,cid,FieldTypes.INTEGER));
		 db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public  void remove(Integer sid,Integer cid) throws DBException{
		String sql = "delete from stu_class where sid= ? and cid =?";
		 List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		 fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		 fpList.add(new FieldParameter(2,cid,FieldTypes.INTEGER));
		 db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public  void removeDiscount(Integer sid,Integer cid) throws DBException{
		String sql = "update stu_class set isDeleteDiscount = true where sid= ? and cid =?";
		 List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		 fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		 fpList.add(new FieldParameter(2,cid,FieldTypes.INTEGER));
		 db.execute(sql, fpList);
	}
}

