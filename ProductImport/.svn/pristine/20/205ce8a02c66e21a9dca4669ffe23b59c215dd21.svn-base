/*
 * ProductPriceService.java	 <br>
 * 2011-10-18<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.service;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.shinetech.dao.DressDAO;
import com.shinetech.handle.PriceHandle;
import com.shinetech.handle.TagHandle;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.FileOperate;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ProductPriceService {
	private DressDAO dao = new DressDAO();
	private Map<String,String> productMap = new HashMap<String, String>();
	private Random random = new Random();
	public void service(){
		File f = new File("data/has_price.tsv");
		if(f.exists())
			f.delete();
		f = new File("data/no_price.tsv");
		if(f.exists())
			f.delete();
		String ss[] = {"915.jpg	270","1802.png	270","1715.png	270","355.png	280","357.png	280","428.png	280","730.png	280","2029.jpg	280","1800.png	300","3092.jpg	300","3159.jpg	300","1284.png	300","803.jpg	340","473.png	350","808.jpg	360","807.jpg	360","853.jpg	360","1411.jpg	370","2117.jpg	370","2097.jpg	370","2089.jpg	370","2082.jpg	370","150.jpg	370","877.jpg	370","1409.jpg	370","1423.jpg	370","825.jpg	370","1373.jpg	370","897.jpg	370","838.jpg	370","1391.jpg	370","1378.jpg	370","1374.png	370","2393.jpg	370","879.jpg	370","2404.jpg	370","2080.jpg	370","9702.jpg	370","2678.jpg	370","421.png	380","793.jpg	380","840.jpg	380","2596.jpg	380","9967.jpg	380","2640.jpg	380","1135.jpg	400","595.jpg	400","156.png	400","1080.jpg	400","1384.jpg	400","2568.jpg	400","2629.jpg	400","1424.jpg	400","2562.jpg	400","2564.jpg	400","2572.jpg	400","9939.jpg	420","10062.jpg	420","10519.jpg	420","2402.jpg	420","10554.jpg	420","9970.jpg	420","10206.jpg	430","10581.jpg	450","10.jpg	450","9547.jpg	450","2611.jpg	450","10250.jpg	460","10272.jpg	460","10282.jpg	460","10536.jpg	460","85.png	460","10502.jpg	460","10514.jpg	460","10190.jpg	460","10348.jpg	470","10078.jpg	470","10107.jpg	470","10185.jpg	480","10129.jpg	480","252.png	520"};
		for(String s : ss){
			String temp[] = s.split("\t");
			int price = Integer.parseInt(temp[1]);
			double d = random.nextDouble()*0.4+1.8;
			price = (int)Math.rint((price*d+150)/6);
			productMap.put(temp[0].substring(0, temp[0].indexOf(".")), price+"");
		}
		try {
			dao.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					try{
						handleAProduct(rs.getLong("id"),  rs.getFloat("real_price")+"", rs.getString("owner_site"), rs.getString("original_url"));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	private PriceHandle priceHandle = new PriceHandle();
	private FileOperate op  = new FileOperate();
	private Logger logger = Logger.getLogger(this.getClass());
	private void handleAProduct(Long productId,String realPrice,String ownerSite,String url){
		String price = productMap.get(productId+"");
		if( price != null){
			op.addContentToEndOfFile2("data/has_price.tsv",productId+"\t"+realPrice+"\t"+price+"\tSpecial\t"+ownerSite+"\t"+url);
			return;
		}
		TagHandle handle = new TagHandle();
		try {
			dao.getTagInfoByPId(productId, handle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> tags = handle.getTags();
		String prices[] = priceHandle.computePrice(productId,tags);
		if(prices[0].equals("0")){
			op.addContentToEndOfFile2("data/no_price.tsv",productId+"\t"+realPrice+"\t"+prices[0]+"\t"+prices[1]+"\t"+ownerSite+"\t"+url);
		}else{
			op.addContentToEndOfFile2("data/has_price.tsv",productId+"\t"+realPrice+"\t"+prices[0]+"\t"+prices[1]+"\t"+ownerSite+"\t"+url);
		}
		logger.info(productId+"\t"+realPrice+"\t"+prices[0]);
	}
}
