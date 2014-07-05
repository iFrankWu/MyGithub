/*
 * ColorService.java	 <br>
 * 2011-10-11<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;


/**
 * 生成一个产品时，如果有bridal gown TAG，那么该产品的颜色值为左边的四种，如果没有那个TAG，那么检查该产品的布料，根据布料确定颜色。
 * 
 * 详情见:onenote:///\\SERVER2\Onenote协作区\公共\Dress\编辑.one#颜色列表&section-id={338DD4CC-3CDE-4AAC-A0ED-F485D50B010A}&page-id={6A78A721-7B98-4040-BE2B-6AF9BB2DC005}&object-id={125ACEC1-23FF-0ABE-12D1-25E8FF558926}&F
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ColorHandle implements IHandle<String>{
//	private static String bridal[] = {"The same as picture","Ivory","White","Champagne","Pink"};
	private FileOperate op = new FileOperate();
	public static void main(String[] args) {
		ColorHandle handle = new ColorHandle();
		handle.print();
		
	}
	public void init(){
		if(!map.isEmpty())return;
		File file = new File("assets/color");
		file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(final File pathname) {
				if(pathname.getName().contains(".svn")){
					return false;
				}
				op.getLine(pathname, new ILineHandle<String>() {

					@Override
					public String lineHandle(String line) {
						String tagName = pathname.getName().split(".txt")[0].trim();
						List<String> list = map.get(tagName);
						if(list == null){
							list = new ArrayList<String>();
						}
						list.add(line.trim());
						map.put(tagName, list);
						return null;
					}
				});
				return false;
			}
		});
	}
	/**
	 * 功能： 根据产品的tag值，生成产品颜色可选值 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param list
	 * @return
	 * String[]
	 */
	private static Map<String,List<String>> map = new HashMap<String,List<String>>();
	public ColorHandle(){
		init();
	}
	public void print(){
		for(String key : map.keySet()){
			System.out.println(key+":"+map.get(key));
		}
	}
	/**
	 * colorSet:一个产品的颜色的并集
	 */ 
	private Set<String> colorSet = new LinkedHashSet<String>();
	@Override
	public void handle(String... t) {
		if(t.length> 0){
			String tag = t[0].trim();
			List<String> list  = map.get(tag);
			if(list != null){
				colorSet.addAll(list);
			}
		}
	}
	/**
	 * 功能：对于颜色值的如果计算结果为空，那么默认为satin颜色组<br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @return
	 * Set<String>
	 */
	public Set<String> getColorSet(){
		if(colorSet.isEmpty()){
			colorSet.addAll(map.get("Satin"));
		}
		return colorSet;
	}
	/**
	 * 功能：返回一个产品的颜色值，用于custom option 
	 * 对于颜色值的如果计算结果为空，那么默认为satin颜色组<br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param tags
	 * @return
	 * Set<String>
	 */
	public Set<String> getColorSet(List<String> tags){
		for(String tag : tags){
			List<String> list  = map.get(tag.trim());
			if(list != null){
				colorSet.addAll(list);
			}
		}
		if(colorSet.isEmpty()){
			colorSet.addAll(map.get("Satin"));
		}
		return colorSet;
	}
}
