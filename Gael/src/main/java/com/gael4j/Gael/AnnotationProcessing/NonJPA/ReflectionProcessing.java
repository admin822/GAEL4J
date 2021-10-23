package com.gael4j.Gael.AnnotationProcessing.NonJPA;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Annotations.columnName;
import com.gael4j.Gael.Annotations.userdata;

public class ReflectionProcessing {
	public static List<DBConfig> reflectionProcessing(String scannedPackagePath){
		List<DBConfig> privateInfoList;
		Reflections reflections=new Reflections(scannedPackagePath,new FieldAnnotationsScanner(),new SubTypesScanner(),new TypeAnnotationsScanner());
		Set<Field> allColAnnotatedFields=reflections.getFieldsAnnotatedWith(columnName.class);
		Map<String, Map<String, String>> class2OldName2NewName=columnNameProcessing.columnNameAnnotationProcessing(allColAnnotatedFields);
		Set<Class<?>>allAnnotatedClasses = reflections.getTypesAnnotatedWith(userdata.class);
		privateInfoList=usernameProcessing.usernameAnnotationProcessing(allAnnotatedClasses,class2OldName2NewName);
		return privateInfoList;
	}
}
