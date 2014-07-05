/*
 * ImageUtil.java	 <br>
 * 2011-10-17<br>
 * com.shinetech.util <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Class description goes here.
 * 
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

@SuppressWarnings("restriction")
public class ImageUtil {
	private static Logger logger = Logger.getLogger(ImageUtil.class);
	public static void main(String[] args) throws Exception {
		String imgsrc = "data/abcd.png", imgdist = "data/out.png";
		int widthdist = 250, heightdist = 250;

		ImageUtil.reduceImg(imgsrc, imgdist, widthdist, heightdist);
	}
	public static void thumbnailator(String imgsrc, String imgdist, int width,
			int height) throws IOException{
		if(new File(imgdist).exists()){
			return;
		}
		Thumbnails.of(new File(imgsrc)).size(width, height).outputQuality(0.95).toFile(imgdist);
	}
	public static void reduceImg(String imgsrc, String imgdist, int widthdist,
			int heightdist) throws Exception {
		logger.info(imgsrc+"==>"+imgdist);
		File srcfile = new File(imgsrc);
		if (!srcfile.exists()) {
			return;
		}
		Image src = javax.imageio.ImageIO.read(srcfile);
		BufferedImage tag = new BufferedImage((int) widthdist,
				(int) heightdist, BufferedImage.TYPE_INT_RGB);

		tag.getGraphics()
				.drawImage(
						src.getScaledInstance(widthdist, heightdist,
								Image.SCALE_SMOOTH), 0, 0, null);
		// / tag.getGraphics().drawImage(src.getScaledInstance(widthdist,
		// heightdist, Image.SCALE_AREA_AVERAGING), 0, 0, null);

		File f = new File(imgdist);
		if(f.exists())
			f.delete();
		FileOutputStream out = new FileOutputStream(imgdist);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		out.close();
	}
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param file
	 * @return height,width
	 * @throws Exception
	 * int[]
	 */
	public static int[] getImageSize(File file)throws Exception{
		int[] sizes = {0,0};
		if(file.exists() && file.length() > 0){
			java.awt.image.BufferedImage bi;
			try {
				bi = javax.imageio.ImageIO.read(file);
				if(bi != null){
					int height = bi.getHeight();// 获得 高度
					int width = bi.getWidth();
					sizes = new int[]{height,width};
				}
			} catch (Exception e) {
				logger.error("Error Image:"+file.getAbsolutePath());
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}else{
			//logger.error("图片不存在或者大小为0，计算图片高度和宽度失败:"+imagePath);
			throw new Exception("图片不存在或者大小为0，计算图片高度和宽度失败:"+file.getAbsolutePath());
		}
		return sizes;
	}
}
