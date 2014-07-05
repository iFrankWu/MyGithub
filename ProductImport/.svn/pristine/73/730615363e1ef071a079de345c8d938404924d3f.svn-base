/*
 * ShowroomCategoryHandle.java	 <br>
 * 2011-11-19<br>
 * com.shinetech.handle <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.handle;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.shinetech.action.ConstConfig;
import com.shinetech.action.ImportCategory;
import com.shinetech.bean.CatalogProduct;
import com.shinetech.dao.CatagoryDAO;
import com.shinetech.dao.DressDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.FileOperate;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ShowroomCategoryHandle implements ResultSetHandler{
	private String name;
	/**
	 * isRecord:是在分类下的产品数目吗？
	 */ 
	private boolean isRecord = true; 
	/**
	 * 功能：保存分类名 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param name
	 * void
	 */
	public void setInfo(String name) {
		this.name = ConstConfig.SHOWROOM[0]+"/"+name;
		saveCategory();
		productList.clear();
	}
	private List<CatalogProduct> productList = new ArrayList<CatalogProduct>();
	private DressDAO dressDAO = new DressDAO();
	/**
	 * saveOk: 分类保存成功了吗？如果没有成功，那么就不往里面导数据了
	 */ 
	private boolean saveOk = false;
	/**
	 * 目的和功能：构造方法,构造ShowroomCategoryHandle类的一个实例 
	 * 注意事项：
	 * @param isRecord  是否只统计分类下的产品数目
	 */
	public ShowroomCategoryHandle(boolean isRecord){
		try{
			this.isRecord = isRecord;
			importCategory.saveCalegory(ConstConfig.SHOWROOM[0]);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private CatagoryDAO categoryDAO = new CatagoryDAO();
	public void handle(ResultSet rs) {
		try{
			Long productId = rs.getLong("id");
			Integer score = rs.getInt("sort_score");
			if(score == null){
				TagCatatogHandle handle = new TagCatatogHandle();
				dressDAO.getTagInfoByPId(productId, handle);
				List<String> tags = handle.getTags();
				score = ProductScoreHandle.getScore(tags);
			}
			CatalogProduct product = new CatalogProduct(productId+"",score);
			productList.add(product);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private ImportCategory importCategory = new ImportCategory(false);
	public void saveCategory(){
		try {
			if(!isRecord)//只有当是统计分类时 才插入数据库
				this.saveOk = importCategory.saveCalegory(name);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	private Random random = new Random();
	/**
	 * 功能：保存一个showroom分类下的产品 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	public void saveCategoryProduct(){
		if(isRecord){
			FileOperate op = new FileOperate();
			op.addContentToEndOfFile2("data/category.tsv",this.name+"\t"+productList.size());
			return;
		}
		if(!this.saveOk){//如果分类没有导入成功，那么直接返回
			return;
		}
		Collections.sort(productList, new Comparator<CatalogProduct>() {
			@Override
			public int compare(CatalogProduct o1, CatalogProduct o2) {
				if(o1.getScore() > o2.getScore()){
					return 1;
				}else if(o1.getScore() == o2.getScore()){
//					return 0;
					return random.nextInt(3)-1;
				}else{
					return -1;
				}
			}
		});
		int sort = 1;
		int entityId = importCategory.getEntityId(); 
		for(CatalogProduct product : productList){
			try{
//				System.out.println(entityId+":"+product.getSku()+":"+sort++);
				categoryDAO.saveCatagoryProduct(entityId, product.getSku(), sort++);
			}catch (NullPointerException e) {
				//这里空指针异常主要是因为，231的产品库中的产品在magento中不一定存在。
				sort--;
			}
			catch (DBException e) {
				e.printStackTrace();
			}
		}
	}
}
