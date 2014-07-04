/*
 * 文件名： ParameterCreatorUtil.java	 <br>
 * 创建日期: 2011-3-8<br>
 * 包名: com.shinetech.sql <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql;

import java.lang.reflect.Field;

/**
 * 功能描述: 返回 FieldParameter对象详细信息<br>
 * 作者: <a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 */

public class ParameterCreatorUtil {

	/**
	 * fieldParameter: 封装预编译过程中使用的参数信息的bean
	 */
	private FieldParameter fieldParameter;

	/**
	 * 目的和功能：构造方法,构造ParameterCreatorUtil类的一个实例 注意事项：
	 * 
	 * @param fieldParameter
	 */
	public ParameterCreatorUtil(FieldParameter fieldParameter) {
		this.fieldParameter = fieldParameter;
	}

	/**
	 * 功能：提供类toString的静态方法 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldParameter
	 * @return String
	 */
	public static String toString(FieldParameter fieldParameter) {
		return new ParameterCreatorUtil(fieldParameter).toString();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("设置  SQL statement 参数：参数索引 [").append(fieldParameter.getIndex());
		buffer.append("], 参数值 [").append(getParameterValue());
		buffer.append("], 参数类型 [").append(getParameterType());
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 功能：返回参数值 <br>
	 * 注意事项：<font color="red">未处理参数类型转换</font> <br>
	 * 
	 * @return Object
	 */
	private Object getParameterValue() {
		return fieldParameter.getValue();
	}

	/**
	 * 功能：返回参数类型 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @return String
	 */
	private String getParameterType() {
		String pType = "unknown[" + fieldParameter.getType() + "]";
		Field[] fieldss = FieldTypes.class.getFields();
		for (Field field : fieldss) {
			String fieldName = field.getName();
			try {
				if (FieldTypes.class.getField(fieldName).getInt(FieldTypes.class) == fieldParameter.getType()) {
					pType = fieldName;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pType;
	}
}
