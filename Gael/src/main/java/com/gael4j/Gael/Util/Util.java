package com.gael4j.Gael.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.cfg.Configuration;

import com.gael4j.Entity.DBConfig;


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
	
	public static List<String> getAllMappers(String pathToMappers){
		List<String> Mappers=new LinkedList<>();
		File folder = new File(pathToMappers);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			String filename = listOfFiles[i].getName();
	        if(filename.endsWith(".hbm.xml")||filename.endsWith(".HBM.XML")) {
	        	Mappers.add(listOfFiles[i].getAbsolutePath());
	        }
		}
		return Mappers;
	}
	
	public static Properties createHibernateProps(String pathToDBProps) {
		Properties dbProperties=new Properties();
		try (InputStream input = new FileInputStream(pathToDBProps)) {
			dbProperties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		System.out.println("creating Hibernate configuration...");
		Properties Hibernateprop= new Properties();
		//generic form: jdbc:mysql://<hostname>:<port>/dbname
		Hibernateprop.setProperty("hibernate.connection.url", dbProperties.getProperty("url"));
		Hibernateprop.setProperty("dialect", "org.hibernate.dialect.MySQ8LDialect");
		Hibernateprop.setProperty("hibernate.connection.username", dbProperties.getProperty("username"));
		Hibernateprop.setProperty("hibernate.connection.password", dbProperties.getProperty("password"));
		Hibernateprop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		Hibernateprop.setProperty("hibernate.hbm2ddl.auto","update");
		Hibernateprop.setProperty("show_sql", "true"); //If you wish to see the generated sql query
		Hibernateprop.setProperty("connection.pool_size", "10"); // pool size is 10 rn
		return Hibernateprop;
	}
	
	
}
