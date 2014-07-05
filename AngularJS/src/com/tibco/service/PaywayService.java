/*
 * PaywayService.java
 * May 13, 2013
 * com.tibco.service
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.service;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.exception.DBException;
import com.tibco.bean.Clazz;
import com.tibco.bean.Payway;
import com.tibco.dao.PaywayDAO;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class PaywayService {
	private PaywayDAO paywayDAO = new PaywayDAO();
	public void updatePayment(Clazz clazz) throws DBException{
		Integer cid = clazz.getId();
		List<Payway> payways = paywayDAO.getPaywaysByClass(cid);
		List<Integer> restPayIds = new ArrayList<Integer>();
		if(clazz.getPayways() != null){
			for(Payway payway : clazz.getPayways() ){
				if(payway.getId() == null){
					savePayway(cid,payway);
				}else{
					paywayDAO.updatePayway(payway);
					restPayIds.add(payway.getId());
				}
			}
		}
		for(Payway pay : payways){
			if(!restPayIds.contains(pay.getId())){
				paywayDAO.delete(pay.getId());
			}
		}
	}
	public void savePayway(Integer cid,Payway payway) throws DBException{
		payway.setCid(cid);
		paywayDAO.addPayway(payway);
	}
}

