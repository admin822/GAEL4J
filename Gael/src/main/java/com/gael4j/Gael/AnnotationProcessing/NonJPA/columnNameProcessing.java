package com.gael4j.Gael.AnnotationProcessing.NonJPA;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gael4j.Gael.Annotations.columnName;

/**
 * 
 * @author Barry Zhang
 * * this module should be called consecutively after 'usernameProcessing' module
 * it aims to replace the names of all annotated fields with the user specified values
 */

public class columnNameProcessing {
	public static Map<String, Map<String, String>> columnNameAnnotationProcessing(Set<Field> allAnnotatedFields){
		Map<String, Map<String, String>> class2OldName2NewName=new HashMap<>();
		for(Field field:allAnnotatedFields) {
			String newName=field.getAnnotation(columnName.class).name();
			String originalName=field.getName();
			String typeName=field.getDeclaringClass().getName();
			Map<String, String> old2New=class2OldName2NewName.getOrDefault(typeName, new HashMap<>());
			old2New.put(originalName,newName);
			class2OldName2NewName.put(typeName, old2New);
			
		}
//		for(String key:class2OldName2NewName.keySet()) {
//			System.out.println(key);
//		}
		return class2OldName2NewName;
	}
}
