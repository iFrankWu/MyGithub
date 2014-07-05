/*
 * DBUtil.java
 * Apr 2, 2013
 * com.tibco.db
 * TEATest
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Clazz;
import com.tibco.bean.Student;
import com.tibco.handle.StundentListHandle;
import com.tibco.util.DateUtil;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class StudentDAO {
	private ClazzDAO clazzDAO = new ClazzDAO();
	private PaymentDAO paymentDAO = new PaymentDAO();
	private CommunicateDAO commDAO = new CommunicateDAO();
	private ClassStudentDAO classStuDAO = new ClassStudentDAO();
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	
	
	public  List<Student> getAllStudents(Boolean onlyStuClass) throws DBException{
		String sql = "select * from student";
		List<Student> students = new ArrayList<Student>();
		StundentListHandle handle = new StundentListHandle(students,onlyStuClass,this);
		db.handleList(sql, handle);
		return handle.getStudents();
	}
	@SuppressWarnings("unchecked")
	public  Integer  addStudent(Student student) throws DBException{
		String getNextAutoIdSql = "select auto_increment from information_schema.`TABLES` where table_name='student'";
		Integer sid =  (Integer)db.queryFirst(Integer.class, getNextAutoIdSql);
		String sql = "insert into student(id,name,sex,age,address,contact,mobilePhone,email,telephone,dataFrom,status,doesBill,createDate,uid,qq,school,otherTelephone,accountBalance,remark) values(?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sid,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,student.getName(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,student.getSex(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(4,student.getAge(),FieldTypes.DATE));
		fpList.add(new FieldParameter(5,student.getAddress(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6,student.getContact(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(7,student.getMobilePhone(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(8,student.getEmail(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(9,student.getTelephone(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(10,student.getDataFrom(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(11,student.getStatus(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(12,student.getDoesBill(),FieldTypes.BOOLEAN));

		fpList.add(new FieldParameter(13,student.getUid(),FieldTypes.INTEGER));
		fpList.add(new FieldParameter(14,student.getQq(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(15,student.getSchool(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(16,student.getOtherTelephone(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(17, student.getAccountBalance(), FieldTypes.FLOAT));
		fpList.add(new FieldParameter(18,student.getRemark(),FieldTypes.VARCHAR));
		db.execute(sql, fpList);
		return sid;
	}
	@SuppressWarnings("unchecked")
	public  Student getStudent(Integer id) throws DBException{
		String sql = "select * from student where id=?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		return (Student) db.queryPrepareFirst(Student.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void deleteStudent(Integer id) throws DBException{
		String sql = "delete from student where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void updateStudent(Integer id,Student student) throws DBException{//UID can not be updated
		String sql = "update student set name = ?,sex = ?,age = ?,address = ?,contact = ?,mobilePhone = ?,email = ?,telephone = ?,dataFrom = ?,status = ?,doesBill = ?,modifyDate =now(),qq=?,school=? ,otherTelephone=?, accountBalance = ? ,remark = ? where (id = ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,student.getName(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,student.getSex(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,student.getAge(),FieldTypes.DATE));
		fpList.add(new FieldParameter(4,student.getAddress(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(5,student.getContact(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6,student.getMobilePhone(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(7,student.getEmail(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(8,student.getTelephone(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(9,student.getDataFrom(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(10,student.getStatus(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(11,student.getDoesBill(),FieldTypes.BOOLEAN));
		
		fpList.add(new FieldParameter(12,student.getQq(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(13,student.getSchool(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(14,student.getOtherTelephone(),FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(15, student.getAccountBalance(), FieldTypes.FLOAT));
		fpList.add(new FieldParameter(16,student.getRemark(),FieldTypes.VARCHAR));
		
		fpList.add(new FieldParameter(17,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void updateStudentUseBalance(Integer id,float accountBalance) throws DBException{
		String sql = "update student set modifyDate =now(), accountBalance = accountBalance+ ?  where (id = ?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, accountBalance, FieldTypes.FLOAT));
		fpList.add(new FieldParameter(2,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public boolean getStudentByNameAndTel(String name,String mobilePhone) throws DBException{
		String sql = "select count(*) from student where name = ? and mobilePhone = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,name,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,mobilePhone,FieldTypes.VARCHAR));
		Integer count = (Integer)db.queryPrepareFirst(Integer.class, sql, fpList);
		return count > 0 ? true: false;
	}
	public List<Student> getStudentByDateRang(Date date1,Date date2) throws DBException{
		String sql = "  select * from student where age BETWEEN ? and  ?";
		final List<Student> students = new ArrayList<Student>();
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,date1,FieldTypes.DATE));
		fpList.add(new FieldParameter(2,date2,FieldTypes.DATE));
		db.handleList(sql, new ResultSetHandler(){

			@Override
			public void handle(ResultSet rs) {
				try {
					Integer sid = rs.getInt("id");
					Student stu = getStudent(sid);
					stu.setId(sid);
					stu.setPaymentHistorys(paymentDAO.getList(sid));
					stu.setCommunications(commDAO.getList(sid));
//					stu.setClazzes(clazzDAO.getList(false));
					final List<Clazz> clazzes = new ArrayList<Clazz>();
					classStuDAO.getClassesByStudent(sid, new ResultSetHandler(){

						@Override
						public void handle(ResultSet rs2) {
							try{
								Clazz clazz = clazzDAO.getClazz(rs2.getInt("cid"), false);
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
		});
		return students;
	}
	
	public List<Student> advanceSerach(String sql) throws DBException{
		final List<Student> students = new ArrayList<Student>();
		db.handleList(sql, new ResultSetHandler(){

			@Override
			public void handle(ResultSet rs) {
				try {
					Integer sid = rs.getInt("id");
					Student stu = getStudent(sid);
					stu.setId(sid);
					stu.setPaymentHistorys(paymentDAO.getList(sid));
					stu.setCommunications(commDAO.getList(sid));
//					stu.setClazzes(clazzDAO.getList(false));
					final List<Clazz> clazzes = new ArrayList<Clazz>();
					classStuDAO.getClassesByStudent(sid, new ResultSetHandler(){

						@Override
						public void handle(ResultSet rs2) {
							try{
								Clazz clazz = clazzDAO.getClazz(rs2.getInt("cid"), false);
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
		});
		return students;
	}
	public void getAllSettingDiscounts(ResultSetHandler handle) throws DBException{
		classStuDAO.getList(handle);
	}
	public void setDiscount(Integer sid,Integer cid,Float discount) throws DBException{
		classStuDAO.addDiscount(sid, cid, discount);
	}
	public void deleteDiscount(Integer sid,Integer cid){
		try {
			classStuDAO.removeDiscount(sid, cid);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	public float getDiscount(Integer sid,Integer cid){
		try {
			return classStuDAO.getDiscount(sid, cid);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * handle page
	 * */
	/**
	 *  以当前最小id起 ，向后得第page页的数据
	 *  @param minId	最小Id值
	 *  @param size		每页大小
	 *  @param page		第几页  与当前相差，比如当前在第三页，那么查找第四页 传入page值为1 就可以
	 *  @param sortColumn	
	 *  @return
	 *  @throws DBException
	 *	List<Student>
	 *select * from student where id=?
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getNextPage(Integer minId,Integer size, Integer page, String sortColumn) throws DBException{
		String sql = "select * from student where id < ? order by ? DESC limit ?,?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,minId,FieldTypes.INTEGER));
		if(sortColumn == null){
			sortColumn = "id";
		}
		fpList.add(new FieldParameter(2,sortColumn,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,size*(page-1),FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,size,FieldTypes.INTEGER));
		List<Student> students = new ArrayList<Student>();
		StundentListHandle handle = new StundentListHandle(students,false,this);
		db.handleList(sql, fpList, handle);
		return handle.getStudents();
		
//		return (List<Student>)db.queryPrepareList(Student.class, sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public List<Student> getPrePage(Integer maxId,Integer size, Integer page,String sortColumn)throws DBException{
		String sql = "select * from (select * from student where id >  ? order by ? ASC limit ?,?) as a order by ? DESC";
		if(sortColumn == null){
			sortColumn = "id";
		}
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,maxId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,sortColumn,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,size*(page-1),FieldTypes.INTEGER));
		fpList.add(new FieldParameter(4,size,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(5,sortColumn,FieldTypes.VARCHAR));
//		return (List<Student>)db.queryPrepareList(Student.class, sql, fpList);
		List<Student> students = new ArrayList<Student>();
		StundentListHandle handle = new StundentListHandle(students,false,this);
		db.handleList(sql, fpList, handle);
		return handle.getStudents();
	}
	/**
	 * the max studentID
	 * */
	@SuppressWarnings("unchecked")
	public List<Student> getCurrentPage(Integer maxId,Integer size, Integer page,String sortColumn)throws DBException{
		String sql = "select * from student where id <= ? order by ? DESC limit 0,?";
		if(sortColumn == null){
			sortColumn = "id";
		}
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,maxId,FieldTypes.INTEGER));
		fpList.add(new FieldParameter(2,sortColumn,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3,size,FieldTypes.INTEGER));
