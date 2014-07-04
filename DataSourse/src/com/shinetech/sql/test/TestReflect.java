package com.shinetech.sql.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shinetech.sql.ReflectUtil;
import com.shinetech.sql.type.ITypeWriter;
import com.shinetech.sql.type.oracle.DefaultBlobWriter;

public class TestReflect {

	/**
	 * 功能：TODO <br>
	 * 注意事项：<font color="red">TODO</font> <br>
	 * 
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Method method = ReflectUtil.getMethod(TestBean.class, "t_c", null);
		System.out.println("method = " + method.getName());

		ITypeWriter<?> typeWriter = new DefaultBlobWriter();
		Type[] type = typeWriter.getClass().getGenericInterfaces();
		System.out.println(((ParameterizedType) type[0]).getActualTypeArguments()[0]);

		System.out.println(Long.class.isPrimitive());
		System.out.println(long.class.isPrimitive());

		Field[] fields = long.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			System.out.println(StringUtils.upperCase(fields[i].getName()) + ", " + fields[i].getType());
		}

		try {
			List<Class<?>> list = new ArrayList<Class<?>>();
			list.add(Boolean.class);
			list.add(Byte.class);
			list.add(Character.class);
			list.add(Short.class);
			list.add(Integer.class);
			list.add(Long.class);
			list.add(Double.class);
			list.add(Float.class);
			for (Class<?> clazz : list) {
				Object l = ReflectUtil.newInstance(clazz);
				System.out.println(l.getClass().getName());
				System.out.println(l);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
