/*
 * CommunicationDAO.java
 * Apr 4, 2013
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
import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Communication;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class CommunicateDAO {
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();	
	@SuppressWarnings("unchecked")
	public  void save(Integer sid,Communication communication) throws DBException{
		String sql = "insert into communication(content,sid,communicationDate,uid,username,status) values(?,?,now(),?,?,1)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,communication.getContent(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,sid,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,communication.getUid(),FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,communication.getUsername(),FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public  Communication getCommunication(Integer id) throws DBException{
		String sql = "select * from communication where id=?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		return (Communication) db.queryPrepareFirst(Communication.class, sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public  void updateCommunication(Communication communication) throws DBException{
		String sql = "update communication set content =? ,communicationDate = now() where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,communication.getContent(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,communication.getId(),FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public   List<Communication> getList(Integer sid) throws DBException{
		String sql = "select * from communication where sid = ? and status = 1";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		return db.queryPrepareList(Communication.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public  void delete(Integer id) throws DBException{
		String sql = "update communication set status = 0 where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	
}

