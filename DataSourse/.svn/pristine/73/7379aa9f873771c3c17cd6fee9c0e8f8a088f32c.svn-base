package com.shinetech.sql.type;

import java.sql.ResultSet;


/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：LobReader.java<br>
 * 包名：com.shinetech.sql.lob<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/23<br>
 * 版本：1.0.0 <br>
 * 功能描述： 大字段读取器接口<br>
 * 读取返回的类型，该类型和查询返回javaBean中的该大字段的类型一致<br>
 * 例如：如果返回的Bean中大字段类型接收为String，则该T的类型也应该为String
 * 
 */
public interface ITypeReader<T> {
	/**
	 * 读取大字段
	 * 
	 * @param rs
	 *            sql查询返回的ResultSet
	 * @param fieldName
	 *            大字段的名称
	 * @return T sql返回bean中大字段对应的类型
	 */
	public T read(ResultSet rs, String fieldName) throws Exception;

}
