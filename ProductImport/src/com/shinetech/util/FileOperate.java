package com.shinetech.util;
/*
 * @(#)FileOperate.java
 *
 * Copyright (c) 2010 88Soft.cn All Rights Reserved.
 */


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * 该类 封装了一下常见的对文件的操作。
 * 
 * @version 1.00 2010-11-04
 * @author <a href="mailto:wushexin@gmail.com">Frank Wu</a>
 */

public class FileOperate {
	private Logger logger = Logger.getLogger(FileOperate.class);

	public FileOperate() {
	}

	/** 
	 * create a new floder
	 * @param folderPath String like c:/fqf 
	 * @return boolean 
	 */
	public void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			logger.info("error in create new Folder");
			e.printStackTrace();
		}
	}

	/** 
	 * create new file
	 * @param filePathAndName String  like c:/fqf.txt 
	 * @return boolean 
	 */
	public boolean newFile(String filePath) {
		File file = new File(filePath);
		try {
			if (!file.exists()) {
				String paths[] = filePath.split("\\" + File.separator);
				String newpath = "";
				for (int i = 0; i < paths.length - 1; i++) {
					newpath += paths[i] + File.separator;
				}
				newFolder(newpath);
				file = new File(filePath);
				if(!file.exists()){
					    paths = filePath.split("/");
						newpath = "";
						for (int i = 0; i < paths.length - 1; i++) {
							newpath += paths[i] + File.separator;
						}
						newFolder(newpath);
						file = new File(filePath);
				}
			}
		} catch (Exception e) {
			logger.info("error in create a new file");
			e.printStackTrace();
		}
		return file.exists();
	}

	/** 
	 * delete file
	 * @param filePathAndName String like c:/fqf.txt 
	 * @param fileContent String 
	 * @return boolean 
	 */
	public void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			logger.info("error in delete a file");
			e.printStackTrace();

		}

	}

	/** 
	 * deltete floder 
	 * @param filePathAndName String like c:/fqf 
	 * @param fileContent String 
	 * @return boolean 
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();

		} catch (Exception e) {
			logger.info("error in delete a floder");
			e.printStackTrace();
		}
	}

	/** 
	 * delete all file in floder 
	 * @param path String like c:/fqf 
	 */
	public void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	/** 
	 * copy file
	 * @param oldPath String like：c:/fqf.txt 
	 * @param newPath String like：f:/fqf.txt 
	 * @return boolean 
	 */
	public void copyFile(String oldPath, String newPath) {
		File oldFile = new File(oldPath);
		copy(oldFile,newPath);
	}
	public void copy(File oldfile ,String newPath){
		try {
			int byteread = 0;
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				newFile(newPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) > 0) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			logger.info("error in copy file,\t"+oldfile.getAbsolutePath());
			e.printStackTrace();
		}
	}

	/** 
	 * copy floder 
	 * @param oldPath String like：c:/fqf 
	 * @param newPath String like ：f:/fqf/ff 
	 * @return boolean 
	 */
	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs();
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/** 
	 * move file from oldPath to newPath
	 * @param oldPath String like：c:/fqf.txt 
	 * @param newPath String like：d:/fqf.txt 
	 */
	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/** 
	 * move floder
	 * @param oldPath String like：c:/fqf.txt 
	 * @param newPath String like：d:/fqf.txt 
	 */
	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	private static File m_root;
	private static List<File> m_dirs;

	/** 
	 * get all file and dir , put it into m_dirs
	 * @param tempRoot dir 
	 */
	public void visitAll(File tempRoot) {
		try {

			File[] dirs = tempRoot.listFiles();
			if (dirs != null) {
				List<File> dirslist = Arrays.asList(dirs);
				m_dirs.addAll(dirslist); //put dirslist into m_dirs
				for (int i = 0; i < dirslist.size(); i++) {
					this.visitAll((File) dirslist.get(i));
				}
			}
		} catch (Exception ex) {
			logger.info("error in visitAll : " + ex.getMessage());
		}

	}

	/** 
	 *  delete empty folder
	 *  
	 */
	public void rootDelete() {
		try {
			if (m_dirs != null) {
				for (int i = m_dirs.size() - 1; i >= 0; i--) {
					File f = (File) m_dirs.remove(i);
					if (f.isDirectory()) {
						if (f.listFiles().length == 0) {
							logger.info("del enpty floder "
									+ f.getAbsolutePath());
							f.delete();
						}
					}
				}
			} else {
				logger.info(" get list （m_dirs）is null");
			}
		} catch (Exception ex) {
			logger.info("error in rootDelete : " + ex.getMessage());
		}
	}

	/** 
	 * delete empty floder
	 * @param dir String
	 */
	public void deleteDirs(String dir) {
		try {
			File dirFile = new File(dir);
			m_root = dirFile;
			m_dirs = new ArrayList<File>();
			if (!m_root.isDirectory()) { //if is dir 
				logger.info(m_root.toString() + " is not dir");
			} else {
				m_dirs.add(m_root);
				this.visitAll(m_root); //get all file and dir at m_root
				this.rootDelete(); //delete all file at m_root 
			}
		} catch (Exception ex) {
			logger.info("error in deleteDirs : " + ex.getMessage());
		}
	}

	/**
	  * 向文件末尾追加文件，如果文件不存在首先创建它. <p>
	  *
	  * @param fileName		文件绝对路径
	  * @param content		要写入的文件
	  * void
	  * @exception  
	  */
	public void addContentToEndOfFile(String fileName, String content) {
		try {
			//logger.info("save " + content + " into " + fileName);
			File file = new File(fileName);
			if (!file.exists()) {
				this.newFile(fileName);
			}
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件 
			FileWriter writer = new FileWriter(fileName, true);
			content = content + "\n";
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			  logger.error("文件写入操作失误,文件名\t"+fileName+"\t文件内容"+content);
	          logger.error(e.getMessage());     
		}
	}
	 /**
	  * 向文件末尾追加文件，如果文件不存在首先创建它. <p>
	  * 与addContentToEndOfFile的区别是增加对文件编码的设定.<p>
	  * @param fileName		指定文件名
	  * @param content		要追加写入的内容
	  * void
	  * @exception 
	  * @see funciton addContentToEndOfFile(fileName,content) 
	  */
	public void addContentToEndOfFile2(String fileName, String content) {
		BufferedWriter out = null;
		FileOutputStream stream = null;// provides file access  
		OutputStreamWriter writer;// writes to the file  
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				this.newFile(fileName);
			}
			stream = new FileOutputStream(fileName, true);
			writer = new OutputStreamWriter(stream, "UTF-8");
			out = new BufferedWriter(writer);
			content = content + "\n";
			out.write(content);
			//logger.info("save " + content + " into " + fileName);
		} catch (Exception e) {
			logger.error("文件写入操作错误,文件名\t" + fileName + "\t文件内容" + content);
			logger.error(e.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.error("文件输出流关闭错误,文件名\t" + fileName + "\t文件内容" + content);
				logger.error(e.getMessage());
			}
		}      
	    } 
	
	/**
	  * 在本地操作问文件，将内容cotent写入文件file，采用覆盖先前内容的形式写入. <p>
	  *
	  * @param fileName
	  * @param content
	  * void
	  * @exception  
	  * @see addContentToEndOfFile2(String fileName,content)
	  */
	public void clearAndWrite(String fileName,String content){
		FileOutputStream stream = null;// provides file access  
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				this.newFile(fileName);
			}
			stream = new FileOutputStream(fileName);
			content = content + "\n";
			stream.write(content.getBytes("UTF-8"));
			stream.flush();
			//logger.info("将内容\t"+content+"写入文件[覆盖原有内容]\t" + fileName);
		} catch (Exception e) {
			logger.error("文件写入操作错误,文件名\t" + fileName + "\t文件内容" + content);
			logger.error(e.getMessage());
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				logger.error("文件输出流关闭错误,文件名\t" + fileName + "\t文件内容" + content);
				logger.error(e.getMessage());
			}
		}      
	}
	
	public void write2file(String fileName,String content,String charset){
		FileOutputStream stream = null;// provides file access  
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				this.newFile(fileName);
			}
			stream = new FileOutputStream(fileName);
			if(charset == null || charset.trim().equals("")){
				stream.write(content.getBytes());
			}else{
				stream.write(content.getBytes(charset));
			}
			stream.flush();
			//logger.info("将内容\t"+content+"写入文件[覆盖原有内容]\t" + fileName);
		} catch (Exception e) {
			logger.error("文件写入操作错误,文件名\t" + fileName + "\t文件内容" + content);
			logger.error(e.getMessage());
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				logger.error("文件输出流关闭错误,文件名\t" + fileName + "\t文件内容" + content);
				logger.error(e.getMessage());
			}
		}
	}      
	/**
	 * 功能：插入某行记录到文件的指定行 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param fileName	文件名
	 * @param content 	内容	
	 * @param lineNum	行号
	 * void
	 */
	public void writeLine(String fileName,String content,int lineNum){
		BufferedWriter out = null;
		FileOutputStream stream;// provides file access
		OutputStreamWriter writer;// writes to the file
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				 this.newFile(fileName);
			}
			//创建一个临时文件
			File temp = new File(file.getParent() + "/temp.ini");
			stream = new FileOutputStream(temp, true);
			writer = new OutputStreamWriter(stream, "UTF-8");
			out = new BufferedWriter(writer);
			
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader read = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			String line = "";
			
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (i == lineNum) {//当行号与设定行号相同时，将内容写入文件
					out.write(content + "\n");
				}
				out.write(line + "\n");
				i++;
			}
			reader.close();
			read.close();
			fis.close();
			out.close();
			file.delete();
			temp.renameTo(file);
			logger.info("save " + content + " into " + fileName);
		} catch (Exception e) {
			logger.error("文件写入操作错误,文件名\t" + fileName + "\t文件内容" + content);
			logger.error(e.getMessage());
		}  
	}
	public void zipFile(String filePath){
		File sourceFile = new File(filePath);
		zipFile(sourceFile,sourceFile.getName());
		
	}
	public void zipFile(String filePath,String zipFileName){
		File sourceFile = new File(filePath);
		zipFile(sourceFile,zipFileName);
	}
	public void zipFile(File sourceFile,String zipFileName){
		try{
			newFile(zipFileName);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			FileInputStream fis = new FileInputStream(sourceFile);
			out.putNextEntry(new ZipEntry(sourceFile.getName()));
			byte []b =new byte[(int)sourceFile.length()];
			if(fis.read(b) != -1){
				out.write(b);
			}
			out.closeEntry();
			out.close();
			fis.close();
		}catch(Exception e){
			logger.error("创建压缩文件时，出错\t"+sourceFile.getAbsolutePath());
			logger.error("错误原因\t"+e.getMessage());
		}
	}
	public void transfer(File sourceFile,String destination){
		if(sourceFile.getName().endsWith(".zip")){
			transferFile(sourceFile, destination);
		}else{
			zipFile(sourceFile,destination);
			sourceFile.delete();
		}
	}
	public void transfer(String filePath,String destination){
		File sourceFile = new File(filePath);
		//String folderPath = destionationPath+MD5Util.md5Path(filePath);
		//newFolder(folderPath);
	    //destionationPath = folderPath + "/" +sourceFile.getName()+".zip";
		transfer(sourceFile,destination);
		//logger.info("File Transfer sourcePath:"+filePath+"\tdestination"+destination);
	}
	public String getContent(String path){
		File f = new File(path);
		return getContent(f);
	}
	public String getContent(File f){
		String content = null;
		if(f.length() > 0){
			try {
				FileInputStream fis = new FileInputStream(f);
				BufferedInputStream in = new BufferedInputStream(fis);
				byte[] b = new byte[(int)f.length()];
				if(in.read(b) != -1){
					content = new String(b,"UTF-8");
//					content = new String(b,"gbk");
				}
				in.close();
				fis.close();
			}catch(Exception e){
				logger.error("读取文件内容出错\t"+f.getAbsolutePath());
			}
		}
		return content;
	}
	public String getContent(String path,String charset){
		File f = new File(path);
		return getContent(f,charset);
	}
	public String getContent(File f,String charset){
		String content = null;
		if(f.length() > 0){
			try {
				FileInputStream fis = new FileInputStream(f);
				BufferedInputStream in = new BufferedInputStream(fis);
				byte[] b = new byte[(int)f.length()];
				if(in.read(b) != -1){
					if(charset == null || "".equals(charset.trim())){
						content = new String(b,"UTF-8");
					}else{
						content = new String(b,charset);
					}
				}
				in.close();
				fis.close();
			}catch(Exception e){
				logger.error("读取文件内容出错\t"+f.getAbsolutePath());
			}
		}
		return content;
	}
	public String getContents(File f){
		String content = null;
		try{
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream in = new BufferedInputStream(fis);
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int num;
			while ((num = in.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
				System.out.println(num);
			}
			content = new String(baos.toByteArray());
			in.close();
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return content;
	}
	public String readZipFile(String file) {
		String content = "";
		try {
			ZipFile zf = new ZipFile(file);
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry ze;
			while ((ze = zin.getNextEntry()) != null) {
					BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze),"UTF-8"));
					String line;
					while ((line = br.readLine()) != null) {
						content += line;
					}
					br.close();
			}
			zin.closeEntry();
			in.close();
			zf.close();
		} catch (Exception e) {
			logger.error("读取压缩文件出错,\t"+file);
			e.printStackTrace();
		}
		return content;
	}  
	public String readGZipFile(String filepath){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String content = "";
		try{
			  GZIPInputStream in = new GZIPInputStream(new FileInputStream(filepath));
			  byte[] b = new byte[1024];
			  while(true){
				   int i = in.read(b) ;
				   if(i == -1){
					   break;
				   }
				   baos.write(b, 0, i);
				   b = new byte[1024];
			  }
			  content =  new String(baos.toByteArray(),"UTF-8");
			  in.close();
			  baos.close();
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return content;
	}
	public String readDeflate(File f) {
		String _content = null;
		try {
			FileInputStream in = new FileInputStream(f);
			_content = readDeflate(in);
			in.close();
		} catch (Exception ex) {
			logger.error("读取deflate压缩文件出错,\t"+f);
		}
		return _content;
	}
	public String readDeflate(InputStream in) throws Exception{
		String _content = null;
		java.io.BufferedInputStream bis = new java.io.BufferedInputStream(in);
		java.util.zip.InflaterInputStream zipInput = new java.util.zip.InflaterInputStream(
				bis, new Inflater(true));
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int num;
		while ((num = zipInput.read(buf, 0, buf.length)) != -1) {
			baos.write(buf, 0, num);
		}
		_content = new String(baos.toByteArray());
		zipInput.close();
		bis.close();
		baos.close();
		return _content;
	}
 
	public void transferFile(File f ,String detin){
		newFile(detin);
		f.renameTo(new File(detin));
	}
	public List<String> getLines(File file) {
		List<String> list = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader read = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			String line = "";
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			reader.close();
			read.close();
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	public int getAllRows(String path){
		File file = new File(path);
		int rows = 0;
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader read = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			@SuppressWarnings("unused")
			String line = null;
			while ((line = reader.readLine()) != null) {
				rows++;
			}
			reader.close();
			read.close();
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rows;
	}
	public void getLine(String path,ILineHandle<?> handle){
		File f = new File(path);
		if(f.exists()){
			getLine(f, handle);
		}else{
			logger.error(path+" is not found!");
		}
	}
	public void getLine(File file,ILineHandle<?> handle){
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader read = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			String line = null;
			while ((line = reader.readLine()) != null) {
				handle.lineHandle(line);
			}
			reader.close();
			read.close();
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">是否需要对注释进行过滤</font> <br>
	 * @param path
	 * @param hasNote
	 * @param handle 是否需要对注释进行过滤
	 * void
	 */
	public void getLine(String path,boolean hasNote,ILineHandle<?> handle){
		File f = new File(path);
		if(f.exists()){
			getLine(f, hasNote,handle);
		}
	}
	public void getLine(File file,boolean hasNote,ILineHandle<?> handle){
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader read = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if(hasNote && line.startsWith("#")){//对很多配置文件读取的时候，发现需要注释
					continue;
				}
				handle.lineHandle(line);
			}
			reader.close();
			read.close();
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 功能：接收字符串内容，以gzip压缩格式写入文件 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param filepath
	 * @param content
	 * void
	 */
	public void writeGZipFile(String filepath,String content){
		try{
			newFile(filepath);
			BufferedOutputStream out = new BufferedOutputStream(
			        new GZIPOutputStream(new FileOutputStream(filepath)));
			out.write(content.getBytes());
			out.close();
		}catch(Exception e){
			logger.error("创建压缩文件时，出错\t");
			logger.error("错误原因\t"+e.getMessage());
		}
	}
	
	/**
	 * 功能：接收gzip压缩流，直接写入文件 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param filepath
	 * @param in
	 * @throws IOException
	 * void
	 */
	public void writeGZipFile(String filepath,InputStream in) throws IOException {
		writeStream2File(filepath,in);
	}
	/**
	 * 功能：将一个输入流写入文件 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param filepath
	 * @param in
	 * @throws IOException
	 * void
	 */
	public void writeStream2File(String filepath,InputStream in)throws IOException {
		newFile(filepath);
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		bis = new BufferedInputStream(in);
		fos = new FileOutputStream(filepath);
		byte []buf = new byte[1024];
		int size = 0;
		while((size = bis.read(buf)) != -1){
			fos.write(buf, 0, size);
		}
		fos.close();
		bis.close();
	}
	public static void main(String []args){
		//TTConst.initLogger();
		FileOperate operate = new FileOperate();
		// operate.writeLine("c:/whereis", "i love you", 2);
		// operate.zipFile("google.com.html", "F:\\temp\\");
		String filePath = "C:/Users/wusx/Desktop/MD5Util.java";
		String destionationPath = "F:/temp/ds/a.zip";
		operate.transfer(filePath, destionationPath);
	}
	public interface ILineHandle<T>{
		T lineHandle(String line);
	}
}
