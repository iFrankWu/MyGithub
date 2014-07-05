/*
 * StudentService.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONObject;

import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Result;
import com.tibco.bean.Student;
import com.tibco.dao.StudentDAO;
import com.tibco.handle.ExportStudentHandler;
import com.tibco.util.XLSExport;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class StudentService {
	private StudentDAO stuDAO = new StudentDAO();
	private CommunicationService commService = new CommunicationService(); 
	private PaymentService payService = new PaymentService(); 
	private ClassService classService = new ClassService();

	
	public Result addStudent(Integer uid,String username, Student student,HttpSession session) throws DBException{
		if(stuDAO.getStudentByNameAndTel(student.getName(),student.getMobilePhone())){
			return new Result(false,"学生,名字："+student.getName()+","+student.getMobilePhone()+"已存在！");
		}
		student.setCreateDate(new Date());
		Integer sid = stuDAO.addStudent(student);// for save, this id must needed.
//		System.out.println("Did get the correct sid :"+sid);
		student.setId(sid);
		payService.savePayment(uid,username,student);
		commService.saveCommunication(student,session);
		classService.saveClass4Student(student);
		return new Result(true,"");
	}
	
	public Result deleteStudent(Integer id) throws DBException{
		stuDAO.deleteStudent(id);
		return new Result(true,"");
	}
	
	public List<Student> getStudentList(Boolean onlyStuClass) throws DBException{
		return stuDAO.getAllStudents(onlyStuClass);
	}
	
	public Result updateStudent(Integer uid,String username,Student student,Boolean isNormal) throws DBException{
		student.setModifyDate(new Date());
		Integer sid = student.getId();
		stuDAO.updateStudent(sid,student);
		//根据需求，下面两个信息不能够修改 因此前台也没传数据给历史后台了
//		payService.savePayment(uid,username,student);
//		commService.saveCommunication(student,session);
		
		//开放修改权限给管理员
		commService.updateCommunication(student);
		payService.updatePayment(uid,username,student,isNormal);
		classService.updateClass4Stundet(student);
		return new Result(true,"");
	}
	
	public Student getStudent(Integer id) throws DBException{
		return stuDAO.getStudent(id);
	}
	public Result doesExistStuNameAndTel(String name,String tel) throws DBException{
		if(stuDAO.getStudentByNameAndTel(name, tel)){
			return new Result(true,String.format("学生名字 %s 联系电话 %s 已经存在", name,tel));
		}
		return new Result(false,"");
	}
	public List<Student> getByDateRang(Date date1,Date date2)throws DBException{
		return stuDAO.getStudentByDateRang(date1, date2);
	}
	public List<Student> advanceSearch(JSONObject json)throws DBException{
//		name:$scope.name,
//		 sex:$scope.sex,
//		 date1:$scope.age,
//		 date2:$scope.age2,
//    contact:$scope.contact,
//mobilePhone:$scope.mobilePhone,
//     status:$scope.status,
//   dataFrom:$scope.dataFrom,
//   doesBill:$scope.doesBill
		StringBuffer sql = new StringBuffer("select * from student where 1=1 ");
		addStringCondition(sql,"name",json);	
		addStringCondition(sql,"sex",json);	
		addStringCondition(sql,"contact",json);	
		addStringCondition(sql,"mobilePhone",json);	
		addStringCondition(sql,"status",json);	
		addStringCondition(sql,"dataFrom",json);	
		if(json.get("date1") != null ){
			sql.append(" and ");
			sql.append("age");
			sql.append(" between '");
			sql.append(json.get("date1"));
			sql.append("' and '");
			sql.append(json.get("date2"));
			sql.append("'");
		}
		if(json.get("doesBill") != null){
			 Boolean doesBill = (Boolean)json.get("doesBill");
			 sql.append(" and  doesBill = ");
			 if(doesBill){
				 sql.append("1");
			 }else{
				 sql.append("0");
			 }
		}
		System.out.println(sql.toString());
		return stuDAO.advanceSerach(sql.toString());
	}
	private void addStringCondition(StringBuffer sql,String columnName,JSONObject json){
		if(json.get(columnName) != null){
			sql.append(" and ");
			sql.append(columnName);
			sql.append(" like '%");
			sql.append(json.get(columnName));
			sql.append("%' ");
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAllSettingDiscounts(){
		final List discounts = new ArrayList(); 
		try {
			stuDAO.getAllSettingDiscounts(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try {
						Integer sid = rs.getInt("sid");
						Integer cid = rs.getInt("cid");
						float discount = rs.getFloat("discount");
						JSONObject dis = new JSONObject();
						dis.put("name", stuDAO.getStudent(sid).getName());
						dis.put("sid", sid);
						dis.put("className",classService.getClazz(cid, false).getClassName() );
						dis.put("cid", cid);
						dis.put("discount", discount);
						discounts.add(dis);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (DBException e) {
			e.printStackTrace();
		}
		return discounts;
	}
	public void setDiscount(int sid,int cid,float discount){
		try {
			stuDAO.setDiscount(sid, cid, discount);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	public void deleteDiscount(int sid,int cid){
		stuDAO.deleteDiscount(sid, cid);
	}
	public float getDiscount(int sid,int cid){
		return stuDAO.getDiscount(sid, cid);
	}
	
	public Map<String,Object> getNextPage(Integer minId,Integer size, Integer page, String sortColumn) throws DBException{
		List<Student> list = stuDAO.getNextPage(minId, size, page, sortColumn);
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("studentList", list);
		map.put("allRecordNumber", stuDAO.getAllRecordNumber());
		if(list != null && !list.isEmpty()){
			map.put("maxId", list.get(0).getId());
			map.put("minId",list.get(list.size()-1).getId());
		}
		return map;
	}
	
	public Map<String,Object> getPrePage(Integer maxId,Integer size, Integer page, String sortColumn) throws DBException{
		List<Student> list = stuDAO.getPrePage(maxId, size, page, sortColumn);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("studentList", list);
		map.put("allRecordNumber", stuDAO.getAllRecordNumber());
		if(list != null && !list.isEmpty()){
			map.put("maxId", list.get(0).getId());
			map.put("minId",list.get(list.size()-1).getId());
		}
		return map;
	}
	
	public Map<String,Object> getCurrentPage(Integer maxId,Integer size, Integer page, String sortColumn) throws DBException{
		List<Student> list = stuDAO.getCurrentPage(maxId, size, page, sortColumn);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("studentList", list);
		map.put("allRecordNumber", stuDAO.getAllRecordNumber());
		if(list != null && !list.isEmpty()){
			map.put("maxId", list.get(0).getId());
			map.put("minId",list.get(list.size()-1).getId());
		}
		return map;
	}
	public Map<String,Object> getTopPage(Integer size, String sortColumn) throws DBException{
		List<Student> list = stuDAO.getTopPage(size, sortColumn);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("studentList", list);
		map.put("allRecordNumber", stuDAO.getAllRecordNumber());
		if(list != null && !list.isEmpty()){
			map.put("maxId", list.get(0).getId());
			map.put("minId",list.get(list.size()-1).getId());
		}
		return map;
	}
	
	public Workbook exportDate(String startDate,String endDate,String userName, Boolean isAdmin) throws DBException{
	   	 XLSExport e  =   new  XLSExport();
		 stuDAO.exportData(startDate, endDate, userName,isAdmin, new ExportStudentHandler(e));
		 return e.getWorkBook();
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String args[]) throws DBException{
		StudentService s = new StudentService();
//		JSONObject json = new JSONObject();
//		json.put("name", "wsx");
//		json.put("doesBill", true);
//		json.put("date1", "1999-10-1");
//		json.put("date1", "2013-10-1");
//		json.put("status", "i dont know");
//		s.advanceSearch(json);
		List l = s.getStudentList(false);
		System.out.println(l.size());
	}
	
}

