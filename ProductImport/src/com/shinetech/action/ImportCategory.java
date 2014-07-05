/*
 * ImportCategory.java	 <br>
 * 2011-10-28<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.shinetech.bean.CatalogProduct;
import com.shinetech.dao.CatagoryDAO;
import com.shinetech.dao.DressDAO;
import com.shinetech.handle.CatalogHandle;
import com.shinetech.handle.CatalogParentHandle;
import com.shinetech.handle.ProductScoreHandle;
import com.shinetech.handle.TagCatatogHandle;
import com.shinetech.simple.ProductUtil;
import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportCategory {
	/**
	 * parentCatagoryMap:用来保存父分类的id
	 */ 
	private static Map<String, Integer> parentCatagoryMap = new HashMap<String, Integer>();
	private CatagoryDAO dao = new CatagoryDAO();
	private int entityTypeId = 9;
	private int attributeId = 111;
	private int attributeSetId  = 12;
	private int storeId = 0;
	private int entityId ;
	
	private FileOperate op = new FileOperate();
	private List<String> catagoryList = new ArrayList<String>();
	
	public static void main(String[] args) {
		Const.initLogger();
		ImportCategory test = new ImportCategory(true);
		try {
//			String catagoryName = "Root Catalog/Furniture/import1";
//			test.action(catagoryName);
			test.service();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void init() throws DBException{
		entityTypeId = dao.getEntityTypeId();
		attributeId = dao.getAttributeId("name", entityTypeId);
		attributeSetId = dao.getAtributeSetId("Default", entityTypeId);
		op.getLine(new File(Const.CATALOG_IMPORT_FORMAT), new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				if(line.startsWith("#")){
					return null;
				}
				catagoryList.add(line.split(";")[0]);
				return null;
			}
		});
		loadStartIdAndCount();
	}
	public ImportCategory(Boolean existProducts){
		try {
			init();
			if(existProducts)
				getProductSkus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void service(){
		for(String catalogName : catagoryList){
			try {
				saveCalegory(catalogName);
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 功能：该方法必须在saveCalegory()方法之后使用 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @return
	 * int
	 */
	public int getEntityId(){
		return entityId;
	}
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param catagoryName
	 * @return
	 * @throws DBException
	 * boolean
	 */
	public boolean saveCalegory(String catagoryName) throws DBException{
//		String catagoryName = "Test Root Category/test sub category/import1";
		String catagorys[] = catagoryName.split("/");
		String name = catagorys[catagorys.length-1];
		int parentId = 1;//找到新插入的分类的父id 
		List<Integer> entityIdList = new ArrayList<Integer>();
		entityIdList.add(1);
		
		//用来保存此分类的父分类路径；Test Root Category/test sub category/import1 ==> Test Root Category/test sub category
		String parentCatagory = catagoryName.substring(0, catagoryName.lastIndexOf("/"));
		if(parentCatagoryMap.get(parentCatagory) == null){
			for(int index = 0;index<catagorys.length;index++){
				String catalogName = catagorys[index];
				CatalogParentHandle handle = new CatalogParentHandle(catalogName,entityTypeId,attributeId);
				dao.getCatalogByParentId(parentId, handle);
				if(handle.getEntityId() > 0){
					parentId = handle.getEntityId();
				}else{
					if(index <catagorys.length-1){
						System.err.println(catalogName+"不存在");
						return false;
					}
				}
				entityIdList.add(handle.getEntityId());
			}
			if(entityIdList.get(entityIdList.size()-1) > 0){
				System.err.println(catagoryName+"该分类已经存在");
				return false;
			}else{
				entityIdList.remove(entityIdList.size()-1);
			}
			parentCatagoryMap.put(parentCatagory, parentId);
		}else{
			parentId = parentCatagoryMap.get(parentCatagory);
		}
		
		dao.getCategoryEntity(parentId, new ResultSetHandler() {
			
			@Override
			public void handle(ResultSet rs) {
				try{
					int position = dao.getMaxPosition(rs.getInt("entity_id"))+1;
					dao.saveCatalogEntity(entityTypeId, attributeSetId, rs.getInt("entity_id"), rs.getString("path"), position, rs.getInt("level")+1);
					entityId =  dao.getLastId();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		dao.updateChildren(entityIdList);
		int varcharAttrIds[] = {111,114,479,118,563,514};
		String attributeCodes[] = {"name","meta_title","url_key","display_mode","custom_design","page_layout"};
//		String varcharValue[] = {name,"page title",catagoryName.toLowerCase().replace(" ", "-").replace("/", "-"),"PRODUCTS","base/default","NULL"};
		String url_key = name.replace(" ", "-").replace("/", "-").toLowerCase();
		if(catagoryName.contains(ConstConfig.SHOWROOM[0])){
			if(name.equalsIgnoreCase(ConstConfig.SHOWROOM[1])){
				url_key = "c";
			}
		}else{
			url_key = "c/"+url_key;
		}
		String varcharValue[] = {name,name,url_key,"PRODUCTS","","NULL"};
		for(int i = 0; i< varcharAttrIds.length;i++){
			int attrId = dao.getAttributeId(attributeCodes[i], entityTypeId);
			dao.saveCatalogEntityVarchar(entityTypeId, attrId, storeId, entityId, varcharValue[i]);
		}
		
		int textAttrIds[] = {112,115,116,530,927};
		String metaDecri = "Buy ${keyword} on 9dresses. Help you make ${keyword} perfect for you and for the occasion. Get it now for a discount price.";
		metaDecri = metaDecri.replace("${keyword}", name);
		attributeCodes = new String[]{"description","meta_keywords","meta_description","custom_layout_update","available_sort_by"}; 
		String textValue[] = {"","",metaDecri,"",""};
		for(int i = 0;i < textAttrIds.length ;i++){
			int attrId = dao.getAttributeId(attributeCodes[i], entityTypeId);
			dao.saveCatalogEntityText(entityTypeId, attrId, storeId, entityId, textValue[i]);
		}
		
		int intAttrIds[] = {119,932,117,120,935,936,937};
		attributeCodes = new String[]{"is_active","include_in_menu","landing_page","is_anchor","custom_use_parent_settings","custom_apply_to_products","filter_price_range"};
		int inMenu = 0;//不在目录中
		if(name.equals("Wedding Dresses") ||name.equals("Special Occasion Dresses")){
			inMenu = 1;
		}
		int is_active = 1;
		if(SimpleProductConfig.NotActiveCategory.contains(catagoryName))
			is_active = 0;
		int intValue[] = {is_active,inMenu,0,1,0,0,0};
		for(int i = 0;i < intAttrIds.length ;i++){
			int attrId = dao.getAttributeId(attributeCodes[i], entityTypeId);
			dao.saveCatalogEntityInt(entityTypeId, attrId, storeId, entityId, intValue[i]);
		}
		
		int dateAttrIds[] = {565,566};
		attributeCodes = new String[]{"custom_design_from","custom_design_to"};
		String dateValue[] = {null,null};
		for(int i = 0;i < dateAttrIds.length ;i++){
			int attrId = dao.getAttributeId(attributeCodes[i], entityTypeId);
			dao.saveCatalogEntityDatetime(entityTypeId, attrId, storeId, entityId, dateValue[i]);
		}
		//分类图片删除
//		int thumbId = dao.getAttributeId("thumbnail", entityTypeId);
//		dao.saveCatalogEntityVarchar(entityTypeId, thumbId, storeId, entityId, "thumb.jpg");
//		int imageId = dao.getAttributeId("image", entityTypeId);
//		dao.saveCatalogEntityVarchar(entityTypeId, imageId, storeId, entityId, "small.jpg");
		
		Set<CatalogProduct> set =  c2pmap.get(name);
		
		if(set != null){
			final Random random = new Random();
			List<CatalogProduct> list = new ArrayList<CatalogProduct>();
			list.addAll(set);
			Collections.sort(list, new Comparator<CatalogProduct>() {
				@Override
				public int compare(CatalogProduct o1, CatalogProduct o2) {
					if(o1.getScore() > o2.getScore()){
						return 1;
					}else if(o1.getScore() == o2.getScore()){
//						return 0;
						return random.nextInt(3)-1;
					}else{
						return -1;
					}
				}
			});
			int sort = 1;
			for(CatalogProduct product : list){
				try{
					dao.saveCatagoryProduct(entityId, product.getSku(), sort++);
				}catch (NullPointerException e) {
					//这里空指针异常主要是因为，231的产品库中的产品在magento中不一定存在。
					sort--;
				}
			}
			set.clear();
			list.clear();
		}
		dao.updatePath(entityId);
		return true;
	}
	private DressDAO dressDAO = new DressDAO();
	/**
	 * map:存放产品对应的分类
	 */ 
	private Map<CatalogProduct,Set<String>> p2cmap = new HashMap<CatalogProduct, Set<String>>();
	/**
	 * c2pmap:存放分类对应的产品
	 */ 
	private Map<String,Set<CatalogProduct>> c2pmap = new HashMap<String, Set<CatalogProduct>>();
	
	public void getProductSkus() throws DBException{
		final CatalogHandle catalogHandle = new CatalogHandle();
		dressDAO.getProductInfo(new ResultSetHandler() {
			@Override
			public void handle(ResultSet rs) {
				try {
					TagCatatogHandle handle = new TagCatatogHandle();
					Long productId =  rs.getLong("id");
				
					if(productId < START_PRODUCNT_ID){
						return;
					}
					if(count >= COUNT){
						return;
					}
					count++;
					dressDAO.getTagInfoByPId(productId, handle);
					List<String> tags = handle.getTags();
//					System.out.println(tags);
					int score = ProductScoreHandle.getScore(tags);
					CatalogProduct product = new CatalogProduct(productId+"",score);
					Set<String> catalogSet = catalogHandle.getCatalog(tags);
					String productType = ProductUtil.getProductType(handle.getCatalogTagMap());
					catalogSet.add(productType);//将产品是婚纱还是特殊场合的礼服的分类加进去
					p2cmap.put(product, catalogSet);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		for(CatalogProduct product : p2cmap.keySet()){
			Set<String> catalogSet = p2cmap.get(product);
			for(String c:catagoryList){
				String temp[] = c.split("/");
				c = temp[temp.length-1];
				if(catalogSet.contains(c)){
					Set<CatalogProduct> productSet = c2pmap.get(c);
					if(productSet == null){
						productSet = new HashSet<CatalogProduct>();
					}
					productSet.add(product);
					c2pmap.put(c, productSet);
				}
			}
		}
	}
	/**
	 * COUNT:用来控制每次生成的产品数目
	 * 
	 * 这里和ProductInfoHandle共用一份配置，流程控制也大体相同
	 */ 
	private static int START_PRODUCNT_ID ;
	private static int COUNT;
	private int count = 0;
	public void loadStartIdAndCount(){
		if(START_PRODUCNT_ID == 0 || COUNT == 0){
			FileOperate op = new FileOperate();
			op.getLine(new File("assets/count_of_product"),new ILineHandle<String>() {

				@Override
				public String lineHandle(String line) {
					if(line.startsWith("#")){
						return null;
					}
					String temp [] = line.split("=");
					if(temp[0].equals("start_product_id")){
						START_PRODUCNT_ID = Integer.parseInt(temp[1]);
					}else if(temp[0].equals("count_prodcut")){
						COUNT = Integer.parseInt(temp[1]);
					}
					return null;
				}
			});
		}
	}
}
