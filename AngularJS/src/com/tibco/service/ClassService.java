/*
 * ClassService.java
 * Apr 13, 2013
 * com.tibco.service
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Clazz;
import com.tibco.bean.Payway;
import com.tibco.bean.Result;
import com.tibco.bean.Student;
import com.tibco.dao.ClassStudentDAO;
import com.tibco.dao.ClazzDAO;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class ClassService {
	private ClazzDAO classDAO = new ClazzDAO();
	private ClassStudentDAO classStuDAO = new ClassStudentDAO();
	private PaywayService paywayService = new PaywayService();
	public List<Clazz> getClassList(boolean withStudents) throws DBException{
		return classDAO.getList(withStudents);
	}
	
	public Result addClazz(Clazz clazz) throws DBException{
		if(classDAO.doesExistClassName(clazz.getClassName())){
			return new Result(false,"班级名已经存在，班级名："+clazz.getClassName());
		}
		clazz.setModifyDate(new Date());
		Integer cid = classDAO.save(clazz);
		if(clazz.getStudents()!=null){
			for(Student stu : clazz.getStudents()){
				if(stu== null || stu.getId() ==null){
					
				}else{
					classStuDAO.add(cid, stu.getId());
				}
			}
		}
		if(clazz.getPayways() != null){//save the payment
			for(Payway payway : clazz.getPayways()){
				paywayService.savePayway(cid, payway);
			}
		}
		return new Result(true,"");
	}
	public Result deletClazz(Integer id) throws DBException{
		classDAO.delete(id);
		return new Result(true,"");
	}
	public Result updateClazz(Clazz clazz) throws DBException{
		clazz.setModifyDate(new Date());
		Integer cid = clazz.getId();
		classDAO.updateClazz(clazz.getId(), clazz);
		if(clazz.getStudents()!=null){
			for(Student stu : clazz.getStudents()){
				if(stu != null)
					classStuDAO.add(cid, stu.getId());
			}
		}
		paywayService.updatePayment(clazz);
		return new Result(true,"");
	}
	public Clazz getClazz(Integer id,boolean withStudents) throws DBException{
		return classDAO.getClazz(id,withStudents);
	}
	public void saveClass4Student(Student student)throws DBException{
		Integer sid = student.getId();
		if(student.getClazzes() != null){
			for(Clazz clazz : student.getClazzes()){
				if(clazz != null)
					classStuDAO.add(clazz.getId(), sid);
			}
		}
	}
	public void updateClass4Stundet(Student student)throws DBException{
		Integer sid = student.getId();
		final List<Integer> originalClassList = new ArrayList<Integer>();
		classStuDAO.getClassesByStudent(sid, new ResultSetHandler(){

			@Override
			public void handle(ResultSet rs) {
				try{
					originalClassList.add(rs.getInt("cid"));
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
		});
		List<Integer> restClassIds = new ArrayList<Integer>();
		if(student.getClazzes() != null){
			for(Clazz clazz : student.getClazzes()){
				if(clazz != null){
					if(originalClassList.contains(clazz.getId())){
						restClassIds.add(clazz.getId());
					}else{
						classStuDAO.add(clazz.getId(), sid);
					}
				}
			}
		}
		for(Integer cid : originalClassList){
			if(!restClassIds.contains(cid)){
				classStuDAO.remove(cid, sid);
			}
		}
	}
}

