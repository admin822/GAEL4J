package com.gael4j.Gael.AnnotationProcessing.NonJPA;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Annotations.columnName;
import com.gael4j.Gael.Annotations.userdata;

public class ReflectionProcessing {
	public static List<DBConfig> reflectionProcessing(String scannedPackagePath){
		List<DBConfig> privateInfoList;
		Reflections reflections=new Reflections(scannedPackagePath);
		Set<Class<?>>allAnnotatedClasses = reflections.getTypesAnnotatedWith(userdata.class);
		Set<Field> allColAnnotatedFields=new HashSet<>();
		for(Class<?> annotatedClass:allAnnotatedClasses) {
			for(Field field:annotatedClass.getDeclaredFields()) {
				if(field.getAnnotation(columnName.class)!=null) {
					allColAnnotatedFields.add(field);
				}
			}
		}
		Map<String, Map<String, String>> class2OldName2NewName=columnNameProcessing.columnNameAnnotationProcessing(allColAnnotatedFields);
		
		privateInfoList=usernameProcessing.usernameAnnotationProcessing(allAnnotatedClasses,class2OldName2NewName);
		return privateInfoList;
	}
}
