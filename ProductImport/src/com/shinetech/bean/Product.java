/*
 * Product.java	 <br>
 * 2011-10-10<br>
 * com.shinetech.bean <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinetech.util.Const;
import com.shinetech.util.DateUtil;
import com.shinetech.util.FileOperate;

/**
 * 导入产品的所有的属性字段
 * 各字段的意义：
 * onenote:///\\SERVER2\Onenote协作区\IT开发\2新软件站\图片采集系统.one#导入字段值的来源说明&section-id={2858E892-0539-4E7D-A635-9EB0AFE09CF8}&page-id={9756FE36-D18D-4F01-8663-E06EC0030093}&object-id={B5EF1544-07D7-0540-1998-E6E735980F6F}&F
 * 
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class Product {
	public  String sku;
	public  String _store = "";
	public  String _attribute_set;
	public  String _type;
	//public  String _category;
	public List<String> categoryList = new ArrayList<String>();
	public  String _product_websites = "base";
//	public  String color = "black";
//	public  String cost = "";
	public  String country_of_manufacture = "China";
	public  String created_at = DateUtil.getNowDate();
	public  String custom_design = "";
	public  String custom_design_from = "";
	public  String custom_design_to = "";
	public  String custom_layout_update = "";
	public  String description;
	public  String enable_googlecheckout = "0";
	public  String gift_message_available = "";
	public  String has_options = "0";//简单产品为0，配置产品为1
	public  String discount;//custom
 	public  String is_imported = "Yes";
//	public  String manufacturer = "china";
	public  String media_gallery = "";
	public  String meta_description;
	public  String meta_keyword;
	public  String meta_title;
	public  String minimal_price = "";
	public  String msrp = "";
	public  String msrp_display_actual_price_type = "Use config";
	public  String msrp_enabled = "Use config";
	public  String name;
	public  String news_from_date = "";
	public  String news_to_date = "";
	public  String options_container;
	public  String page_layout = "";
	public  String price;
	public  String required_options = "0";//简单 == 0，配置 = 1
	public  String short_description;
	public  String size;
	public  String special_from_date = "";
	public  String special_price = "";
	public  String special_to_date = "";
	public  String status = "1";
	public  String tax_class_id = "4";
	public  String gallery = "";//custom
	public  String updated_at = DateUtil.getNowDate();
	public  String url_key;
	public  String url_path;
	public  String visibility = "4";
	public  String weight;
	public  String qty = "100000000";
	public  String min_qty = "0";
	public  String use_config_min_qty = "1";
	public  String is_qty_decimal = "0";
	public  String backorders = "0";
	public  String use_config_backorders = "1";
	public  String min_sale_qty = "1";
	public  String use_config_min_sale_qty = "1";
	public  String max_sale_qty = "0";
	public  String use_config_max_sale_qty = "1";
	public  String is_in_stock = "1";
	public  String notify_stock_qty = "";
	public  String use_config_notify_stock_qty = "1";
	public  String manage_stock = "0";
	public  String use_config_manage_stock = "1";
	public  String stock_status_changed_auto = "0";
	public  String use_config_qty_increments = "1";
	public  String qty_increments = "0";
	public  String use_config_enable_qty_inc = "1";
	public  String enable_qty_increments = "0";
	public  String _links_related_sku = "";
	public  String _links_related_position = "";
	public  String _links_crosssell_sku = "";
	public  String _links_crosssell_position = "";
	public  String _links_upsell_sku = "";
	public  String _links_upsell_position ="";
	public  String _associated_sku = "";
	public  String _associated_default_qty = "";
	public  String _associated_position = "";
	public  String _tier_price_website = "";
	public  String _tier_price_customer_group = "";
	public  String _tier_price_qty = "";
	public  String _tier_price_price = "";
	/**
	 * customOptionList:自定义 选项 custom option可能有多个
	 */ 
	public List<CustomOption> customOptionList =new ArrayList<CustomOption>() ;
	/**
	 * imgRelated:图片相关的变量
	 */ 
	public ImageRelated imgRelated ;
	/**
	 * attrMap:对属性及其可选项的操作，全部写入map中
	 */ 
	public Map<String,List<String>> attrMap = new HashMap<String, List<String>>();
	/**
	 * superProductsList:由可配置产品生产的简单产品
	 */ 
	public List<SuperProducts> superProductsList = new ArrayList<SuperProducts>();
	
	/*public String toString() {
		return  sku+","+_store+","+_attribute_set+","+_type+","+_category+","+_product_websites+","+back_details+","+body_shape+","+color+","+cost+","+country_of_manufacture+","+created_at+","+custom_design+","+custom_design_from+","+custom_design_to+","+custom_layout_update+","+description+","+discount+","+embellishment+","+enable_googlecheckout+","+fabric+","+gallery+","+gift_message_available+","+has_options+","+hemline+","+image+","+image_label+","+is_imported+","+manufacturer+","+media_gallery+","+meta_description+","+meta_keyword+","+meta_title+","+minimal_price+","+msrp+","+msrp_display_actual_price_type+","+msrp_enabled+","+name+","+neckline+","+news_from_date+","+news_to_date+","+options_container+","+page_layout+","+price+","+required_options+","+short_description+","+sillouette+","+size+","+sleeve_length+","+sleeve_style+","+small_image+","+small_image_label+","+special_from_date+","+special_occasion_dresses+","+special_price+","+special_to_date+","+status+","+style+","+tax_class_id+","+thumbnail+","+thumbnail_label+","+train+","+updated_at+","+url_key+","+url_path+","+venues+","+visibility+","+waistline+","+wedding_dresses+","+weight+","+qty+","+min_qty+","+use_config_min_qty+","+is_qty_decimal+","+backorders+","+use_config_backorders+","+min_sale_qty+","+use_config_min_sale_qty+","+max_sale_qty+","+use_config_max_sale_qty+","+is_in_stock+","+notify_stock_qty+","+use_config_notify_stock_qty+","+manage_stock+","+use_config_manage_stock+","+stock_status_changed_auto+","+use_config_qty_increments+","+qty_increments+","+use_config_enable_qty_inc+","+enable_qty_increments+","+_links_related_sku+","+_links_related_position+","+_links_crosssell_sku+","+_links_crosssell_position+","+_links_upsell_sku+","+_links_upsell_position+","+_associated_sku+","+_associated_default_qty+","+_associated_position+","+_tier_price_website+","+_tier_price_customer_group+","+_tier_price_qty+","+_tier_price_price+","+_media_attribute_id+","+_media_image+","+_media_lable+","+_media_position+","+_media_is_disabled+","+_custom_option_store+","+_custom_option_type+","+_custom_option_title+","+_custom_option_is_required+","+_custom_option_price+","+_custom_option_sku+","+_custom_option_max_characters+","+_custom_option_sort_order+","+_custom_option_row_title+","+_custom_option_row_price+","+_custom_option_row_sku+","+_custom_option_row_sort+","+_super_products_sku+","+_super_attribute_code+","+_super_attribute_option+","+_super_attribute_price_corr;
	}*/
	private FileOperate op = new FileOperate();
	private String attrs[] = "back_details,body_shape,embellishment,fabric,hemline,neckline,season,sillouette,sleeve_length,sleeve_style,special_occasion_dresses,style,train,venues,waistline,wedding_dresses".split(",");
	public void genericConfigPro(){
		genericHeader();
		int rows = getRows(customOptionList, imgRelated, attrMap, superProductsList);
		StringBuilder sb = new StringBuilder();
		
		//String s =sku+","+_store+","+_attribute_set+","+_type+","+_category+","+_product_websites+","+color+","+cost+","+country_of_manufacture+","+created_at+","+custom_design+","+custom_design_from+","+custom_design_to+","+custom_layout_update+","+description+","+discount+","+enable_googlecheckout+","+gallery+","+gift_message_available+","+has_options+","+is_imported+","+manufacturer+","+media_gallery+","+meta_description+","+meta_keyword+","+meta_title+","+minimal_price+","+msrp+","+msrp_display_actual_price_type+","+msrp_enabled+","+name+","+news_from_date+","+news_to_date+","+options_container+","+page_layout+","+price+","+required_options+","+short_description+","+size+","+special_from_date+","+special_price+","+special_to_date+","+status+","+tax_class_id+","+updated_at+","+url_key+","+url_path+","+visibility+","+weight+","+qty+","+min_qty+","+use_config_min_qty+","+is_qty_decimal+","+backorders+","+use_config_backorders+","+min_sale_qty+","+use_config_min_sale_qty+","+max_sale_qty+","+use_config_max_sale_qty+","+is_in_stock+","+notify_stock_qty+","+use_config_notify_stock_qty+","+manage_stock+","+use_config_manage_stock+","+stock_status_changed_auto+","+use_config_qty_increments+","+qty_increments+","+use_config_enable_qty_inc+","+enable_qty_increments+","+_links_related_sku+","+_links_related_position+","+_links_crosssell_sku+","+_links_crosssell_position+","+_links_upsell_sku+","+_links_upsell_position+","+_associated_sku+","+_associated_default_qty+","+_associated_position+","+_tier_price_website+","+_tier_price_customer_group+","+_tier_price_qty+","+_tier_price_price+","+_custom_option_store+","+_custom_option_type+","+_custom_option_title+","+_custom_option_is_required+","+_custom_option_price+","+_custom_option_sku+","+_custom_option_max_characters+","+_custom_option_sort_order+","+_custom_option_row_title+","+_custom_option_row_price+","+_custom_option_row_sku+","+_custom_option_row_sort+","+_super_products_sku+","+_super_attribute_code+","+_super_attribute_option+","+_super_attribute_price_corr+","+image+","+image_label+","+small_image+","+small_image_label+","+thumbnail+","+thumbnail_label+","+_media_attribute_id+","+_media_image+","+_media_lable+","+_media_position+","+_media_is_disabled+","+back_details+","+body_shape+","+embellishment+","+fabric+","+hemline+","+neckline+","+sillouette+","+season+","+style+","+train+","+waistline+","+venues+","+wedding_dresses+","+sleeve_length+","+sleeve_style+","+special_occasion_dresses;
		
		for(int i = 0; i< rows;i++){
			if(i == 0){
				sb.append(sku+","+_store+","+_attribute_set+","+_type+","+_product_websites+","+country_of_manufacture+","+created_at+","+custom_design+","+custom_design_from+","+custom_design_to+","+custom_layout_update+","+description+","+discount+","+enable_googlecheckout+","+gallery+","+gift_message_available+","+has_options+","+is_imported+","+media_gallery+","+meta_description+","+meta_keyword+","+meta_title+","+minimal_price+","+msrp+","+msrp_display_actual_price_type+","+msrp_enabled+","+name+","+news_from_date+","+news_to_date+","+options_container+","+page_layout+","+price+","+required_options+","+short_description+","+size+","+special_from_date+","+special_price+","+special_to_date+","+status+","+tax_class_id+","+updated_at+","+url_key+","+url_path+","+visibility+","+weight+","+qty+","+min_qty+","+use_config_min_qty+","+is_qty_decimal+","+backorders+","+use_config_backorders+","+min_sale_qty+","+use_config_min_sale_qty+","+max_sale_qty+","+use_config_max_sale_qty+","+is_in_stock+","+notify_stock_qty+","+use_config_notify_stock_qty+","+manage_stock+","+use_config_manage_stock+","+stock_status_changed_auto+","+use_config_qty_increments+","+qty_increments+","+use_config_enable_qty_inc+","+enable_qty_increments+","+_links_related_sku+","+_links_related_position+","+_links_crosssell_sku+","+_links_crosssell_position+","+_links_upsell_sku+","+_links_upsell_position+","+_associated_sku+","+_associated_default_qty+","+_associated_position+","+_tier_price_website+","+_tier_price_customer_group+","+_tier_price_qty+","+_tier_price_price+",");
			}else{
				for(int j = 0;j < 78;j++)
					sb.append(",");
			}
			if(i <categoryList.size()){
				sb.append(categoryList.get(i));
			} 
			sb.append(",");
			
			if(i < customOptionList.size()){
				CustomOption  option = customOptionList.get(i);
//				if(i == 0){
					sb.append(option.toString());
//				}else{
//					sb.append(option.to2String());
//				}
			}else{
				CustomOption  option = new CustomOption("", "", "", "", "", "", "", "", "", "", "", "");
				sb.append(option.toString());
			}
			
			if(i < superProductsList.size()){
				SuperProducts superPro = superProductsList.get(i);
				sb.append(superPro.toString());
			}else{
				sb.append(new SuperProducts("","","","").toString());
			}
			
			if(i < imgRelated.getImgList().size()){
				Image img = imgRelated.getImgList().get(i);
				if(i == 0){
					sb.append(imgRelated.toString());
				}else{
					sb.append(new ImageRelated().toString());
				}
				sb.append(img.toString());
			}else{
				sb.append(new ImageRelated().toString());
				sb.append(new Image("","","","","").toString());
			}
			for(String attr : attrs){
				List<String> list = attrMap.get(attr);
				if(list != null && i < list.size()){
					sb.append(list.get(i));
				} 
				sb.append(",");
			}
			op.addContentToEndOfFile2(Const.OUT_IMPORT_CSV, sb.toString());
			System.out.println(sb.toString());
			sb = new StringBuilder();
		}
		//System.exit(1);
	}
	public void genericSimplePro(){
		StringBuilder sb = new StringBuilder();
		sb.append(sku+","+_store+","+_attribute_set+","+_type+","+_product_websites+","+country_of_manufacture+","+created_at+","+custom_design+","+custom_design_from+","+custom_design_to+","+custom_layout_update+","+description+","+discount+","+enable_googlecheckout+","+gallery+","+gift_message_available+","+has_options+","+is_imported+","+media_gallery+","+meta_description+","+meta_keyword+","+meta_title+","+minimal_price+","+msrp+","+msrp_display_actual_price_type+","+msrp_enabled+","+name+","+news_from_date+","+news_to_date+","+options_container+","+page_layout+","+price+","+required_options+","+short_description+","+size+","+special_from_date+","+special_price+","+special_to_date+","+status+","+tax_class_id+","+updated_at+","+url_key+","+url_path+","+visibility+","+weight+","+qty+","+min_qty+","+use_config_min_qty+","+is_qty_decimal+","+backorders+","+use_config_backorders+","+min_sale_qty+","+use_config_min_sale_qty+","+max_sale_qty+","+use_config_max_sale_qty+","+is_in_stock+","+notify_stock_qty+","+use_config_notify_stock_qty+","+manage_stock+","+use_config_manage_stock+","+stock_status_changed_auto+","+use_config_qty_increments+","+qty_increments+","+use_config_enable_qty_inc+","+enable_qty_increments+","+_links_related_sku+","+_links_related_position+","+_links_crosssell_sku+","+_links_crosssell_position+","+_links_upsell_sku+","+_links_upsell_position+","+_associated_sku+","+_associated_default_qty+","+_associated_position+","+_tier_price_website+","+_tier_price_customer_group+","+_tier_price_qty+","+_tier_price_price+",");
		sb.append(",");
		CustomOption  option = new CustomOption("", "", "", "", "", "", "", "", "", "", "", "");
		sb.append(option.toString());
		sb.append(new SuperProducts("","","","").toString());
		sb.append(new ImageRelated().toString());
		sb.append(new Image("","","","","").toString());
		for(@SuppressWarnings("unused") String attr : attrs){
			sb.append(",");
		}
		String proStr = sb.toString();
		if(proStr.endsWith(",")){
			proStr = proStr.substring(0, proStr.length()-1);
		}
		op.addContentToEndOfFile(Const.OUT_IMPORT_CSV,proStr);
	}
	private void genericHeader(){
		String header = "sku,_store,_attribute_set,_type,_product_websites,country_of_manufacture,created_at,custom_design,custom_design_from,custom_design_to,custom_layout_update,description,discount,enable_googlecheckout,gallery,gift_message_available,has_options,is_imported,media_gallery,meta_description,meta_keyword,meta_title,minimal_price,msrp,msrp_display_actual_price_type,msrp_enabled,name,news_from_date,news_to_date,options_container,page_layout,price,required_options,short_description,size,special_from_date,special_price,special_to_date,status,tax_class_id,updated_at,url_key,url_path,visibility,weight,qty,min_qty,use_config_min_qty,is_qty_decimal,backorders,use_config_backorders,min_sale_qty,use_config_min_sale_qty,max_sale_qty,use_config_max_sale_qty,is_in_stock,notify_stock_qty,use_config_notify_stock_qty,manage_stock,use_config_manage_stock,stock_status_changed_auto,use_config_qty_increments,qty_increments,use_config_enable_qty_inc,enable_qty_increments,_links_related_sku,_links_related_position,_links_crosssell_sku,_links_crosssell_position,_links_upsell_sku,_links_upsell_position,_associated_sku,_associated_default_qty,_associated_position,_tier_price_website,_tier_price_customer_group,_tier_price_qty,_tier_price_price,_category,_custom_option_store,_custom_option_type,_custom_option_title,_custom_option_is_required,_custom_option_price,_custom_option_sku,_custom_option_max_characters,_custom_option_sort_order,_custom_option_row_title,_custom_option_row_price,_custom_option_row_sku,_custom_option_row_sort,_super_products_sku,_super_attribute_code,_super_attribute_option,_super_attribute_price_corr,image,image_label,small_image,small_image_label,thumbnail,thumbnail_label,_media_attribute_id,_media_image,_media_lable,_media_position,_media_is_disabled,back_details,body_shape,embellishment,fabric,hemline,neckline,season,sillouette,sleeve_length,sleeve_style,special_occasion_dresses,style,train,venues,waistline,wedding_dresses";
		op.clearAndWrite(Const.OUT_IMPORT_CSV, header);
		System.out.println(header);
	}
	/**
	 * 功能：得到配置产品的生产csv文件的行数 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param customOptionList
	 * @param imgRelated
	 * @param attrMap
	 * @param superProductsList
	 * @return
	 * int
	 */
	private  int getRows(List<CustomOption> customOptionList,ImageRelated imgRelated,Map<String,List<String>> attrMap,List<SuperProducts> superProductsList){
		int rows = 0;
		int s1 = customOptionList.size();
		rows = Math.max(rows, s1);
		rows = Math.max(rows, customOptionList.size());
		int s3 = imgRelated.getImgList().size();
		rows = Math.max(rows, s3);
		for(String attr : attrMap.keySet()){
			int s4 = attrMap.get(attr).size();
			rows = Math.max(rows, s4);
		}
		rows = Math.max(rows, superProductsList.size());
		return rows;
	}

}
