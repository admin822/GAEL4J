package com.gael4j.Gael.AnnotationProcessing.NonJPA;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.gael4j.DAO.NonJPA.Hibernate.Utils;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Annotations.userdata;

public class RelationMapping {
	
	private Map<String, String> getAllAnnotatedClasses(String scannedPackagePath) {
		Map<String,String> class2Table=new HashMap<>();
		Reflections reflections=new Reflections(scannedPackagePath);
		Set<Class<?>>allAnnotatedClasses = reflections.getTypesAnnotatedWith(userdata.class);
		for(Class<?> annotatedClass:allAnnotatedClasses) {
			String table=annotatedClass.getAnnotation(userdata.class).table();
			String className=annotatedClass.getName();
			if(table=="") {
				String[] splits=className.split(".");
				table=splits[splits.length-1];
			}
			class2Table.put(className, table);
		}
		return class2Table;
	}
	
	private Map<String, Set<String>> getFKConnections(String pathToMappers, Map<String,String> class2Table){
		Map<String, Set<String>> FKConnections=new HashMap<>();
		List<String> allMappers=Utils.getAllMappers(pathToMappers);
		return FKConnections;
	}
	
	public static List<DBConfig> relationMapping(String scannedPackagePath, String pathToMappers){
		
	}
}
 