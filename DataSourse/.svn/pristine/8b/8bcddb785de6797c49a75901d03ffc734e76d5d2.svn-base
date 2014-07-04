package com.shinetech.sql;

import java.util.List;

import com.shinetech.sql.exception.DBException;
import com.shinetech.sql.type.ITypeReader;
import com.shinetech.sql.type.ITypeWriter;

/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：DatabaseAccess.java<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/22<br>
 * 版本：1.0.0 <br>
 * 功能描述：
 * <p>
 * 数据库访问接口<br>
 * 查询结果直接是javaBean，类似hibernate，不需要自己从ResultSet中读取数据封装成Bean<br>
 * <font color="red">注意： 该接口的实现类的一个实例只能返回一种类型，如果想改变T的值，只能重新生成一个实例并指明T的类型 </font>
 * </p>
 * 
 * @param <T>
 *            查询操作封装成的javaBean的类型 如：T为String
 *            则该实例中所有操作的T都是String，例如，queryFirst方法返回的就是一个String
 * 
 * 作者：loong<br>
 * 版本：1.0.1<br>
 * 修改日期：2011/3/8<br>
 * 修改原因：变量名称及描述；返回结果描述
 * 
 */
public interface IDatabaseAccess<T> {

	public T queryFirst(Class<T> clazz, String sql) throws DBException;

	/**
	 * 查询多条记录，返回查询记录封装成的javabean的集合<br>
	 * <font color="red"> 注意:如果查询中存在大字段，且大字段读取方式要按自定义读取器来读取，
	 * 则必须在执行该方法之前注册自定义读取器(
	 * 通过调用registerTypeReadHandle方法注册),否则大字段按默认方式读取（clob读成String，blob读成byte[])
	 * </font>
	 * 
	 * @param clazz
	 *            每条查询结果需要封装成的javabean的类型的class
	 * @param sql
	 *            查询的sql语句
	 * @return List<T> 每条查询记录封装成的javaBean的集合
	 * @throws DBException
	 *             查询异常或者封装成类型T的对象时异常
	 */
	public List<T> queryList(Class<T> clazz, String sql) throws DBException;

	public void handleList(String sql, ResultSetHandler handler) throws DBException;

	public void handleList(String sql, List<FieldParameter> paraList, ResultSetHandler handler) throws DBException;

	/**
	 * 预编译查询一条记录，返回查询结果封装成的javaBean<br>
	 * <font color="red"> 注意:如果查询中存在大字段，且大字段读取方式要按自定义读取器来读取，
	 * 则必须在执行该方法之前注册自定义读取器(
	 * 通过调用registerLobReadHandle方法注册),否则大字段按默认方式读取（clob读成String，blob读成byte[])
	 * </font>
	 * 
	 * @param clazz
	 *            返回类型的class
	 * @param sql
	 *            查询的sql语句
	 * @param paraList
	 *            sql中站位符号的对应参数集合
	 * @return T 查询的结果封装成的javaBean
	 * @throws DBException
	 *             查询异常或者封装成类型T的对象时异常
	 */
	public T queryPrepareFirst(Class<T> clazz, String sql, List<FieldParameter> paraList) throws DBException;

	/**
	 * 预编译查询多条记录，返回查询记录封装成的javabean的集合<br>
	 * <font color="red"> 注意:如果查询中存在大字段，且大字段读取方式要按自定义读取器来读取，
	 * 则必须在执行该方法之前注册自定义读取器(
	 * 通过调用registerLobReadHandle方法注册),否则大字段按默认方式读取（clob读成String，blob读成byte[])
	 * </font>
	 * 
	 * @param clazz
	 *            返回类型的class
	 * @param sql
	 *            查询的sql语句
	 * @param paraList
	 *            sql中站位符号的对应参数集合
	 * @return List<T> 每条查询记录封装成的javaBean的集合
	 * @throws DBException
	 *             查询异常或者封装成类型T的对象时异常
	 */
	public List<T> queryPrepareList(Class<T> clazz, String sql, List<FieldParameter> paraList) throws DBException;

	/**
	 * 分页查询 <font color="red"> 注意:如果查询中存在大字段，且大字段读取方式要按自定义读取器来读取，
	 * 则必须在执行该方法之前注册自定义读取器
	 * (通过调用registerLobReadHandle方法注册),否则大字段按默认方式读取（clob读成String，blob读成byte[])
	 * </font>
	 * 
	 * @param clazz
	 *            返回类型的class
	 * @param sql
	 *            查询的sql语句
	 * @param pageSize
	 *            查询每页的记录数
	 * @param currentPage
	 *            当前页数
	 * @return List<T> 每条查询记录封装成的javaBean的集合
	 * @throws DBException
	 *             查询异常或者封装成类型T的对象时异常
	 */
	public List<T> queryPage(Class<T> clazz, String sql, int pageSize, int currentPage) throws DBException;

	/**
	 * 预编译分页查询 <font color="red"> 注意:如果查询中存在大字段，且大字段读取方式要按自定义读取器来读取，
	 * 则必须在执行该方法之前注册自定义读取器
	 * (通过调用registerLobReadHandle方法注册),否则大字段按默认方式读取（clob读成String，blob读成byte[])
	 * </font>
	 * 
	 * @param clazz
	 *            返回类型的class
	 * @param sql
	 *            查询的sql语句
	 * @param paraList
	 *            sql中站位符号的对应参数集合
	 * @param pageSize
	 *            查询每页的记录数
	 * @param currentPage
	 *            当前页数
	 * @return List<T> 每条查询记录封装成的javaBean的集合
	 * @throws DBException
	 *             查询异常或者封装成类型T的对象时异常
	 */
	public List<T> queryPreparePage(Class<T> clazz, String sql, List<FieldParameter> paraList, int pageSize, int currentPage) throws DBException;

