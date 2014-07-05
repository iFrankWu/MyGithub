/*
 * UserService.java
 * Apr 13, 2013
 * com.tibco.service
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Result;
import com.tibco.bean.User;
import com.tibco.dao.UserDAO;
import com.tibco.util.Const;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class UserService {
	private UserDAO userDAO = new UserDAO();
	public Result Login(User user,HttpSession session) throws DBException{
		if(userDAO.existUsename(user.getUsername())){
			if(user.getPassword()!= null){
				User u = userDAO.getUserByNameAndPasswd(user.getUsername(), user.getPassword());
				if(u != null){
					if(Const.STATUS_USEABLE.equals(u.getStatus())){
						session.setAttribute(Const.LOGIN_SUCCESS, true);
						session.setAttribute(Const.CURRENT_USER_TYPE, u.getType());
						session.setAttribute(Const.CURRENT_USER_ID, u.getId());
						session.setAttribute(Const.CURRENT_USER_NAME, u.getUsername());
						u.setPassword(null);
						return new Result(true,u);
					}
					return new Result(false,"该用户当前状态为禁用状态，不可以登录，如有请联系管理员。");
				}
				return new Result(false,"密码错误。");
			}
	    	return new Result(false,"密码不能为空");
    	}
		return new Result(false,"该用户不存在，用户名：" +user.getUsername());
	}
	public List<User> getUserList() throws DBException{
		return userDAO.getUserList();
	}
	
	public Result addUser(User user) throws DBException{
		if(userDAO.existUsename(user.getUsername())){
			return new Result(false,"用户名:"+user.getUsername()+" 已存在。");
		}
		user.setModifyDate(new Date());
		userDAO.addUser(user);
		return new Result(true,"");
	}
	public Result removeUser(Integer id) throws DBException{
		userDAO.deleteUser(id);
		return new Result(true,"");
	}
	public Result updateUser(User user,boolean  modifyPasswd) throws DBException{
		userDAO.updateUser(user,modifyPasswd);
		return new Result(true,"");
	}
	
	public User getUserById(Integer id) throws DBException{
		return userDAO.getUser(id);
	}
}

