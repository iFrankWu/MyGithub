package com.shinetech.sql.type;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Copyright (C), 2010-2011, ShineTech. Co., Ltd.<br>
 * 文件名 ：LobWriter.java<br>
 * 包名：com.shinetech.sql.lob<br>
 * 作者 : husw<br>
 * 创建日期 : 2011/2/23<br>
 * 版本：1.0.0 <br>
 * 功能描述： 大字段写如器接口<br>
 * 
 * 作者 : loong<br>
 * 创建日期 : 2011/3/7<br>
 * 版本：1.0.1 <br>
 * 修改原因：增加是否需要清理临时对象 <br>
 */
public interface ITypeWriter<T> {

	/**
	 * 大字段写入
	 * 
	 * @param conn
	 *            数据库连接
	 * @param stmt
	 *            预编译statement
	 * @param fileldName
	 *            大字段名
	 * @param t
	 *            绑定到大字段上的值
	 * @return
	 */
	public int write(Connection conn, PreparedStatement stmt, int index, T t) throws Exception;

	/**
	 * 
	 * 功能：释放大字段 <br>
	 * 注意事项：<font color="red">在完成数据库操作后必须调用该方法</font> <br>
	 * void
	 */
	public void free() throws Exception;

	/**
	 * 功能：是否需要清理临时对象 <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @return boolean
	 */
	public boolean isFree();
}
