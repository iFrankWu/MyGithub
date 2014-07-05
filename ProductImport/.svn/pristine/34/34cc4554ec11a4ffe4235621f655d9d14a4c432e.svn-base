/*
 * TestCompressImage.java	 <br>
 * 2011-10-22<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;


/**
 * Class description goes here.
 * 
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TestCompressImage {
	/*
	 * Application starting point, open an image and save it in JPEG with a
	 * compression factor.
	 */
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("image/abc.jpg"))
        .size(200, 200)
        .outputQuality(0.8d)
        .toFile("image/abcd.jpg");
	}
}