package com.gael4j.Gael.AnnotationProcessing;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import com.gael4j.Gael.Annotations.userdata;
import com.gael4j.Gael.Model.DatabaseConfig;
import com.gael4j.Gael.Util.Util;

/**
 * 
 * @author Barry Zhang
 * the functionality of this module is to process 'userdata' annotations
 */
public class usernameProcessing {
	public static Map<String, DatabaseConfig> usernameAnnotationProcessing(Set<Class<?>> allAnnotatedClasses,Map<String, Map<String, String>>class2OldName2NewName) {
		Map<String, DatabaseConfig> privateInfoMap=new HashMap<>();
		for(Class<?> annotatedClass:allAnnotatedClasses) {
			String dbName=annotatedClass.getAnnotation(userdata.class).schema();
			String tableName=annotatedClass.getAnnotation(userdata.class).table();
			String className=annotatedClass.getName();
			privateInfoMap.put(className, new DatabaseConfig(dbName,
													tableName,
													Util.getAllFieldNames(annotatedClass,class2OldName2NewName)));
		}
		return privateInfoMap;
	}
}
