/*
 * CheckImageHandle.java	 <br>
 * 2011-12-15<br>
 * com.shinetech.check <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.check;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.MD5Util;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CheckImageHandle implements ResultSetHandler{
	private Set<String> set = new HashSet<String>();
	public boolean exist = false;
	@Override
	public void handle(ResultSet rs) {
		try{
			String path = Const.ENTRY_DIR+rs.getString("path");
			String md5 = MD5Util.fileToMD5(path);
			if(set.add(md5)){
				
			}else{
				exist = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
