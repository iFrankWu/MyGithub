/*
 * ProductService.java	 <br>
 * 2011-10-11<br>
 * com.shinetech.service <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.shinetech.bean.CustomOption;
import com.shinetech.bean.Image;
import com.shinetech.bean.ImageRelated;
import com.shinetech.dao.DressDAO;
import com.shinetech.handle.CatalogHandle;
import com.shinetech.handle.CustonAttributeHandle;
import com.shinetech.handle.ImageHandle;
import com.shinetech.handle.PriceHandle;
import com.shinetech.handle.ProductScoreHandle;
import com.shinetech.handle.TagCatatogHandle;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.FileOperate;

/**
 * 将导入产品时需要用到一些相关方法集中写道这里
 * onenote:///\\SERVER2\Onenote协作区\公共\Dress\编辑.one#产品页显示信息&section-id={338DD4CC-3CDE-4AAC-A0ED-F485D50B010A}&page-id={AF638D4E-42E9-4990-82A7-09E19E37E24E}&object-id={B8CEAEBA-4A4F-069B-35BC-42B692F725D4}&F
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class SimpleProductService {
	private Logger logger = Logger.getLogger(this.getClass());
	private Long productId ;
	public Long getProductId(){
		return productId;
	}
//	private String productType = SimpleProductConfig.productTypes[0];
	public SimpleProductService(Long productId){
		this.productId = productId;
		initPNameSet();
		getTags();
		catalogList = catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[0]);
		if(catalogList == null){
			catalogList = new ArrayList<String>();
		}
		if(catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[1]) != null)
			catalogList.addAll(catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[1]));
//		getTags();
//		if(catalogTagMap == null || catalogTagMap.isEmpty()){
//			System.err.println("产品类别和tag信息为空或读取失败：pid="+productId);
//			System.exit(1);
//		}
		
	}
	private void initPNameSet(){
		if(productNameSet.isEmpty() || itemCodeSet.isEmpty()){
			try {
				dao.getProductInfo(new ResultSetHandler() {
					
					@Override
					public void handle(ResultSet rs) {
						try {
							productNameSet.add(rs.getString("9dresses_producut_name"));
							itemCodeSet.add(rs.getString("9dress_itemcode"));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能：规则变更，先前，当一件产品的Wedding Dresses大类下，对应的包含值为Bridal Gowns的tag，那么该产品为wedding dresses 否则为special occasion dresses。
			变更为：Wedding Dresses和special occasion dresses分类下，只要有tag被选中，那么那就属性对应的分类
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @return
	 * String
	 */
	public String getProductType(){
		return ProductUtil.getProductType(catalogTagMap);
	}
	private List<String> tags;
	private  Map<String,ArrayList<String>> catalogTagMap;
	private List<String> mainFabricTags = new ArrayList<String>();
	private List<String> fabric1Tags = new ArrayList<String>();
	private List<String> getTags(){
		if(tags == null){
			tags = new ArrayList<String>();
			TagCatatogHandle tagHandle = new TagCatatogHandle();
			try {
				dao.getTagInfoByPId(productId, tagHandle);
			} catch (DBException e) {
				e.printStackTrace();
			}
			tags = tagHandle.getTags();
			catalogTagMap = tagHandle.getCatalogTagMap();
			mainFabricTags = tagHandle.getMainFabricList();
			fabric1Tags = tagHandle.getFabrice1List();
		}
		return tags;
	}
	public boolean checkTags(){
		return tags.size() > 0;
	}
	private DressDAO dao = new DressDAO();
	
	/**
	 * name:产品名
	 */ 
	private String name ;

	public String getProductSku() throws Exception{
		return productId+"";
	}
	/**
	 * 功能：这里通过属性计算出来的 <br>
	 * 注意事项：<font color="red">未实现</font> <br>
	 * @return
	 * String
	 */
	public String getAttributeSet(){
		return SimpleProductConfig.AttributeSets[0];
//		List<String> tempList = catalogTagMap.get("Wedding Dresses");
//		if(tempList != null && !tempList.isEmpty() && tempList.contains("Bridal Gowns")){
//			return SimpleProductConfig.AttributeSets[0];
//		}
//		return SimpleProductConfig.AttributeSets[1];
	}
	/**
	 * 功能：得到产品的类别 <br>
	 * 注意事项：<font color="red">未实现</font> <br>
	 * @param categoryList
	 * void
	 */
	public void getCategory(List<String> categoryList){
//		String []s = "Tools,Dress/Girl Dress,Dress/Boy Dress,Furniture".split(",");//231
//		String []s = "Wedding Dresses,Ball Gowns".split(",");//230
		CatalogHandle handle = new CatalogHandle();
		Set<String> catagorySet = handle.getCatalog(tags);
		
		for(int i = 0;i < SimpleProductConfig.productTypes.length;i++){
			if(catalogTagMap.get(SimpleProductConfig.productTypes[i]) != null && !catalogTagMap.get(SimpleProductConfig.productTypes[i]).isEmpty()){
				catagorySet.add(SimpleProductConfig.productTypes[i]);
			}
		}
		categoryList.addAll(catagorySet);
	}
	
	/**
	 * 功能：得到产品描述 <br>
	 * 注意事项：<font color="red">有些产品的某些分类下的tag没有值，在此处没有报错</font> <br>
	 * @return
	 * String
	 */
	public String getDescription(String weight,int colorAdjustment){
		StringBuilder description = new StringBuilder();
		getProductType();
		//如果是Bridal Gowns婚纱
		List<String> tempList = catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[0]);
		if(tempList != null && !tempList.isEmpty() && tempList.contains("Bridal Gowns")){
			for(String key :SimpleProductConfig.DESCRIPTION_NUMBER[0]){
				if(key.equals("Wedding Venues")){
					description.append("<b>Wedding Venues: </b>"+list2String(catalogTagMap.get("Venues")));
				}else if(key.equals("Fabric")){
//					List<String> list = catalogTagMap.get(key);
//					Set<String> set1 = new HashSet<String>();
//					if(list != null){
//						set1.addAll(list);
//					}
//					
//					for(Iterator< String > it = set1.iterator();it.hasNext();){
//						if(SimpleProductConfig.FabricDisplayTag.contains(it.next())){
//							it.remove();
//						}
//					}
//					System.out.println(set1);
//					description.append("<b>"+key+": </b>"+list2String(set1));
					description.append("<b>"+key+": </b>"+list2String(catalogTagMap.get(key)));
				}else{
					description.append("<b>"+key+": </b>"+list2String(catalogTagMap.get(key)));
				}
				description.append("<br>");
			}
		}else{
			for(String key :SimpleProductConfig.DESCRIPTION_NUMBER[1]){
				if(key.equals("Occasion")){
					List<String> list = new ArrayList<String>();//Occasion 值
					Set<String> set = new HashSet<String>();//这里使用set集合主要是去重
					if(catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[1]) != null)
						set.addAll(getOccasion(catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[1])));
					if(catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[0]) != null)
						set.addAll(getOccasion(catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[0])));
					list.addAll(set);
					description.append("<b>Occasion: </b>"+list2String(list));
				}else if(key.equals("Fabric")){
//					List<String> list = catalogTagMap.get(key);
//					List<String> list1 = new ArrayList<String>();
//					if(list != null){
//						list1.addAll(list);
//					}
//					
//					for(Iterator< String > it = list1.iterator();it.hasNext();){
//						if(SimpleProductConfig.FabricDisplayTag.contains(it.next())){
//							it.remove();
//						}
//					}
////					System.out.println(list1);
//					description.append("<b>"+key+": </b>"+list2String(list1));
					description.append("<b>"+key+": </b>"+list2String(catalogTagMap.get(key)));
				}else{
					description.append("<b>"+key+": </b>"+list2String(catalogTagMap.get(key)));
				}
				description.append("<br>");
			}
		}
