/*
 * FormatImagePath.java	 <br>
 * 2012-1-9<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.shinetech.dao.DressDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class FormatImagePath {
	/**
	 * oldNewPathMap:保存旧路径和新路径的对比关系
	 */ 
	private Map<String, String> oldNewPathMap = new HashMap<String, String>();
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * hasRenameDirSet:保存那些已经被重名的原始路径
	 */ 
	private Set<String> hasRenameDirSet = new HashSet<String>();
	private DressDAO dressDAO = new DressDAO();
	public void format(){
		try {
			dressDAO.getAllImage(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try {
						String path = rs.getString("path");
						String newpath = handlePath(path);
						dressDAO.updatePicPath(rs.getInt("id"), newpath);
//						op.addContentToEndOfFile2("data/picture.path.tsv", rs.getInt("id")+"\t"+rs.getInt("original_product_id")+"\t"+newpath.substring(0, newpath.lastIndexOf("/")));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	private FileOperate op = new FileOperate();
	private String handlePath(String path){
		String pathArr[] = path.split("/");
		
		for(int i= 0;i<pathArr.length-1;i++){
			String dir = pathArr[i];
			if(oldNewPathMap.get(dir) == null){//说明这个目录需要处理
				String newdir = validDir(dir, false);
				if(newdir != null){
					oldNewPathMap.put(dir, newdir);
					//需要重命名文件夹
					StringBuffer renameDir = new StringBuffer();
					StringBuffer renameToDir = new StringBuffer();
					for(int j=0;j<=i;j++){
						renameDir.append(pathArr[j]);
						renameDir.append("/");
						renameToDir.append(oldNewPathMap.get(pathArr[j]) == null ?pathArr[j] :oldNewPathMap.get(pathArr[j]));
						renameToDir.append("/");
					}
					if(hasRenameDirSet.add(renameDir.toString())){//当该目录第一次出现，需要重命名，否则跳过
						File oriDir = new File(Const.ENTRY_DIR+renameDir.toString());
						if(oriDir.exists()){
							oriDir.renameTo(new File(Const.ENTRY_DIR+renameToDir.toString()));
							op.addContentToEndOfFile2("data/renameto.txt", renameDir.toString()+" ==> "+renameToDir.toString());
						}
					} 
				}
			}
		}
		
		String name = pathArr[pathArr.length-1];
		String newImageName = validDir(name, true);
		
		//最后要生成新的图片路径
		StringBuffer sb = new StringBuffer();
		for(int i= 0;i<pathArr.length-1;i++){
			String newpath = pathArr[i];
			String s = oldNewPathMap.get(newpath)== null ? newpath : oldNewPathMap.get(newpath);
			sb.append(s);
			sb.append("/");
		}
//		sb.deleteCharAt(sb.length()-1);
		String updateDir = sb.toString();
		
		String updatePath = updateDir + name;
		if(newImageName != null){
			File f = new File(Const.ENTRY_DIR+updateDir+name);
			logger.info("图片名存在非法字符："+f.getAbsolutePath());
			f.renameTo(new File(Const.ENTRY_DIR+updateDir+newImageName));
			updatePath = updateDir+newImageName;
		}
		op.addContentToEndOfFile2("data/picture.path", updatePath);
		return updatePath;
//		System.out.println(updatePath);
		
	}
	private Pattern imageNamePattern = Pattern.compile("[^A-Za-z0-9_\\-./]");
	private String validDir(String path,boolean isImageName){
		boolean isValid = true;//是否存在非法字符，true 表示不存在
		Matcher	  matcher = imageNamePattern.matcher(path);
		while(matcher.find()){
			isValid = false;
			String s = matcher.group();
			path = path.replace(s, "");
		}
		if(!isValid)
			return path;
		return null;
	}
	public void test(){
//		String rs = validDir("dadssa.da,===,...",true);
		op.getLine("assets/test", new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				String s = validDir(line,false);
				System.out.println("mv \""+line+"\" "+s);
				return null;
			}
			
		});
//		handlePath("promdressshop/2011-Sherri-H3&*$%$#@===ill-Homecoming/Prom-...Dress-Sherri-Hill-2537.aspx/253712906547681.png");
//		System.out.println(rs);
	}
	/**
	 * 功能：检查图片是否更新成功
	 * 	检查的方法：当图片路径对应的文件存在，以及该路径不包含特殊字符 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	public void checkUpdateResult(){
		try {
			dressDAO.getAllImage(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
						String path = Const.ENTRY_DIR+rs.getString("path");
						String rt = validDir(path,false);
						if(rt == null){
							File file = new File(path);
							if(!file.exists()){
								logger.error("File Not exists:"+rs.getInt("id")+"\t"+path);
							}
						}else{
							logger.error("Valid Fail Path:"+rs.getInt("id")+"\t"+path);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Const.initLogger();
		FormatImagePath format = new FormatImagePath();
//		format();
//		format.format();
		format.checkUpdateResult();
	}
}

