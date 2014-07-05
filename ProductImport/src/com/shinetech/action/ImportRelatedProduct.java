/*
 * RelatedProducts.java	 <br>
 * 2011-11-9<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.shinetech.bean.CatalogProduct;
import com.shinetech.dao.DressDAO;
import com.shinetech.dao.MagentoProductDAO;
import com.shinetech.handle.RelatedProductHandle;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;

/**
 * onenote:///\\SERVER2\Onenote协作区\公共\Dress\编辑.one#相关产品策略&section-id={338DD4CC-3CDE-4AAC-A0ED-F485D50B010A}&page-id={A1CA3D8E-E0CE-4D16-B076-17F16780A679}&object-id={C202FB58-2DD1-0319-3522-C2AE1F559687}&F
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class ImportRelatedProduct {
	private Logger logger = Logger.getLogger(this.getClass());
	private DressDAO dressDAO = new DressDAO();
	private MagentoProductDAO magentoDAO = new MagentoProductDAO();
	/**
	 * tagPidsMap:tag对应的产品id列表
	 */ 
	private Map<String,ArrayList<CatalogProduct>> tagPidsMap; 
	public static void main(String[] args) throws Exception {
		Const.initLogger();
		ImportRelatedProduct relatedProducts = new ImportRelatedProduct();
		relatedProducts.initProductInfo();
		relatedProducts.computeRelateProduct();
	}
	/**
	 * productList:产品列表
	 */ 
	private List<CatalogProduct> productList;
	
	public void initProductInfo() throws Exception{
		RelatedProductHandle handle = new RelatedProductHandle();
		dressDAO.getProductInfo(handle);
		tagPidsMap = RelatedProductHandle.tagPidsMap;
		productList = RelatedProductHandle.productList;
//		System.out.println(productList);
//		System.out.println(productList.get(2).getCatalogTagsMap());
//		System.out.println(productList.get(4).getCatalogTagsMap());
	}
	//public static List<String> catalogs = Arrays.asList("Wedding Dresses,Special Occasion Dresses,Silhouette,Style".split(","));
	public void computeRelateProduct(){
		for(int i = 0;i< productList.size();i++){
//		for(CatalogProduct product : productList){
			CatalogProduct product = productList.get(i);
			//找到一个产品的品类名
			List<String> proTagList = new ArrayList<String>();
			//品类wedding Dresses下的所有Tag
			List<String> weddingTags = product.getCatalogTagsMap().get(ConstConfig.catalogs.get(0));
			//品类Special Occasion Dresses下的所有Tag
			List<String> occasionTags = product.getCatalogTagsMap().get(ConstConfig.catalogs.get(1));
			if(weddingTags != null){
				proTagList.addAll(weddingTags);
			}
			if(occasionTags != null){
				proTagList.addAll(occasionTags);
			}
			
			/**
			 * 与当前产品相同品类的产品集合
			 * */
			List<CatalogProduct> theSameCataPros = new ArrayList<CatalogProduct>();
			for(String tag : proTagList){
				theSameCataPros.addAll(tagPidsMap.get(tag));
			}
			//相关产品
			Set<CatalogProduct> relatedProductset = new HashSet<CatalogProduct>();
			final Random random = new Random();
			for(int j =2 ;j <= 3;j++){
				//候选产品
				List<CatalogProduct> candidatePros = new ArrayList<CatalogProduct>();
				//Silhouette款型、style风格
				String catalog = ConstConfig.catalogs.get(j);
				List<String> proStyleOrSilhouetteTags = product.getCatalogTagsMap().get(catalog);
				for(CatalogProduct pro : theSameCataPros){
					List<String> styleOrSilhouetteTags = pro.getCatalogTagsMap().get(catalog);
					if(styleOrSilhouetteTags != null){
//						styleOrSilhouetteTags.retainAll(proStyleOrSilhouetteTags);
//						//取交集，如果剩下的结合不为空，那么说明交集不为空,，则该产品为候选产品
//						if(!styleOrSilhouetteTags.isEmpty()){
//							candidatePros.add(pro);
//						}
						if(!Collections.disjoint(styleOrSilhouetteTags, proStyleOrSilhouetteTags)){//是否有交集
							candidatePros.add(pro);
						}
					}
				}
				//对候选产品排序
				if(!candidatePros.isEmpty()){
					Collections.sort(candidatePros, new Comparator<CatalogProduct>() {
						@Override
						public int compare(CatalogProduct o1, CatalogProduct o2) {
							if(o1.getScore() > o2.getScore()){
								return 1;
							}else if(o1.getScore() == o2.getScore()){
								return random.nextInt(3)-1;
							}else{
								return -1;
							}
						}
					});
					
					int counts = 0;
					if(candidatePros.size() > 3){
						for(CatalogProduct p :candidatePros){
							if(relatedProductset.add(p)){
								counts++;
								if(counts >= 3){
									break;
								}
							}
						}
					}else{
						relatedProductset.addAll(candidatePros);
					}
				}
			}
			if(relatedProductset.contains(product)){
				relatedProductset.remove(product);
			}
			//到此为止，当前遍历的产品已经将同款型和同类别的产品选出来了。
			while(true){
				int index = random.nextInt(theSameCataPros.size());
				if(index != i){
					relatedProductset.add(theSameCataPros.get(index));
				}
				if(relatedProductset.size() >= 10){
					break;
				}
				if(theSameCataPros.size() < 10){
					relatedProductset.addAll(theSameCataPros);
					break;//因为在这里面选产品时，要是同一品类
				}
			}
			//剩下的是保存
//			System.out.println(relatedProductset);
//			dao.insertProductLinkInt("relation", "position", 3, 1);
//			dao.insertProductLinkInt(1, 4, 4);
			saveRelatedProduct(product,relatedProductset);
		}
	}
	public void saveRelatedProduct(CatalogProduct product,Set<CatalogProduct> relatedProductset){
		int post = 1;
		for(CatalogProduct p :relatedProductset){
			try {
				magentoDAO.saveProductLink(product.getSku(), p.getSku(), ConstConfig.product_link_type_code[0]);
				int linkId = magentoDAO.getLastId();
				magentoDAO.insertProductLinkInt(ConstConfig.product_link_type_code[0], ConstConfig.product_link_attribute_code[0], linkId, post++);
			} catch (DBException e) {
				logger.error("出错：导入产品"+product.getSku()+"的相关产品"+p.getSku());
				e.printStackTrace();
			}
		}
	}
}
