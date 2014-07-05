/*
 * PaymentSerivce.java
 * Apr 13, 2013
 * com.tibco.service
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Payment;
import com.tibco.bean.Student;
import com.tibco.dao.PaymentDAO;
import com.tibco.dao.StudentDAO;
import com.tibco.util.Const;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class PaymentService {
	private Logger logger = Logger.getLogger(PaymentService.class);
	private StudentDAO stuDAO = new StudentDAO();
	private PaymentDAO payDAO = new PaymentDAO();
	public void savePayment(Integer uid,String receiver,Student student) throws DBException{
		Integer sid = student.getId();
		if(student.getPaymentHistorys() != null){
			for(Payment payment : student.getPaymentHistorys()){
				payment.setUid(uid);
				payment.setPayer(student.getName());
				payment.setReceiver(receiver);
				if(payment.getId() == null){
					payDAO.save(sid, payment);
				}else{
					payDAO.updatePayment(payment);
				}
			}
		}
	}
	public void updatePayment(Integer uid,String receiver,Student student,Boolean isNormal) throws DBException{
		Integer sid = student.getId();
		List<Payment> payments = payDAO.getList(sid);
		List<Integer> restPayIds = new ArrayList<Integer>();
		if(student.getPaymentHistorys() != null){
			for(Payment payment : student.getPaymentHistorys()){
				payment.setUid(uid);
				payment.setPayer(student.getName());
				payment.setReceiver(receiver);
				if(payment.getId() == null){
					payDAO.save(sid, payment);
					if(payment.getUseBalance() > 0){
						stuDAO.updateStudentUseBalance(sid, (0-payment.getUseBalance()));
					}
				}else{
					payDAO.updatePayment(payment);
					restPayIds.add(payment.getId());
				}
			}
		}
		for(Payment pay : payments){
			if(!restPayIds.contains(pay.getId())){
				if(!isNormal || pay.getUid().equals(uid)){
					payDAO.delete(pay.getId());
					logger.info("付款信息被删除：用户(Id:"+uid+")删除付款信息(paymentId:"+pay.getId()+")，若有疑问请及时联系该用户。");
				}else{
					logger.warn("该用户(Id:"+uid+")不能够删除付款信息(paymentId:"+pay.getId()+")，因为权限不够！");
				}
			}
		}
	}
	public List<Payment> getPaymentListByUserAndDateRang(Integer uid,String date1,String date2) throws DBException{
		return payDAO.getListByUserAndDateRang(uid, date1, date2);
	}
	public List<Payment> getRefundList() throws DBException{
		return payDAO.getRefoundList();
	}
	public List<Payment> getRefundListByUser(Integer uid) throws DBException{
		return payDAO.getRefoundList(uid);
	}
	public void addRefund(Map<String,Object> payment) throws DBException{
		payDAO.addRefund(payment);
		if(payment.get("cashOrCard").equals(Const.PAYWAY_REFUND)){
			stuDAO.updateStudentUseBalance(Integer.parseInt(payment.get("sid").toString()), Float.parseFloat(payment.get("payAmount").toString()));
			logger.info("增加退款信息，退款方式："+Const.PAYWAY_REFUND+",因此学生账户余额会增加:"+payment.get("payAmount"));
		}
	}
	public void deleteRefund(Integer payId) throws DBException{
		Payment payment = payDAO.getPayment(payId);
		payDAO.delete(payId);
		if(payment.getCashOrCard().equals(Const.PAYWAY_REFUND) && payment.getUseBalance() > 0){
			stuDAO.updateStudentUseBalance(payment.getSid(), (0-payment.getUseBalance()));
			logger.info("删除退款信息，退款方式："+payment.getCashOrCard()+",因此学生账户余额会减少:"+payment.getUseBalance());
		}
	}
		
		
	public Payment getPaymentById(Integer payId) throws DBException{
		return payDAO.getPayment(payId);
	}
	 
}

