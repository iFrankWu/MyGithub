/*
 * TestSortList.java	 <br>
 * 2011-10-12<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.shinetech.bean.CatalogProduct;

/**
 * Class description goes here.
 *
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TestSortList {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		test5();
		
		//System.out.println(System.getenv());
//		Random random = new Random();
//		int i = random.nextInt(100);
//		double d = random.nextDouble();
//		System.out.println(i);
//		System.out.println(d);
	}
	public static void test5(){
		List<Integer> al = new ArrayList<Integer>() ; 
		List<Integer> alr = new ArrayList<Integer>(); 
		al.add(1); 
		al.add(2); 
		al.add(3); 
		al.add(4); 
//		alr.add(1); 
//		alr.add(2); 
		alr.add(3); 
		System.out.println("al: "+al); 
		System.out.println("alr: "+alr); 
		System.out.println(al.retainAll(alr)); 
		System.out.println("Modified al: "+al); 
		System.out.println("Modified al: "+alr);

	}
	public static void test4(){
		List< String > list = new ArrayList< String >();
        list.add("A");
        list.add("B");
        list.add("E");
        list.add("D");
        list.add("C");
//        list.remove("E");
        list.set(2, "C");
        System.out.println(list);
	}
	public static void test3(){
		List< String > list = new ArrayList< String >();
        list.add("A");
        list.add("B");
        list.add("C");
        
        for( Iterator< String > it = list.iterator(); it.hasNext() ; )
        {
            String str = it.next();
            if( str.equals( "B" ) )
            {
                it.remove();
            }
        }
        
        for( String str : list )
        {
            System.out.println( str );
        }
	}
	public static void test2(){
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("1,9,23,2,3,2".split(",")));
//		for(String s : list){
//			if(s.equals("9")){
//				list.remove(s);
//			}
//		}
//		for(int i=0;i<list.size();){
//			if(list.get(i).equals("9")){
//				list.remove(i);
//				continue;
//			}
//			i++;
//		}
		
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			String s = it.next();
			if(s.equals("9")){
				it.remove();
			}
		}
		System.out.println(list);
	}
	public static void test(){
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(Arrays.asList(1,9,23,2,3,2));
		Collections.sort(list);
		System.out.println(list);
	}
	public static void test1(){
		List<CatalogProduct> list = new ArrayList<CatalogProduct>();
		list.add(new CatalogProduct("s1", 1));
		list.add(new CatalogProduct("s2", 3));
		list.add(new CatalogProduct("s3", 4));
		list.add(new CatalogProduct("s4", 2));
		list.add(new CatalogProduct("s5",4));
		list.add(new CatalogProduct("s6", 4));
		list.add(new CatalogProduct("s7", 5));
		list.add(new CatalogProduct("s8", 6));
		list.add(new CatalogProduct("s9",4));
		list.add(new CatalogProduct("s10", 4));
		list.add(new CatalogProduct("s11",4));
		list.add(new CatalogProduct("s12", 3));
		list.add(new CatalogProduct("s13", 3));
		list.add(new CatalogProduct("s15", 3));
		final Random random = new Random();
		Collections.sort(list,new Comparator<CatalogProduct>() {

			@Override
			public int compare(CatalogProduct o1, CatalogProduct o2) {
				if(o1.getScore() > o2.getScore()){
					return 1;
				}else if(o1.getScore() == o2.getScore()){
//					return 0;
					int rs = random.nextInt(3)-1;
					return rs;
				}else{
					return -1;
				}
			}
		});
		for(CatalogProduct p : list){
			System.out.println(p.getSku()+":"+p.getScore());
		}
		
	}
}
