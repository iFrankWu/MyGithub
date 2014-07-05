/*
 * ExportImageHandle.java	 <br>
 * 2011-11-10<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ExportImageHandle implements ResultSetHandler {
	private FileOperate op = new FileOperate();
	private Logger logger = Logger.getLogger(this.getClass());
	public int count = 0;
	@Override
	public void handle(ResultSet rs) {
		try {
			String path = rs.getString("path");
			count++;
			String newPath =  Const.EXPORT_IMAGE_PATH+path;
			op.copyFile(Const.ENTRY_DIR+path, newPath);
			logger.info("Export Image:"+Const.ENTRY_DIR+path+"==>"+newPath);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
