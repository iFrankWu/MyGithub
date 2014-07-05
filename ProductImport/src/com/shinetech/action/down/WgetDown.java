/**
 * 
 */
package com.shinetech.action.down;

/**
 * @author wusx
 *
 */
public class WgetDown implements IDown{

	public void downFile(String fileName) throws Exception {
		 StringBuilder sb = new StringBuilder();
		 sb.append("wget --ftp-user=wusx --ftp-password=wushexin ftp://192.168.2.68/ftp/infosum.net/");
		 sb.append(fileName);
		 Process process = Runtime.getRuntime().exec(sb.toString());
		 process.waitFor();
	}

	public void continueDownFile(String fileName) throws Exception {
		 StringBuilder sb = new StringBuilder();
		 sb.append("wget --ftp-user=wusx --ftp-password=wushexin ftp://192.168.2.68/ftp/infosum.net/");
		 sb.append(fileName);
		 Process process = Runtime.getRuntime().exec(sb.toString());
		 process.waitFor();
	}

	@Override
	public void downFile(String user, String pass, String ip, String path,
			String fileName, String localPath, boolean isCountinue)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

 

}
