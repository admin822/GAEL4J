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
	 * this function will return a list include all field names and column names for a class, including 
	 * all declared fields and inherited fields; if pass in 'null' for the second parameter, then the two
	 * lists returned by this function will be the same.
	 * @param someClass: as indicated by name, it could be any class
	 * @param class2OldName2NewName: Map{className, Map{fieldName(like Sample User), columnNameInDatabase(like sample_user)}}
	 * @return a list includes two lists, the first list is the a list of fields of 'someClass', the second one is the column names
	 * in the database that correspond to these fields
	 */
	public static List<List<String>> getAllFieldNames(Class<?> someClass,Map<String, Map<String, String>>class2OldName2NewName){
		List<String> currentFieldList=new ArrayList<>();
		List<String> oldFieldList=new ArrayList<>();
		List<List<String>> resultList=new ArrayList<>();
		Map<String, String> old2New;
		if(class2OldName2NewName==null) {
			old2New=null;
		}
		else {
			old2New=class2OldName2NewName.getOrDefault(someClass.getName(), null);
		}
		for(Field field:someClass.getDeclaredFields()) {
			oldFieldList.add(field.getName());
			if(old2New==null||old2New.containsKey(field.getName())==false) {
				currentFieldList.add(field.getName());
			}
			else {
				currentFieldList.add(old2New.get(field.getName()));
			}
		}
		if(someClass.getSuperclass()!=null) {
			List<List<String>>superClassFieldList=getAllFieldNames(someClass.getSuperclass(), class2OldName2NewName);
			oldFieldList=Stream.concat(oldFieldList.stream(), superClassFieldList.get(0).stream()).collect(Collectors.toList());
			currentFieldList=Stream.concat(currentFieldList.stream(), superClassFieldList.get(1).stream()).collect(Collectors.toList());
		}
		resultList.add(oldFieldList);
		resultList.add(currentFieldList);
		return resultList;
	}
	
	
}
