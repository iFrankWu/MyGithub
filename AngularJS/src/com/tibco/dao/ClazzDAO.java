/*
 * ClazzDAO.java
 * Apr 4, 2013
 * com.tibco.db
 * StudentManage
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Clazz;
import com.tibco.bean.Payway;
import com.tibco.bean.Student;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class ClazzDAO {
	private ClassStudentDAO classStuDAO = new  ClassStudentDAO();
	private PaywayDAO paywayDAO = new PaywayDAO();
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();	
	
	/**
	 *  TODO we must make sure the calss table is unique in you msql database;
	 *  @param clazz
	 *  @return
	 *  @throws DBException
	 *	Integer
	 */
	@SuppressWarnings("unchecked")
	public Integer save(Clazz clazz) throws DBException{
		String getNextAutoIdSql = "select auto_increment from information_schema.`TABLES` where table_name='class'";
		Integer cid =  (Integer)db.queryFirst(Integer.class, getNextAutoIdSql);
		String sql = "insert into class(className,classNotice,teacher,payAmount,createDate,id) values(?,?,?,?,now(),?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,clazz.getClassName(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,clazz.getClassNotice(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,clazz.getTeacher(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(4,clazz.getPayAmount(),FieldTypes.FLOAT));
		fpList.add(new FieldParameter(5,cid,FieldTypes.INTEGER));
		db.execute(sql, fpList);
		return cid;
	}
	@SuppressWarnings("unchecked")
	public  Clazz getClazz(Integer id,Boolean withStudents) throws DBException{
		final StudentDAO stuDAO = new StudentDAO();
		String sql = "select * from class where id =?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		final Clazz clazz = (Clazz)db.queryPrepareFirst(Clazz.class, sql, fpList);
		if(withStudents){
			classStuDAO.getStudentsByClass(id, new ResultSetHandler(){

				@Override
				public void handle(ResultSet rs) {
					try{
						Integer sid = (Integer)rs.getInt("sid");
						Student student = stuDAO.getStudent(sid);
						student.setId(sid);
						clazz.getStudents().add(student);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
//			clazz.setPayways(paywayDAO.getPaywaysByClass(id));
		}
		return clazz;
	}
	@SuppressWarnings("unchecked")
	public void updateClazz(Integer id,Clazz clazz) throws DBException{
		String sql = "update class set className = ?,classNotice = ?,teacher = ?,payAmount = ?,modifyDate = now() where id =?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,clazz.getClassName(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,clazz.getClassNotice(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,clazz.getTeacher(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(4,clazz.getPayAmount(),FieldTypes.FLOAT));
		fpList.add(new FieldParameter(5,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}

	public  List<Clazz> getList(final Boolean withStudents) throws DBException{
		String sql = "select * from class";
//		if(withStudents){
			final  List<Clazz> list = new ArrayList<Clazz>();
			db.handleList(sql, new ResultSetHandler(){
	
				@Override
				public void handle(ResultSet rs) {
					try {
						Integer cid = rs.getInt("id");
						final Clazz clazz = getClazz(cid,withStudents);
						
						paywayDAO.getPaywayList(cid, new ResultSetHandler(){

							@Override
							public void handle(ResultSet rs1) {
								try{
									Payway p = paywayDAO.getPayway(rs1.getInt("id"));
									p.setId(rs1.getInt("id"));
									clazz.getPayways().add(p);
								}catch(Exception e){
									e.printStackTrace();
								}
								
							}
							
						});
						clazz.setId(cid);
						list.add(clazz);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public  void delete(Integer id) throws DBException{
		String sql = "delete from class where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public Boolean doesExistClassName(String className) throws DBException{
		String sql = "select count(*) from class where className = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,className,FieldTypes.CHAR));
		Integer count = (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
		return count > 0 ? true: false;
	}
}

