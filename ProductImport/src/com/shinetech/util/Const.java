package com.shinetech.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;

public class Const {
	public static String[] ATTRIBUTE_FILE = "assets/attribute_wedding,assets/attribute_no_wedding".split(",");
	public static String OTHER_ATTRIBUTE = "assets/other_attribute";
	public static String OUT_IMPORT_CSV ="assets/import.csv";
	public static String CATALOG_TAGS= "assets/catalog_tags";
	public static String CATALOG_IMPORT_FORMAT = "assets/catalog_import_format";
	public static String OUT_IMPORT_SIMPLE_CSV ="data/simple_import.csv";
	public static String DELETE_PRO_LIST_FILE = "assets/delete_pro_list";
	
	public static int WIDTH_SMAll = 176+1;
	public static int HIGHT_SMALL = 270;
	public static int WIDTH_THUMB = 95+1;
	public static int HIGHT_THUMB = 146;
	
	
	
	public static List<String> FABRIC_CATALOGS = Arrays.asList("Fabric,The Main Fabric,Fabric1,Fabric2".split(","));
	public static String WINDOWS_202 = "//192.168.2.202/DataBackup/wedding/";
	public static String UNIX_202 = "/mnt/wedding/";
	
	
	public static String ENTRY_DIR = UNIX_202;
	
	static{
		String osName = System.getProperties().getProperty("os.name");
		if (osName != null && osName.contains("Linux")) {
			ENTRY_DIR = UNIX_202;
		}else{
			ENTRY_DIR = WINDOWS_202;
		}
	}
	
	/**
	 * EXPORT_IMAGE_PATH:原始图片存放路径
	 */ 
	public static String EXPORT_IMAGE_PATH = ENTRY_DIR+"qualified_image_12-15/";
	
	/**
	 * EXPORT_IMAGE_PATH_BY_SIZE:尺寸目录
	 */ 
	public static String EXPORT_IMAGE_PATH_BY_SIZE = ENTRY_DIR+"qualified_image_by_size_12-15/";
	/**
	 * EXPORT_IMAGE_PATH_WATER:有水印的图片文件
	 */ 
	public static String EXPORT_IMAGE_PATH_WATER = ENTRY_DIR+"qualified_image_water/";
	
	public static String CATALOG_PRODUCT = "/opt/lampp/htdocs/wordpress/magento/media/catalog/product";

	/**
	 * 功能：启动日志<br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	public  static void initLogger() {
		String log4j = System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j.xml";
 		DOMConfigurator.configure(log4j);
	}
}
