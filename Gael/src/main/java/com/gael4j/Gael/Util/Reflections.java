package com.gael4j.Gael.Util;

public class Reflections {
	public static <T> T createInstanceofClass(Class<T> newClass) {
	        try {
	            return newClass.newInstance();
	        } catch (Exception e) {
	            return null; // handle in the future.
	        }
	}
}
