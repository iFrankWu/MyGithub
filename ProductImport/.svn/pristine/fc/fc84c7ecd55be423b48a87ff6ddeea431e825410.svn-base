/*
 * UpdateLocalData.java	 <br>
 * 2012-1-5<br>
 * com.shinetech.synchronize <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.synchronize;

import java.util.ArrayList;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.FieldParameter;
import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class UpdateLocalData {
	@SuppressWarnings("rawtypes")
	private IDatabaseAccess  db = (new DatabaseAccessBuilder()).getDatabaseAccess();
	private FileOperate op = new FileOperate();
	public static void main(String[] args) {
		Const.initLogger();
		UpdateLocalData update = new UpdateLocalData();
		update.update();
	}
	public void update(){
		db.setDatabase("dress2");
		op.getLine("data/product_info.tsv", new ILineHandle<String>() {

			@Override
			public String lineHandle(String line) {
				String temp[] = line.split("\t");
				int productId = Integer.parseInt(temp[0]);
				String _9dName = temp[1];
				String mg_meta_title = temp[2];
				String mg_meta_description = temp[3];
				String _9dItemcode = temp[4];
				float discount = Float.parseFloat(temp[5]);
				String mg_description = temp[6];
				String mg_short_description = temp[7];
				float _9dMarketPrice = Float.parseFloat(temp[8]);
				float _9dRealPrice = Float.parseFloat(temp[9]);
//				System.out.println(productId+"\t"+ _9dName+"\t"+ _9dItemcode+"\t"+ _9dMarketPrice+"\t"+ _9dRealPrice+"\t"+ discount+"\t"+ mg_description+"\t"+ mg_short_description+"\t"+ mg_meta_title+"\t"+ mg_meta_description);
				try {
					update9Dresses(productId, _9dName, _9dItemcode, _9dMarketPrice, _9dRealPrice, discount, mg_description, mg_short_description, mg_meta_title, mg_meta_description);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
				return null;
			}
		
		});
	}
	@SuppressWarnings("unchecked")
	private void update9Dresses(int productId,String _9dName,String _9dItemcode,float _9dMarketPrice ,float _9dRealPrice ,float discount,
			String mg_description,String mg_short_description,
			String mg_meta_title,String mg_meta_description)throws Exception{
		String sql = "update dr_original_product set 9dresses_producut_name = ? ,9dresses_market_price = ? , 9dresses_real_price = ? ,discount = ?,9dress_itemcode = ?,mg_description = ?,mg_short_description = ?,mg_meta_title = ?,mg_meta_description = ? where id = ?";
		List<FieldParameter> fpList = new ArrayList<FieldParameter>();
		fpList.add(new FieldParameter(1,_9dName,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(2,_9dMarketPrice,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(3,_9dRealPrice,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(4,discount,FieldTypes.NUMERIC));
		fpList.add(new FieldParameter(5,_9dItemcode,FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(6, mg_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(7, mg_short_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(8, mg_meta_title, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(9, mg_meta_description, FieldTypes.VARCHAR));
		fpList.add(new FieldParameter(10, productId, FieldTypes.NUMERIC));
		db.execute(sql, fpList);
	}
	
}
