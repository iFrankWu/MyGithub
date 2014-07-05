/*
 * EditUpdateHandle.java	 <br>
 * 2011-12-15<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import com.shinetech.sql.ResultSetHandler;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class EditUpdateHandle implements ResultSetHandler{
	private Set<String> editTypeSet = new HashSet<String>();
	@Override
	public void handle(ResultSet rs) {
		try{
			editTypeSet.add(rs.getString("edit_type"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Set<String> getEditTypeSet(){
		return editTypeSet;
	}

}
