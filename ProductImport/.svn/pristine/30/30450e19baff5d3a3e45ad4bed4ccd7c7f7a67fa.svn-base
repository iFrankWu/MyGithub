package com.shinetech.test;

import java.util.Random;

public class TestTemp {
	public static void main(String[] args) {
		// byte b= new Integer(0xC8).byteValue();
		// System.out.println(b);
		// System.out.println(new Integer(0xC8));
		// String[] s = " , ".split(",");
		// System.out.println(s.length);
		// System.out.println(s);
//		double price = 232.99;
//		double fire = price * 100.0f / 45.0f;
//
//		double op = Double.parseDouble(String.format("%.2f", fire));
//
//		double ok = op * 0.45;
//		System.out.println(String.format("%.2f", ok));
//		System.out.println(price + ":" + fire + ":" + ok);
//		System.out.println(Double.parseDouble(SimpleProductConfig.discount_9[2]));
		String itemCode;
		Random random = new Random();
		while(true){
		    itemCode = random.nextInt(999999)+"";
		    if(itemCode.length()<= 6){
		    	int length = itemCode.length();
				for(int i = 0;i < 6-length;i++){
					itemCode = "0"+itemCode; 
				}
			}
		    itemCode = "D"+itemCode;
		    if(itemCode.length() != 7){
		    	System.out.println(itemCode);
		    	break;
		    }
		}
	}
}
