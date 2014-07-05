/*
 * ParseOrderXml.java	 <br>
 * 2011-11-4<br>
 * com.shinetech.order.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.order.handle;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import com.shinetech.dao.OrdergDAO;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ParseOrderXml {
	public static void main(String[] args) {
		ParseOrderXml parse = new ParseOrderXml();
		parse.test();
	}
	public void test(){
		try {
			parse("data/100000025-1320312583.xml");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	private OrdergDAO dao = new OrdergDAO();
	@SuppressWarnings({ "unused", "unchecked" })
	public void parse(String orderxml) throws NumberFormatException, DBException{
		org.jdom.input.SAXBuilder saxReader = new org.jdom.input.SAXBuilder();
		Document document = null;
		try {
			document = saxReader.build(orderxml);
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Element order = document.getRootElement();
//		{
			/**
			 * 订单信息
			 * */
			String order_id = order.getChildTextTrim("order_id");
			String order_state = order.getChildTextTrim("order_state");
			String order_status = order.getChildTextTrim("order_status");
			String order_create_time = order.getChildTextTrim("order_create_time");
			String order_update_time = order.getChildTextTrim("order_update_time");
			String order_currency_code = order.getChildTextTrim("order_currency_code");
			String currency_rate = order.getChildTextTrim("currency_rate");
			String order_paid = order.getChildTextTrim("order_paid");
			String coupon_code = order.getChildTextTrim("coupon_code");
//		}
//		{
			/**客户信息*/
			Element customer = order.getChild("customer");
			String customer_id = customer.getChildTextTrim("customer_id");
			String customer_is_guest = customer.getChildTextTrim("customer_is_guest");
			String customer_group_id = customer.getChildTextTrim("customer_group_id");
			String customer_email = customer.getChildTextTrim("customer_email");
			String customer_firstname = customer.getChildTextTrim("customer_firstname");
			String customer_lastname = customer.getChildTextTrim("customer_lastname");
			
//		}
//		{
			/**产品信息
			 * */
			Element items = order.getChild("items");
			List<Element> list = (List<Element>)items.getChildren("item");
			for(Element item : list){
				String product_id = item.getChildTextTrim("product_id");
				String product_sku = item.getChildTextTrim("product_sku");
				String product_name = item.getChildTextTrim("product_name");
				String create_at = item.getChildTextTrim("create_at");
				String update_at = item.getChildTextTrim("update_at");
				String weight = item.getChildTextTrim("weight");
				String price = item.getChildTextTrim("price");
				String qty = item.getChildTextTrim("qty");
				String color = item.getChildTextTrim("color");
				String size = item.getChildTextTrim("size");
			}
//		}
//		{
			/**
			 * 帐单地址
			 * */
			Element billing_address = order.getChild("billing_address");
			String bill_street = billing_address.getChildTextTrim("street");
			String bill_postcode = billing_address.getChildTextTrim("postcode");
			String bill_country = billing_address.getChildTextTrim("country");
			String bill_region = billing_address.getChildTextTrim("region");
			String bill_city = billing_address.getChildTextTrim("city");
			String bill_telephone = billing_address.getChildTextTrim("telephone");
			String bill_firstname = billing_address.getChildTextTrim("firstname");
			String bill_lastname = billing_address.getChildTextTrim("lastname");
			String bill_email = billing_address.getChildTextTrim("email");
//		}
//		{
			/**
			 * 送货地址
			 * */
			Element shipping_address = order.getChild("shipping_address");
			String ship_street = shipping_address.getChildTextTrim("street");
			String ship_postcode = shipping_address.getChildTextTrim("postcode");
			String ship_country = shipping_address.getChildTextTrim("country");
			String ship_region = shipping_address.getChildTextTrim("region");
			String ship_city = shipping_address.getChildTextTrim("city");
			String ship_telephone = shipping_address.getChildTextTrim("telephone");
			String ship_firstname = shipping_address.getChildTextTrim("firstname");
			String ship_lastname = shipping_address.getChildTextTrim("lastname");
			String ship_email = shipping_address.getChildTextTrim("email");
//		}
		/**
		 * 以下五项值 有待确定
		 * */
		String ship_company = "";
		String online_order_status = "";
		String bill_company = "";
		Date order_finish_time = null;
		Date plan_delivery_time = null;
		Date order_create_times = null;
		
		dao.saveSalesInfo(Integer.parseInt(order_id), Integer.parseInt(customer_id), order_create_times, order_finish_time, plan_delivery_time,
				ship_firstname, ship_lastname, ship_city, ship_region, ship_country, ship_company, ship_street, ship_postcode, ship_telephone,
				bill_company, bill_city, bill_country, bill_firstname, bill_lastname, bill_telephone, bill_postcode, bill_region, bill_street, online_order_status
				,bill_email,ship_email);
	}
}