//		return (List<Student>)db.queryPrepareList(Student.class, sql, fpList);
		List<Student> students = new ArrayList<Student>();
		StundentListHandle handle = new StundentListHandle(students,false,this);
		db.handleList(sql, fpList, handle);
		return handle.getStudents();
	}
	/**
	 * the max studentID
	 * */
	@SuppressWarnings("unchecked")
	public List<Student> getTopPage(Integer size, String sortColumn)throws DBException{
		String sql = "select * from student order by ? desc limit 0,?";
		if(sortColumn == null){
			sortColumn = "id";
		}
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,sortColumn,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,size,FieldTypes.INTEGER));
//		return (List<Student>)db.queryPrepareList(Student.class, sql, fpList);
		List<Student> students = new ArrayList<Student>();
		StundentListHandle handle = new StundentListHandle(students,false,this);
		db.handleList(sql, fpList, handle);
		return handle.getStudents();
	}
	
	@SuppressWarnings("unchecked")
	public Integer getAllRecordNumber() throws DBException{
		String sql = "select count(*) from student";
		return (Integer)db.queryFirst(Integer.class, sql);
	}
	public void exportData(String startDate,String endDate,String userName,boolean isAdmin,ResultSetHandler handler) throws DBException{
		String sql = "select student.*,user.username from student, user where user.id = student.uid  ";
		if(userName != null){
			 sql += " and user.username = '"+userName+"'";
		}
		if(startDate != null){
			sql += " and student.createDate >= '"+startDate+"'";
		}
		
		if(endDate != null){
			sql += " and student.createDate < '"+DateUtil.getNextDay(endDate)+"'";
		}
		System.out.println(sql);
		db.handleList(sql, handler);
	}
	
}

