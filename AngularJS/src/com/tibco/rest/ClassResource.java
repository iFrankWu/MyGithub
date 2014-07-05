/*
 * ClassResource.java
 * Mar 31, 2013
 * org.timecontrol.rest
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.rest;

import java.util.List;

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
import com.tibco.bean.Clazz;
import com.tibco.bean.Result;
import com.tibco.service.ClassService;
import com.tibco.util.Const;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
@Path("/clazz")
public class ClassResource {
		@Context
	    HttpServletRequest request;
		private Logger logger = Logger.getLogger(this.getClass());
		private ClassService classService = new ClassService();
		
		@Path("{id}")
		@GET
		@Produces
		public Clazz getClazzDetails(@PathParam("id")Integer id){
			try {
				return classService.getClazz(id,true);
			} catch (DBException e) {
				String msg = "获取班级详细信息失败，班级ID： "+id+"，错误原因: "+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return null;
			}
		}
		
		@Path("{id}/{withStudent}")
	 	@GET
	    @Produces(MediaType.APPLICATION_JSON)
		public List<Clazz> getClassList(@PathParam("id") Integer id , @PathParam("withStudent")boolean withStudent){
			try {
				return classService.getClassList(withStudent);
			} catch (DBException e) {
				String msg = "获取班级列表失败 : "+id+"，错误原因： "+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return null;
			}
		}
	    
	    @PUT
	    @Produces(MediaType.APPLICATION_JSON)
	    public Result addClazz(Clazz clazz) {
	    	try {
	    		if(Const.TYPE_NORMAL.equals(request.getSession().getAttribute(Const.CURRENT_USER_TYPE))){
	    			return new Result(false,"该操作需要管理员权限，请联系管理员。");
	    		}
				return classService.addClazz(clazz);
			} catch (DBException e) {
				String msg = "增加班级失败，班级名： "+clazz.getClassName()+"，错误原因: "+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return null;
			}
	    }
	    @Path("{id}")
	    @PUT
	    @Produces(MediaType.APPLICATION_JSON)
	    public Result updateClazz(@PathParam("id")Integer id,Clazz clazz){
	    	try {
	    		if(Const.TYPE_NORMAL.equals(request.getSession().getAttribute(Const.CURRENT_USER_TYPE))){
	    			return new Result(false,"该操作需要管理员权限，请联系管理员。");
	    		}
				return classService.updateClazz( clazz);
			} catch (DBException e) {
				String msg = "更新班级失败，班级ID： "+id+"，错误原因: "+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return new Result(false,msg);
				
			}
	    }
	    
	    
	    @Path("{id}")
	    @DELETE
	    @Produces(MediaType.APPLICATION_JSON)
	    public Result deleteClazz(@PathParam("id")Integer id) {
	    	try {
	    		if(Const.TYPE_NORMAL.equals(request.getSession().getAttribute(Const.CURRENT_USER_TYPE))){
	    			return new Result(false,"该操作需要管理员权限，请联系管理员。");
	    		}
				return classService.deletClazz(id);
			} catch (DBException e) {
				String msg = "删除班级失败，班级ID: "+id+"，错误原因: "+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return new Result(false,msg);
			}
	    }
	    
	 	public  static void main(String []args){
	 		ClassResource resource = new ClassResource();
//	 		System.out.println(resource.getClassList(null,false));
	 		System.out.println(resource.getClazzDetails(2));
	 	}
}

