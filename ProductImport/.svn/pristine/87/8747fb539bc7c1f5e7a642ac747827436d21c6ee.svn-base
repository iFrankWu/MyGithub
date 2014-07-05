/*
 * PetticoatHandle.java	 <br>
 * 2011-11-14<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shinetech.dao.DressDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class PetticoatHandle {
	private static String  Petticoats[] = "大摆衬裙,大摆束腰衬裙,小摆衬裙,小摆束腰衬裙,拖尾衬裙".split(",");
	public static  Map<String,Integer> PetticoatsIdMap = new HashMap<String, Integer>();
	//大摆衬裙
	private static String LargeSlip[] = "Ball Gown;Empire;Natural;Ankle Length;Floor Length;Sweep Train;Court Train".split(";");
//	大摆束腰衬裙
	private static String LargeWaistSkirt[] = "Ball Gown;Dropped;Ankle Length;Floor Length;Sweep Train;Court Train".split(";");
	//小摆衬裙
	private static String SmallSlip[] = "A-line;Empire;Natural;Ankle Length;Floor Length;Sweep Train;Court Train".split(";");
	//小摆束腰衬裙
	private static String SmallWaistSkirt[] = "A-line;Dropped;Ankle Length;Floor Length;Sweep Train;Court Train".split(";");
	//拖尾衬裙
	private static String TailSkirt[] = "Chapel Train;Cathedral Train;Monarch Train".split(";");
	
	private static String [][]tags = {LargeSlip,LargeWaistSkirt,SmallSlip,SmallWaistSkirt,TailSkirt};
	 
	private Set<String> pettSet = new HashSet<String>();
	public PetticoatHandle(){
		if(PetticoatsIdMap.isEmpty()){
			 try {
				PetticoatsIdMap.put(Petticoats[0], 245);
				PetticoatsIdMap.put(Petticoats[1], 246);
				PetticoatsIdMap.put(Petticoats[2], 247);
				PetticoatsIdMap.put(Petticoats[3], 248);
				PetticoatsIdMap.put(Petticoats[4], 249);
				dao.getPetticoatTag(Arrays.asList(Petticoats), new ResultSetHandler() {
					
					@Override
					public void handle(ResultSet rs) {
						try {
							System.out.println(rs.toString());
							int tagId = rs.getInt("tag_id");
							String tagName = rs.getString("tag_name");
							System.out.println(tagId+":"+tagName);
							PetticoatsIdMap.put(tagName, tagId);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
				System.out.println(PetticoatsIdMap);
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		
		petticoat();
	}
	private DressDAO dao = new DressDAO();
	private static Map<String,Map<String,ArrayList<String>>> pettMap = new HashMap<String, Map<String,ArrayList<String>>>();
	public void petticoat(){
		if(pettMap.isEmpty()){
			try {
				for(int i = 0; i < Petticoats.length;i++){
					final Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>(); 
					dao.getPetticoatTag(Arrays.asList(tags[i]), new ResultSetHandler() {
						
						@Override
						public void handle(ResultSet rs) {
							String tagName;
							try {
								tagName = rs.getString("tag_name").trim();
								String catalogName = rs.getString("catalog_name").trim();
								ArrayList<String> list = map.get(catalogName);
								if(list == null){
									list = new ArrayList<String>();
									map.put(catalogName, list);
								}
								list.add(tagName);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					});
					pettMap.put(Petticoats[i], map);
				}
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}
	public  Set<String> getPett(Map<String,ArrayList<String>> map){
		for(String pett : pettMap.keySet()){
			boolean isPett = true;
			Map<String,ArrayList<String>> pMap  = pettMap.get(pett);//衬裙对应的  分类和子标签 的map
			for(String catalog : pMap.keySet()){
				List<String> pettTags = pMap.get(catalog);//从衬裙中找出标签
				List<String> proTags = map.get(catalog);//从产品tag中找
				if(Collections.disjoint(pettTags, proTags)){//无交集
					isPett = false;
				}
			}
			if(isPett){
				pettSet.add(pett);
			}
		}
		return pettSet;
	}
}
