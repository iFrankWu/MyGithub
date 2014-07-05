package com.shinetech.dress.tag;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.shinetech.dao.DressDAO;
import com.shinetech.handle.TagCatatogHandle;
import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;

public class ClearDuplicateTag {
	public static void main(String[] args) {
		Const.initLogger();
		ClearDuplicateTag cdt = new ClearDuplicateTag();
		cdt.clearReset();
//		cdt.handleSpecial();
//		cdt.handleStrapless();
//		cdt.clearDuplicate();
		
	}

	public void clearReset() {
		List<String> catalogList = new ArrayList<String>();
		catalogList.add("Venues");
		catalogList.add("Body Shape");
		catalogList.add("Season");
		List<Tag> tagList = new ArrayList<Tag>();
		tagList.add(TagAdapter.getInstance().getTag("Style", "Modest"));

		List<Tag> exceptList = new ArrayList<Tag>();
		exceptList.add(TagAdapter.getInstance().getTag("Body Shape", "Plus Size"));

		for (String catalog : catalogList) {
			HashMap<String, List<Tag>> map = TagAdapter.getInstance().getTagMap(catalog);
			Iterator<List<Tag>> iter = map.values().iterator();
			while (iter.hasNext()) {
				List<Tag> ptagList = iter.next();
				for (Tag tag : ptagList) {
					System.out.println("catalog:" + catalog + "; tag:" + tag.getName());
					if (checkExcept(tag, exceptList)) {
						System.out.println("find exception");
					} else {
						System.out.println("delete...");
						tagList.add(tag);
					}
				}
			}
		}
		 
		for (Tag tag : tagList) {
			 clearTag(tag);
		}
	}

	private boolean checkExcept(Tag stag, List<Tag> exceptList) {
		for (Tag dtag : exceptList) {
			if (stag.getTagId() == dtag.getTagId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能：Style大类中新增Modest子类，将Sleeve Length不为Sleeveless的产品中Neckline不为One Shoulder的产品标记为Modest <br>
	 * 注意事项：<font color="red">测试状态，插入语句被注释掉了</font> <br>
	 * void
	 */
	public void handleSpecial() {
		List<Tag> tagList = new ArrayList<Tag>();
		tagList.add(TagAdapter.getInstance().getTag("Sleeve Length", "Sleeveless"));
		tagList.add(TagAdapter.getInstance().getTag("Neckline", "One Shoulder"));

		final Tag destTag = TagAdapter.getInstance().getTag("Style", "Modest");
		StringBuffer sqlmain = new StringBuffer();
		StringBuffer sqlwhere = new StringBuffer();
		sqlmain.append("select distinct id from dr_original_product a, dr_product_tag b");
		sqlwhere.append(" where a.product_status = '1' and a.tag_status = '1' and a.id = b.product_id and b.tag_id not in (-1 ");
		for (Tag t : tagList) {
			sqlwhere.append(",").append(t.getTagId());
		}
		sqlwhere.append(")");
		System.out.println(sqlmain.toString() + sqlwhere.toString());
		final IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		try {
			db.handleList(sqlmain.toString() + sqlwhere.toString(), new ResultSetHandler() {
				@Override
				public void handle(ResultSet rs) {
					try {
						Long productId = rs.getLong("id");
						String sql = "insert into dr_product_tag(product_id,tag_id) value(?,?)";
						List<FieldParameter> fpList = new ArrayList<FieldParameter>();
						fpList.add(new FieldParameter(1, productId, FieldTypes.NUMERIC));
						fpList.add(new FieldParameter(2,destTag.getTagId(), FieldTypes.NUMERIC));
						System.out.println("insert dr_product_tag:"+productId+":"+destTag.getTagId());
						db.execute(sql, fpList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	/**
	 * TAG_SWEETHEART:甜心标签
	 */ 
	private static String TAG_SWEETHEART = "Sweetheart";
	/**
	 * TAG_STRAPLESS:抹胸标签
	 */ 
	private static String TAG_STRAPLESS = "Strapless";
	/**
	 * 功能：Neckline新增单向匹配，即在Neckline大类中，仅选中了Sweetheart，则视为自动匹配标记上Strapless <br>
	 * 注意事项：<font color="red">测试状态，插入语句被注释掉了</font> <br>
	 * void
	 */
	public void handleStrapless(){
		final DressDAO dao = new DressDAO();
		try{
			final IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
			dao.getProductInfo(new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet res) {
					try{
						TagCatatogHandle handle = new TagCatatogHandle();
						long productId =res.getLong("id");
						dao.getTagInfoByPId(productId, handle);
						Map<String,ArrayList<String>> map = handle.getCatalogTagMap();
						List<String> nk = map.get("Neckline");
						if(nk == null || nk.isEmpty()){
							return;
						}
						if(nk.size() == 1 && nk.contains(TAG_SWEETHEART)){
							String sql = "insert into dr_product_tag(product_id,tag_id) value(?,(select id from dr_tag where show_name = ?))";
							List<FieldParameter> fpList = new ArrayList<FieldParameter>();
							fpList.add(new FieldParameter(1, productId, FieldTypes.NUMERIC));
							fpList.add(new FieldParameter(2,TAG_STRAPLESS, FieldTypes.VARCHAR));
							System.out.println("Handle sweetheart tag:"+productId+":"+TAG_STRAPLESS);
							db.execute(sql, fpList);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void clearTag(Tag tag) {
		String sql = "delete from dr_product_tag where tag_id = " + tag.getTagId();
		IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		try {
			db.execute(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clearDuplicate() {
		IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		try {
			db.execute("create table auto_tmp_product_tag as select distinct * from dr_product_tag");
			db.execute("truncate table dr_product_tag");
			db.execute("insert into dr_product_tag select * from auto_tmp_product_tag");
			db.execute("drop table auto_tmp_product_tag");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
