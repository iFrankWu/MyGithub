package com.shinetech.dress.tag;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.Tools;

public class TagUpdateRule2 {

	private String output = "assets/rule/step2.sql.txt";
	private String filename = "assets/rule/step2.rule.txt";
	private List<TagConfig> configList = new ArrayList<TagConfig>();
	private PrintWriter printer = null;

	public static void main(String[] args) {
		Const.initLogger();
		TagUpdateRule2 tu = new TagUpdateRule2();
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

	private boolean blnO;

	private void executeConfig() {
		for (TagConfig tc : configList) {
			System.out.println(tc.getLine());
			String line = tc.getLine();
			blnO = false;

			if (line.equals("Court Train:Church;")) {
				System.out.println("find:" + line);
				blnO = true;
			}

			StringBuffer sqlmain = new StringBuffer();
			StringBuffer sqlwhere = new StringBuffer();
			sqlmain.append("select id from dr_original_product a ");
			sqlwhere.append(" where 1=1");

			List<Tag> oTagList = tc.getOTagList();
			for (int i = 0; i < oTagList.size(); i++) {
				sqlmain.append(" ,dr_product_tag b").append(i);
				long tagId = tc.getOTagList().get(i).getTagId();
				sqlwhere.append(" and a.id = b").append(i).append(".product_id and b").append(i).append(".tag_id = ").append(tagId);
			}

			List<Tag> dTagList = tc.getDTagList();
			for (final Tag tagBean : dTagList) {
				String sql2 = sqlmain.toString() + sqlwhere.toString();
				sql2 = sql2 + " and a.id not in (select product_id from dr_product_tag where tag_id = " + tagBean.getTagId() + ")";

				IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
				try {
					db.handleList(sql2, new ResultSetHandler() {
						@Override
						public void handle(ResultSet rs) {
							try {
								long productId = rs.getLong("id");
								String tsql = "insert into dr_product_tag(product_id, tag_id) value(" + productId + "," + tagBean.getTagId() + ")";
								output(tsql + ";");
								sqllist.add(tsql);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
				} catch (Exception ex) {
					ex.printStackTrace();
					System.exit(0);
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
		String[] parts = Tools.split(line.trim(), ":");
		if (parts.length < 2) {
			System.out.println("无效的行:" + line);
			System.exit(0);
		}

		if (line.equals("Court Train:Church;")) {
			System.out.println();
		}

		TagConfig tc = new TagConfig();
		tc.setLine(line);
		String[] oitems = Tools.split(parts[0].trim(), "&");
		for (int i = 0; i < oitems.length; i++) {
			String[] pairs = Tools.split(oitems[i], " ");
			if (pairs.length > 1 && "Waistline".equals(pairs[1])) {
				String tag = pairs[0].trim();
				String catalog = pairs[1];
				Tag tagBean = TagAdapter.getInstance().getTag(catalog, tag);
				if (tagBean == null) {
					System.out.println("无效的Tag:" + pairs[1] + ">" + pairs[0] + ";行:" + line);
					System.exit(0);
				}
				tc.addOTag(tagBean);
				tc.addOTagCatalog(catalog);
			} else {
				String tag = oitems[i].trim();
				Tag tagBean = TagAdapter.getInstance().getTag(tag);
				if (tagBean == null) {
					System.out.println("无效的Tag:" + pairs[0] + ";行:" + line);
					System.exit(0);
				}
				tc.addOTag(tagBean);
				tc.addOTagCatalog("");
			}
		}

		String[] ditems = Tools.split(parts[1], ";");
		for (int i = 0; i < ditems.length; i++) {
			if (ditems[i].trim().length() > 0) {
				String tag = ditems[i].trim();
				Tag tagBean = TagAdapter.getInstance().getTag(tag);
				if (tagBean == null) {
					System.out.println("无效的Tag:" + ditems[i].trim() + ";行:" + line);
					System.exit(0);
				}
				tc.addDTag(tagBean);
			}
		}
		configList.add(tc);
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
