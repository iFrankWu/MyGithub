/**
 * 
 */
package com.shinetech.action.down;

import java.io.IOException;
import java.util.Map;

import com.shinetech.handle.IHandle;
 
/**
 * ftp操作接口描述，包括基本的登录、下载、获取远程文件夹类别、断线等功能
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */
public interface IFtpOperate {
	/**
	 * 得到远程服务器上 remoteDir目录下的文件列表
	 * 
	 * @param remoteDir
	 */
	public Map<String,Long>  getRemoteFileList(String remoteDir) throws IOException;
	
	/**
	 * 得到远程服务器上 remoteDir目录下的文件列表
	 * 
	 * @param remoteDir
	 */
	public Map<String,Long>  getRemoteFileList(String remoteDir,IHandle<String> handle) throws IOException;
	
	/**
	 * 功能：下载文件 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param remotefile
	 * @param localfile
	 * @throws Exception
	 * void
	 */
	public void dowmload(String remotefile,String localfile) throws Exception;
	 /**
	 * 功能：连接ftp服务器 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param hostname
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 * boolean
	 */
	public boolean connect(String hostname,int port,String username,String password) throws IOException;
	 /**
	 * 功能：释放连接 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @throws IOException
	 * void
	 */
	public void disconnect() throws IOException;
}
