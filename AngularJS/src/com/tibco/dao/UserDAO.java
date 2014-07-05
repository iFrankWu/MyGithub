/*
 * UserDAO.java
 * Mar 28, 2013
 * org.timecontrol.db
 * AngularJS
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
import com.shinetech.sql.exception.DBException;
import com.tibco.bean.User;
import com.tibco.util.Const;


/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class UserDAO {
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	
	@SuppressWarnings("unchecked")
	public  void addUser(User user) throws DBException{
		String sql = "insert into user(username,password,type,status,createDate) values(?,password(?),?,?,now())";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,user.getUsername(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,user.getPassword(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,user.getType(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(4,user.getStatus(),FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public List<User> getUserList() throws DBException{
		String sql = "select id,username,type,status,createDate,modifyDate from user";
		return db.queryList(User.class, sql);
	}
	@SuppressWarnings("unchecked")
	public void deleteUser(Integer id) throws DBException{
		String sql = "delete from user where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public Boolean existUsename(String username)throws DBException{
		String sql = "select count(*) from user where username = ? ";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,username,FieldTypes.VARCHAR));
		Integer i = (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
		if(i > 0){
			return true;
		}
	    return false;
	}
	@SuppressWarnings("unchecked")
	public User getUserByNameAndPasswd(String username,String password) throws DBException{
		String sql = "select * from user where username = ? and password=password(?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,username,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,password,FieldTypes.VARCHAR));
		Object o = db.queryPrepareFirst(User.class, sql, fpList);
		if(o == null)return null;
		return (User)o;
	}
	@SuppressWarnings("unchecked")
	public User getUser(Integer id) throws DBException{
		String sql = "select * from user where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		return (User)db.queryPrepareFirst(User.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public User getUserByName(String username) throws DBException{
		String sql = "select * from user where username = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,username,FieldTypes.VARCHAR));
		return (User)db.queryPrepareFirst(User.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void updateUser(User user,boolean  modifyPasswd) throws DBException{
		String sql = "update user set username=?,type=?,status=?,modifyDate=now(),password=password(?) where id =?";
		if(!modifyPasswd)
			sql = "update user set username=?,type=?,status=?,modifyDate=now() where id =?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,user.getUsername(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,user.getType(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,user.getStatus(),FieldTypes.VARCHAR));
		if(modifyPasswd){
			fpList.add(new FieldParameter(4,user.getPassword(),FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(5,user.getId(),FieldTypes.INTEGER));
		}else{
			fpList.add(new FieldParameter(4,user.getId(),FieldTypes.INTEGER));
		}
		db.execute(sql, fpList);
	}
	public static void main(String args[]) throws DBException{
		Const.initLogger();
		UserDAO dao = new UserDAO();
//		Boolean b = dao.existUsename("A");
//		
//		System.out.println(b);
		dao.deleteUser(3);
	}
}

