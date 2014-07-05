/*
 * OrderImgDAO.java	 <br>
 * 2011-11-3<br>
 * com.shinetech.dao <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class OrdergDAO {
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	public OrdergDAO(){
		db.setDatabase("test");//231 上 dress产品的测试库
//		db.setDatabase("default");//这是231 dress产品的正式库
	}
	@SuppressWarnings("unchecked")
	public void savePictureInfo(Long productId,int pictureId,String pictureLocalPath,String pictureRemotePath,int isMain) throws DBException{
		String sql = "insert into or_picture_info(product_id,picture_id,picture_local_path,picture_remote_path,is_main) value(?,?,?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, productId, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2, pictureId, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(3, pictureLocalPath, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(4, pictureRemotePath, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(5, isMain, FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void saveSalesInfo(int order_id,int customer_id,Date order_create_time,Date order_finish_time,Date plan_delivery_time,String ship_firstname,String ship_lastname,
			String ship_city,String ship_region,String ship_country,String ship_company,String ship_street,String ship_postalcode,String ship_phone,
			String bill_company,String bill_city,String bill_country,String bill_firstname,String bill_lastname,String bill_phone,String bill_postalcode,String bill_region,
			String bill_street,String online_order_status,String bill_email,String ship_email) throws DBException{
		String sql = "insert into or_sales_order_info(order_id ,customer_id ,order_create_time ,order_finish_time ,plan_delivery_time ," +
					 "ship_firstname ,ship_lastname ,ship_city ,ship_region ,ship_country ,ship_company ,ship_street ,ship_postalcode ,ship_phone ," +
				     "bill_firstname ,bill_lastname ,bill_city ,bill_region ,bill_country ,bill_company ,bill_street ,bill_postalcode ,bill_phone ," +
				     "online_order_status,bill_email,ship_email) " +
				     "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1, order_id, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2, customer_id, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3, order_create_time, FieldTypes.DATE));
		fpList.add(new FieldParameter(4, order_finish_time, FieldTypes.DATE));
		fpList.add(new FieldParameter(5, plan_delivery_time, FieldTypes.DATE));
		fpList.add(new FieldParameter(6, ship_firstname, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(7, ship_lastname, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(8, ship_city, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(9, ship_region, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(10, ship_country, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(11, ship_company, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(12, ship_street, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(13, ship_postalcode, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(14, ship_phone, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(15, bill_firstname, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(16, bill_lastname, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(17, bill_city, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(18, bill_region, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(19, bill_country, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(20, bill_company, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(21, bill_street, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(22, bill_postalcode, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(23, bill_phone, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(24, online_order_status, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(25, bill_email, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(26, ship_email, FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	public void saveOrderDetails(){
//		String sql = "insert into or_order_details(product_id,order_id,currency,price,is_pannier,color,is_custom,size,bust,waist,hips,hollow_to_floor,qty,status,comment)" +
//				"	 values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
	}
}
