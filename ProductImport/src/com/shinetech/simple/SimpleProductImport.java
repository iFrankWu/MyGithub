/*
 * ImportProduct.java	 <br>
 * 2011-10-10<br>
 * com.shinetech.action <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.shinetech.bean.Statics9DAttribute;
import com.shinetech.dao.DressDAO;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.DateUtil;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class SimpleProductImport {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 * @throws DBException 
	 */
	public static void main(String[] args) throws Exception {
		SimpleProductImport importProdcut = new SimpleProductImport(null);
		importProdcut.genericSimpleProduct(false);
	}
	private SimpleProductService service ;
	private Statics9DAttribute statics9DAttribute ;
	private DressDAO dao = new DressDAO();
	public SimpleProductImport(Statics9DAttribute statics9DAttribute){
		this.statics9DAttribute = statics9DAttribute;
		service	= new SimpleProductService(statics9DAttribute.getProductId());
	}
	private Logger logger = Logger.getLogger(this.getClass());
	private Random random = new Random();
	 
	/**
	 * 功能：生产产品的csv文件 <br>
	 * 注意事项：<font color="red">如果是更新产品，那么图片和自定义选项不生产</font> <br>
	 * @param isUpdate 是更新产品吗？
	 * void
	 */
	public void genericSimpleProduct(boolean isUpdate){
		try{
			if(statics9DAttribute.getWeight() == null){
				logger.error("产品Id:"+service.getProductId()+",Weight is null");
	//			throw new Exception("产品:"+service.getProductId()+"Weight is null");
				return ;
			}
			if(!service.checkTags()){
				logger.error("产品Id："+statics9DAttribute.getProductId()+",Tags is null");
				return;
			} 
			boolean needUpdate = false;
			SimpleProduct product = new SimpleProduct();
			product.required_options = "1";
			product.has_options = "0";
			product._attribute_set = service.getAttributeSet();
			product._type = "simple";
			product.created_at = DateUtil.getNowDate();
		
	//		System.out.println(statics9DAttribute.getDiscount());
			if(statics9DAttribute.getDiscount() != null && statics9DAttribute.getDiscount() > 0){
				product.discount = statics9DAttribute.getDiscount()+"";
			}else{
				product.discount  = service.getDiscount();
				needUpdate = true;
				statics9DAttribute.setDiscount(Float.parseFloat(product.discount));
			}
			product.has_options = service.getHasOption();
			product.options_container = service.getOptionContainer();
			product.required_options = service.getRequiredOptions();
			product.tax_class_id = service.getTaxClassId();
			product.qty = service.getQty();
			/**
			 * 对属性及其可选项的操作，全部写入map中
			 * */
			product.attrMap =  service.genericAttributeOptions();
			//产品名
			if(statics9DAttribute.get_9dName() == null){
				needUpdate = true;
				String name = service.getName();
				statics9DAttribute.set_9dName(name);
			}
			product.name = statics9DAttribute.get_9dName();
			service.setName(product.name);
			
			product.sku = service.getProductSku();//这是根据产品名生成的
	
//			if(statics9DAttribute.get_9dMarketPrice() != null && statics9DAttribute.get_9dMarketPrice() > 0){
//				product.price = statics9DAttribute.get_9dMarketPrice()+"";
//				service.setPrice(statics9DAttribute.get_9dRealPrice()+"");
//			}else{
//				Float realPrice = Float.parseFloat(service.getPrice());
//				int rp = Math.round(realPrice);
//				//尾数
//				float mantissa = Float.parseFloat(SimpleProductConfig.discount_9[random.nextInt(SimpleProductConfig.discount_9.length)]);
//				//实际售价 168.99
//				float rprice = rp+mantissa;
//				statics9DAttribute.set_9dRealPrice(rprice);
//				Float marketPrice = rprice*100/(100-statics9DAttribute.getDiscount());
//				product.price = String.format("%.2f", marketPrice);
//				statics9DAttribute.set_9dMarketPrice(Float.parseFloat(product.price));
//				service.setPrice(rprice+"");
//				needUpdate = true;
//			}
//			Float pri = Float.parseFloat(product.price);
//			if(pri <= 0){
//				logger.error("产品Id:"+service.getProductId()+",Price is zero");
//				return;
//			}
			
//			PriceStatusPriceHandle pspHandle = new PriceStatusPriceHandle();
//			float marketPrice =  pspHandle.computePrice(statics9DAttribute.getCostPrice(), statics9DAttribute.getOldCostPrice(), statics9DAttribute.getPriceStatus(), statics9DAttribute.getDiscount(),statics9DAttribute.getPriceFactor());
//			product.price = String.format("%.2f", marketPrice);
//			service.setPrice(statics9DAttribute.getPriceStatus().trim().equals("1")?statics9DAttribute.getOldCostPrice()+"":statics9DAttribute.getCostPrice()+"");
//			if(statics9DAttribute.get_9dMarketPrice() == null || statics9DAttribute.get_9dMarketPrice() == 0){
//				  needUpdate = true;
//				  statics9DAttribute.set_9dMarketPrice(Float.parseFloat(product.price));
//				  statics9DAttribute.set_9dRealPrice(Float.parseFloat(product.price)*(100-statics9DAttribute.getDiscount())/100);
//			}
			
			/*计算价格**/
			if(statics9DAttribute.get_9dMarketPrice() > 0 && statics9DAttribute.get_9dRealPrice() > 0){
				product.price = statics9DAttribute.get_9dMarketPrice()+"";
				service.setPrice(statics9DAttribute.get_9dRealPrice()+"");
			}else{
				int usePrice = 0;
				if(statics9DAttribute.getOldCostPrice() == 0){
					usePrice = statics9DAttribute.getCostPrice();
				}else{
					int diff = statics9DAttribute.getOldCostPrice() - statics9DAttribute.getCostPrice();
					if(diff >= -200 && diff <= 129){
						usePrice = (statics9DAttribute.getOldCostPrice()+statics9DAttribute.getCostPrice())/2;
					}else if(diff > 129){
						if("1".equals(statics9DAttribute.getPriceStatus().trim())){
							usePrice = statics9DAttribute.getOldCostPrice();
						}else{
							usePrice = statics9DAttribute.getCostPrice();
						}
					}else{
						logger.error("价格差价小于-200，采用自定义的值.pid="+service.getProductId()+":,old_cost_price="+statics9DAttribute.getOldCostPrice()+":,cost_price:"+statics9DAttribute.getCostPrice());
						usePrice = statics9DAttribute.getCostPrice();
					}
				}
				
				float price = (usePrice*statics9DAttribute.getPriceFactor())/6+5;//得出的是
				int rp = Math.round(price);
				//尾数
				float mantissa = Float.parseFloat(SimpleProductConfig.discount_9[random.nextInt(SimpleProductConfig.discount_9.length)]);
				//实际售价 168.99
				float rprice = rp+mantissa;
				Float marketPrice = rprice*100/(100-statics9DAttribute.getDiscount());//这个价格是上传给商城的，商城根据这个值和折扣值计算产品实际售价			
				
				product.price = String.format("%.2f", marketPrice);
				
				if(statics9DAttribute.get_9dMarketPrice() == null || statics9DAttribute.get_9dMarketPrice() == 0){
					  needUpdate = true;
					  statics9DAttribute.set_9dMarketPrice(Float.parseFloat(product.price));
					  statics9DAttribute.set_9dRealPrice(rprice);
				}
				service.setPrice(rprice+"");
			}
			
			//product.meta_description = service.getMetaDescription();//这里包含价格，估计描述不正确
//			product.meta_keyword = service.getMetaKeyword();
//			product.meta_title = service.getMetaTitle();
			
			if(statics9DAttribute.getMg_meta_description() == null){
				needUpdate = true;
				String meta_description = service.getMetaDescription();
				statics9DAttribute.setMg_meta_description(meta_description);
				product.meta_description = statics9DAttribute.getMg_meta_description();
			}else{
				product.meta_description = "\""+statics9DAttribute.getMg_meta_description()+"\"";//"\""+temp+"\"";
			}
		
			
			if(statics9DAttribute.getMg_meta_keywords() == null){
				 needUpdate = true;
				 statics9DAttribute.setMg_meta_keywords(service.getMetaKeyword());
			}
			product.meta_keyword = statics9DAttribute.getMg_meta_keywords();
			
			if(statics9DAttribute.getMg_meta_title() == null){
				needUpdate = true;
				statics9DAttribute.setMg_meta_title(service.getMetaTitle());
			}
			product.meta_title = statics9DAttribute.getMg_meta_title();
			if(!isUpdate){
				/**
				 * 增加自定义颜色的处理，自定义 选项 custom option 可能有多个，都写如这个list中
				 * */
				service.genericCustonOptionColor(product.customOptionList,statics9DAttribute.getShownColor(),statics9DAttribute.getColorAdjustMent());
				
				/**
				 * 处理自定义尺寸
				 * */
				service.genericCustonOptionSize(product.customOptionList);
	//			service.genericCustonOptionPannier(product.customOptionList);
				service.genericCustonOptionCustomSize(product.customOptionList);
			
				/**
				 * 图片相关操作
				 * */
				product.imgRelated = service.genericImageRelated(product.name);
				if(product.imgRelated.getImgList().isEmpty()){
					logger.error("产品:"+service.getProductId()+"图片为空");
					return;
				}
			}
			
			product.url_key = service.getUrlKey();
			product.url_path = service.getUrlPath();
			if(statics9DAttribute.getWeight().contains("(")){
				needUpdate = true;
				String weight = statics9DAttribute.getWeight().split("\\(")[0].trim();
				statics9DAttribute.setWeight(weight);
			}
			product.weight = statics9DAttribute.getWeight();
			if(statics9DAttribute.getMg_description() == null){
				needUpdate = true;
				statics9DAttribute.setMg_description(service.getDescription(product.weight,statics9DAttribute.getColorAdjustMent()));
				product.description = statics9DAttribute.getMg_description();
			}else{
				product.description = "\""+statics9DAttribute.getMg_description()+"\"";
			}
			
			service.getCategory(product.categoryList);	
			//记录分类产品数目
			recordCategory(product.categoryList);
			
			if(statics9DAttribute.getMg_short_description() == null ){
				needUpdate = true;
				statics9DAttribute.setMg_short_description(service.getShortDescription());
			}
			product.short_description = statics9DAttribute.getMg_short_description();
			
			if(statics9DAttribute.get_9dItemCode() == null || statics9DAttribute.get_9dItemCode().trim().equals("")){
				needUpdate = true;
				statics9DAttribute.set_9dItemCode(service.getItemCode());
			} 
			product.itemcode = statics9DAttribute.get_9dItemCode();
			if(statics9DAttribute.getSortScore() == null){
				 int score = service.getSortScore();
				 statics9DAttribute.setSortScore(score);
				 needUpdate = true;
			} 
			product.sortScore = statics9DAttribute.getSortScore()+"";
			product.petticoat = service.getHasPetticoat();
			
			//看产品成本价是否为0
//			if(!(statics9DAttribute.getCostPrice() > 0)){
//				int costPrice = service.getCostPrice();
//				statics9DAttribute.setCostPrice(costPrice);
//				needUpdate = true;
//			}
			if(statics9DAttribute.getShownColor() == null){
				product.shownColor = "";
			}else{
				product.shownColor = statics9DAttribute.getShownColor().replace("_", " ");
			}
			if(needUpdate){
				dao.update9Dresses(statics9DAttribute.getProductId(), statics9DAttribute.get_9dName(), 
						statics9DAttribute.get_9dItemCode(),
						statics9DAttribute.get_9dMarketPrice(), 
						statics9DAttribute.get_9dRealPrice(), statics9DAttribute.getDiscount(),statics9DAttribute.getWeight(),
						statics9DAttribute.getSortScore(),
//						statics9DAttribute.getCostPrice(),
						statics9DAttribute.getMg_description(),statics9DAttribute.getMg_short_description(),statics9DAttribute.getMg_meta_title(),
						statics9DAttribute.getMg_meta_keywords(),statics9DAttribute.getMg_meta_description());
			}
			//生产产品的csv文件
			product.genericSimpleProduct();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("产品："+statics9DAttribute.getProductId()+"\t"+e.getMessage());
		}
	}
	public static Map<String,Integer> categoriesMap = new HashMap<String, Integer>();
	private void recordCategory(List<String> categories){
		for(String category : categories){
			Integer i = categoriesMap.get(category);
			if(i == null){
				i = 1;
			}else{
				i++;
			}
			categoriesMap.put(category, i);
		}
	}
}
