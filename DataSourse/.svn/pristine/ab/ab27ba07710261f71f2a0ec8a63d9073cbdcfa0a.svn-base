/*
 * 文件名： Dialect.java	 <br>
 * 创建日期: 2011-3-3<br>
 * 包名: com.shinetech.sql.dialect <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.sql.dialect;

import java.util.HashMap;
import java.util.Map;

import com.shinetech.sql.FieldTypes;
import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.type.ITypeReader;
import com.shinetech.sql.type.ITypeWriter;

/**
 * 功能描述: 特定方言的关系型数据库实现 <br>
 * 作者: <a href="mailto:wdps.cn@gmail.com">loong</a> <br>
 * 版本: 1.0.0<br>
 */

public abstract class Dialect {

	/**
	 * primitiveMap: 基本数据类型映射集合
	 */
	private Map<Class<?>, String> primitiveMap = new HashMap<Class<?>, String>();
	/**
	 * typeNamesMap: 字段数据类型映射集合
	 */
	private Map<Integer, String> typeNamesMap = new HashMap<Integer, String>();
	/**
	 * defaultTypeReaderMap: 默认自定义字段类型处理类集合
	 */
	private Map<Integer, ITypeReader<?>> defaultTypeReaderMap = new HashMap<Integer, ITypeReader<?>>();
	/**
	 * defaultTypeWriterMap: 默认自定义字段类型处理类集合
	 */
	private Map<Integer, ITypeWriter<?>> defaultTypeWriterMap = new HashMap<Integer, ITypeWriter<?>>();

	/**
	 * 目的和功能：构造方法,构造Dialect类的一个实例 注意事项：
	 */
	public Dialect() {
		registerColumnType(FieldTypes.BIT, "Boolean");
		registerColumnType(FieldTypes.REAL, "Object");
		registerColumnType(FieldTypes.TINYINT, "Byte");
		registerColumnType(FieldTypes.SMALLINT, "Short");
		registerColumnType(FieldTypes.INTEGER, "Int");
		registerColumnType(FieldTypes.BIGINT, "Long");
		registerColumnType(FieldTypes.FLOAT, "Float");
		registerColumnType(FieldTypes.DOUBLE, "Double");
		registerColumnType(FieldTypes.NUMERIC, "Long");
		registerColumnType(FieldTypes.DECIMAL, "BigDecimal");
		registerColumnType(FieldTypes.CHAR, "String");
		registerColumnType(FieldTypes.VARCHAR, "String");
		registerColumnType(FieldTypes.LONGVARCHAR, "AsciiStream");
		registerColumnType(FieldTypes.DATE, "Date");
		registerColumnType(FieldTypes.TIME, "Time");
		registerColumnType(FieldTypes.TIMESTAMP, "Timestamp");
		registerColumnType(FieldTypes.BINARY, "BinaryStream");
		registerColumnType(FieldTypes.VARBINARY, "Bytes");
		registerColumnType(FieldTypes.LONGVARBINARY, "BinaryStream");
		registerColumnType(FieldTypes.NULL, "Object");
		registerColumnType(FieldTypes.OTHER, "Object");
		registerColumnType(FieldTypes.JAVA_OBJECT, "Object");
		registerColumnType(FieldTypes.DISTINCT, "Object");
		registerColumnType(FieldTypes.STRUCT, "Object");
		registerColumnType(FieldTypes.ARRAY, "Array");
		registerColumnType(FieldTypes.BLOB, "Blob");
		registerColumnType(FieldTypes.CLOB, "Clob");
		registerColumnType(FieldTypes.REF, "Ref");
		registerColumnType(FieldTypes.DATALINK, "Object");
		registerColumnType(FieldTypes.BOOLEAN, "Boolean");
		registerColumnType(FieldTypes.ROWID, "RowId");
		registerColumnType(FieldTypes.NCHAR, "NString");
		registerColumnType(FieldTypes.NVARCHAR, "NString");
		registerColumnType(FieldTypes.LONGNVARCHAR, "NString");
		registerColumnType(FieldTypes.NCLOB, "NClob");
		registerColumnType(FieldTypes.SQLXML, "SQLXML");

		registerPrimitiveType(Boolean.class, "Boolean");
		registerPrimitiveType(Byte.class, "Byte");
		registerPrimitiveType(Character.class, "CharacterStream");
		registerPrimitiveType(Short.class, "Short");
		registerPrimitiveType(Integer.class, "Int");
		registerPrimitiveType(Long.class, "Long");
		registerPrimitiveType(Double.class, "Double");
		registerPrimitiveType(Float.class, "Float");
		registerPrimitiveType(String.class, "String");
	}

