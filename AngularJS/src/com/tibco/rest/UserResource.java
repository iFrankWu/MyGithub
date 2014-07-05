/*
 * UserResource.java
 * Mar 28, 2013
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Payment;
import com.tibco.bean.Result;
import com.tibco.bean.User;
import com.tibco.dao.PaymentDAO;
import com.tibco.service.UserService;
import com.tibco.util.Const;

/*
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
@Path("/user")
public class UserResource {
		@Context
	    HttpServletRequest request;
		
		private UserService userService = new UserService();
		private Logger logger = Logger.getLogger(this.getClass());
		
		private Boolean isAmin(){
			String userType = request.getSession().getAttribute(Const.CURRENT_USER_TYPE).toString();
			return Const.TYPE_NORMAL.equals(userType) ? false : true;
		}
	    
	    @POST
	    @Produces(MediaType.APPLICATION_JSON)
	    public Result login(User user) {
//	    	System.out.println("Recived Request 2:"+user.getUsername()+"/"+user.getPassword());
	    	try {
	    		return  userService.Login(user,request.getSession());
			} catch (DBException e) {
				String msg = "Fail to login,username : "+user.getUsername()+":"+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return new Result(false,msg);
			}
	    }
	    
	    @Path("{id}")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Result logout(@PathParam("id")Integer id) {
	    	request.getSession().invalidate();//直接销毁session
//	    	request.getSession().setAttribute(Const.LOGIN_SUCCESS, false);
	    	return new Result(true,"Logout success");
	    }
	    
	    @PUT
	    @Produces(MediaType.APPLICATION_JSON)
	    public Result addUser(User user) {
//	    	System.out.println("Recived Request 2:"+user.getUsername()+"/"+user.getPassword());
	    	if(!isAmin()){
	    		return new Result(false,"执行失败：此操作需要管理权限");
	    	}
	    	try {
	    		if(Const.TYPE_SUPER.equals(user.getType())){
	    			return new Result(false,"执行失败：超级管理员不能够由系统创建");
	    		}
				return userService.addUser(user);
			} catch (DBException e) {
				String msg = "Add user fail,username : "+user.getUsername()+":"+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return new Result(false,msg);
			}
	    }
	    @Path("{id}")
	    @PUT
	    @Produces(MediaType.APPLICATION_JSON)
	    public Result updateUser(@PathParam("id")Integer id,User user){
	    	try {
	    		
		    	boolean modifyPasswd = true;
	    		if(user.getPassword() == null || user.getPassword().trim().equals("")){
	    			modifyPasswd  = false;
	    		}
		    	if(((Integer)request.getSession().getAttribute(Const.CURRENT_USER_ID)).equals(user.getId()) ){
					return userService.updateUser(user,modifyPasswd);
				}
		    	if(!isAmin()){
		    		return new Result(false,"执行失败：此操作需要管理权限");
		    	}
	    	
	    		User old = userService.getUserById(user.getId());
	    	
	    		if(old.getType().equals(Const.TYPE_SUPER)){
	    			if(((Integer)request.getSession().getAttribute(Const.CURRENT_USER_ID)).equals(user.getId()) ){
	    				return userService.updateUser(user,modifyPasswd);
	    			}
	    			return new Result(false,"更新失败：不能更新超级用户");
	    		}
				return userService.updateUser(user,modifyPasswd);
			} catch (DBException e) {
				String msg = "Update user fail,username : "+user.getUsername()+":"+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return new Result(false,msg);
			}
	    }
	    
	    
	    @Path("{id}")
	    @DELETE
	    @Produces(MediaType.APPLICATION_JSON)
	    public Result removeUser(@PathParam("id")Integer id) {
	    	if(!isAmin()){
	    		return new Result(false,"删除失败：此操作需要管理权限");
	    	}
	    	try {
	    		if(request.getSession().getAttribute(Const.CURRENT_USER_ID).equals(id)){
	    			return new Result(false,"删除失败：不能够删除自身");
	    		}
	    		if(userService.getUserById(id).getType().equals(Const.TYPE_SUPER)){
	    			if(request.getSession().getAttribute(Const.CURRENT_USER_TYPE).equals(Const.TYPE_SUPER)){// super user can delete and update other user
	    				return userService.removeUser(id);
	    			}
	    			return new Result(false,"删除失败：不能删除超级用户");
	    		}
	 
				return userService.removeUser(id);
			} catch (DBException e) {
				String msg = "remove user fail, user id : "+id+":"+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return new Result(false,msg);
				
			}
	    }
	    
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<User> getUserList() {
	    	try {
				return userService.getUserList();
			} catch (DBException e) {
				String msg = "Get user list fail "+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return null;
			}
	    	
	    }
	    public static void main(String args[]) throws Exception{
//	    	UserResource suer = new UserResource();
//	    	List<User> s = suer.getUserList();
//	    	System.out.print(s.size());
	    	Const.initLogger();
	    	PaymentDAO dao = new PaymentDAO();
//	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
////	    	Date date1 = format.parse("2013-5-22");
////    		Date date2 = format.parse("2013-5-26");
//	    	String date1 = "2013-5-24";
//	    	String date2 = "2013-5-25";
//	    	for(Payment p : dao.getListByUserAndDateRang(1, date1, date2)){
//	    		System.out.println(p.getPurpose());
//	    	}
	    	
//	    	for(PayBean4Print pay :dao.getListByUserAndDateRang(2, null, null)){
//	    		System.out.println(pay.getPayer()+":"+pay.getPurpose()+":"+pay.getReceiver()+":"+pay.getPayAmount()+":"+pay.getId());
//	    	}
	    	for(Payment pay :dao.getListByStudent(1)){
	    		System.out.println(pay.getPayer()+":"+pay.getPurpose()+":"+pay.getReceiver()+":"+pay.getPayAmount()+":"+pay.getId());
	    	}
	    	
	    }
}