//		System.out.println(catalogTagMap.get("Fabric"));
		description.append("<b>Fully Lined: </b>Yes");
		description.append("<br>");
		description.append("<b>Built-in Bra: </b>Yes");
		description.append("<br>");
		description.append("<b>Shipping Weight: </b>"+weight+"Kg");
		if(colorAdjustment != 1 && this.shownColor != null && !this.shownColor.trim().equals("")){
			description.append("<br>");
			description.append("<b>Shown Color: </b>"+this.shownColor.replace("_", " "));
		}
		String temp = description.toString();
		temp =  temp.replace(SimpleProductConfig.GOWNS2GOWN[0], SimpleProductConfig.GOWNS2GOWN[1]);
		temp = temp.replace("\"", "");//发现有tag出现中 包含引号，此时与csv文件的引号产生冲突
		temp = "\""+temp+"\"";
		return temp;
	}
	/**
	 * 功能：onenote:///\\SERVER2\Onenote协作区\公共\Dress\编辑.one#产品页显示信息&section-id={338DD4CC-3CDE-4AAC-A0ED-F485D50B010A}&page-id={AF638D4E-42E9-4990-82A7-09E19E37E24E}&object-id={BF77D0CF-519A-0CFF-1821-9E20678672C2}&13		
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param specialOccasion
	 * @return
	 * List<String>
	 */
	private List<String> getOccasion(List<String> specialOccasion){
		List<String> displayList = new ArrayList<String>();
		if(specialOccasion != null){
			for(String tag:specialOccasion){
				String display = SimpleProductConfig.map.get(tag);
				if(display != null){
					displayList.add(display);
				}
			}
		}
		return displayList;
	}
	private String list2String(Collection<String> clist){
		if(clist == null || clist.isEmpty())
			return "";
		List<String> list = new ArrayList<String>();
		list.addAll(clist);
		if(list.contains("Autumn") && list.contains("Fall")){
			list.remove("Fall");
			for(int i = 0;i<list.size();){
				if(list.get(i).equals("Autumn")){
					list.set(i, "Fall");
				}
				i++;
			}
		}
		StringBuilder sb = new StringBuilder();
		for(String s : list){
			sb.append(s);
			sb.append(", ");
		}
		sb.deleteCharAt(sb.length()-2);
		return sb.toString();
	}
	
	public String getDiscount(){
		return SimpleProductConfig.discounts[random.nextInt(SimpleProductConfig.discounts.length)];
	}
	public String getHasOption(){
		return "1";
	}
	public String getName(){
		/**计算产品的catalog,注意需要初始化catalogTagMap,目前已经放到构造方法中了*/
		while(true){
			String style = getRandomValue(catalogTagMap.get("Style"));
//			name = style+" "+getSomeAttributes(100000).replace(",", " ");//这个空格不需要？
			String attrs = getSomeAttributes(10000).replace(",", " ").trim();
			name = style+" "+attrs;
			name = name.trim();
			List<String> bodyShapeList = catalogTagMap.get("Body Shape");
			if(bodyShapeList != null && catalogTagMap.get("Body Shape").contains("Plus Size")){
				name += " Plus Size";
			}
			name = name + " ";
			name += getRandomValue(catalogList);
			name = name.replace(SimpleProductConfig.DRESSES2DRESS[0], SimpleProductConfig.DRESSES2DRESS[1]);
			if(name.contains(SimpleProductConfig.GOWNS2GOWN[0])){
				name = name.replace(SimpleProductConfig.GOWNS2GOWN[0], SimpleProductConfig.GOWNS2GOWN[1]);
			}
			name = name.replace("\"", "").trim();
			name = name.replace("  ", " ");
			if(productNameSet.add(name)){
				break;
			}
		}
		return name;
	}
	public void setName(String name){
		this.name = name.trim();
	}
	private String metaDescriptions_1[] = {
//			"${name} at a wholesale price - ${price} on 9dresses.com. Buy now for free shipping!",
//			"${name} now only costs you ${price}. Get a wholesale price with free shipping on 9dresses.com."
			//"${name} at a wholesale price - US$${price} on 9dresses.com. Buy now get a discount!",
			//"${name} now only costs you US$${price}. Get a wholesale price on 9dresses.com."
			"${name} at a discount price - US$${price} on 9dresses.com. ",
			"${name} is US$${price} for sale on 9dresses.com. "
	};
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @return
	 * String
	 */
	public String getMetaDescription(){
		int index = random.nextInt(metaDescriptions_1.length);
		String meta = metaDescriptions_1[index].replace("${name}", name);
		meta = meta.replace("${price}", getPrice());
		
//		String s = "This dress can be used as a ${catalog}. It is "+getSomeAttributes()+" perfect for ${Body Shape} Body Shapes.";
//		String s = "This dress can be used as a ${catalog}. It is ${some_attribute} perfect for ${Body Shape} Body Shapes.";
		String s = "It's ";
		String s1 = "can be a ";
 
		int length = meta.length();
		meta += s;
		meta = volidStr(meta,"Silhouette");
		meta = volidStr(meta,"Fabric");
		meta = volidStr(meta,"Hemline/Train");
		meta = volidStr(meta,"Embellishment");
		meta += s1;
		length = meta.length();
		StringBuilder catalogSB = new StringBuilder();
		for(String ca : catalogList){
			ca = ca.replace(SimpleProductConfig.DRESSES2DRESS[0], SimpleProductConfig.DRESSES2DRESS[1]);
			if(length+catalogSB.length()+ca.length()+2 > 255){
				break;
			}
			catalogSB.append(ca);
			catalogSB.append(", ");//注意这里加了空格
		}
		if(catalogSB.length() > 0)
			meta += catalogSB.deleteCharAt(catalogSB.length()-2).toString() ;
		meta = meta.replace(SimpleProductConfig.GOWNS2GOWN[0], SimpleProductConfig.GOWNS2GOWN[1]);
		meta = meta.replace("\"", "");
		meta = meta.trim()+".";
		if(meta.length()> 255){
			System.err.println("产品pid:"+productId+"\tmeta描述长度:"+meta.length()+"，故被剪裁为255.");
			meta = meta.substring(0,255);
		}
		
		return "\""+meta+"\"";
	}
	public String volidStr(String meta,String category){
		String t = getRandomValue(catalogTagMap.get(category));
		if(!t.equals("") && meta.length()+t.length() <255){
			meta += t+", ";//注意这里加了空格
		}
		return meta;
	}
	public String getMetaKeyword(){
		return "";
	}
	public String getMetaTitle(){
		return name+" - US$"+getPrice(); 
	}
	private String itemCode ;
	/**
	 *这个集合应该是一个全局的
	 * */
	private static Set<String> itemCodeSet = new HashSet<String>();
	/**
	 * 确保产品名不重复
	 * */
	private static Set<String> productNameSet = new HashSet<String>();
	private List<String> catalogList;
	
	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param maxlength	返回的字符串的最大长度
	 * @return
	 * String
	 */
	private String getSomeAttributes(int maxlength){
		String attributes = "";
		Set<Integer> set = new HashSet<Integer>();
		List<String> tempList = catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[0]);
		int len = 6;//是Const.NAME_CATAGORY 数组 除去后几项的值
		if(tempList != null && !tempList.isEmpty() && tempList.contains("Bridal Gowns")){
			len = 5;
		}
		while(true){
			int index = random.nextInt(SimpleProductConfig.NAME_CATAGORY.length-len);
			if(set.add(index)){
				String temp = attributes+getRandomValue(catalogTagMap.get(SimpleProductConfig.NAME_CATAGORY[index]))+",";
				if(temp.length() > maxlength){
					break;
				}
				attributes += getRandomValue(catalogTagMap.get(SimpleProductConfig.NAME_CATAGORY[index]))+",";
			}
			if(set.size() > 4){
				break;
			}
		}