	/**
	 * 功能：注册字段数据类型映射 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldType
	 * @param name
	 *            void
	 */
	protected void registerColumnType(int fieldType, String name) {
		typeNamesMap.put(fieldType, name);
	}

	/**
	 * 功能：注册基本数据类型映射 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param clazz
	 * @param name
	 *            void
	 */
	protected void registerPrimitiveType(Class<?> clazz, String name) {
		primitiveMap.put(clazz, name);
	}

	/**
	 * 功能：注册字段数据类型自定义默认写入器处理类 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldType
	 * @param typeReader
	 *            void
	 */
	protected void registerDefaultTypeReader(int fieldType, ITypeReader<?> typeReader) {
		defaultTypeReaderMap.put(fieldType, typeReader);
	}

	/**
	 * 功能：注册字段数据类型自定义默认读取器处理类 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldType
	 * @param typeWriter
	 *            void
	 */
	protected void registerDefaultTypeWriter(int fieldType, ITypeWriter<?> typeWriter) {
		defaultTypeWriterMap.put(fieldType, typeWriter);
	}

	/**
	 * 功能：检查是否存在指定字段类型默认读取器 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldType
	 * @return boolean
	 */
	public boolean containDefaultTypeReader(int fieldType) {
		return defaultTypeReaderMap.containsKey(fieldType);
	}

	/**
	 * 功能：检查是否存在指定字段类型默认写入器 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldType
	 * @return boolean
	 */
	public boolean containDefaultTypeWriter(int fieldType) {
		return defaultTypeWriterMap.containsKey(fieldType);
	}

	/**
	 * 功能：返回指定字段类型默认读取器 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldType
	 * @return
	 * @throws DBException
	 *             ITypeReader<?>
	 */
	public ITypeReader<?> getDefaultTypeReader(int fieldType) throws DBException {
		if (defaultTypeReaderMap.containsKey(fieldType)) {
			return defaultTypeReaderMap.get(fieldType);
		}
		throw new DBException("数据类型[" + fieldType + "]默认自定义字段类型读取器 不存在");
	}

	/**
	 * 功能：返回指定字段类型默认写入器 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldType
	 * @return
	 * @throws DBException
	 *             ITypeWriter<?>
	 */
	public ITypeWriter<?> getDefaultTypeWriter(int fieldType) throws DBException {
		if (defaultTypeWriterMap.containsKey(fieldType)) {
			try {
				return defaultTypeWriterMap.get(fieldType).getClass().newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new DBException("返回数据类型[" + fieldType + "]默认自定义字段类型写入器对象失败", e);
			}
		}
		throw new DBException("数据类型[" + fieldType + "]默认自定义字段类型写入器 不存在");
	}

	/**
	 * 功能：返回基本字段数据类型对应字段类型 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param clazz
	 * @return
	 * @throws DBException
	 *             String
	 */
	public String getReflectType(Class<?> clazz) throws DBException {
		if (primitiveMap.containsKey(clazz)) {
			return primitiveMap.get(clazz);
		}
		throw new DBException("基本数据类型[" + clazz.getName() + "]对应字段类型 不存在");
	}

	/**
	 * 功能：返回指定字段数据类型对应字段类型 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param fieldType
	 * @return String
	 * @throws DBException
	 */
	public String getReflectType(int fieldType) throws DBException {
		if (typeNamesMap.containsKey(fieldType)) {
			return typeNamesMap.get(fieldType);
		}
		throw new DBException("数据类型[" + fieldType + "]对应字段类型 不存在");
	}

	/**
	 * 功能：返回限制条件的SQL语句 <br>
	 * 注意事项：<font color="red">此方法不支持执行更新</font> <br>
	 * 
	 * @param sql
	 *            String 限定待查询的SQL语句
	 * @param hasOffset
	 *            boolean 是否指定记录Offset
	 * @return String
	 */
	public abstract String getLimitString(String sql);

	/**
	 * 功能：查询限定语句是否支持 Offset<br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @return boolean
	 */
	public abstract boolean supportsLimitOffset();

	/**
	 * 功能：是否支持查询限定语句 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @return boolean
	 */
	public abstract boolean supportsLimit();
}
