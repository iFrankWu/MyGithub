/*
 * ImageService.java	 <br>
 * 2011-10-11<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.bean.Image;
import com.shinetech.sql.ResultSetHandler;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImageHandle implements ResultSetHandler {
	private String name;
	public ImageHandle(String name){
		this.name = name;
	}
	private List<Image> imageList = new ArrayList<Image>();
	
	/*
	 * @return the imageList
	 */
	public List<Image> getImageList() {
		return imageList;
	}

	@Override
	public void handle(ResultSet rs) {
		String imagePath;
		try {
			imagePath = "/image/"+rs.getString("path");
			imageList.add(new Image("80",imagePath,name+"-"+rs.getInt("tag_status") ,rs.getInt("tag_status")+"","0"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
