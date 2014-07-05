/*
 * CurlDown.java	 <br>
 * 2011-7-21<br>
 * com.shinetech.down <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action.down;

import java.io.File;
import java.io.IOException;

import com.shinetech.handle.IHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CurlDown implements IDown{
	public void getRemoteFileList(String remotePath,IHandle<String> handle){
		
	}

	@Override
	public void downFile(String user, String pass, String ip, String path,
			String fileName, String localPath,boolean isContinue) throws Exception {
		//curl -u wusx:wushexin ftp://192.168.2.68/ftp/infosum.net/infosum.net.10-12-23.log -o download/infosum.net.10-12-23.log
		StringBuilder curldown = new StringBuilder();
		curldown.append("curl -u ");
		curldown.append(user);
		curldown.append(":");
		curldown.append(pass);
		curldown.append(" ftp://");
		curldown.append(ip);
		curldown.append("/");
		curldown.append(path);
		//curldown.append("/");
		curldown.append(fileName);
		if(isContinue){
			curldown.append(" -C -");
		}
		curldown.append(" --connect-timeout 120 ");
		curldown.append(" -o ");
		
		curldown.append(localPath);//如果这个文件不存在，那么首先得创建它
		File file = new File(localPath);
		if(file.exists()){
			if(!file.isDirectory()){
				file.renameTo(new File(file.getAbsoluteFile()+".bak"));
				file.mkdirs();
			}
		}else{
			file.mkdirs();
		}
		//curldown.append("/");
		curldown.append(fileName);
		System.out.println(curldown);
//		Process process = Runtime.getRuntime().exec(curldown.toString());
//	    process.waitFor();
	}
	public void renameFtpServerFile(String oldPath,String newPath,String user, String pass,String hostOrIp) throws IOException, InterruptedException{
		 //curl -Q "RNFR test/game.sh" -Q "RNTO test/game.sh.new.name" "ftp://ftpuser:password@192.168.2.230″
		StringBuilder sb = new StringBuilder();
		sb.append("curl -Q \"RNFR ");
		sb.append(oldPath);
		sb.append("\"");
		sb.append(" -Q \"RNTO ");
		sb.append(newPath);
		sb.append("\" ");
		sb.append("\"ftp://");
		sb.append(user);
		sb.append(":");
		sb.append(pass);
		sb.append("@");
		sb.append(hostOrIp);
		sb.append("\"");
		System.out.println(sb.toString());
//		Process process = Runtime.getRuntime().exec(sb.toString());
//	    process.waitFor();
	}
	public void mkdir(String dir,String user, String pass,String hostOrIp){
		
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		CurlDown down = new CurlDown();
		down.renameFtpServerFile("test/game.sh.bak", "test/game.sh", "oracle", "oracle", "192.168.2.230");
	}

}
