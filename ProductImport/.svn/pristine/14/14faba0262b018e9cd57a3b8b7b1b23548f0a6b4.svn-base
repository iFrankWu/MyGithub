/*
 * ProductHandle.java	 <br>
 * 2011-11-3<br>
 * com.shinetech.order.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.order.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.dao.DressDAO;
import com.shinetech.dao.OrdergDAO;
import com.shinetech.order.bean.OrderImgBean;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProductHandle implements ResultSetHandler{
	private OrdergDAO orderDAO = new OrdergDAO();
	private DressDAO  dressDAO = new DressDAO();
	private List<OrderImgBean> getProductImg(final Long productId) throws DBException{
		final List<OrderImgBean> orderImgList = new ArrayList<OrderImgBean>();
		dressDAO.getImageByProId(productId,new ResultSetHandler() {
			
			@Override
			public void handle(ResultSet rs) {
				try {
					OrderImgBean bean = new OrderImgBean();
					bean.setProdctId(productId);
					bean.setLocalPath(rs.getString("path"));
					bean.setPictureId(rs.getInt("id"));
					orderImgList.add(bean);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		return orderImgList;
	}
	private String getRemotePath(OrderImgBean bean){
		return null;
	}
	@Override
	public void handle(ResultSet rs) {
		try {
			Long productId = rs.getLong("id");
			List<OrderImgBean>  orderImgList = getProductImg(productId);
			for(int i = 0; i < orderImgList.size(); i++){
				OrderImgBean bean = orderImgList.get(i);
				int isMain = 0;
				if(i == 0){
					isMain = 1;
				}
				bean.setIsMain(isMain);
				orderDAO.savePictureInfo(productId,bean.getPictureId() , bean.getLocalPath(),getRemotePath(bean), isMain);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
