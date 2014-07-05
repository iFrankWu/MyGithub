package com.shinetech.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5Util {
	private final static char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


	private static String toHexString(byte[] md) {
		int j = md.length;
		char str[] = new char[j * 2];
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[2 * i] = HEX_DIGITS[byte0 >>> 4 & 0xf];
			str[i * 2 + 1] = HEX_DIGITS[byte0 & 0xf];
		}
		return new String(str);
	}

	public static String streamToMD5(InputStream inputStream) {
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = inputStream.read(buffer)) > 0) {
				mdTemp.update(buffer, 0, numRead);
			}
			return toHexString(mdTemp.digest());
		} catch (Exception e) {
			return null;
		}
	}

	public static String stringToMD5(String str) {
		try {
			byte[] strTemp = str.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			return toHexString(mdTemp.digest());
		} catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}

	public static String fileToMD5(String fileName) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			return streamToMD5(inputStream);
		} catch (Exception e) {
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static long md5Path(String filePath){
		String md5 = MD5Util.stringToMD5(filePath);
		String subMd5 = md5.substring(0, 4)+md5.substring(16, 20)+md5.substring(28,32);
		Long l = Long.parseLong(subMd5, 16);
		l = l % 128;
		return l;
	}
	public static void main(String[] args) {
		String url = "http://www.articlecat.com/Article/000-061-practice-exam/453077";
		//String str = MD5Util.stringToMD5(url);
		//System.out.println(str);
		long i = MD5Util.md5Path(url);
		System.out.println(i);
	}
}
