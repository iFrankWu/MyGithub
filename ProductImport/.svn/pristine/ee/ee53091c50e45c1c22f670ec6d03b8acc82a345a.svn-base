/*
 * SimpleProductConfig.java	 <br>
 * 2011-10-25<br>
 * com.shinetech.simple <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 * 
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class SimpleProductConfig {
	public static List<String> NotActiveCategory = Arrays.asList("Default Category/Petticoat,Default Category/showroom,Default Category/Special".split(","));
	public static String attributes[] = {
		"style","wedding_dresses","special_occasion_dresses","venues","silhouette",
										"hemline","train",
										"neckline","fabric",
										"body_shape","season","sleeve_length",
										"sleeve_style","waistline","back_details","embellishment"
										};
public static String NAME_ATTRIBUTES[] = {
		
		"silhouette","neckline","sleeve_length","back_details",
		"fabric","hemline","embellishment","venues"
		
		/*"wedding_dresses","special_occasion_dresses","train",
		"body_shape","season",
		"sleeve_style","waistline"*/
	};
	public static String NAME_CATAGORY[] = {
		"Silhouette",
		"Neckline",
		"Sleeve Length",
		"Back Details",
		"Fabric",
		//"Hemline/Train/Gown Length",
		"Hemline/Train",
		"Embellishment",
		"Venues",//这项值由先前的Wedding Venues改成 Venues
		"Body Shape",//该项仅显示plus size
		
		//以下各项是目前没用上的
		"Waistline",
		"Sleeve Shape/Sleeve Style",
		"Season",
		"Style"
	};
	
	/**
	 * sizes:尺寸
	 */
	public static String sizes[][] = { { "Custom Size", "16.99" },
			{ "US 2", "" }, { "US 4", "" }, { "US 6", "" }, 
			{ "US 8", "" }, { "US 10", "" }, { "US 12", "" }, { "US 14", "" },
			{ "US 16", "" }, { "US 14W", "9.99" },{ "US 16W", "9.99" }, { "US 18W", "9.99" },
			{ "US 20W", "9.99" }, { "US 22W", "9.99" }, { "US 24W", "9.99" },
			{ "US 26W", "9.99" } };
	/**
	 * productTypes:产品的分类，目前是只要是婚纱、非婚纱两种
	 */
	public static String productTypes[] = { "Wedding Dresses",
			"Special Occasion Dresses" };
	/**
	 * TAG_CATALOG_DRESS:产品2个大分类的标签，Wedding Dresses,Special Occasion Dresses 
	 */ 
	public static String TAG_CATALOG_DRESS[] = { "Wedding Dresses","Special Occasion Dresses" };
	public static String ATTRIBUTE_FILE[] = { "assets/attribute_wedding",
			"assets/attribute_wedding" };// {"assets/attribute_wedding","assets/attribute_no_wedding"};
	/**
	 * PREFIX_ATTRIBUTE:2套属性对应的前缀
	 */
	// public static String PREFIX_ATTRIBUTE[] =
	// ",".split(",");//"wedding_,no_wedding_".split(",");
	/**
	 * discounts：产品折扣数组
	 */
	public static String discounts[] = "40,40,45,45,45,50,50,50,50,50,50,55,55,55,55,55,55,60,60,60,60,60,60,65,65,65,70,70"
			.split(",");
	public static String discount_9[] = "0.49,0.49,0.49,0.49,0.49,0.49,0.49,0.69,0.69,0.89,0.89,0.89,0.99,0.99,0.99,0.99,0.99,0.99,0.99"
			.split(",");
	/**
	 * DESCRIPTION_NUMBER:存放description中的显示的数组
	 */
	public static String DESCRIPTION_NUMBER[][] = {
			"Silhouette,Neckline,Sleeve Length,Back Details,Fabric,Hemline/Train,Embellishment,Waistline,Wedding Venues,Body Shape,Season,Style"
					.split(","),
			"Silhouette,Neckline,Sleeve Length,Back Details,Fabric,Hemline/Train,Embellishment,Waistline,Occasion,Body Shape,Season,Style"
					.split(",") };

	private static String Occasion[] = "Bridesmaid Dresses,Flower Girl Dresses,Mother of the Bride Dresses,Wedding Guest Dresses"
			.split(",");
	public static List<String> FabricDisplayTag = Arrays.asList("Sequined"
			.split(","));// Arrays.asList("Chiffon,Lace,Satin,Taffeta,Tulle".split(","));
	/**
	 * map:存放Occasion显示值与标签对应关系
	 */
	public static Map<String, String> map = new HashMap<String, String>();
	static {
		for (String s : Occasion) {
			map.put(s, "Wedding Party");
		}
		map.put("Club Dresses", "Club");
		map.put("Cocktail Dresses", "Cocktail");
		map.put("Evening Dresses", "Formal Evening");
		map.put("Graduation Dresses", "Graduation");
		map.put("Homecoming Dresses", "Homecoming");
		map.put("Party Dresses", "Party");
		map.put("Prom Dresses", "Prom");
		map.put("Quinceanera Dresses", "Quinceanera");
		map.put("Sweet 16 Dresses", "Sweet 16");
		// map.put("Little Black Dresses","不显示");
		// System.out.println(FabricDisplayTag+":"+FabricDisplayTag.contains("Chiffon"));
	}
	public static String[] Catalogs = "Silhouette,Neckline,Sleeve Length,Sleeve Shape/Sleeve Style,Hemline/Train,Waistline,Back Details,Fabric,Embellishment,Venues,Season,Style,Body Shape,Wedding Dresses,Special Occasion Dresses,Accessories"
			.split(",");
	public static String[] AttributeSets = "Wedding,No Wedding".split(",");
	public static String petticoatXml = "assets/petticoat.xml";
	/**
	 * PRTTICOAT:衬裙属性
	 */ 
	public static String PRTTICOAT = "Petticoat";
	/**
	 * COLOR_CHART:色卡表
	 */
	public static String COLOR_CHART = "colorchart";
	public static String THE_SAME_AS_PICTURE = "Shown Color";
	public static String COLOR_FILE[] = "assets/color/bridal.txt,assets/color/other.txt"
			.split(",");
	
		/**
	 * SpecialTag:将Bridal Gowns在显示的时候改成Bridal Gown
	 */ 
	public static String GOWNS2GOWN[] = "Bridal Gowns,Bridal Gown".split(",");
	/**
	 * DRESSES2DRESS:将产品名的 Dresses ==> Dress
	 */ 
	public static String DRESSES2DRESS[] = "Dresses,Dress".split(",");
	
	/**
	 * bridalColors:婚纱颜色
	 */
	public static List<String> bridalColors = new ArrayList<String>();
	public static List<String> otherColors = new ArrayList<String>();
	static {
		FileOperate op = new FileOperate();
		op.getLine(COLOR_FILE[0], new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				bridalColors.add(line.trim());
				return null;
			}
		});
		op.getLine(COLOR_FILE[1], new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				otherColors.add(line.trim());
				return null;
			}
		});
	}
	public static String EDIT_TYPES[] = "product,tag,picture".split(","); 
}
