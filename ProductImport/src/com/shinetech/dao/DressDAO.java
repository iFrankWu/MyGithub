/*
 * DressDAO.java	 <br>
 * 2011-10-10<br>
 * com.shinetech.dao <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.dao;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DressDAO {
	public DressDAO(){
//		db.setDatabase("test"); //mysql://192.168.2.231:3306/product_dress
		//在本地操作用是指原来的231
//			db.setDatabase("default"); //mysql://192.168.2.231:3306/product_dress
	
		db.setDatabase("dress2");
	}
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	
	/**
	 * 功能：得到产品信息，并且对产品信息进行处理 <br>
	 * 注意事项：<font color="red">2011/12/9 增加过滤条件color_exception != 1</font> <br>
	 * @param handle
	 * @throws DBException
	 * void
	 */
	public void getProductInfo(ResultSetHandler handle) throws DBException{
		String sql = "SELECT * FROM `dr_original_product` where product_status = 1 and tag_status = 1 and color_exception != 1";
//		String sql = "SELECT * FROM `dr_original_product` where product_status = 1 and tag_status = 1";
		db.handleList(sql, handle);
	}
	@SuppressWarnings("unchecked")
	public int getValidProductCount() throws DBException{
		String sql = "SELECT count(*) FROM `dr_original_product` where product_status = 1 and tag_status = 1 and color_exception != 1";
		return (Integer)db.queryFirst(Integer.class, sql);
	}
	public void getProductInfo(int resultCount,ResultSetHandler handle) throws DBException{
		String sql = "SELECT * FROM `dr_original_product` where product_status = 1 and tag_status = 1 limit 50,"+resultCount;
		db.handleList(sql, handle);
	}
	public void getPetticoatInfo(ResultSetHandler handle) throws DBException{
		String sql = "SELECT * FROM `dr_original_product` where id in (14982,14983,14984,14985,14986) order by id";
		db.handleList(sql, handle);
	}
	public void getSpecialInfo(ResultSetHandler handle) throws DBException{
		String sql = "SELECT * FROM `dr_original_product` where product_status = 6 and tag_status = 6 order by id";
		db.handleList(sql, handle);
	}
	@SuppressWarnings("unchecked")
	public void getProductById(Long productId,ResultSetHandler handle) throws DBException{
		String sql = "SELECT * FROM `dr_original_product` where id = ?"; 
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList,handle); 
	}
	/**
	 * 功能： <br>
	 * 注意事项：<font color="red">此时的图片已经按照tag_status升序排序</font> <br>
	 * @param productId
	 * @param handle
	 * @throws DBException
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void getImageByProId(Long productId, ResultSetHandler handle) throws DBException{
		//String sql = "SELECT path,tag_status FROM `dr_picture` where original_product_id = ? and fix_status = 1 order by tag_status";
		//测试时，图片fix状态暂不考虑
		String sql = "SELECT path,tag_status,id FROM `dr_picture` where original_product_id = ? and fix_status in (1) order by tag_status asc";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList,handle);
	}
	@SuppressWarnings("unchecked")
	public void getTagInfoByPId(Long productId,ResultSetHandler handle) throws DBException{
		String sql = "SELECT t2.show_name as tag_name ,t2.match_name as tag_match_name , t3.show_name as catalog_name ,t3.match_name as catalog_match_name,t2.id as tag_id FROM `dr_product_tag` t1 ,dr_tag t2,dr_tag_catalogs t3 where t1.product_id = ? and t2.id = t1.tag_id and t3.id = t2.catalog_id";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList,handle);
	}
	/**
	 * 功能：更新数据库原始产品表 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param productId
	 * @param _9dName
	 * @param _9dItemcode
	 * @param _9dMarketPrice
	 * @param _9dRealPrice
	 * @param discount
	 * @throws Exception
	 * void
	 */
	@SuppressWarnings("unchecked")
	public void update9Dresses_old(Long productId,String _9dName,String _9dItemcode,float _9dMarketPrice ,float _9dRealPrice ,float discount,String weight,int sortScore,int costPrice,
			String mg_description,String mg_short_description,
			String mg_meta_title,String mg_meta_keywords,String mg_meta_description)throws Exception{
		String sql = "update dr_original_product set 9dresses_producut_name = ? ," +
				"9dresses_market_price = ? , 9dresses_real_price = ? ,discount = ?,9dress_itemcode = ?,weight = ?,sort_score = ? ,cost_price = ?" +
				",mg_description = ?,mg_short_description = ?,mg_meta_title = ?,mg_meta_keywords = ?,mg_meta_description = ? where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,_9dName,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,_9dMarketPrice,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,_9dRealPrice,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4,discount,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(5,_9dItemcode,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6, weight, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(7, sortScore, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(8, costPrice, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(9, mg_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(10, mg_short_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(11, mg_meta_title, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(12, mg_meta_keywords, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(13, mg_meta_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(14, productId, FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void update9Dresses(Long productId,String _9dName,String _9dItemcode,float _9dMarketPrice ,float _9dRealPrice ,float discount,String weight,int sortScore,
			String mg_description,String mg_short_description,
			String mg_meta_title,String mg_meta_keywords,String mg_meta_description)throws Exception{
		String sql = "update dr_original_product set 9dresses_producut_name = ? ," +
				"9dresses_market_price = ? , 9dresses_real_price = ? ,discount = ?,9dress_itemcode = ?,weight = ?,sort_score = ?" +
				",mg_description = ?,mg_short_description = ?,mg_meta_title = ?,mg_meta_keywords = ?,mg_meta_description = ? where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,_9dName,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,_9dMarketPrice,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,_9dRealPrice,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4,discount,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(5,_9dItemcode,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6, weight, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(7, sortScore, FieldTypes.NUMERIC));
//		fpList.add(new FieldParameter(8, costPrice, FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(8, mg_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(9, mg_short_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(10, mg_meta_title, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(11, mg_meta_keywords, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(12, mg_meta_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(13, productId, FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	

	@SuppressWarnings("unchecked")
	public void deleteProTag(Long pId,int tagId) throws DBException{
		String sql = "delete from dr_product_tag where tag_id = ? and product_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,pId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,tagId,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void getCatalog(String catalogShowName,ResultSetHandler handle) throws DBException{
		//'Petticoat' 
		String sql = "SELECT dr_tag.* FROM dr_tag_catalogs,dr_tag where dr_tag_catalogs.show_name = ? and dr_tag.catalog_id = dr_tag_catalogs.id";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,catalogShowName,FieldTypes.VARCHAR));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public void getTag(String catalogShowName,String tagShowName,ResultSetHandler handle) throws DBException{
		//'Petticoat' 
		String sql = "SELECT dr_tag.* FROM dr_tag_catalogs,dr_tag where dr_tag_catalogs.show_name = ? and dr_tag.catalog_id = dr_tag_catalogs.id and and dr_tag.show_name = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,catalogShowName,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,catalogShowName,FieldTypes.VARCHAR));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public void getTag(String tagShowName,ResultSetHandler handle) throws DBException{
		String sql = "SELECT dr_tag.* FROM dr_tag where   dr_tag.show_name = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,tagShowName,FieldTypes.VARCHAR));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public void svaeProductTag(Long productId,int tagId) throws DBException{
		String sql = "insert into dr_product_tag(product_id,tag_id) values(?,?)";
		System.out.println(productId+":"+tagId);
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,tagId,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void svaeProductTag(Long productId,String tagName) throws DBException{
		String sql = "insert into dr_product_tag(product_id,tag_id) values(?,(select id from dr_tag where show_name = ?))";
		System.out.println(productId+":"+tagName);
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(2,tagName,FieldTypes.VARCHAR));
		db.execute(sql, fpList);
	}
	public void getPetticoatTag(List<String> tags,ResultSetHandler handle) throws DBException{
		StringBuilder sql = new StringBuilder("SELECT dr_tag.id as tag_id,dr_tag_catalogs.id as catalig_id ,dr_tag.show_name as tag_name,dr_tag_catalogs.show_name as catalog_name FROM dr_tag,dr_tag_catalogs where dr_tag.id != 195 and dr_tag.catalog_id =dr_tag_catalogs.id and  dr_tag.show_name in (");
		for(String tag : tags){
			sql.append("'");
			sql.append(tag);
			sql.append("'");
			sql.append(",");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		System.out.println(sql);
		db.handleList(sql.toString(), handle);
	}
	@SuppressWarnings("unchecked")
	public void updateProductStatus(Long productId) throws DBException{
		String sql = "update dr_original_product set product_status = '0' ,tag_status = '0' where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.NUMERIC));
//		System.out.println(sql.replace("?", productId+""));
		db.execute(sql, fpList);
	}
	@SuppressWarnings("unchecked")
	public void updatePriceFactor(float pricefactor,Long productId) throws DBException{
		String sql = "update dr_original_product set price_factor = ? where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,pricefactor,FieldTypes.FLOAT));
		fpList.add(new FieldParameter(2,productId,FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	public void getPic(ResultSetHandler handle){
		String sql = "select t1.* from dr_picture t1 ,dr_original_product t2 where t2.product_status = 1 and t2.tag_status = 1 and t2.color_exception != 1 and t2.id = t1.	original_product_id and t1.fix_status = 1";
		try {
			db.handleList(sql, handle);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public String getImgPathByimgId(int imageId) throws DBException{
		String sql = "select path from dr_picture where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,imageId,FieldTypes.INTEGER));
		return (String)db.queryPrepareFirst(String.class, sql, fpList);
	}
	public void getSpecialPic(ResultSetHandler handle) throws DBException{
		String sql = "select * from dr_picture where original_product_id in(670,711,897,913,916,942,1409,1677,1768,2132,11820,12412,12713,12804,12866,13413,13636,13653,13898,14056)";
		db.handleList(sql, handle);
	}
	public void getNeedUpdateProdctIds(ResultSetHandler handle) throws DBException{
		String sql = "select distinct product_id from dr_or_log where update_status = 0 order by 1";
		db.handleList(sql, handle);
	}
	@SuppressWarnings("unchecked")
	public void getProductEditTypeByPid(long productId,ResultSetHandler handle) throws DBException{
		String sql = "select * from dr_or_log where product_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.NUMERIC));
		db.handleList(sql, fpList, handle);
	}
	@SuppressWarnings("unchecked")
	public void updateDrLogUpdateStatus(int productId) throws DBException{
		String sql = "update dr_or_log set update_status = 1 where product_id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,productId,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
	public void getAllImage(ResultSetHandler handle) throws DBException{
		String sql = "select * from dr_picture";
		db.handleList(sql, handle);
	}
	@SuppressWarnings("unchecked")
	public void updatePicPath(int id,String newpath) throws DBException{
		String sql = "update dr_picture set path = ? where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,newpath,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,id,FieldTypes.INTEGER));
		db.execute(sql, fpList);
	}
}	
