package com.shinetech.sql.exception;

import com.shinetech.sql.DatabaseConst;

/**
 * 功能描述：数据库操作或数据封装异常类 <br>
 * 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 * 
 * 作者 : loong<br>
 * 修改日期 : 2011/3/4<br>
 * 版本：1.0.1 <br>
 * 修改原因：覆盖默认异常输出信息，输出结果同printStackTrace()方法执行效果<br>
 */
public class DBException extends Exception {
	private static final long serialVersionUID = -4251570135571804031L;

	public DBException(String message) {
		super(message);
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName()).append(": ");
		buffer.append(getMessage()).append(DatabaseConst.LINE_SEPARATOR);

		// if (getCause() instanceof SQLException) {
		// SQLException e = (SQLException) getCause();
		// int code = e.getErrorCode();
		// String state = e.getSQLState();
		String message = getCause().getMessage();
		if (message != null) {
			buffer.append("Caused by: ").append(getCause().getClass().getName()).append(": ");
			// buffer.append(code).append(": ");
			// buffer.append(state == null ? "" : state + ": ");
			buffer.append(message).append(DatabaseConst.LINE_SEPARATOR);
		}
		// }

		StackTraceElement[] stackTraceElements = getCause().getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraceElements)
			buffer.append("\t").append("at ").append(stackTraceElement.toString()).append(DatabaseConst.LINE_SEPARATOR);

		return buffer.toString();
	}

}
