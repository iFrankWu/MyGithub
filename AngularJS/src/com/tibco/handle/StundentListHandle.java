/*
 * StundentListHandle.java
 * May 23, 2013
 * com.tibco.handle
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.handle;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.ResultSetHandler;
import com.tibco.bean.Clazz;
import com.tibco.bean.Payway;
import com.tibco.bean.Student;
import com.tibco.dao.ClassStudentDAO;
import com.tibco.dao.ClazzDAO;
import com.tibco.dao.CommunicateDAO;
import com.tibco.dao.PaymentDAO;
import com.tibco.dao.PaywayDAO;
import com.tibco.dao.StudentDAO;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class StundentListHandle implements ResultSetHandler{
	private ClazzDAO clazzDAO = new ClazzDAO();
	private PaymentDAO paymentDAO = new PaymentDAO();
	private CommunicateDAO commDAO = new CommunicateDAO();
	private ClassStudentDAO classStuDAO = new ClassStudentDAO();
	private PaywayDAO paywayDAO = new PaywayDAO();
	private StudentDAO stuDAO;
	private List<Student> students;
	private Boolean onlyStuClass;
	public StundentListHandle(List<Student> students,Boolean onlyStuClass,StudentDAO stuDAO){
		this.students = students;
		this.onlyStuClass = onlyStuClass;
		this.stuDAO = stuDAO;
	}
	public List<Student> getStudents(){
		return students;
	}
	@Override
	public void handle(ResultSet rs) { 

		try {
			Integer sid = rs.getInt("id");
			Student stu = stuDAO.getStudent(sid);
			stu.setId(sid);
//			stu.setPaymentHistorys(paymentDAO.getList(sid));
			if(!onlyStuClass){
				stu.setPaymentHistorys(paymentDAO.getListByStudent(sid));
				stu.setCommunications(commDAO.getList(sid));
			}
//			stu.setClazzes(clazzDAO.getList(false));
			final List<Clazz> clazzes = new ArrayList<Clazz>();
			classStuDAO.getClassesByStudent(sid, new ResultSetHandler(){

				@Override
				public void handle(ResultSet rs2) {
					try{
						final Clazz clazz = clazzDAO.getClazz(rs2.getInt("cid"), false);
						//here need return class payway info
						paywayDAO.getPaywayList(rs2.getInt("cid"), new ResultSetHandler(){

							@Override
							public void handle(ResultSet rs3) {
								try{
									Payway p = paywayDAO.getPayway(rs3.getInt("id"));
									p.setId(rs3.getInt("id"));
									clazz.getPayways().add(p);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						});
						clazz.setId(rs2.getInt("cid"));
						clazzes.add(clazz);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			});
			stu.setClazzes(clazzes);
			students.add(stu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}

