package com.shinetech.dress.tag;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.Tools;

public class TagUpdateRule3 {

	private List<Tag> allList = new ArrayList<Tag>();
	private HashMap<String, List<Tag>> filterMap = new HashMap<String, List<Tag>>();

	private PrintWriter printer = null;
	private String filename = "assets/rule/step3.rule.txt";
	private String output = "assets/rule/step3.sql.txt";

	public static void main(String[] args) {
		Const.initLogger();
		TagUpdateRule3 tu = new TagUpdateRule3();
		tu.execute();
	}

	public void execute() {
		loadConfig();
		initOutput();
		executeConfig();
		closeOutput();
		executeSqlList(sqllist);
	}

	private List<String> sqllist = new ArrayList<String>();

	private void executeSqlList(List<String> list) {
		IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		try {
			db.executeBatch(list);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	HashMap<Long, List<Long>> productTagMap = new HashMap<Long, List<Long>>();

	private void executeConfig() {
		// 加载所有的产品和标签数据
		IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		String sql = "select product_id, tag_id from dr_product_tag";
		try {
			db.handleList(sql, new ResultSetHandler() {
				@Override
				public void handle(ResultSet rs) {
					try {
						long productId = rs.getLong("product_id");
						long tagId = rs.getLong("tag_id");
						if (productTagMap.containsKey(productId) == false) {
							productTagMap.put(productId, new ArrayList<Long>());
						}
						productTagMap.get(productId).add(tagId);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Iterator<Long> iter = productTagMap.keySet().iterator();
		while (iter.hasNext()) {
			// 遍历产品
			long productId = iter.next();
			for (Tag tagBean : allList) {
				// 遍历通配的标签
				boolean match = true;
				long tagId = tagBean.getTagId();
				String isql = "insert into dr_product_tag(product_id, tag_id) value(" + productId + "," + tagId + ")";
				if (filterMap.containsKey(tagBean.getName())) {
					List<Tag> ftaglist = filterMap.get(tagBean.getName());
					for (Tag ftagBean : ftaglist) {
						// 通配的标签存在例外
						if (productTagMap.get(productId).contains(ftagBean.getTagId())) {
							System.out.println("发现例外:" + ftagBean.getName());
							match = false;
							break;
						}
					}
				}
				if (match) {
					output(isql + ";");
					sqllist.add(isql);
				}
			}
		}
	}

	private void loadConfig() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				handleLine(line);
			}
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleLine(String line) {
		if (line == null || line.trim().length() == 0) {
			return;
		}
		if (line.trim().startsWith("*")) {
			String[] parts = Tools.split(line.trim(), ":");
			if (parts.length < 2) {
				System.out.println("无效的行:" + line);
				System.exit(0);
			}
			String[] items = Tools.split(parts[1].trim(), ";");
			for (int i = 0; i < items.length; i++) {
				if (items[i].trim().length() == 0) {
					continue;
				}
				String tag = items[i].trim();
				Tag tagBean = TagAdapter.getInstance().getTag(tag);
				if (tagBean == null) {
					System.out.println("无效的Tag:" + tag + ";行:" + line);
					System.exit(0);
				}
				allList.add(tagBean);
			}
		} else if (line.trim().startsWith("!")) {
			String[] parts = Tools.split(line.trim(), ":");
			if (parts.length < 2) {
				System.out.println("无效的行:" + line);
				System.exit(0);
			}
			String tag = parts[0].trim().substring(1);
			Tag tagBean = TagAdapter.getInstance().getTag(tag);
			if (tagBean == null) {
				System.out.println("无效的Tag:" + tag + ";行:" + line);
				System.exit(0);
			}
			List<Tag> flist = new ArrayList<Tag>();
			String[] items = Tools.split(parts[1].trim(), ";");
			for (int i = 0; i < items.length; i++) {
				if (items[i].trim().length() == 0) {
					continue;
				}
				String ftag = items[i].trim();
				Tag ftagBean = TagAdapter.getInstance().getTag(ftag);
				if (ftagBean == null) {
					System.out.println("无效的Tag:" + ftag + ";行:" + line);
					System.exit(0);
				}
				flist.add(ftagBean);
			}
			filterMap.put(tag, flist);
		} else {
			System.out.println("无效的行:" + line);
		}
	}

	private void initOutput() {
		try {
			printer = new PrintWriter(new FileWriter(output, false), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void output(String sql) {
		printer.println(sql);
	}

	private void closeOutput() {
		printer.flush();
		printer.close();
	}

}
