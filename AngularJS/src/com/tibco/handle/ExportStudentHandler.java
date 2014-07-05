/*
 * ExportReportHandler.java
 * Mar 6, 2014
 * com.tibco.handle
 * AngularJS
 * Copyright (C), 2014, TIBCO Software Inc.
 * 
 */
package com.tibco.handle;

import java.sql.ResultSet;

import com.shinetech.sql.ResultSetHandler;
import com.tibco.util.XLSExport;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class ExportStudentHandler implements ResultSetHandler {
	
	private int rowIndex = 1;
	private  XLSExport xlsExport;
	private String tableHeader[] = "学生ID,姓名,性别,年龄,联系地址,联系人,联系电话,email,固定电话 ,qq,数据来源,状态,是否签单,备注,学校,修改时间,创建日期,其它联系电话,账户余额,创建用户".split(",");
	public ExportStudentHandler(XLSExport xlsExport){
		this.xlsExport = xlsExport;
		xlsExport.createRow( 0 );
		//create table header 
		for(int i=0;i<tableHeader.length;i++){
			xlsExport.setCell(i, tableHeader[i]);
		}
	}
	@Override
	public void handle(ResultSet rs) {
		xlsExport.createRow( rowIndex );
	    try{
			xlsExport.setCell( 0 , rs.getInt(1));//学生id
			xlsExport.setCell( 1 ,  rs.getString(2));//姓名
			xlsExport.setCell( 2 , rs.getString(3));//性别
			xlsExport.setCell( 3 ,  rs.getString(4) );//年龄
			xlsExport.setCell( 4 ,  rs.getString(5) );//联系地址
			xlsExport.setCell( 5 , rs.getString(6) );//联系人
			xlsExport.setCell( 6 ,  rs.getString(7));//联系电话
			xlsExport.setCell( 7 ,   rs.getString(8) );//email
			xlsExport.setCell( 8 ,   rs.getString(9) );//固定电话
			xlsExport.setCell( 9 ,   rs.getString(10) );//qq
			xlsExport.setCell( 10 ,   rs.getString(11) );//数据来源
			xlsExport.setCell(11,  rs.getString(12));//状态
			
			xlsExport.setCell(12, rs.getBoolean(13) == true ? "是":"否");//是否签单
			xlsExport.setCell(13, rs.getString(14));//备注
			xlsExport.setCell(14,  rs.getString(15));//学校
			xlsExport.setCell(15,  rs.getDate(16)+"");//修改日期
			xlsExport.setCell(16,  rs.getDate(17)+"");//创建日期
			xlsExport.setCell(17, rs.getString(19));//其他联系电话
			xlsExport.setCell(18,  rs.getFloat(20));//账户余额
			xlsExport.setCell(19,  rs.getString(21));//创建用户
			
			rowIndex++;
		}catch(Exception e){
				e.printStackTrace();
		}
	    
	}
}

