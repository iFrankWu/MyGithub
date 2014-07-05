/*
 * DeleteAttributeSet.java	 <br>
 * 2011-11-25<br>
 * com.shinetech.init <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.init;

import com.shinetech.action.ConstConfig;
import com.shinetech.dao.AttributeSetDAO;
import com.shinetech.dao.CatagoryDAO;
import com.shinetech.util.Const;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class DeleteAttributeSet {
	public static void main(String[] args) {
		Const.initLogger();
		try{
			AttributeSetDAO attrSetDAO = new AttributeSetDAO();
			CatagoryDAO categoryDAO = new CatagoryDAO();
			int entityTypeId = categoryDAO.getEntityTypeId(ConstConfig.ENTITY_TYPE_CODES[3]);
			attrSetDAO.deleteAttrSet(entityTypeId, ConstConfig.ATTRIBUTE_SET_NAME);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
