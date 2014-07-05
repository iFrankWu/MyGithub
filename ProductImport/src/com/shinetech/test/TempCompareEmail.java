/*
 * TempCompareEmail.java	 <br>
 * 2012-2-4<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.util.FileOperate;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TempCompareEmail {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		TempCompareEmail temp = new TempCompareEmail();
		List<String> list1 = temp.getContent("data/a.txt");
		List<String> list2 = temp.getContent("data/ga.txt");
		List<String> list = new ArrayList<String>();
		for(String s1 : list1){
			if(list2.contains(s1)){
				list.add(s1);
			}
		}
		System.out.println(list.size());
		temp.getWanted("data/a.txt", list,true,"data/same.txt");
		temp.getWanted("data/a.txt", list,false,"data/a_rest.txt");
		temp.getWanted("data/ga.txt", list,false,"data/ga_rest.txt");
	}
	private FileOperate op = new FileOperate();
	public List<String> getContent(String path){
		List<String> list = new ArrayList<String>();
		 String fileContent = op.getContent(path);
		 String []contents = fileContent.split("--------------------------");
		 for(String s : contents){
			 String temp[] = s.split("\n");
			 int size = temp.length;
			 if(size == 7){
				 String newcontent = "";
				 for(int i =3;i<7;i++){
					 newcontent += temp[i]+"\n";
				 }
				 list.add(newcontent);
			 }
		 }
		 return list;
	}
	public void getWanted(String path,List<String> list,boolean isSame,String outpath){
		File f = new File(outpath);
		if(f.exists()){
			f.delete();
		}
		 String fileContent = op.getContent(path);
		 String []contents = fileContent.split("--------------------------");
		 for(String s : contents){
			 String temp[] = s.split("\n");
			 int size = temp.length;
			 if(size == 7){
				 String newcontent = "";
				 for(int i =3;i<7;i++){
					 newcontent += temp[i]+"\n";
				 }
				 if(isSame){
					 if(list.contains(newcontent)){
						op.addContentToEndOfFile(outpath,"--------------------------");
						op.addContentToEndOfFile(outpath, s);
					 }
				 }else{
					 if(!list.contains(newcontent)){
						 op.addContentToEndOfFile(outpath,"--------------------------");
						 op.addContentToEndOfFile(outpath, s);
					 }
				 }
				
			 }
		 }
	}

}
