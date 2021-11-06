package com.gael4j.Gael.AnnotationProcessing.NonJPA;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Annotations.columnName;
import com.gael4j.Gael.Annotations.userdata;
import com.gael4j.Gael.CustomExceptions.MultiplePrimaryKeyException;
import com.gael4j.Gael.Annotations.primarykey;

public class ReflectionProcessing {
	public static List<DBConfig> reflectionProcessing(String scannedPackagePath){
		List<DBConfig> privateInfoList;
		Reflections reflections=new Reflections(scannedPackagePath);
		Set<Class<?>>allAnnotatedClasses = reflections.getTypesAnnotatedWith(userdata.class);
		Set<Field> allColAnnotatedFields=new HashSet<>();
		Map<Class<?>, Field> class2PrimaryKeys=new HashMap<>();
		try {
			for(Class<?> annotatedClass:allAnnotatedClasses) {
				Field defaultPrimarykey=null;
				for(Field field:annotatedClass.getDeclaredFields()) {
					if(defaultPrimarykey==null) {
						defaultPrimarykey=field; // initialization
					}
					if(field.getAnnotation(columnName.class)!=null) {
						allColAnnotatedFields.add(field);
					}
					if(field.getAnnotation(primarykey.class)!=null) {
						if(class2PrimaryKeys.containsKey(annotatedClass)) {
							throw new MultiplePrimaryKeyException();
						}
						class2PrimaryKeys.put(annotatedClass, field);
					}
				}
				if(class2PrimaryKeys.containsKey(annotatedClass)==false) {
					class2PrimaryKeys.put(annotatedClass, defaultPrimarykey);
				}
			}
			Map<String, Map<String, String>> class2OldName2NewName=columnNameProcessing.columnNameAnnotationProcessing(allColAnnotatedFields);
			privateInfoList=usernameProcessing.usernameAnnotationProcessing(allAnnotatedClasses,class2OldName2NewName,class2PrimaryKeys);
			return privateInfoList;
		} catch (MultiplePrimaryKeyException mpe) {
			System.out.println("One table cannot have multiple primary keys!");
			mpe.printStackTrace();
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
