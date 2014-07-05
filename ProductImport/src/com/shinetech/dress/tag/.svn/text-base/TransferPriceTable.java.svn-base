package com.shinetech.dress.tag;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.FileUtil;

public class TransferPriceTable {
	private int maxWeightPerDresses = 3000;
	private int maxCountPerOrder = 10;
	private PrintWriter printer = null;

	private String output = FileUtil.PathAppendEx(System.getProperty("user.dir"), "express-price-table-output.txt");
	private String filename = FileUtil.PathAppendEx(System.getProperty("user.dir"), "express-price-table.txt");

	public static void main(String[] args) {
		TransferPriceTable tpt = new TransferPriceTable();
		tpt.execute();
	}

	private static String tags[] = "大摆衬裙,大摆束腰衬裙,小摆衬裙,小摆束腰衬裙,拖尾衬裙".split(",");

	public void execute() {
		StringBuilder sql = new StringBuilder(
				"SELECT dr_tag.id as tag_id,dr_tag_catalogs.id as catalig_id ,dr_tag.show_name as tag_name,dr_tag_catalogs.show_name as catalog_name FROM dr_tag,dr_tag_catalogs where dr_tag.id != 195 and dr_tag.catalog_id =dr_tag_catalogs.id and  dr_tag.show_name in (");
		for (String tag : tags) {
			sql.append("'");
			sql.append(tag);
			sql.append("'");
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");

		System.out.println(sql.toString());

		IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		try {
			db.handleList(sql.toString(), new ResultSetHandler() {
				@Override
				public void handle(ResultSet rs) {
					// TODO Auto-generated method stub
					try {
						System.out.println(rs.getString(1));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void executeW() {
		initOutput();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
			String line;
			int idx = 0;
			while ((line = reader.readLine()) != null) {
				idx++;
				if (idx == 1) {
					continue;
				}
				handleLine(line);
			}
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleLine(String line) {
		String[] items = line.split("\t");
		if (items.length < 6) {
			return;
		}
		StringBuffer bufs = new StringBuffer();
		// country abbr
		bufs.append(items[2]).append(",");
		// region
		bufs.append("*").append(",");
		// Postal
		bufs.append("*").append(",");

		// 行末
		StringBuffer bufe = new StringBuffer();
		bufe.append(items[1]).append(",").append(items[3]);

		int sp = Integer.parseInt(items[4]);
		int dt = Integer.parseInt(items[5]);

		// weight
		for (int i = 0; i <= maxWeightPerDresses * maxCountPerOrder; i = i + 500) {
			StringBuffer bufb = new StringBuffer();
			String weight = String.format("%.1f", (float) i / 1000.0);
			String price = String.format("%.2f", (float) sp / 6.0);
			bufb.append(weight).append(",").append(price).append(",");
			sp = sp + dt;
			output(bufs.toString() + bufb.toString() + bufe.toString());
		}
		//
	}

	private void initOutput() {
		try {
			printer = new PrintWriter(new FileWriter(output, false), true);
			output("Country,Region/State,\"Zip/Postal Code\",\"Weight (and above)\",\"Shipping Price\", Country-Full-Name, Express");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void output(String buf) {
		printer.println(buf);
	}

	private void closeOutput() {
		printer.flush();
		printer.close();
	}
}

class PriceUnit {
	private String country;
	private String countryCode;
	private String expressVender;
	private int firstPrice;
	private int additionalPrice;
}
