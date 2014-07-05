/*
 * CommonResource.java
 * May 21, 2013
 * com.tibco.rest
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONObject;

import com.tibco.bean.Payment;
import com.tibco.bean.Result;
import com.tibco.service.CommunicationService;
import com.tibco.service.PaymentService;
import com.tibco.service.StudentService;
import com.tibco.util.Const;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
@Path("/comm")
public class CommonResource {
	private StudentService studentSerivce = new StudentService();
	private PaymentService paymentService = new PaymentService();
	private CommunicationService commService = new CommunicationService();
	private Logger logger = Logger.getLogger(this.getClass());
		
		@Context
		 HttpServletRequest request;
	
	 
	    @POST
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object HandlePost(JSONObject para) {
//	    	System.out.println(para);
	    	if(para == null ||  para.get("action") == null ){
	    		return null;
	    	}
	    	String action =  (String)para.get("action");
	    	try{
	    		if(Const.ACTION_SearchStudent.equals(action)){
		    		return studentSerivce.advanceSearch(para);
		    	}else if(Const.ACTION_SerachPaymentByUser.equals(action)){
		    		Integer uid = (Integer)request.getSession().getAttribute(Const.CURRENT_USER_ID);
		    		return paymentService.getPaymentListByUserAndDateRang(uid, (String)para.get("date1"), (String)para.get("date2"));
		    	}else if(Const.ACTION_AddCommunicate.equals(action)){
		    		try{
		    			commService.saveCommunication(para,request.getSession());
		    		}catch(Exception e){
		    			e.printStackTrace();
		    			return new String[]{"Error:"+e.getMessage()};
		    		}
		    		return new String[]{};
		    	}else if(Const.ACTION_GetSettingDiscount.equals(action)){
		    		return studentSerivce.getAllSettingDiscounts();
		    	}else if(Const.ACTION_GetAllRefunds.equals(action)){
		    		if(Const.TYPE_NORMAL.equals(request.getSession().getAttribute(Const.CURRENT_USER_TYPE))){
		    			Integer uid = (Integer)request.getSession().getAttribute(Const.CURRENT_USER_ID);
		    			return paymentService.getRefundListByUser(uid);
		    		}else{//admin 
		    			return paymentService.getRefundList();
		    		}
		    	}
	    	}catch(Exception e){
	    		String msg = "Add student fail, student name : "+para+":"+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return new Result(false,msg);
	    	}
	     	return null;
	    }
	    @SuppressWarnings("unchecked")
		@PUT
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object handlePut(JSONObject para) {
//	    	System.out.println(para);
	    	if(para == null ||  para.get("action") == null ){
	    		return new Result(false,"请求参数不正确");
	    	}
	    	String action =  (String)para.get("action");
	    	try{
		    	if(Const.ACTION_CheckStudent.equals(action)){
		    		return studentSerivce.doesExistStuNameAndTel((String)para.get("name"),(String)para.get("mobilePhone"));
		    	}else if(Const.ACTION_GetStuNameById.equals(action)){
		    		return studentSerivce.getStudent((Integer)para.get("sid"));
		    	}else if(Const.ACTION_SettingDiscount.equals(action)){
		    		int sid = (Integer)para.get("sid");
		    		int cid = (Integer)para.get("cid");
		    		float discount = Float.parseFloat(para.get("discount").toString());
		    		studentSerivce.setDiscount(sid, cid, discount);
		    		return "ok";
		    	}else if(Const.ACTION_DeleteSettingDiscount.equals(action)){
		    		int sid = (Integer)para.get("sid");
		    		int cid = (Integer)para.get("cid");
		    		logger.info("折扣信息被删除：用户(Id:"+request.getSession().getAttribute(Const.CURRENT_USER_ID)+")删除付款信息(sid:"+sid+",cid:"+cid+")，若有疑问请及时联系该用户：");
		    		studentSerivce.deleteDiscount(sid, cid);
		    		return "ok";
		    	}else if(Const.ACTION_GetDiscountForStudentByClass.equals(action)){
		    		int sid = (Integer)para.get("sid");
		    		int cid = (Integer)para.get("cid");
		    		JSONObject json = new JSONObject();
		    		json.put("discount", studentSerivce.getDiscount(sid, cid));
		    		return json;
		    	}else if(Const.ACTION_AddRefund.equals(action)){
		    		paymentService.addRefund((Map<String,Object>) para.get("payment"));
		    		return "ok";
		    	}else if(Const.ACTION_DeleteRefund.equals(action)){
		    		int payId = (Integer)para.get("payId");
		    		if(Const.TYPE_NORMAL.equals(request.getSession().getAttribute(Const.CURRENT_USER_TYPE))){//admin 
		    			Payment payment = paymentService.getPaymentById(payId);
		    			if(payment.getUid().equals(request.getSession().getAttribute(Const.CURRENT_USER_ID))){
		    				return new Result(false,"Cannot remove,permission not approved!");
		    			}
		    		}
		    		logger.info("退款信息被删除：用户(Id:"+request.getSession().getAttribute(Const.CURRENT_USER_ID)+")删除付款信息(paymentId:"+payId+")，若有疑问请及时联系该用户。");
		    		paymentService.deleteRefund(payId);
		    		return "ok";
		    	}
	    	}catch(Exception e){
	    		String msg = "Add student fail, student name : "+para+":"+e.getMessage();
				logger.error(msg);
				e.printStackTrace();
				return new Result(false,msg);
	    	}
	     	return new Result(false,para.toJSONString());
	    }
	    
	    @POST
	    @Path("exports")
	    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
	    public Response exportExcel(String requestStr)  throws Exception  {
	    	
	    	System.out.println(requestStr);
	    	String startDate = null,endDate = null,userName = null;
	    	if(requestStr != null){
	    		String req[] = requestStr.split("&");
	    		String startDates[] = req[0].split("=");
	    		String endDates[] = req[1].split("=");
	    		String userNames[] = req[2].split("=");
	    		if(startDates.length == 2){
	    			startDate = startDates[1];
	    		}
	    		if(endDates.length ==2){
	    			endDate = endDates[1];
	    		}
	    		if(userNames.length ==2){
	    			userName = userNames[1];
	    		}
	    	}
	    	final Workbook workbook;
	    	Integer uid = (Integer)request.getSession().getAttribute(Const.CURRENT_USER_ID);
	    	if(request.getSession().getAttribute(Const.CURRENT_USER_TYPE).equals(Const.TYPE_NORMAL)){
	    		userName = request.getSession().getAttribute(Const.CURRENT_USER_NAME).toString();
	    	}
	    	 workbook = studentSerivce.exportDate(startDate, endDate,userName,false);
	    	
	        StreamingOutput stream = new StreamingOutput() {
	            public void write(OutputStream output) throws IOException, WebApplicationException {
	                try {
	                	workbook.write(output);
	                } catch (Exception e) {
	                    throw new WebApplicationException(e);
	                }
	            }
	        };
	    	
	    	String exportName = "StudentManagementSystem_"+request.getSession().getAttribute(Const.CURRENT_USER_NAME);//+startDate+"_"+endDate+".xls";
	     
	    	if(startDate != null){
	    		exportName += "_s_"+startDate;
	    	}
	    	if(endDate != null){
	    		exportName += "_e_"+endDate;
	    	}
	    	exportName += ".xls";
	    	exportName =  URLEncoder.encode(exportName, "UTF-8");
	        return Response.ok(stream).header("content-disposition","attachment; filename = "+exportName).build();

	    }
}

