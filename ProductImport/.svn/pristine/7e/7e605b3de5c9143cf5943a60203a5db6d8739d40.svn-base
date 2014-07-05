/*
 * GetNewAddShowRoom.java	 <br>
 * 2011-12-20<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class GetNewAddShowRoom {
	private FileOperate op = new FileOperate();
	public static void main(String[] args) {
		GetNewAddShowRoom showroom = new GetNewAddShowRoom();
//		showroom.compare();
		showroom.chongfu();
	}
	private int i = 0;
	public void chongfu(){
		final Set<String> set = new HashSet<String>();
		
		op.getLine("assets/showroom.1220.tsv", new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				i++;
				String s[] = line.split("\t");
				if(s.length < 4){
					System.out.println("Error:"+i+line);
					return "";
				}
				if(set.add(s[1])){
					
				}else{
					System.out.println("关键词重复"+i+line);
				}
				return null;
			}
		});
	}
	public void compare(){
		File file = new File("assets/new_add_showroom.tsv");
		if(file.exists()){
			file.delete();
		}
		List<String> list1 = new ArrayList<String>();
		list1 = op.getLines(new File("assets/in.tsv"));//这个文件是包含先前内容的展厅文件
		List<String> list2 = op.getLines(new File("assets/in_svn6496257615424583224.tsv"));//这是先前展厅文件
		list1.removeAll(list2);//两者之差即新增的展厅
		for(String s : list1){
			op.addContentToEndOfFile2("assets/new_add_showroom.tsv", s);
		}
		
	}
}
