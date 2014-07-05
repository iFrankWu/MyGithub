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
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.Tools;

public class TagUpdateRule1 {

	private HashMap<String, List<List<Tag>>> configMap = new HashMap<String, List<List<Tag>>>();
	private PrintWriter printer = null;

	// 规则1
	private String filename_1 = "assets/rule/step1.rule.txt";
	private String output_1 =  "assets/rule/step1.sql.txt";

	// 规则4
	private String filename_4 =  "assets/rule/step4.rule.txt";
	private String output_4 =  "assets/rule/step4.sql.txt";

	public static void main(String[] args) {
		Const.initLogger();
		TagUpdateRule1 tu = new TagUpdateRule1();
//		tu.execute(tu.filename_1, tu.output_1);
		 tu.execute(tu.filename_4, tu.output_4);
	}

	public void execute(String filename, String output) {
		loadConfig(filename);
		initOutput(output);
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

	private void executeConfig() {
		Iterator<String> iter = configMap.keySet().iterator();
		while (iter.hasNext()) {
			String catalog = iter.next();
			List<List<Tag>> plist = configMap.get(catalog);
			System.out.println("正在处理类别:" + catalog + "; plist.size=" + plist.size());
			for (List<Tag> slist : plist) {
				List<FieldParameter> fpList = new ArrayList<FieldParameter>();
				fpList.add(new FieldParameter(2, slist.get(0).getTagId(), FieldTypes.NUMERIC));
				String sql = "select distinct product_id from dr_product_tag where tag_id != ? and tag_id in (?";
				for (int i = 1; i < slist.size(); i++) {
					sql = sql + ",?";
					fpList.add(new FieldParameter(2 + i, slist.get(i).getTagId(), FieldTypes.NUMERIC));
				}
				sql = sql + ")";
				for (int i = 0; i < slist.size(); i++) {
					removeTag(fpList);
					final long tagId = slist.get(i).getTagId();
					fpList.add(new FieldParameter(1, tagId, FieldTypes.NUMERIC));
					IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
					try {
						db.handleList(sql, fpList, new ResultSetHandler() {
							@Override
							public void handle(ResultSet rs) {
								try {
									long productId = rs.getLong("product_id");
									String sql = "insert into dr_product_tag(product_id, tag_id) value(" + productId + "," + tagId + ")";
									output(sql + ";");
									sqllist.add(sql);
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	private void initOutput(String output) {
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

	private void removeTag(List<FieldParameter> fpList) {
		for (int i = 0; i < fpList.size(); i++) {
			FieldParameter fp = fpList.get(i);
			if (fp.getIndex() == 1) {
				fpList.remove(i);
				break;
			}
		}
	}

	private void loadConfig(String filename) {
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
		if (line.trim().length() == 0) {
			return;
		}
		String[] parts = Tools.split(line, ":");
		if (parts.length < 2) {
			System.out.println("无效的行:" + line);
			return;
		}
		String catalog = parts[0].trim();
		String[] items = Tools.split(parts[1], ";");
		List<List<Tag>> plist = new ArrayList<List<Tag>>();
		for (int i = 0; i < items.length; i++) {
			if (items[i].trim().length() == 0) {
				continue;
			}
			String[] pairs = Tools.split(items[i], "=");
			List<Tag> slist = new ArrayList<Tag>();
			for (int j = 0; j < pairs.length; j++) {
				String tag = pairs[j].trim();
				Tag tagBean = TagAdapter.getInstance().getTag(catalog, tag);
				if (tagBean == null) {
					System.out.println("无效的Tag:" + catalog + ">" + tag + ";行:" + line);
					System.exit(0);
				}
				slist.add(tagBean);
			}
			plist.add(slist);
		}
		configMap.put(catalog, plist);
	}
}
