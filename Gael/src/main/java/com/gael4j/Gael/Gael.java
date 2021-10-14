package com.gael4j.Gael;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.gael4j.Gael.Annotations.userdata;
import com.gael4j.Gael.Model.DatabaseConfig;

public class Gael {
	private Reflections reflections;
	private Map<String,DatabaseConfig> annotatedClasses;
	public Gael(String packagePath) {
		reflections=new Reflections(packagePath);
		annotatedClasses=new HashMap<>();
		Set<Class<?>> allAnnotatedClasses=reflections.getTypesAnnotatedWith(userdata.class);
		
		for(Class<?> someClass:allAnnotatedClasses) {
			annotatedClasses.put(someClass.getAnnotation(userdata.class).schema(),
									com.gael4j.Gael.Util.Reflections.createInstanceofClass(someClass));
		}
	}
	public void showdown() {
		for(String key:annotatedClasses.keySet()) {
			System.out.println(key);
			System.out.println("   "+annotatedClasses.get(key).getClass().getName()+": ");
			for(Field f:annotatedClasses.get(key).getClass().getDeclaredFields()) {
				System.out.println("      "+f.toString());
			}
		}
	}
}
