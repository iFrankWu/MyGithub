/*
 * StudentResource.java
 * Mar 31, 2013
 * org.timecontrol.rest
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.rest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Result;
import com.tibco.bean.Student;
import com.tibco.service.StudentService;
import com.tibco.util.Const;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
@Path("/student")
public class StudentResource {
	private Logger logger = Logger.getLogger(this.getClass());
	private StudentService studentService = new StudentService();
	@Context
    HttpServletRequest request;
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Result addStudent(Student student){
		try {
			if(student.getUid()==null){
				return new Result(false,"传入参数有误，请退出重新登录后尝试。");
			}
			return studentService.addStudent((Integer)request.getSession().getAttribute(Const.CURRENT_USER_ID),(String)request.getSession().getAttribute(Const.CURRENT_USER_NAME),student,request.getSession());
		} catch (DBException e) {
			String msg = "增加学生失败，学生名: "+student.getName()+"，错误原因: "+e.getMessage();
			logger.error(msg);
			e.printStackTrace();
			return new Result(false,msg);
		}
	}
	@Path("{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Result updateStudent(@PathParam("id")Integer id,Student student){
		try {
			if(student.getUid()==null){
				return new Result(false,"传入参数有误，请退出重新登录后尝试。");
			}
			Integer uid = (Integer)request.getSession().getAttribute(Const.CURRENT_USER_ID);
			Boolean isNormal = request.getSession().getAttribute(Const.CURRENT_USER_TYPE).equals(Const.TYPE_NORMAL);
			
			if(student.getUid().equals(request.getSession().getAttribute(Const.CURRENT_USER_ID)) || !request.getSession().getAttribute(Const.CURRENT_USER_TYPE).equals(Const.TYPE_NORMAL))
				return studentService.updateStudent(uid,(String)request.getSession().getAttribute(Const.CURRENT_USER_NAME),student,isNormal);
			else{
				Student old = studentService.getStudent(id);
				if(old.getName().equals(student.getName()) && old.getMobilePhone().equals(student.getMobilePhone())){//can't change name and mobile phone
					return studentService.updateStudent(uid,(String)request.getSession().getAttribute(Const.CURRENT_USER_NAME),student,isNormal);
				}
				return new Result(false,"没有权限编辑更新该学生信息。 ");
			}
		} catch (DBException e) {
			String msg = "更新学生失败，学生ID: "+student.getId()+"，错误原因： "+e.getMessage();
			logger.error(msg);
			e.printStackTrace();
			return new Result(false,msg);
			
		}
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("id")Integer id){
		try {
			return studentService.getStudent(id);
		} catch (DBException e) {
			String msg = "获取学生信息失败，学生ID: "+id+"错误原因: "+e.getMessage();
			logger.error(msg);
			e.printStackTrace();
			return null;
		}
	}
	
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Result deleteStudent(@PathParam("id")Integer id){
		try {
			if(!request.getSession().getAttribute(Const.CURRENT_USER_TYPE).equals(Const.TYPE_NORMAL) || studentService.getStudent(id).getUid().equals(request.getSession().getAttribute(Const.CURRENT_USER_ID)) )
				return studentService.deleteStudent(id);
			else{
				return new Result(false,"没有权限删除该学生信息 ");
			}
		} catch (DBException e) {
			String msg = "删除学生失败，学生ID: "+id+"错误原因: "+e.getMessage();
			logger.error(msg);
			e.printStackTrace();
			return new Result(false,msg);
			
		}
	}
	@Path("{id}/{onlyStuClass}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Student> getStudentList(@PathParam("onlyStuClass") String onlyStuClass){
		try {
			Boolean onlyStuClassBoolean = Boolean.parseBoolean(onlyStuClass);			
			return studentService.getStudentList(onlyStuClassBoolean);
		} catch (DBException e) {
			String msg = "获取学生列表失败，错误原因： "+e.getMessage();
			logger.error(msg);
			e.printStackTrace();
			return null;
		}
	}
    
	 @Path("{id}/{onlyStuClass}/Next/{size}/{page}/{sortColumn}")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Map<String,Object> getNextPage(@PathParam("id")Integer maxId,@PathParam("size")Integer size,@PathParam("page")Integer page,@PathParam("sortColumn")String sortColumn){
	    	try{
	    		if(maxId == 1){// since angularJS will not send the int value zero.
	    			maxId = 0;
	    		}
//	    		studentService.addLogRecord(request,"获取下一页报告单",maxId+""+size+""+page+""+sortColumn);
	    		return studentService.getNextPage(maxId, size, page, sortColumn);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	    }
	    @Path("{id}/{onlyStuClass}/Pre/{size}/{page}/{sortColumn}")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Map<String,Object> getPrePage(@PathParam("id")Integer minId,@PathParam("size")Integer size,@PathParam("page")Integer page,@PathParam("sortColumn")String sortColumn){
	    	try{
//	    		studentService.addLogRecord(request,"获取上一页报告单",minId+""+size+""+page+""+sortColumn);
	    		return studentService.getPrePage(minId, size, page, sortColumn);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	    }
	    
	    @Path("{id}/{onlyStuClass}/Current/{size}/{page}/{sortColumn}")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Map<String,Object> getCurrentPage(@PathParam("id")Integer minId,@PathParam("size")Integer size,@PathParam("page")Integer page,@PathParam("sortColumn")String sortColumn){
	    	try{
//	    		if(minId == 1){// since angularJS will not send the int value zero.
//	    			minId = 0;
//	    		}
//	    		studentService.addLogRecord(request,"获取当前页报告单",minId+""+size+""+page+""+sortColumn);
	    		return studentService.getCurrentPage(minId, size, page, sortColumn);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	    }
	    
	    @Path("{id}/{onlyStuClass}/Top/{size}/{page}/{sortColumn}")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Map<String,Object> getTopPage(@PathParam("id")Integer minId,@PathParam("size")Integer size,@PathParam("page")Integer page,@PathParam("sortColumn")String sortColumn){
	    	try{
//	    		studentService.addLogRecord(request,"获得第一页报告单",minId+""+size+""+page+""+sortColumn);
	    		return studentService.getTopPage(size, sortColumn);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	    }
//    @GET
//    @Path("{id}/{type}/")
//    @Produces(MediaType.APPLICATION_JSON)
//	public List<Student> getStudentList(){
//		try {
//			return studentService.getStudentList();
//		} catch (DBException e) {
//			String msg = "Get student list fail "+e.getMessage();
//			logger.error(msg);
//			e.printStackTrace();
//			return null;
//		}
//	}
    
 	public  static void main(String []args){
 		StudentResource resource = new StudentResource();
 		Student student = new Student();
 		student.setName("FFFFF");
// 		student.setSex("male");
// 		resource.addStudent(student);
 		System.out.println(resource.getStudentList("false"));
 	}
}

