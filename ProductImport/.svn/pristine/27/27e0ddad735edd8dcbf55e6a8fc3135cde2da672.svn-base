/*
 * TestFormat.java	 <br>
 * 2011-10-26<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TestFormat {
	public static void main(String args[]) {

		// 不使用格式化输出数
		double d = 10000.0 / 3.0;
		System.out.println("无格式化输出：" + d);

		// 使用本地默认格式输出数
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		//numberFormat.setMaximumFractionDigits(4);
		//numberFormat.setMinimumIntegerDigits(6);
		String numberString = numberFormat.format(d);
		System.out.println("本地默认格式输出数：" + numberString);

		// 使用本地默认格式输出货币值
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		System.out.println("本地默认格式输出货币值：" + currencyFormat.format(d));

		// 使用本地默认格式输出百分数
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		System.out.println("本地默认格式输出百分数：" + percentFormat.format(d));

		// 在不同的国家和地区数字表示的格式也有区别。如德国
		// 使用德国的格式化输出数
		NumberFormat numberFormatG = NumberFormat
		.getNumberInstance(Locale.GERMANY);
		System.out.println("德国数字输出形式：" + numberFormatG.format(d));

		// 使用德国货币输出形式
		NumberFormat currencyFormatG = NumberFormat
		.getCurrencyInstance(Locale.GERMANY);
		System.out.println("德国货币输出形式：" + currencyFormatG.format(d));

		// 使用美国货币输出形式
		NumberFormat currencyFormatA = NumberFormat
		.getCurrencyInstance(Locale.US);
		System.out.println("美国货币输出形式：" + currencyFormatA.format(d));

		// 使用德国百分数输出形式
		NumberFormat percentFormatG = NumberFormat
		.getPercentInstance(Locale.GERMANY);
		System.out.println("德国百分数输出形式：" + percentFormatG.format(d));

		System.exit(0);
		}
}
