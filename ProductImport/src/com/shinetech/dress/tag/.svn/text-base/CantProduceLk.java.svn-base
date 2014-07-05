package com.shinetech.dress.tag;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.util.FileUtil;

public class CantProduceLk {
	public static void main(String[] args) {
		CantProduceLk cpl = new CantProduceLk();
		cpl.execute();
	}

	private List<String> idList = new ArrayList<String>();
	private PrintWriter printer = null;
	private String filename = FileUtil.PathAppendEx(System.getProperty("user.dir"), "step0.cant.txt");
	private String output = FileUtil.PathAppendEx(System.getProperty("user.dir"), "step0.sql.txt");

	public void execute() {
		loadConfig();
		initOutput();
		executeConfig();
		closeOutput();
	}

	public void executeConfig() {
		System.out.println("总计处理:" + idList.size() + "条记录！");
		for (String id : idList) {
			output("update dr_original_product set product_status = '3', user_id = 1 where id = " + id + ";");
		}
	}

	private void handleLine(String line) {
		if (line == null || line.trim().length() == 0) {
			return;
		}
		int pos = line.indexOf(".");
		if (pos < 1) {
			System.out.println("无效的行:" + line);
			System.exit(0);
		}
		String id = line.substring(0, pos);
		System.out.println(id);
		idList.add(id);
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

	public void loadConfig() {
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
}
