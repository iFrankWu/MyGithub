/*
 * TestColors.java	 <br>
 * 2011-10-22<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import com.shinetech.dao.DressDAO;
import com.shinetech.handle.ColorHandle;
import com.shinetech.handle.TagHandle;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TestColors {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		TestColors service = new TestColors();
		service.service();
	}
	private DressDAO dao = new DressDAO();
	private FileOperate op = new FileOperate();
	private int count = 0;
	public void service(){
		try {
			dao.getProductInfo(new ResultSetHandler() {
				 
				@Override
				public void handle(ResultSet rs) {
					try {
						TagHandle handle = new TagHandle();
						dao.getTagInfoByPId(rs.getLong("id"), handle);
						List<String> tags = handle.getTags();
						ColorHandle colorHandle = new ColorHandle();
						Set<String> set = colorHandle.getColorSet(tags);
						if(set.isEmpty()){
							 op.addContentToEndOfFile2("data/null_color_product", rs.getLong("id")+"\t"+tags.toString());
						}
						count++;
					}  catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			op.addContentToEndOfFile2("data/null_color_product", count+"");
			System.out.println(count);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
