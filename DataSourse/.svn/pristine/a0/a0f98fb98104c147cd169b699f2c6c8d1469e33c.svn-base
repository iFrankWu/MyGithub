package com.shinetech.sql;


/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：DatabaseAccessBuilder.java<br>
 * 包名：com.shinetech.sql<br>
 * 作者 : zxs<br>
 * 创建日期 : 2011/2/23<br>
 * 版本：1.0.0 <br>
 * 功能描述：封装预编译过程中使用的参数信息的bean
 * 
 * 作者：loong<br>
 * 修改日期：2011/3/8<br>
 * 版本：1.0.1<br>
 * 修改原因：返回参数信息对象详细信息
 */
public class FieldParameter {

	/**
	 * 预编译sql中占位符?的位置
	 */
	private int index;
	/**
	 * 绑定的值
	 */
	private Object value;
	/**
	 * 绑定参数所对应的sql类型
	 */
	private int type;

	/**
	 * 初始化，绑定值
	 * 
	 * @param index
	 *            预编译sql中占位符?的位置
	 * @param value
	 *            绑定的值
	 * @param type
	 *            绑定参数所对应的sql类型
	 */
	public FieldParameter(int index, Object value, int type) {
		this.index = index;
		this.value = value;
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public Object getValue() {
		return value;
	}

	public int getType() {
		return type;
	}
}
