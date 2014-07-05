/*
 * ExportImageBySize.java	 <br>
 * 2011-12-14<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ExportImageBySizeHandle implements ResultSetHandler{
	private FileOperate op = new FileOperate();
	@Override
	public void handle(ResultSet rs) {
		try {
			int imageId = rs.getInt("id");
			int width = rs.getInt("picture_width");
			int height = rs.getInt("picture_height");
			String sizeDir = width+"x"+height;
			String path = rs.getString("path");
			File dir = new File(Const.EXPORT_IMAGE_PATH_BY_SIZE+sizeDir);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(Const.ENTRY_DIR+path);
			op.copyFile(Const.ENTRY_DIR+path, dir+"/"+imageId+"_"+file.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
