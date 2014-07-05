/*
 * ExportQualifiedProductImage.java	 <br>
 * 2011-11-10<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.shinetech.dao.DressDAO;
import com.shinetech.handle.ExportImageHandle;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ExportQualifiedProductImage {
	private DressDAO dao = new DressDAO();
	public int count = 0;
	public void exportQualifiedProductImage() throws DBException{
		
		dao.getProductInfo(new ResultSetHandler() {
			
			@Override
			public void handle(ResultSet rs) {
				try {
					ExportImageHandle handle = new ExportImageHandle();
					dao.getImageByProId(rs.getLong("id"), handle);
					count += handle.count;
				} catch (DBException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	public void handleSpecial(){
		ExportImageHandle handle = new ExportImageHandle();
		try {
			dao.getSpecialPic(handle);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Const.initLogger();
		ExportQualifiedProductImage image = new ExportQualifiedProductImage();
		image.handleSpecial();
	}
}
