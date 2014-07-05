/*
 * PaywayDAO.java
 * May 13, 2013
 * com.tibco.dao
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
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Payway;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class PaywayDAO {

	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	
	@SuppressWarnings("unchecked")
	public Payway getPayway(Integer id) throws DBException{
		String sql = "select * from payway where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		return (Payway)db.queryPrepareFirst(Payway.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public  Integer addPayway(Payway payway) throws DBException{
		String getNextAutoIdSql = "select auto_increment from information_schema.`TABLES` where table_name='payway'";
		Integer paywayId =  (Integer)db.queryFirst(Integer.class, getNextAutoIdSql);
		String sql = "insert into payway(id,payway,discount,createDate,cid) values(?,?,?,now(),?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,paywayId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,payway.getPayway(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,payway.getDiscount(),FieldTypes.FLOAT));
		fpList.add(new FieldParameter(4,payway.getCid(),FieldTypes.INTEGER));
		db.execute(sql, fpList);
		return paywayId;
	}
	@SuppressWarnings("unchecked")
	public void updatePayway(Payway payway)throws DBException{
		String sql = "update payway set payway = ?,discount=?,modifyDate = now() where id= ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,payway.getPayway(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,payway.getDiscount(),FieldTypes.FLOAT));
		fpList.add(new FieldParameter(3,payway.getId(),FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public List<Payway> getPaywaysByClass(Integer cid)throws DBException{
		String sql = "select * from payway where cid = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,cid,FieldTypes.INTEGER));
		return db.queryPrepareList(Payway.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void getPaywayList(Integer cid,ResultSetHandler handler) throws DBException{
		String sql = "select * from payway where cid = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,cid,FieldTypes.INTEGER));
		db.handleList(sql, fpList, handler);
	}
	@SuppressWarnings("unchecked")
	public  void delete(Integer id) throws DBException{
		String sql = "delete from payway where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
}

