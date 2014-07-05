/*
 * ConvertCharset.java
 * Jun 22, 2013
 * com.tibco.test
 * AngularJS
 * Copyright (C), 2013, TIBCO Software Inc.
 * 
 */
package com.tibco.test;

import java.io.File;

import com.tibco.util.FileOperate;

/**
 * class description goes here.
 *
 * @author <a href="mailto:swu@tibco-support.com">Frank Wu</a>
 * @version 1.0.0
 */
public class ConvertCharset {

	/**
	 *  TODO what's the function for this method.
	 *  @param args
	 *	void
	 */
	public static void main(String[] args) {
		FileOperate op = new FileOperate();
		String s = op.getContent(new File("./web/TruScreen.html"), "GBK");
		op.write2file("./web/TruScreen-1.html", s, "UTF-8");
		System.out.println(s);
	}

}

