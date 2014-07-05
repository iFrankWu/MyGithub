/*
 * UpdateProductPetticoat.java	 <br>
 * 2011-11-14<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.shinetech.dao.DressDAO;
import com.shinetech.handle.TagCatatogHandle;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class UpdateProductPetticoat {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		System.err.println("该程序是用来更新产品衬裙");
//		UpdateProductPetticoat update = new UpdateProductPetticoat();
//		update.update();
	}
	private static List<String> updateTagList =Arrays.asList("Ankle Length;Floor Length;Sweep Train;Court Train;Chapel Train;Cathedral Train;Monarch Train;Panel Train;Watteau Train;Asymmetric;Hi-Lo".split(";"));
	DressDAO dao = new DressDAO();
	public void update(){
		try {
			dao.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					TagCatatogHandle handle = new TagCatatogHandle();
					try {
						long productId  = rs.getLong("id");
						
						dao.getTagInfoByPId(productId, handle);
						if(handle.getCatalogTagMap().get("Petticoat")!= null){
							List<String> list = handle.getTags();
							if(!Collections.disjoint(list, updateTagList)){
								dao.updateProductStatus(rs.getLong("id"));
							}
						}
					} catch (DBException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
