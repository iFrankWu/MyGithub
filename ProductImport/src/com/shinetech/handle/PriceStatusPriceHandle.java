/*
 * PriceStatusPriceHandle.java	 <br>
 * 2011-12-7<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Random;

import com.shinetech.dao.DressDAO;
import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class PriceStatusPriceHandle{
	private Random random = new Random();
	
	/**
	 * 功能：得到产品商城中产品的原价 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param costPrice
	 * @param oldCostPrice
	 * @param priceStatus
	 * void
	 */
	public float computePrice(int costPrice,int oldCostPrice,String priceStatus,float discount,float priceFactor){
		//实际采用的价格
		//	2. 新的售价计算通过 price_status 确认基数。
		//	a. 0, 2 为 cost_price;
		//	b. 1 为 old_cost_price;
		int usePrice = costPrice;
		if("1".equals(priceStatus.trim())){
			usePrice = oldCostPrice;
		}
		float price = (usePrice*priceFactor)/6+5;//得出的是
		int rp = Math.round(price);
		//尾数
		float mantissa = Float.parseFloat(SimpleProductConfig.discount_9[random.nextInt(SimpleProductConfig.discount_9.length)]);
		//实际售价 168.99
		float rprice = rp+mantissa;
		Float marketPrice = rprice*100/(100-discount);//这个价格是上传给商城的，商城根据这个值和折扣值计算产品实际售价
		return marketPrice;//String.format("%.2f", marketPrice);
	}
	private DressDAO dressDAO = new DressDAO();
	

//	System.out.println(dec.format(value));
	/**
	 * 功能：初始化价格因子， <br>
	 * 注意事项：<font color="red">值执行一次</font> <br>
	 * void
	 */
	public void initPriceFactor(){
		final DecimalFormat dec = new DecimalFormat("###.##");
		try{
			dressDAO.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
						Float factor = random.nextFloat()*0.4f+2.0f;
						String f = dec.format(factor);
						float p = Float.parseFloat(f);
						dressDAO.updatePriceFactor(p, rs.getLong("id"));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Const.initLogger();
//		PriceStatusPriceHandle handle = new PriceStatusPriceHandle();
//		handle.initPriceFactor();
	}
	 
}
