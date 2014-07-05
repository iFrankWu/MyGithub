/*
 * CatalogHandle.java	 <br>
 * 2011-10-26<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CatalogHandle {
	/**
	 * catalogTagMap:tag 与类别的对应关系
	 * 类属静态变量，确保每次生产产品时  读取"assets/catalog_tags"文件次数为1.
	 *
	 */ 
	private  static Map<String,HashSet<String>> catalogTagMap = new HashMap<String, HashSet<String>>();
	public CatalogHandle(){
		if(catalogTagMap == null || catalogTagMap.isEmpty()){
			FileOperate op = new FileOperate();
			op.getLine(new File(Const.CATALOG_TAGS),new ILineHandle<String>() {

				@Override
				public String lineHandle(String line) {
					if(line.startsWith("#")){
						return null;
					}
					String temp[] = line.split(";");
					String tags[] = temp[1].split(",");
					for(String tag : tags){
						HashSet<String> set = catalogTagMap.get(tag);
						if(set == null){
							set = new HashSet<String>();
							catalogTagMap.put(tag, set);
						}
						set.add(temp[0]);
						catalogTagMap.put(tag, set);
					}
					return null;
				}
			});
		}
	}
	public Set<String> getCatalog(List<String> tags){
		Set<String> catalogSet = new HashSet<String>();
		for(String tag : tags){
			Set<String> set = catalogTagMap.get(tag);
			if(set != null){
				catalogSet.addAll(set);
			}
 		}
		return catalogSet;
	}
	
}
