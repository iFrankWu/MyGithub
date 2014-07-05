/*
 * PaymentDAO.java
 * Apr 4, 2013
 * com.tibco.db
 * StudentManage
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Payment;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class PaymentDAO {
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();// selectedClass selectedPayway 
	
	@SuppressWarnings("unchecked")
	public  void save(Integer sid, Payment payment) throws DBException{
		String sql = "insert into payment(payAmount,purpose,doesNeedReminder,endDate,remark,payDate,sid,uid,pwayid,startDate,payer,receiver,status,useBalance,cashOrCard,selectedClass,selectedPayway,discount) values(?,?,?,?,?,?,?,?,?,?,?,?,1,?,?,?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,payment.getPayAmount(),FieldTypes.FLOAT));
		fpList.add(new FieldParameter(2,payment.getPurpose(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,payment.getDoesNeedReminder(),FieldTypes.BOOLEAN));
		fpList.add(new FieldParameter(4,payment.getEndDate(),FieldTypes.DATE));
		fpList.add(new FieldParameter(5,payment.getRemark(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6,payment.getPayDate(),FieldTypes.DATE));
		fpList.add(new FieldParameter(7,sid,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(8,payment.getUid(),FieldTypes.INTEGER));
		fpList.add(new FieldParameter(9,payment.getPwayid(),FieldTypes.INTEGER));
		fpList.add(new FieldParameter(10,payment.getStartDate(),FieldTypes.DATE));
		fpList.add(new FieldParameter(11,payment.getPayer(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(12,payment.getReceiver(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(13,payment.getUseBalance(),FieldTypes.FLOAT));
		fpList.add(new FieldParameter(14,payment.getCashOrCard(),FieldTypes.VARCHAR));
		
		fpList.add(new FieldParameter(15,payment.getSelectedClass(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(16,payment.getSelectedPayway(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(17,payment.getDiscount(),FieldTypes.FLOAT));
		
		db.execute(sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public  Payment getPayment(Integer id) throws DBException{
		String sql = "select * from payment where id=?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		return (Payment) db.queryPrepareFirst(Payment.class, sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public void updatePayment(Payment payment) throws DBException{
		String sql = "update payment set payAmount= ?,purpose =?,doesNeedReminder=?,endDate=?,remark=?,payDate=?, pwayid = ? ,startDate = ?, useBalance = ?,cashOrCard= ? ,selectedClass =?,selectedPayway =?,discount = ? where id =?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,payment.getPayAmount(),FieldTypes.FLOAT));
		fpList.add(new FieldParameter(2,payment.getPurpose(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,payment.getDoesNeedReminder(),FieldTypes.BOOLEAN));
		fpList.add(new FieldParameter(4,payment.getEndDate(),FieldTypes.DATE));
		fpList.add(new FieldParameter(5,payment.getRemark(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6,payment.getPayDate(),FieldTypes.DATE));
		fpList.add(new FieldParameter(7,payment.getPwayid(),FieldTypes.INTEGER));
		fpList.add(new FieldParameter(8,payment.getStartDate(),FieldTypes.DATE));
		fpList.add(new FieldParameter(9,payment.getUseBalance(),FieldTypes.FLOAT));
		fpList.add(new FieldParameter(10,payment.getCashOrCard(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(11,payment.getSelectedClass(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(12,payment.getSelectedPayway(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(13,payment.getDiscount(),FieldTypes.FLOAT));
		
		fpList.add(new FieldParameter(14,payment.getId(),FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public  List<Payment> getList(Integer sid) throws DBException{
		String sql = "select * from payment where sid = ? and status = 1";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		return db.queryPrepareList(Payment.class, sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public  List<Payment> getListByUser(Integer uid) throws DBException{
		String sql = "select * from payment where uid = ? and status = 1";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,uid,FieldTypes.INTEGER));
		return db.queryPrepareList(Payment.class, sql, fpList);
	 
	}
	/**
	 * 这个方法 在多个表中查找数据 和上面那些就在一个表中查找数据是不一样的
	 * */
	@SuppressWarnings("unchecked")
	public  List<Payment> getListByStudent(Integer sid) throws DBException{
//		StringBuffer sqlBuffer = new StringBuffer("select distinct payment.id as id,payment.payAmount as payAmount,payment.purpose as purpose,payment.doesNeedReminder as doesNeedReminder,payment.startDate as startDate,payment.endDate as endDate,payment.remark as remark,payment.payDate as payDate,student.name as payer ,user.username as receiver"+
//				" from payment, student,user "+
//				" where student.id = payment.sid and user.id= payment.uid and student.id=? ");
		StringBuffer sqlBuffer = new StringBuffer("select * from payment where sid = ? and status =1");
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		return db.queryPrepareList(Payment.class, sqlBuffer.toString(), fpList);
	}
	
	@SuppressWarnings("unchecked")
	public  List<Payment> getListByUserAndDateRang(Integer uid,String date1,String date2) throws DBException{
//		String sql = "select * from payment where uid = ? and payDate between ? and ?";
//		StringBuffer sqlBuffer = new StringBuffer("select distinct payment.payAmount as payAmount,payment.purpose as purpose,payment.doesNeedReminder as doesNeedReminder,payment.startDate as startDate,payment.endDate as endDate,payment.remark as remark,payment.payDate as payDate,student.name as payer ,user.username as receiver"+
//				" from payment, student,user "+
//				" where student.id = payment.sid and user.id= payment.uid and payment.uid=? ");
		
		StringBuffer sqlBuffer = new StringBuffer("select * from payment where uid = ? and status =1");
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,uid,FieldTypes.INTEGER));
		if("".equals(date1)){
			date1 = null;
		}
		if("".equals(date2)){//因为我发现这里有 空字符串
			date2 = null;
		}
		if(date1 != null && date2 != null){
			sqlBuffer.append(" and payDate between ? and ?");
			fpList.add(new FieldParameter(2,date1,FieldTypes.VARCHAR));
			fpList.add(new FieldParameter(3,date2,FieldTypes.VARCHAR));
		}else if(date1 != null && date2 == null){
			sqlBuffer.append(" and payDate > ? ");
			fpList.add(new FieldParameter(2,date1,FieldTypes.VARCHAR));
		}else if(date1 == null && date2 != null){
			sqlBuffer.append(" and payDate < ? ");
			fpList.add(new FieldParameter(2,date2,FieldTypes.VARCHAR));
		}
		System.out.println(sqlBuffer.toString());
		return db.queryPrepareList(Payment.class, sqlBuffer.toString(), fpList);
	 
	}
	 
	@SuppressWarnings("unchecked")
	public  void delete(Integer id) throws DBException{
		String sql = "update payment set status = 0 where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	
	@SuppressWarnings("unchecked")
	public List<Payment> getRefoundList() throws DBException{
		String sql = "select * from payment where status = -1";
		return db.queryList(Payment.class, sql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Payment> getRefoundList(Integer uid) throws DBException{
		String sql = "select * from payment where status = -1 and uid = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,uid,FieldTypes.INTEGER));
		return db.queryPrepareList(Payment.class, sql, fpList);
	}
//	public void addRefund(Integer sid, Payment payment) throws DBException{
//		String sql = "insert into payment(payAmount,purpose,doesNeedReminder,endDate,remark,payDate,sid,uid,pwayid,startDate,payer,receiver,status,useBalance,cashOrCard,selectedClass,selectedPayway,discount) values(?,?,?,?,?,?,?,?,?,?,?,?,-1,?,?,?,?,?)";
//		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
//		fpList.add(new FieldParameter(1,payment.getPayAmount(),FieldTypes.FLOAT));
//		fpList.add(new FieldParameter(2,payment.getPurpose(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(3,payment.getDoesNeedReminder(),FieldTypes.BOOLEAN));
//		fpList.add(new FieldParameter(4,payment.getEndDate(),FieldTypes.DATE));
//		fpList.add(new FieldParameter(5,payment.getRemark(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(6,payment.getPayDate(),FieldTypes.DATE));
//		fpList.add(new FieldParameter(7,sid,FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(8,payment.getUid(),FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(9,payment.getPwayid(),FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(10,payment.getStartDate(),FieldTypes.DATE));
//		fpList.add(new FieldParameter(11,payment.getPayer(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(12,payment.getReceiver(),FieldTypes.VARCHAR));
//		
//		fpList.add(new FieldParameter(13,payment.getUseBalance(),FieldTypes.FLOAT));
//		fpList.add(new FieldParameter(14,payment.getCashOrCard(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(15,payment.getSelectedClass(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(16,payment.getSelectedPayway(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(17,payment.getDiscount(),FieldTypes.FLOAT));
//		db.execute(sql, fpList);
//	}
	@SuppressWarnings("unchecked")
	public void addRefund(Map<String,Object> payment) throws DBException{
		String sql = "insert into payment(payAmount,sid,uid,payer,receiver,payDate,useBalance,cashOrCard,selectedClass,status,endDate,discount,selectedPayway)values(?,?,?,?,?,now(),?,?,?,-1,now(),1,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,payment.get("payAmount"),FieldTypes.FLOAT));
//		fpList.add(new FieldParameter(2,payment.getPurpose(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(3,payment.getDoesNeedReminder(),FieldTypes.BOOLEAN));
//		fpList.add(new FieldParameter(4,payment.getEndDate(),FieldTypes.DATE));
//		fpList.add(new FieldParameter(5,payment.getRemark(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(6,payment.getPayDate(),FieldTypes.DATE));
		fpList.add(new FieldParameter(2,payment.get("sid"),FieldTypes.INTEGER));
		fpList.add(new FieldParameter(3,payment.get("uid"),FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(9,payment.getPwayid(),FieldTypes.INTEGER));
//		fpList.add(new FieldParameter(10,payment.getStartDate(),FieldTypes.DATE));
		fpList.add(new FieldParameter(4,payment.get("payer"),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(5,payment.get("receiver"),FieldTypes.VARCHAR));
		
		fpList.add(new FieldParameter(6,payment.get("useBalance"),FieldTypes.FLOAT));//if this value large than 0, indicate that the balance save to the account
		fpList.add(new FieldParameter(7,payment.get("cashOrCard"),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(8,payment.get("selectedClass"),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(9,payment.get("selectedPayway"),FieldTypes.VARCHAR));
		
//		fpList.add(new FieldParameter(16,payment.getSelectedPayway(),FieldTypes.VARCHAR));
//		fpList.add(new FieldParameter(17,payment.getDiscount(),FieldTypes.FLOAT));
		db.execute(sql, fpList);
	}
	 public static void main(String[] args) throws DBException {
		PaymentDAO dao  = new PaymentDAO();
		List<Payment> l = dao.getRefoundList();
		if(l.isEmpty()){
			System.out.println(true);
		}else{
			System.out.println(l.size());
			for(Payment p : l){
				System.out.println(p);
			}
		}
	}

}

