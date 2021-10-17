package com.gael4j.Gael.Util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 
 * @author Barry Zhang
 * this class provides util functions that are needed in GAEL4J
 * ALL FUNCTIONS MUST BE STATIC
 */

public class Util {
	
	/***
	 * 
	 * @author Barry Zhang
	 * this function will return a list include all field names for a class, including 
	 * all declared fields and inherited fields
	 * @param someClass
	 * the class object for the class that we need all fields from
	 * 
	 */
	public static List<String> getAllFieldNames(Class<?> someClass,Map<String, Map<String, String>>class2OldName2NewName){
		List<String> currentFieldList=new ArrayList<>();
		Map<String, String> old2New=class2OldName2NewName.getOrDefault(someClass.getName(), null);
		for(Field field:someClass.getDeclaredFields()) {
			if(old2New==null||old2New.containsKey(field.getName())==false) {
				currentFieldList.add(field.getName());
			}
			else {
				currentFieldList.add(old2New.get(field.getName()));
			}
		}
		if(someClass.getSuperclass()!=null) {
			currentFieldList=Stream.concat(currentFieldList.stream(), 
					getAllFieldNames(someClass.getSuperclass(),class2OldName2NewName).stream()).collect(Collectors.toList());
		}
		return currentFieldList;
	}
	
	
}
