/*
 * DownOrder.java	 <br>
 * 2011-11-3<br>
 * com.shinetech.order <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.order;

import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

import com.shinetech.action.down.ApacheFtpOperate;
import com.shinetech.action.down.CurlDown;
import com.shinetech.util.DateUtil;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DownOrder {
	public static void main(String[] args) {
		DownOrder order = new DownOrder();
		order.test();
	}
	public void test(){
		downOrder();
	}
	public void downOrder(){
		ApacheFtpOperate ftp = new ApacheFtpOperate();
		String user = "oracle";
		String pass = "oracle";
		String ip = "192.168.2.230";
		String remotePath = "magento/var/download/";
		String localPath = "data/";
		int prot = 21;
		try {
			ftp.connect(ip, prot, user, pass);
			List<FTPFile> list = ftp.getRemoteFileList(remotePath);
			CurlDown down = new CurlDown();
			for(FTPFile file  :list){
				if(file.isFile()){
					String fileName = file.getName();
					System.out.println(fileName);
					down.downFile(user, pass, ip, remotePath, fileName, localPath, true);
					String arctive = DateUtil.getToday();
					ftp.createDir(arctive);
					System.out.println(arctive);
					down.renameFtpServerFile(remotePath+fileName, arctive+"/"+fileName, user, pass, ip);
				}
			}
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
