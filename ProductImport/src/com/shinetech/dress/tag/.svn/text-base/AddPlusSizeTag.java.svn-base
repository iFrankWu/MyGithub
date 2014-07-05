package com.shinetech.dress.tag;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.FileUtil;

public class AddPlusSizeTag {

	public static void main(String[] args) {
		AddPlusSizeTag tag = new AddPlusSizeTag();
		tag.execute();
	}

	public void execute() {
		Tag tag = TagAdapter.getInstance().getTag("Plus Size");
		clearPlusSizeTag(tag);
		addPlusSizeTag(tag);
	}

	private void clearPlusSizeTag(Tag tag) {
		String sql = "delete from dr_product_tag where tag_id = " + tag.getTagId();
		IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		try {
			db.execute(sql);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addPlusSizeTag(Tag tag) {
		try {
			List<String> sqlList = new ArrayList<String>();
			List<String> list = FileUtil.getFileContentEx(System.getProperty("user.dir") + "/plussize.txt");
			for (String id : list) {
				String sql = "insert into dr_product_tag(product_id, tag_id) values(" + id + ", " + tag.getTagId() + ")";
				System.out.println(sql);
				sqlList.add(sql);
			}
			IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
			db.executeBatch(sqlList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
