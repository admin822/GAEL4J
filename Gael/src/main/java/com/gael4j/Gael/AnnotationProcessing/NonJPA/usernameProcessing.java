package com.gael4j.Gael.AnnotationProcessing.NonJPA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Annotations.userdata;
import com.gael4j.Gael.Util.Util;

/**
 * 
 * @author Barry Zhang
 * the functionality of this module is to process 'userdata' annotations
 */
public class usernameProcessing {
	public static List<DBConfig> usernameAnnotationProcessing(Set<Class<?>> allAnnotatedClasses,Map<String, Map<String, String>>class2OldName2NewName) {
		List<DBConfig> privateInfoList=new ArrayList<>();
		for(Class<?> annotatedClass:allAnnotatedClasses) {
			String dbName=annotatedClass.getAnnotation(userdata.class).schema();
			String tableName=annotatedClass.getAnnotation(userdata.class).table();
			String className=annotatedClass.getName();
			List<List<String>>fieldLists=Util.getAllFieldNames(annotatedClass,class2OldName2NewName);
		    privateInfoList.add(new DBConfig(dbName, tableName, className, fieldLists.get(0), fieldLists.get(1)));
		}
		return privateInfoList;
	}
}
