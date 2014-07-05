/*
 * CompareProducData.java	 <br>
 * 2011-10-27<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CompareProducData {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		CompareProducData compare = new CompareProducData();
		try {
			compare.compare();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Map<String,ArrayList<String>> exportProductMap = new HashMap<String, ArrayList<String>>();
	private Map<String,ArrayList<String>> importProductMap = new HashMap<String, ArrayList<String>>();
 
	
	private void initMap(List<ArrayList<String>> list,Map<String,ArrayList<String>> map){
		ArrayList<String> row = list.get(0);
		for(int i =0;i<row.size(); i++){//这是列
			ArrayList<String> keyList = map.get(row.get(i));
			if(keyList == null){
				keyList = new ArrayList<String>();
				map.put(row.get(i), keyList);
			}
			for(int index = 1 ; index < list.size();index++){//这是处理行
					ArrayList<String> ary = list.get(index);
					if(ary.size() > i)
						keyList.add(ary.get(i));
			}
		}
	}
	public void compare() throws Exception{
		List<ArrayList<String>> importProdct =  new TestSplitCSV().csvfile2Array(Const.OUT_IMPORT_SIMPLE_CSV);
		List<ArrayList<String>> exportProduct = new TestSplitCSV().csvfile2Array("assets/export.csv");
//		for(ArrayList<String > ary : importProdct){
//			System.out.println(ary.size()+":"+ary);
//		}
		//System.exit(1);
		initMap(importProdct,importProductMap);
		
		initMap(exportProduct, exportProductMap);
		int count = 0;
		Map<String,ArrayList<String>> map = importProductMap.size() > exportProductMap.size() ?importProductMap:exportProductMap;
		for(String key : map.keySet()){
			ArrayList<String> imList = importProductMap.get(key);
			ArrayList<String> exList = exportProductMap.get(key);
			if(!equalsList(imList,exList)){
				System.out.println("------"+count+++"-------");
				System.out.println("不一致，key："+key);
				System.out.println("import:"+imList.size()+":"+imList);
				System.out.println("export:"+exList.size()+":"+exList);
			}
		}
	}
	private boolean equalsList(List<String> list1,List<String> list2){
		if(list1 == null){
			if(list2 != null){
				return false;
			}else{
				return true;
			}
		}else{
			if(list2 == null){
				return false;
			}else{
				if(list1.size() == list2.size()){
					Set<String> set1 = new HashSet<String>();
					set1.addAll(list1);
					Set<String> set2 = new HashSet<String>();
					set2.addAll(list2);
					if(set1.equals(set2)){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		}
	}
}
