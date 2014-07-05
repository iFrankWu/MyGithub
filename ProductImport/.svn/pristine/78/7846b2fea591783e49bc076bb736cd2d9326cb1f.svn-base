package com.shinetech.handle;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shinetech.simple.SimpleProductConfig;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * 根据配置文件生产自定义属性可选项
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */
public class CustonAttributeHandle implements ResultSetHandler{
	/**
	 * 将所有的属性和属性的选项值都存放到一个map中
	 * */
	private Map<String,List<String>> attrMap = new HashMap<String,List<String>>();
	/**
	 * 存放标签对应的选项
	 * */
	private Map<String,List<String>> optionTagMap =new HashMap<String, List<String>>();
	
	public static void main(String[] args) {
		Const.initLogger();
		CustonAttributeHandle service = new CustonAttributeHandle(SimpleProductConfig.productTypes[0]);
		CustonAttributeHandle service1 = new CustonAttributeHandle(SimpleProductConfig.productTypes[1]);
		service.test();
		service1.test();
	}
	public void test(){
		System.out.println("属性Map--------");
		for(String s : attrMap.keySet()){
			System.out.println(s+":"+attrMap.get(s));
		}
		System.out.println("选项Map---------");
		for(String s : optionTagMap.keySet()){
			System.out.println(s+":"+optionTagMap.get(s));
		}
	}
	/**
	 * map:存放属性以及对于的选中项
	 */ 
	private Map<String,List<String>>  map = new HashMap<String, List<String>>();
	/**
	 * 功能：找到一个产品的所有的属性code以及对应的选中项 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param tags
	 * @return
	 * Map<String,List<String>>
	 */
	public Map<String,List<String>> getSelectedOption(List<String> tags){
		for(String tag : tags){
			getSelectedOption(tag);
		}
		return map;
	}
	/**
	 * 功能：通过一个tag,找到对应的属性code和属性的选中项 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param tag
	 * void
	 */
	public void getSelectedOption(String tag){
		List<String> options = getOption(tag);
		for(String option : options){
			List<String> codes = getAttributeCode(option);
			for(String code : codes){
				List<String> list = map.get(code);
				if(list == null){
					list = new ArrayList<String>();
					map.put(code, list);
				}
				list.add(option);
			}
		}
	}
	private String productType ;
	public CustonAttributeHandle(String productType){
		this.productType = productType;
		init(productType);
	}
	/**
	 * 功能：一个option，可能对应于多个属性code,故此时返回的是数组 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param option
	 * @return
	 * String[]
	 */
	private List<String> getAttributeCode(String option){
		List<String> code = new  ArrayList<String>();
		for(String c : attrMap.keySet()){
			if(attrMap.get(c).contains(option)){
				code.add(c);
			}
		}
		return code;
	}
	/**
	 * 功能：一个tag最多对应一个Option <br>
	 * 注意事项：<font color="red">事实上，它对应多个Option</font> <br>
	 * @param tag
	 * @return
	 * String
	 */
	private List<String> getOption(String tag){
		List<String> opt = new ArrayList<String>();
		for(String option : optionTagMap.keySet()){
			if(optionTagMap.get(option).contains(tag)){
				opt.add(option);
			}
		}
		return opt;
	}
//	private String prefixAttr = SimpleProductConfig.PREFIX_ATTRIBUTE[0];
	/**
	 * 功能：读取文件值，将文件中的内容存入2个Map中，方便查询 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * void
	 */
	private void init(String productType){
		FileOperate op = new FileOperate();
		int index = 0;
		if(productType.equals(SimpleProductConfig.productTypes[0])){
			index = 0;
		}else{
			index = 1;
		}
//		prefixAttr = SimpleProductConfig.PREFIX_ATTRIBUTE[index];
		op.getLine(new File(SimpleProductConfig.ATTRIBUTE_FILE[index]),new ILineHandle<String>(){

			@Override
			public String lineHandle(String line) {
				handleLine(line);
				return null;
			}
		});
	}
	private void handleLine(String line){
		String t[] = line.split(":");
		String attribute = t[0];
		if(attribute.endsWith("[T]")){
			attribute = attribute.substring(0, attribute.indexOf("[T]"));
		}else{
//			不显示
			return;
		}
//		attribute = prefixAttr+attribute;
		if(attribute.contains("{")){
			attribute = attribute.substring(0, attribute.indexOf("{"));
		}
		//如果此时的属性为Venues，并且产品为非婚纱，那么此时不加载到属性Map中
		if(productType.equals(SimpleProductConfig.productTypes[1]) && attribute.equals("Venues")){
			return;
		}
		attribute = attribute.toLowerCase().replace(" ", "_");//将属性变成属性Code
		String options[] = t[1].split(";");
		List<String> optionList = new ArrayList<String>();
		
	
		for(String option:options){
			List<String> tagList = new ArrayList<String>();
			if(option.endsWith("[T]")){
				option = option.substring(0, option.indexOf("[T]"));
			}else{
				continue;
			}
			if(option.contains("{")){
				int start = option.indexOf("{");
				int end = option.indexOf("}");
				String tags = option.substring(start+1,end);
				tagList.addAll(Arrays.asList(tags.split(",")));
				option = option.substring(0, start).trim();
				optionList.add(option);
			}else{
				option = option.trim();
				tagList.add(option);
				optionList.add(option);
			}
			optionTagMap.put(option, tagList);
			
			 /*检查分类在数据库是否存在*/
//			 TagAdapter checkTag = TagAdapter.getInstance();
//			 for(String tg : tagList){
//				Tag tag = checkTag.getTag(tg);
//				if(tag == null){
//					System.out.println(tg+" Not exist!");
//				}
//			}
		}
		attrMap.put(attribute, optionList);
	}
	public Map<String,List<String>> getAttrOptionMap(){
		for(String key : map.keySet()){
			List<String> list = map.get(key);
			Set<String> set = new HashSet<String>();
			set.addAll(list);
			list.clear();
			list.addAll(set);//去重
		}
		return map;
	}
	@Override
	public void handle(ResultSet rs) {
		try {
			String tag = rs.getString("tag_name");
			tag = tag.replace("\"", "").trim();
			getSelectedOption(tag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
