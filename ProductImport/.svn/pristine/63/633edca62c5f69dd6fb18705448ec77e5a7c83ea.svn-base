/*
 * HasSameImage.java	 <br>
 * 2011-12-15<br>
 * com.shinetech.check <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.check;

import java.sql.ResultSet;

import com.shinetech.dao.DressDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;

/**
 *检查产品中是否两个相同的图片，指完全相同，实现上采用md5值比较
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class HasSameImage {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		Const.initLogger();
		HasSameImage image = new HasSameImage();
		image.checkProuductImage();
	}
	private DressDAO dao = new DressDAO();
	public void checkProuductImage(){
		try{
			dao.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rss) {
					try{
						long pId = rss.getLong("id");
						CheckImageHandle handle = new CheckImageHandle();
						dao.getImageByProId(pId,handle);
						if(handle.exist){
							System.out.println(pId);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
