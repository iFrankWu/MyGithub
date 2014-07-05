/*
 * TestImageCompressEffect.java	 <br>
 * 2011-10-22<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.ImageUtil;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TestImageCompressEffect {
	public static void main(String[] args) {
		Const.initLogger();
		service();	
	}
	
	public static void service(){
		final String path = "//server2/Temp/zxs/";
		final Map<String,List<ImageEntity>> map = new HashMap<String, List<ImageEntity>>();
		File file = new File(path+"520x800");
//		file = new File(path+"temp");
		file.list(new FilenameFilter() {
//			private int a[][] = {{70,100},{100,143},{350,500},{146,209}};
			private int a[][] = {{176,270},{95,146}};//站点上线使用尺寸
			@Override
			public boolean accept(File dir, String name) {
				
				if(name.equals("Thumbs.db")){
					return false;
				}
				File oriFile = new File(dir.getAbsolutePath()+"/"+name);
				try{
					int size[] = ImageUtil.getImageSize(oriFile);
					ImageEntity entity = new ImageEntity(size[0], size[1], oriFile.length());
					List<ImageEntity> list = new ArrayList<ImageEntity>();
					entity.setPath(oriFile.getAbsolutePath());
					list.add(entity);
					map.put(oriFile.getAbsolutePath(), list);
				}catch (Exception e) {
					e.printStackTrace();
				}
				for(int i = 0;i < 1;i++){
					int widthdist = a[i][0];
					int heightdist = a[i][1];
					try {
						File f = new File(path+widthdist+"x"+heightdist);
						if(!f.exists()){
							f.mkdirs();
						}
//						for(double quality = 0.5;quality<= 1d;quality= quality+0.1){
						for(double quality = 0.8;quality<= 1d;quality= quality+0.05){
//						double quality = 0.85; 
							File fs = new File(path+(int)(quality*100)+"_"+widthdist+"x"+heightdist+"/");
							if(!fs.exists()){
								fs.mkdirs();
							}
							System.out.println(fs.getAbsolutePath());
							
							String newPath = path+(int)(quality*100)+"_"+widthdist+"x"+heightdist+"/"+name;
							Thumbnails.of(oriFile).
							size(widthdist, heightdist)
							.outputQuality(quality)
							.toFile(newPath);
							File newFile = new File(newPath);
							int size[] = ImageUtil.getImageSize(newFile);
							ImageEntity entity = new ImageEntity(size[0], size[1], newFile.length());
							entity.setPath(newFile.getAbsolutePath());
							entity.setQuality((int)(quality*100));
							map.get(oriFile.getAbsolutePath()).add(entity);
						}
						//ImageUtil.thumbnailator(dir.getAbsolutePath()+"/"+name, path+widthdist+"x"+heightdist+"/"+name, widthdist, heightdist);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return false;
			}
		});
		 DecimalFormat   df   =   new   DecimalFormat( "###,### "); 
		File files = new File("data/size_image_compare.tsv");
		if(files.exists())
			files.delete();
		op.addContentToEndOfFile2("data/size_image_compare.tsv", "原始图片	原始大小	50	60	70	80	90	100");
		 for(String p : map.keySet()){
			StringBuilder sb = new StringBuilder();
			
			sb.append(p);
			sb.append("\t");
			for(ImageEntity entity: map.get(p)){
//				sb.append(entity.getPath());
//				sb.append("\t");
//				sb.append(entity.getQuality());
//				sb.append("\t");
//				sb.append(entity.getWeight());
//				sb.append("\t");
//				sb.append(entity.getHeight());
//				sb.append("\t");
				sb.append(df.format(entity.getSize()));
				sb.append("\t");
			}
			op.addContentToEndOfFile2("data/size_image_compare.tsv", sb.toString());
			System.out.println(p+"\t"+sb.toString());
		 }
		 
	}
	private static FileOperate op = new FileOperate();
}
