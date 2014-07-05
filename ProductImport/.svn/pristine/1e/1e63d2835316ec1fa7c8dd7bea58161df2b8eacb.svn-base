/*
 * ProductScore.java	 <br>
 * 2011-10-31<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * onenote:///\\SERVER2\Onenote协作区\公共\Dress\编辑.one#展厅产品排序&section-id={338DD4CC-3CDE-4AAC-A0ED-F485D50B010A}&page-id={E9B7344A-E7A9-4513-8156-A105FC9C0D48}&object-id={F7BCA0CA-950E-0A6E-3517-469C3CBA9F8E}&F
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProductScoreHandle {
	public static Map<String,Integer> tag2ScoreMap = new HashMap<String, Integer>();
	static{
		tag2ScoreMap.put("第一级", 0);
		tag2ScoreMap.put("第二级", 2);
		tag2ScoreMap.put("第三级", 5);
		tag2ScoreMap.put("第四级", 8);
		tag2ScoreMap.put("主图不清晰", -10);
		tag2ScoreMap.put("后背图不好", -1);
		tag2ScoreMap.put("细节图不清晰", -1);
		tag2ScoreMap.put("有细节图", 2);
	}
	public static int getScore(List<String> tags){
		int score = 0;
		for(String tag : tags){
			Integer i = tag2ScoreMap.get(tag);
			if(i != null){
				score += i;
			}
		}
		return score;
	}
}	
