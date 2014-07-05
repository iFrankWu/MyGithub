/**
 * 
 */
package com.shinetech.action.down;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.shinetech.handle.IHandle;

/**
 * 基于Apache提供的jar包，实现了IFtpOperate接口
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 * @see IFtpOperate
 */
public class ApacheFtpOperate {
	
	public FTPClient ftpClient = new FTPClient();
	/**
	 * 功能：在当前目录下创建目录 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param pathname
	 * void
	 */
	public boolean createDir(String pathname){
		boolean ok = false;
		try {
			ok = ftpClient.makeDirectory(pathname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	public List<FTPFile> getRemoteFileList(String remoteDir,IHandle<FTPFile> handle) throws IOException {
		//这里采用treeMap 是为了 生产 待分拆列表时的顺序
		List<FTPFile> list = new ArrayList<FTPFile>();
		if (ftpClient.changeWorkingDirectory(remoteDir)) {
			ftpClient.getReplyString();
			FTPFile[] files = ftpClient.listFiles();
			for (FTPFile file : files) {
				if(handle != null){
					handle.handle(file);
				}else{
					list.add(file);
				}
			}
		}
		return list;
	}
	
	public List<FTPFile> getRemoteFileList(String remoteDir)
			throws IOException {
		return getRemoteFileList(remoteDir, null);
	}

	/**
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	public boolean connect(String hostname, int port, String username,
			String password) throws IOException {
		//ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		ftpClient.connect(hostname, port);
		ftpClient.setControlEncoding("UTF-8");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, password)) {
				ftpClient.enterLocalPassiveMode();//设定ftp模式，不设定有可能阻塞在getList处
				return true;
			}
		}
		disconnect();
		return false;
	}

	/**
	 * 断开与远程服务器的连接
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	public void dowmload(String remotefile, String localfile) throws Exception {
		FileOutputStream fos = new FileOutputStream(localfile);
		ftpClient.retrieveFile(remotefile, fos);
		fos.close();
	}

	

}
