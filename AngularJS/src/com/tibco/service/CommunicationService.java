/*
 * CommunicationService.java
 * Apr 13, 2013
 * com.tibco.service
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;

import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Communication;
import com.tibco.bean.Student;
import com.tibco.dao.CommunicateDAO;
import com.tibco.util.Const;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class CommunicationService {
	private CommunicateDAO commDAO = new CommunicateDAO();
	public void saveCommunication(Student student,HttpSession session) throws DBException{
		Integer sid = student.getId();
		if(student.getCommunications() != null){
			for(Communication communication : student.getCommunications())
				if(communication != null){
					communication.setUid(Integer.parseInt(session.getAttribute(Const.CURRENT_USER_ID).toString()));
					communication.setUsername(session.getAttribute(Const.CURRENT_USER_NAME).toString());
					commDAO.save(sid, communication);
				}
		}
	}
	
	public void saveCommunication(JSONObject para,HttpSession session) throws DBException, ParseException, JSONException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date d = format.parse((String)para.get("commDate"));
		Communication communication = new Communication();
		communication.setCommunicationDate(d);
		communication.setContent((String)para.get("content"));
		Integer sid = (Integer)para.get("sid");
		communication.setSid(sid);
		communication.setUid(Integer.parseInt(session.getAttribute(Const.CURRENT_USER_ID).toString()));
		communication.setUsername(session.getAttribute(Const.CURRENT_USER_NAME).toString());
		commDAO.save(sid, communication);
	}
	/**
	 *  TODO update communication, include delete and update
	 *  @param student
	 *  @throws DBException
	 *	void
	 */
	public void updateCommunication(Student student) throws DBException{
		Integer sid = student.getId();
		List<Communication> comms = commDAO.getList(sid);
		List<Integer> restCommIds = new ArrayList<Integer>();
		if(student.getCommunications() != null){
			for(Communication communication : student.getCommunications()){
				if(communication.getId()!= null){
					commDAO.updateCommunication(communication);
					restCommIds.add(communication.getId());
				}else{
					commDAO.save(sid, communication);// add a new record , it's easy
				}
			}
		}
		for(Communication c : comms){
			if(!restCommIds.contains(c.getId())){//if the comm id not in rest comm,delete it from db
				commDAO.delete(c.getId());
			}
		}
	}
}