//		for(Integer i : set){
//			attributes += getRandomValue(catalogTagMap.get(Const.NAME_CATAGORY[i]))+",";
//		}
		return attributes.trim();
	}
	
	/**
	 * 功能：生成itemcode 全局唯一 <br>
	 * 注意事项：<font color="red">当第二次生成时，需要将先前的itemcode来初始化itemCodeSet</font> <br>
	 * @return
	 * String
	 */
	public String getItemCode(){
		if(itemCode == null){
			while(true){
			    itemCode = random.nextInt(99999)+"";
			    if(itemCode.length()<= 5){
			    	int length = itemCode.length();
					for(int i = 0;i < 5-length;i++){
						itemCode = "0"+itemCode; 
					}
				}
			    itemCode = "D"+itemCode;
			    if(itemCodeSet.add(itemCode)){
					return itemCode;
				}
			}
		}
		return itemCode;
	}
	private Random random = new Random();
	private String getRandomValue(List<String> list){
		String value = "";
		if(list == null || list.isEmpty()){
			value = "";
		}else{
			int size = list.size();
			int index = random.nextInt(size);
			value = list.get(index);
		}
		return value.trim();
	}
	/**
	 *  返回二者之一
	 * */
	public String getOptionContainer(){
		String s = "Product Info Column";
		s = "Block after Info Column";
		return s;
	}
	private String price;
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 * costPrice:成本价
	 */ 
	private int costPrice ;
	public int getCostPrice(){
		if(!(costPrice > 0)){
			getPrice();
		}
		return costPrice;
	}
	/**
	 * 功能：如果价格已经初始化，则字节返回 <br>
	 * 注意事项：<font color="red">这是实际售价</font> <br>
	 * @return
	 * String
	 */
	public String getPrice(){
		if(price == null){
			PriceHandle priceHandle = new  PriceHandle();
			String temp[] = priceHandle.computePrice(productId,tags);
			price = temp[0];
			costPrice = Integer.parseInt(temp[3]);
			return temp[0];
		}
		return price;
	}
	public String getRequiredOptions(){
		return "1";
	}
	public String getShortDescription(){
		StringBuilder sb = new StringBuilder();
		int SIZE = 7;
		for(int i = 0; i< SIZE ;i++){
			String category = SimpleProductConfig.NAME_CATAGORY[i];
			String s = getRandomValue(catalogTagMap.get(category));
			if(s == null || s.equals("")){
				continue;
			}
			sb.append(s);
			sb.append(" ");
		}
		sb.append("with");
		sb.append(" ");
		String s = getRandomValue(catalogTagMap.get(SimpleProductConfig.NAME_CATAGORY[9]));
		sb.append(s);
		sb.append(" ");
		sb.append("waistline");
		return sb.toString();
	}
	public String getTaxClassId(){
		/*
		 * 	0==>None
			2==>Taxable Goods
			4==>Shipping
		 * */
		return "4";
	}
	public String getUrlKey() throws Exception{
		String urlKey = name;
		urlKey = urlKey.replace(" ", "-");
		urlKey = urlKey.replace("/", "-");
		 urlKey =  urlKey.toLowerCase();
		return "p/"+urlKey+"-"+getProductSku();
	}
	public String getUrlPath() throws Exception{
		String urlPath = name.replace(" ", "-");
		urlPath = urlPath.replace("_", "-");
		urlPath = urlPath.replace("/", "-");
		urlPath = urlPath.toLowerCase();
		return "p/"+urlPath+"-"+getProductSku()+".html";
	}
	public String getQty(){
		return "10000000";
	}
	 
	/**
	 * 功能：对图片相关赋值全在这里 <br>
	 * 注意事项：<font color="red">描述信息还没有正确生产</font> <br>
	 * @return
	 * @throws DBException
	 * ImageRelated
	 */
	public ImageRelated genericImageRelated(String name) throws DBException{
		ImageHandle handle = new ImageHandle(name);
		dao.getImageByProId(productId, handle);
		List<Image> imageList = handle.getImageList();
//		System.out.println("Imgae List Size:"+imageList.size());
		ImageRelated imgRelated = new ImageRelated(imageList);
		return imgRelated;
	}
	/**
	 * 属性以及对应的选中项，都存放一个map中
	 * */
	public Map<String,List<String>> genericAttributeOptions() throws DBException{
		String productTypes = SimpleProductConfig.productTypes[0]; 
		List<String> tempList = catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[0]);
		if(tempList != null && !tempList.isEmpty() && tempList.contains("Bridal Gowns")){
			productTypes = SimpleProductConfig.productTypes[0]; 
		}else{
			productTypes = SimpleProductConfig.productTypes[1]; 
		}
		
		CustonAttributeHandle handle = new CustonAttributeHandle(productTypes);
		dao.getTagInfoByPId(productId, handle);
		Map<String,List<String>> map = handle.getAttrOptionMap();
		addColorChart(map);
		return map;
	}
	/**
	 产品页色卡（Color Chart）确定方案：
	一. 当主面料不为蕾丝（Lace）时，使用主面料对应的色卡
	二. 当主面料为蕾丝（Lace）时，使用面料一对应的色卡
	三. 当需要用到纱（Tulle）的色卡时，直接使用欧根纱（Organza）的色卡
	 * void
	 */
	public void addColorChart(Map<String,List<String>> map){
		List<String> list = new ArrayList<String>();
		if(mainFabricTags.contains("Lace")){
			if(fabric1Tags != null)
				list.addAll(fabric1Tags);
		}else{
			list.addAll(mainFabricTags);
		}
		if(list.contains("Tulle")){
			list.remove("Tulle");
			list.add("Organza");
		}
		map.put(SimpleProductConfig.COLOR_CHART, list);
	}
	private FileOperate op = new FileOperate();
	private String shownColor = "";
	/**
	 * 以下生成自定义的颜色、尺寸以及尺寸的子尺寸
	 * */
	public void genericCustonOptionColor(List<CustomOption> customList,String shownColor,int adjustment) throws DBException{
		this.shownColor = shownColor;
//		ColorHandle handle = new ColorHandle();
//		Set<String> colors = handle.getColorSet(getTags());
		List<String> colors = getColors(adjustment);
		if(colors.isEmpty()){
			logger.error("颜色值为空，pid:"+productId+"\tTags:"+getTags());
			op.addContentToEndOfFile2("data/null_color","pid:"+productId+"\tTags:"+getTags());
		}
		//经过打印结果知道，这里颜色值确实有空值。
		String colorss [][] = new String[colors.size()][];
		int index = 0;
		for(String color : colors){
			colorss[index] = new String[]{color,"0"};
			index++;
		}
		genericCustomOption("Color",colorss,customList,"1");
	}
	public void genericCustonOptionSize(List<CustomOption> customList){
//		List<String> sizes = Arrays.asList("10","20","30","custom");
		//genericCustomOption("Size",SimpleProductConfig.sizes,customList,"2");
	}
	
	private void genericCustomOption(String _custom_option_title,String[][] _custom_option_row ,List<CustomOption> customList,String sortOrder){
		int i = 0;
		for(String title[] : _custom_option_row){
			CustomOption colorCustom ;
			String posi = (i+1)+"";//标记各个选项的位置
			if(title[0].trim().equalsIgnoreCase(SimpleProductConfig.THE_SAME_AS_PICTURE)){
				if(this.shownColor != null && !this.shownColor.trim().equals("")){
					title[0] += " ( "+this.shownColor.replace("_", " ")+" )";
				}
				posi = "0";
			}
			if(i == 0 ){
				 colorCustom = new CustomOption("", "drop_down", _custom_option_title, "1", "", "", "0", sortOrder, title[0], title[1], "", posi);
			}else{
				
				 colorCustom = new CustomOption("", "", "", "", "", "", "", "", title[0], title[1], "", posi);
			}
			customList.add(colorCustom);
			i++;
		}
		//System.out.println(customList);
		System.out.print(productId);
		System.out.print(",");
		for(CustomOption c : customList){
			System.out.print(c.get_custom_option_row_title());
			System.out.print(",");
		}
		System.out.println("");
		
		i = 0;
		for(String title[] : _custom_option_row){
			if(i > 0){
				CustomOption colorCustom = new CustomOption("default", "", "", "", "", "", "", "", title[0], "", "", "");
				customList.add(colorCustom);
			}
			i++;
		}
	}
