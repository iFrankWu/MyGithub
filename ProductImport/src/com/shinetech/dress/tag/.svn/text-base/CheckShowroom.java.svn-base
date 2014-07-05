/*
 * CheckShowroom.java	 <br>
 * 2011-12-20<br>
 * com.shinetech.dress.tag <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.dress.tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.Tools;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * 检查in.tsv文件是否正确
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class CheckShowroom {
	private int lineNo = 1;
	private List<ShowroomInfo> list = new ArrayList<ShowroomInfo>();
	private String inTsvFile;//初始处理文件
	 
	private List<String> filterList = new ArrayList<String>();

	//检查标签
	public static void main(String[] args) {
		Const.initLogger();
		Properties pro = new Properties();
		try{
			pro.load(new FileInputStream(new File("assets/now_use_showroom.txt")));
			String showroom = pro.getProperty("now_use_showroom_file","assets/in.tsv");
			System.out.println("当前检查的展厅文件是："+showroom);
			CheckShowroom gq = new CheckShowroom(showroom);
			gq.check();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 目的和功能：关键词中除特殊情况（a、and、as、by、for、of、on、in、is、so、the、to、up、under、with）外，单词首字母均需大写；
	 * 注意事项：
	 * @param inTsvFile
	 */
	public CheckShowroom(String inTsvFile) {
		this.inTsvFile = inTsvFile;
		filterList.addAll(Arrays.asList("a,and,as,by,for,of,on,in,is,so,the,to,up,under,with".split(",")));
	}


	public void check() {
		String filename = inTsvFile;//"assets/in.tsv";
		loadConfig(filename);
		chongfu(filename);
	}


	private void loadConfig(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				if (lineNo == 1) {
					// 忽略首行
					lineNo++;
					continue;
				}
				if (line.trim().length() == 0) {
					continue;
				}
				ShowroomInfo si = parseLine(line);
				if (si != null) {
					list.add(si);
				}
			}
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private ShowroomInfo parseLine(String line) {
		ShowroomInfo info = new ShowroomInfo();
		String[] items = Tools.split(line, "\t");
		if (items.length < 5) {
			System.out.println("无效的行:" + line);
		}
		info.setShowName(getFormattedName(items[0], items[1]));
		info.setName(items[1]);
		info.setMinPrice(getNum(items[2]));
		info.setMaxPrice(getNum(items[3]));
		
		
		String[] tags = Tools.split(items[4], ";");
		for (int i = 0; i < tags.length; i++) {
			String tag = tags[i];
			if (tag.trim().length() == 0) {
				continue;
			}
			if (tag.contains("}") && tag.contains("{")) {
				int pos = tag.indexOf("}");
				ShowroomTag st = new ShowroomTag(lineNo, tag.substring(1, pos), Tools.replace(tag.substring(pos + 1), "\"", ""));
				info.getTagList().add(st);
			} else {
				ShowroomTag st = new ShowroomTag(lineNo, null, Tools.replace(tag, "\"", ""));
				info.getTagList().add(st);
			}
		}
		lineNo++;
		return info;
	}

	private String getFormattedName(String showName, String keyword) {
		if (showName.trim().length() > 0) {
			return showName;
		}
		StringBuffer buf = new StringBuffer();
		boolean append = false;
		StringTokenizer st = new StringTokenizer(keyword, " ");
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			if (isFilter(s)) {
				if (append) {
					buf.append(" ");
				}
				buf.append(s);
			} else {
				if (append) {
					buf.append(" ");
				}
				buf.append(upperFirst(s));
			}
			append = true;
		}
		return buf.toString();
	}

	private String upperFirst(String s) {
		return (s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
	}

	private boolean isFilter(String s) {
		if (filterList.contains(s.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	private int getNum(String s) {
		if (s == null || s.trim().length() == 0) {
			return -1;
		}

		try {
			int r = Integer.parseInt(s);
			return r;
		} catch (Exception ex) {
			System.out.println("无效的行:" + lineNo);
		}
		return -1;
	}
	
	private int i = 0;
	private FileOperate op = new FileOperate();
	public void chongfu(String file){
		final Set<String> set = new HashSet<String>();
		
		op.getLine(file, new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				i++;
				String s[] = line.split("\t");
				if(s.length < 4){
					System.err.println("出现错误：行"+i+",内容："+line);
					if(line == null || line.trim().equals("")){
						System.err.println("第"+i+"行内容为空。");
					}
					return "";
				}
				if(set.add(s[1])){
					
				}else{
					System.err.println("关键词重复,行号："+i+"行内容："+line);
					if(line == null || line.trim().equals("")){
						System.err.println("行号："+i+"行内容为空，请删除");
					}
					
				}
				return null;
			}
		});
	}
}
