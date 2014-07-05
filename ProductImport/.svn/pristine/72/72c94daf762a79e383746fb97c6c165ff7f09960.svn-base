package com.shinetech.action.down;

/**
 *  下载文件统一约定的接口，至少提供下载和断点续传下载两种下载方式
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */
public interface IDown {
	/**
	 * 功能：下载ftp服务器指定目录下的某个文件到本地目录下，<br>
	 * 文件保存名和服务器相同 <br>
	 * 
	 * 注意事项：<font color="red">isCountinue 是一个是否断点续传的开发</font> <br>
	 * @param user	
	 * @param pass
	 * @param ip
	 * @param path
	 * @param fileName
	 * @param localPath
	 * @param isCountinue 是否采用断点续传方式下载，true为是
	 * @throws Exception
	 * void
	 */
	public void  downFile(String user,String pass,String ip,String path,String fileName,String localPath,boolean isCountinue)throws Exception;
	 
}
