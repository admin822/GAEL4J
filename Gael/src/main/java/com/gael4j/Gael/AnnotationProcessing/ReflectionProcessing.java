package com.gael4j.Gael.AnnotationProcessing;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.gael4j.Gael.Annotations.columnName;
import com.gael4j.Gael.Annotations.userdata;
import com.gael4j.Gael.Model.DatabaseConfig;

public class ReflectionProcessing {
	public static Map<String , DatabaseConfig> reflectionProcessing(String scannedPackagePath){
		Map<String,DatabaseConfig> result;
		Reflections reflections=new Reflections(scannedPackagePath,new FieldAnnotationsScanner(),new SubTypesScanner(),new TypeAnnotationsScanner());
		Set<Field> allColAnnotatedFields=reflections.getFieldsAnnotatedWith(columnName.class);
		Map<String, Map<String, String>> class2OldName2NewName=columnNameProcessing.columnNameAnnotationProcessing(allColAnnotatedFields);
		Set<Class<?>>allAnnotatedClasses = reflections.getTypesAnnotatedWith(userdata.class);
		result=usernameProcessing.usernameAnnotationProcessing(allAnnotatedClasses,class2OldName2NewName);
		return result;
	}
}
