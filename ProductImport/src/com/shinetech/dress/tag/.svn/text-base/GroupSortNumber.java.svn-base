package com.shinetech.dress.tag;

import java.sql.ResultSet;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;

public class GroupSortNumber {
	public static void main(String[] args) {
		GroupSortNumber gsn = new GroupSortNumber();
		gsn.execute();
	}

	private long lastId = 0;
	private long sort = 1;

	public void execute() {
		String sql = "SELECT a.original_product_id,  a.id from dr_picture a, dr_original_product b where b.product_status = '0' and b.tag_status = '0' and b.id = a.original_product_id order by 1, 2, a.path";
		IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		try {
			db.handleList(sql, new ResultSetHandler() {
				@Override
				public void handle(ResultSet rs) {
					// TODO Auto-generated method stub
					try {
						long pid = rs.getLong("original_product_id");
						long id = rs.getLong("id");
						if (pid == lastId) {
							sort++;
						} else {
							lastId = pid;
							sort = 1;
						}
						String usql = "update dr_picture set tag_status = " + sort + " where id = " + id;
						IDatabaseAccess db2 = (new DatabaseAccessBuilder()).getDatabaseAccess();
						db2.execute(usql);
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
