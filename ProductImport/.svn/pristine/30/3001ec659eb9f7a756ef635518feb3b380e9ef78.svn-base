/*
 * CatalogProduct.java	 <br>
 * 2011-10-31<br>
 * com.shinetech.bean <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CatalogProduct implements Comparable<CatalogProduct>{
	public CatalogProduct(){
		
	}
	private String sku;//产品sku，对应product_dress中的id
	private int score;//产品的得分，对应商城中的sort_score属性
	private int entityId;//产品在商城中的id
	/**
	 * catalogTagsMap:大类别对应的tag集合
	 */ 
	private Map<String,ArrayList<String>> catalogTagsMap;
	
	/*
	 * @return the catalogTagsMap
	 */
	public Map<String, ArrayList<String>> getCatalogTagsMap() {
		return catalogTagsMap;
	}
	/*
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}
	/*
	 * @return the entityId
	 */
	public int getEntityId() {
		return entityId;
	}
	/*
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/*
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}
	/*
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/*
	 * @param catalogTagsMap the catalogTagsMap to set
	 */
	public void setCatalogTagsMap(Map<String, ArrayList<String>> catalogTagsMap) {
		this.catalogTagsMap = catalogTagsMap;
	}
	
	/*
	 * @param entityId the entityId to set
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public CatalogProduct(String sku, int score) {
		super();
		this.sku = sku;
		this.score = score;
	}
	
	public CatalogProduct(String sku, int score,
			Map<String, ArrayList<String>> catalogTagsMap) {
		super();
		this.sku = sku;
		this.score = score;
		this.catalogTagsMap = catalogTagsMap;
	}
	
	public CatalogProduct(int score, int entityId) {
		super();
		this.score = score;
		this.entityId = entityId;
	}
	public String toString(){
		return sku+":"+score;
	}
	
	public static void main(String[] args) {
		CatalogProduct product1 = new CatalogProduct("sku1", 10);
		CatalogProduct product2 = new CatalogProduct("sku2", 17);
		CatalogProduct product3 = new CatalogProduct("sku3", 8);
		List<CatalogProduct> list = new ArrayList<CatalogProduct>();
		list.add(product1);
		list.add(product2);
		list.add(product3);
		System.out.println(list);
		
		Collections.sort(list, new Comparator<CatalogProduct>() {
			@Override
			public int compare(CatalogProduct o1, CatalogProduct o2) {
				if(o1.getScore() > o2.getScore()){
					return 1;
				}else if(o1.getScore() == o2.getScore()){
					return 0;
				}else{
					return -1;
				}
			}
		});
		
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);
		
	}
	@Override
	public int compareTo(CatalogProduct o2) {
		if(this.getScore() > o2.getScore()){
			return 1;
		}else if(this.getScore() == o2.getScore()){
			return 0;
		}else{
			return -1;
		}
	}
}
