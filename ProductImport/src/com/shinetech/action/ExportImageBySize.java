/*
 * ExportImageBySize.java	 <br>
 * 2011-12-14<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import com.shinetech.dao.DressDAO;
import com.shinetech.handle.ExportImageBySizeHandle;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ExportImageBySize {
	public static void main(String[] args) {
		Const.initLogger();
		DressDAO dao = new DressDAO();
		ExportImageBySizeHandle handle = new ExportImageBySizeHandle();
		dao.getPic(handle);
	}
}
