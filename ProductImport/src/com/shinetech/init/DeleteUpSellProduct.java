package com.shinetech.init;

import com.shinetech.action.ConstConfig;
import com.shinetech.dao.MagentoProductDAO;
import com.shinetech.sql.exception.DBException;

public class DeleteUpSellProduct {
	public static void main(String[] args) {
		MagentoProductDAO dao = new MagentoProductDAO();
		//此处不需要删除位置信息关系，因为表是删除时候会casecate级联删除
		try {
			dao.deleteProductLink(ConstConfig.product_link_type_code[2]);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
