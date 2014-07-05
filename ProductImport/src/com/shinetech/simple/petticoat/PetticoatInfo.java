/*
 * PetticoatInfo.java	 <br>
 * 2011-11-15<br>
 * com.shinetech.simple.petticoat <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.simple.petticoat;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import com.shinetech.bean.Petticoat;
import com.shinetech.dao.DressDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;


/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class PetticoatInfo {
	private static Logger logger = Logger.getLogger(PetticoatInfo.class);
	public static void main(String[] args) throws Exception {
		com.shinetech.util.Const.initLogger();
//		PetticoatInfo.getInfo("assets/petticoat.xml");
		System.out.println(PetticoatInfo.petticoatTagProductSku());
	}
	public static List<Petticoat> getInfo(String xmlpath) throws Exception{
			org.jdom.input.SAXBuilder saxReader = new org.jdom.input.SAXBuilder();
			Document document = null;
			try {
				document = saxReader.build(xmlpath);
			} catch (JDOMException e1) {
				e1.printStackTrace();
				logger.error("xml文件出错："+xmlpath);
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.error("xml文件出错："+xmlpath);
			}
			if(document == null)
				return null;
			Element config = document.getRootElement();
			if(config == null)
				return null;
			@SuppressWarnings("unchecked")
			List<Element> list = config.getChildren();
			List<Petticoat> lt = new ArrayList<Petticoat>();
			for(Element e : list){
				Petticoat petticoat = new Petticoat();
				String name = e.getChildTextTrim("name");
				petticoat.setName(name);
				petticoat.setMetaTitle(e.getChildTextTrim("meta_title").replace("${name}", name));
				petticoat.setMetaDesci(e.getChildTextTrim("meta_desc").replace("${name}", name));
				petticoat.setDecription(e.getChildTextTrim("description"));
				petticoat.setImage(e.getChildTextTrim("image"));
				petticoat.setShort_description(e.getChildTextTrim("short_description"));
				petticoat.setKeywords("");
				lt.add(petticoat);
//				System.out.println(petticoat);
			}
			return lt;
		}
	private static Map<String,Long> map = new HashMap<String, Long>();
	public static Map<String,Long> petticoatTagProductSku(){
		if(map.isEmpty()){
			DressDAO dao = new DressDAO();
			try {
				dao.getPetticoatInfo(new ResultSetHandler() {
					
					@Override
					public void handle(ResultSet rs) {
						try{
							map.put(rs.getString("product_name"), rs.getLong("id"));
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				});
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
