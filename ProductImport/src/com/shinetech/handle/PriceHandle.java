/*
 * PriceHandle.java	 <br>
 * 2011-10-18<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * onenote:///\\SERVER2\Onenote协作区\公共\Dress\temp.one#报价计算&section-id={DE3E3D96-3329-44DA-AE19-E068EEF44C03}&page-id={2A50FC80-B20C-4824-91F3-0CF7A51723A3}&object-id={7E486F0E-EA6E-0758-2990-C524CE892023}&F
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class PriceHandle {
	
	private static Map<String,String> map = new HashMap<String, String>();
	
	/**
	 * singlePriceProMap:存放单独报价的产品id和成本价 （人民币）
	 */ 
	private static Map<Long,Integer> singlePriceProMap = new HashMap<Long, Integer>();
	private Random random = new Random();
	public PriceHandle(){
		init();
	}
	private void init(){
		if(map.isEmpty()){
			map.put("Cathedral Train", "450-550");
			map.put("Monarch Train", "450-550");
			map.put("Panel Train", "450-550");
			map.put("Watteau Train", "450-550");
			
			map.put("Sweep Train", "350-450");
			map.put("Court Train", "350-450");
			map.put("Chapel Train", "350-450");
			
			map.put("Ball Gown", "350-400");
			
			map.put("Floor Length", "300-350");
			
			map.put("A-line", "250-350");
			map.put("Knee Length", "250-350");
			map.put("Tea Length", "250-350");
			map.put("Ankle Length", "250-350");
			map.put("Hi-Lo", "250-350");
			map.put("Asymmetric", "250-350");
			
			map.put("Mini", "200-250");
			/**单独报价的产品*/
			String ss[] = {"915.jpg	270","1802.png	270","1715.png	270","355.png	280","357.png	280","428.png	280","730.png	280","2029.jpg	280","1800.png	300","3092.jpg	300","3159.jpg	300","1284.png	300","803.jpg	340","473.png	350","808.jpg	360","807.jpg	360","853.jpg	360","1411.jpg	370","2117.jpg	370","2097.jpg	370","2089.jpg	370","2082.jpg	370","150.jpg	370","877.jpg	370","1409.jpg	370","1423.jpg	370","825.jpg	370","1373.jpg	370","897.jpg	370","838.jpg	370","1391.jpg	370","1378.jpg	370","1374.png	370","2393.jpg	370","879.jpg	370","2404.jpg	370","2080.jpg	370","9702.jpg	370","2678.jpg	370","421.png	380","793.jpg	380","840.jpg	380","2596.jpg	380","9967.jpg	380","2640.jpg	380","1135.jpg	400","595.jpg	400","156.png	400","1080.jpg	400","1384.jpg	400","2568.jpg	400","2629.jpg	400","1424.jpg	400","2562.jpg	400","2564.jpg	400","2572.jpg	400","9939.jpg	420","10062.jpg	420","10519.jpg	420","2402.jpg	420","10554.jpg	420","9970.jpg	420","10206.jpg	430","10581.jpg	450","10.jpg	450","9547.jpg	450","2611.jpg	450","10250.jpg	460","10272.jpg	460","10282.jpg	460","10536.jpg	460","85.png	460","10502.jpg	460","10514.jpg	460","10190.jpg	460","10348.jpg	470","10078.jpg	470","10107.jpg	470","10185.jpg	480","10129.jpg	480","252.png	520"};
			for(String s : ss){
				String temp[] = s.split("\t");
				int price = Integer.parseInt(temp[1]);//成本价
				String pid = temp[0].substring(0, temp[0].indexOf("."));
				singlePriceProMap.put(Long.parseLong(pid), price);
			}
		}
	}
//	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param pid
	 * @param tags
	 * @return
	 * String[]  实际销售价、选中标签、 成本价区间、 成本价
	 */
	public String[] computePrice(Long pid,List<String> tags){
		if(singlePriceProMap.get(pid) != null){
			int pRandom = singlePriceProMap.get(pid);
			double d = random.nextDouble()*0.2+2.8;
			double price = (pRandom*d)/6+5;
			return new String[]{(int)Math.rint(price)+"","Signal","Special",pRandom+""};
		}
		String key = "",value = "";
		if(tags.contains("Cathedral Train")){
			key = "Cathedral Train";
		}else
		if(tags.contains("Monarch Train")){
			key = "Monarch Train";
		}else
		if(tags.contains("Panel Train")){
			key = "Panel Train";
		}else
		if(tags.contains("Watteau Train")){
			key = "Watteau Train";
		}else
		if(tags.contains("Sweep Train")){
			key = "Sweep Train";
		}else if(tags.contains("Court Train")){
			key = "Court Train";
		}else if(tags.contains("Chapel Train")){
			key = "Chapel Train";
		}else if(tags.contains("Ball Gown")){
			key = "Ball Gown";
		}else if(tags.contains("Floor Length")){
			key = "Floor Length";
		}else if(tags.contains("A-line")){
			key = "A-line";
		}else if(tags.contains("Knee Length")){
			key = "Knee Length";
		}else if(tags.contains("Tea Length")){
			key = "Tea Length";
		}else if(tags.contains("Ankle Length")){
			key = "Ankle Length";
		}else if(tags.contains("Hi-Lo")){
			key = "Hi-Lo";
		}else if(tags.contains("Asymmetric")){
			key = "Asymmetric";
		}
		else if(tags.contains("Mini")){
			key = "Mini";
		}
		value = map.get(key);
		if(value == null || value.equals("")){
			return new String[]{"0",key,value,"0"};
		}else{
			String []temp = value.split("-");
			int p1 = Integer.parseInt(temp[0]);
			int p2 = Integer.parseInt(temp[1]);
			int pRandom = p1 + random.nextInt(p2-p1);
			double d = random.nextDouble()*0.2+2.8;
			double price = (pRandom*d)/6+5;
//			logger.info(pRandom+":"+d+":"+price);
			//return (int)Math.rint(price);
			return new String[]{(int)Math.rint(price)+"",key,value,pRandom+""};
		}
	}
}
