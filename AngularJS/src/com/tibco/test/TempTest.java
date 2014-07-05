/*
 * TempTest.java
 * May 23, 2013
 * com.tibco.test
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.test;

import java.io.File;
import java.io.FileFilter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class TempTest {

	/**
	 *  TODO what's the function for this method.
	 *  @param args
	 *	void
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
//		Date d = format.parse("2012/2/3");
		System.out.println(cryptWithMD5("hello"));
		System.out.println(cryptWithMD5("hello"));
		
		String s = "/opt/TIBCO/DSEngine/work/wushexin-dt-0/tibco/tibcojre64/1.7.0/lib/amd64";
		String sub = s.substring(0, s.lastIndexOf("/"));
		System.out.println(sub);
		TempTest test = new TempTest();
		test.searchFileUnderFolder(new File("D:/cloud/workspace/AngularJS"), "asm-3.1.jar");
		 
	}
	private Boolean found = false;
	public void searchFileUnderFolder(File parent,final String fileName ){
		if(parent.isDirectory() && !found){
			parent.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					if(pathname.isDirectory()){
						searchFileUnderFolder(pathname,fileName);
					}else{
						if(pathname.getName().equals(fileName)){
							System.out.println(pathname.getAbsolutePath());
							found = true;
							return true;
						}
					}
					return false;
				}
			});
		}
	}
	 private static MessageDigest md;

	
	 
	   public static String cryptWithMD5(String pass){
	    try {
	        md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = pass.getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException ex) {
	    	ex.printStackTrace();
	    }
	        return null;

	   }

}

