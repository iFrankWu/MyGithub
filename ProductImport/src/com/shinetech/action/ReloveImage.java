/*
 * ReloveImage.java	 <br>
 * 2011-12-14<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.io.File;
import java.io.FileFilter;

import com.shinetech.dao.DressDAO;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ReloveImage {
	private FileOperate op = new FileOperate();
	private DressDAO dao = new DressDAO();
	public static void main(String[] args) {
		Const.initLogger();
		ReloveImage image = new ReloveImage();
		image.getImage(Const.ENTRY_DIR+"qualified_image_by_size_12-15");
	}
	public void getImage(String mainDir){
		File file = new File(mainDir);
		if(file.exists() && file.isDirectory()){
			file.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					if(pathname.isDirectory()){
						pathname.listFiles(new FileFilter() {
							
							@Override
							public boolean accept(File imgFile) {
								resloveImage(imgFile);
								return false;
							}
						});
					}
					return false;
				}
			});
		}
	}
	public void resloveImage(File imgFile){
		try{
			if(imgFile.exists()){
				String filename = imgFile.getName();
				String temp[] = filename.split("_");
				int imgId = Integer.parseInt(temp[0]);
				String path = dao.getImgPathByimgId(imgId);
				File file = new File(Const.EXPORT_IMAGE_PATH_WATER+path);
				if(file.exists()){
					return;
				}
				op.copy(imgFile,Const.EXPORT_IMAGE_PATH_WATER+path);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