//	public void genericCustonOptionPannier(List<CustomOption> customList){
//		customList.add(new CustomOption("", "checkbox", "Skirt", "0", "", "", "", "3", "Sleected", "12", "", "1"));
//	}
	public void genericCustonOptionCustomSize(List<CustomOption> customList){
		customList.add(new CustomOption("", "field", "Bust", "0", "0", "", "0", "4", "", "", "", ""));
		customList.add(new CustomOption("", "field", "Waist", "0", "0", "", "0", "5", "", "", "", ""));
		customList.add(new CustomOption("", "field", "Hips", "0", "0", "", "0", "6", "", "", "", ""));
		customList.add(new CustomOption("", "field", "Hollow to Floor", "0", "0", "", "0", "7", "", "", "", ""));
	}
	public int getSortScore(){
		return ProductScoreHandle.getScore(tags);
	}
	public String getHasPetticoat(){
		if(catalogTagMap.get(SimpleProductConfig.PRTTICOAT) == null || catalogTagMap.get(SimpleProductConfig.PRTTICOAT).isEmpty()){
			return "0";
		}else{
			return "1";
		}
	}
	/**
	 * 	一. 当主面料为蕾丝（Lace）、面料一为亮片（Sequined）时，选色范围仅显示图片色
		二. 婚纱（Bridal Gowns）品类产品，需修图的情况下，颜色下拉选择菜单为7种可选色
		三. 婚纱（Bridal Gowns）品类产品，不需修图的情况下，颜色下拉选择菜单为图片色加7种可选色
		四. 除婚纱品类以外的产品，需修图的情况下，颜色下拉选择菜单为28种可选色
		五. 除婚纱品类以外的产品，不需修图的情况下，颜色下拉选择菜单为图片色加28种可选色
	 * 
	 * */
	private List<String> getColors(int adjustment){
		List<String> colors = new ArrayList<String>();
		if(mainFabricTags.contains("Lace") && fabric1Tags.contains("Sequined")){
			colors.add(SimpleProductConfig.THE_SAME_AS_PICTURE);
		}else{
			List<String> tempList = catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[0]);
			if(tempList != null && !tempList.isEmpty() && tempList.contains("Bridal Gowns")){
				colors.addAll(SimpleProductConfig.bridalColors);
				if(adjustment == 1){
					colors.remove(SimpleProductConfig.THE_SAME_AS_PICTURE);
				}
			}else{
				colors.addAll(SimpleProductConfig.otherColors);
				if(adjustment == 1){
					colors.remove(SimpleProductConfig.THE_SAME_AS_PICTURE);
				}
			}
		}
		if(this.shownColor == null || this.shownColor.trim().equals("")){
			if(colors.contains(SimpleProductConfig.THE_SAME_AS_PICTURE)){
				colors.remove(SimpleProductConfig.THE_SAME_AS_PICTURE);
			}
		}
		
//		//只要面料一为亮片的产品颜色选择下拉菜单仅显示图片色
//		if(fabric1Tags.contains("Sequined")){
//			colors.add(SimpleProductConfig.THE_SAME_AS_PICTURE);
////			return colors;
//		}else{
//			List<String> tempList = catalogTagMap.get(SimpleProductConfig.TAG_CATALOG_DRESS[0]);
//			if(tempList != null && !tempList.isEmpty() && tempList.contains("Bridal Gowns")){
//				colors.addAll(SimpleProductConfig.bridalColors);
//			}else{
//				colors.addAll(SimpleProductConfig.otherColors);
//			}
//		}
//		if(adjustment == 1 && !fabric1Tags.contains("Sequined")){
//			if(colors.contains(SimpleProductConfig.THE_SAME_AS_PICTURE)){
//				colors.remove(SimpleProductConfig.THE_SAME_AS_PICTURE);
//			}
//		}
//		if(adjustment == 1 && mainFabricTags.contains("Lace") && fabric1Tags.contains("Sequined")){
//			System.out.println(productId);
//		}
		return colors;
	}
	
}