	/**
	 * 执行数据更新操作,该方法不能处理大字段<br>
	 * 
	 * @param sql
	 *            更新操作的sql语句，可以为insert,update,delete
	 * @return 执行结果<br>
	 *         <ul>
	 *         <li>更新所影响的行数(DML语句)</li>
	 *         <li>0(DDL语句)</li>
	 *         </ul>
	 * @throws DBException
	 *             查询异常或者封装成类型T的对象时异常
	 */
	public void execute(String sql) throws DBException;

	/**
	 * 预编译执行数据更新操作<br>
	 * <font color="red"> 注意：如果在insert或则update中存在大字段，且大字段处理要按自定义写入器方式写入，
	 * 则必须在执行此方法前注册自定义大字段写处理器
	 * ，否则按默认写入方式写入大字段（clob按String来构造sql.Clob对象，blob按byte[]
	 * 来构造sql.Blob对象，这两种情况class中的该字段必须为与这两种情况的类型一致，否则会出现异常） </font>
	 * 
	 * @param sql
	 *            更新操作的sql语句，可以为insert,update,delete
	 * @param paraList
	 *            sql中站位符号的对应参数集合
	 * @return 执行结果<br>
	 *         <ul>
	 *         <li>更新所影响的行数(DML语句)</li>
	 *         <li>0(DDL语句)</li>
	 *         </ul>
	 * @throws DBException
	 *             更新数据库异常或者封装成类型T的对象时异常
	 */
	public void execute(String sql, List<FieldParameter> paraList) throws DBException;

	/**
	 * 同一事务中执行多条数据更新操作,该方法不能处理大字段<br>
	 * 
	 * 
	 * @param sqlList
	 *            更新操作的sql语句的集合，可以为insert,update,delete
	 * @return 执行结果<br>
	 *         <ul>
	 *         <li>0(成功)</li>
	 *         <li><0(失败)</li>
	 *         </ul>
	 * @throws DBException
	 *             更新数据库异常或者封装成类型T的对象时异常
	 */
	public void executeBatch(List<String> sqlList) throws DBException;

	/**
	 * 同一事务中预编译执行相同sql的多次数据更新操作<br>
	 * <font color="red"> 注意：如果在insert或则update中存在大字段，且大字段处理要按自定义写入器方式写入，
	 * 则必须在执行此方法前注册自定义大字段写处理器，否则按默认写入方式写入大字段（clob按String来构造sql.Clob对象，
	 * blob按byte[]来构造sql.Blob对象，这两种情况class中的该字段必须为与这两种情况的类型一致，否则会出现异常） </font>
	 * 
	 * @param sql
	 *            更新操作的sql语句，可以为insert,update,delete
	 * @param listParaList
	 *            所有绑定参数的集合，集合中的每个元素为一个sql操作的参数集合
	 * @return 执行结果<br>
	 *         <ul>
	 *         <li>0(成功)</li>
	 *         <li><0(失败)</li>
	 *         </ul>
	 * @throws DBException
	 *             更新数据库异常或者封装成类型T的对象时异常
	 */
	public void executePrepareBatch(String sql, List<List<FieldParameter>> listParaList) throws DBException;

	/**
	 * 同一事务中执行不同sql的数据更新操作<br>
	 * <font color="red"> 注意：如果在insert或则update中存在大字段，且大字段处理要按自定义写入器方式写入，
	 * 则必须在执行此方法前注册自定义大字段写处理器，否则按默认写入方式写入大字段（clob按String来构造sql.Clob对象，
	 * blob按byte[]来构造sql.Blob对象，这两种情况class中的该字段必须为与这两种情况的类型一致，否则会出现异常） </font>
	 * 
	 * @param list
	 *            sql预编译对象的集合
	 * @return 执行结果<br>
	 *         <ul>
	 *         <li>0(成功)</li>
	 *         <li><0(失败)</li>
	 *         </ul>
	 * @throws DBException
	 *             更新数据库异常或者封装成类型T的对象时异常
	 */
	public void executePrepareMultiBath(List<SqlParameter> list) throws DBException;

	/**
	 * 注册字段数据类型读取器
	 * 
	 * @param fieldName
	 *            字段数据类型对应的表中的字段名
	 * @param typeReader
	 *            <?> 自定义字段数据类型读取器 ?指的是读取返回的类型，与查询操作返回对象中该字段的类型一致
	 * @throws Exception
	 *             注册字段数据类型读取器时异常
	 */
	public void registerTypeReadHandle(String fieldName, ITypeReader<?> typeReader) throws Exception;

	/**
	 * 注册字段数据类型写入器
	 * 
	 * @param index
	 *            字段数据类型对应的预编译位子
	 * @param typeWriter
	 *            <?> 自定义字段数据类型写入器 ?指要写入到数据库的类型
	 * @throws Exception
	 *             注册字段数据类型写入器时异常
	 */
	public void registerTypeWriteHandle(int index, ITypeWriter<?> typeWriter) throws Exception;

	/**
	 * 设置数据源标识
	 * 
	 * @param poolName
	 *            使用连接池的名称
	 */
	public void setDatabase(String poolName);
}
