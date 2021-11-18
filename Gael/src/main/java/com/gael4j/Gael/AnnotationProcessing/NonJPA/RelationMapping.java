package com.gael4j.Gael.AnnotationProcessing.NonJPA;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.reflections.Reflections;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.gael4j.Entity.ChildNode;
import com.gael4j.Gael.Annotations.userdata;
import com.gael4j.Gael.Util.Util;

import org.w3c.dom.Element;

public class RelationMapping {
	
	private static Set<Class<?>> getAllAnnotatedClasses(String scannedPackagePath) {
		Reflections reflections=new Reflections(scannedPackagePath);
		Set<Class<?>>allAnnotatedClasses = reflections.getTypesAnnotatedWith(userdata.class);
		System.out.println("Detected "+allAnnotatedClasses.size()+" user privacy related classes in "+scannedPackagePath);
		return allAnnotatedClasses;
	}
	private static void manyToOneParsing(Map<Class<?>, Set<ChildNode>> FKConnections,Element element,Class<?> currentClass,
															Map<Class<?>, Map<Class<?>, Boolean>> directionalMap,
															Map<Class<?>, Map<Class<?>, String>> child2Parent2ForeignKey) {
		 NodeList manyToOneList=element.getElementsByTagName("many-to-one");
	   	 NodeList oneToManyList=element.getElementsByTagName("one-to-many");
	   	 System.out.println("\tDetected "+manyToOneList.getLength()+" many to one associations in this mapper file");
	   	 System.out.println("\tDetected "+oneToManyList.getLength()+" one to many associations in this mapper file");
	   	 try {
	   		for(int i=0;i<oneToManyList.getLength();i++) {
		   		 Node oneToManyNode=oneToManyList.item(i);
		   		 if(oneToManyNode.getNodeType()==Node.ELEMENT_NODE) {
		   			 Element oneToManyElement=(Element)oneToManyNode;
		   			 String className=oneToManyElement.getAttribute("class");
		   			 Class<?>childClass=Class.forName(className);
		   			 if(FKConnections.containsKey(childClass)==false) {
		   				 continue;
		   			 }
		   			 Map<Class<?>, Boolean> currentMap=directionalMap.getOrDefault(currentClass, new HashMap<>());
		   			 currentMap.put(childClass, true);
		   			 directionalMap.put(currentClass, currentMap);
		   		 }
		   		 
		   	 }
	   		for(int i=0;i<manyToOneList.getLength();i++) {
		   		 Node manyToOneNode=manyToOneList.item(i);
		   		 if(manyToOneNode.getNodeType()==Node.ELEMENT_NODE) {
		   			 Element manyToOneElement=(Element)manyToOneNode;
		   			 String className=manyToOneElement.getAttribute("class");
		   			 String foreignKeyName=manyToOneElement.getAttribute("name");
		   			 Class<?>parentClass=Class.forName(className);
		   			 if(FKConnections.containsKey(parentClass)==false) {
		   				 continue;
		   			 }
		   			 Map<Class<?>, String> currentParentMap=child2Parent2ForeignKey.getOrDefault(currentClass, new HashMap<>());
		   			 currentParentMap.put(parentClass, foreignKeyName);
		   			 child2Parent2ForeignKey.put(currentClass, currentParentMap);
		   			 Map<Class<?>, Boolean> currentMap=directionalMap.getOrDefault(parentClass, new HashMap<>());
		   			 if(currentMap.containsKey(currentClass)) {
		   				 continue;
		   			 }
		   			 currentMap.put(currentClass, false);
		   			 directionalMap.put(parentClass, currentMap);
		   		 }
		   		 
		   	 }
	   		
	   	 }catch (Exception e) {
			System.out.println("many to one parsing error!");
			e.printStackTrace();
		}
	   	
	   	 
	   	 
	}
	private static Map[] getFKConnections(String pathToMappers, Set<Class<?>> allAnnotatedClasses){
		Map<Class<?>, Set<ChildNode>> FKConnections=new HashMap<>();
		Map<Class<?>, Map<Class<?>, String>> child2Parent2ForeignKey=new HashMap<>();
		Map[] result=new Map[2];
		List<String> allMappers=Util.getAllMappers(pathToMappers);
		System.out.println("Detected "+allMappers.size()+" mapper files in "+pathToMappers);
		Map<Class<?>, Map<Class<?>, Boolean>> directionalMap=new HashMap<>();
		for(Class<?> annotatedClass:allAnnotatedClasses) {
			FKConnections.put(annotatedClass, new HashSet<>());
		}
		try {
			for(String mapperFile:allMappers) {
				 File inputFile = new File(mapperFile);
		         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         Document doc = dBuilder.parse(inputFile);
		         System.out.println("Start parsing mapper file: "+mapperFile+" ...");
		         doc.getDocumentElement().normalize();
		         NodeList nList = doc.getElementsByTagName("class");
		         for (int temp = 0; temp < nList.getLength(); temp++) {
		             Node nNode = nList.item(temp);
		             if(nNode.getNodeType()==Node.ELEMENT_NODE) {
		            	 Element element=(Element)nNode;
		            	 Class<?> currentClass=Class.forName(element.getAttribute("name"));
		            	 System.out.println("  Currently parsing class "+ currentClass.getName());
		            	 if(allAnnotatedClasses.contains(currentClass)==false) { // not a privacy class
		            		 continue;
		            	 }
		            	 
		            	 manyToOneParsing(FKConnections, element, currentClass,directionalMap,child2Parent2ForeignKey); // handles un/bidirectional Many-to-one, one-to-one association
		             }
		        }
			}
	         for(Class<?> parentClass:directionalMap.keySet()) {
		   			Map<Class<?>, Boolean> directionalEdges=directionalMap.get(parentClass);
		   			for(Class<?> childClass:directionalEdges.keySet()) {
		   				ChildNode newNode=new ChildNode();
		   				newNode.setBidirectional(directionalEdges.get(childClass));
		   				newNode.setNodeClass(childClass);
		   				Set<ChildNode> currentSet=FKConnections.get(parentClass);
		   				currentSet.add(newNode);
		   				FKConnections.put(parentClass, currentSet);
		   			}
		   		}
	         result[0]=FKConnections;
	         result[1]=child2Parent2ForeignKey;
	         return result;
			 }catch (Exception e) {
				 System.out.println("Foreign Key Parsing error");
				e.printStackTrace();
				return result;
			}
		}
	
	public static Map[]  relationMapping(String scannedPackagePath, String pathToMappers){
		System.out.println("================== BUILDING FOREIGN KEY DIRECTIONAL GRAPH ==================");
		return getFKConnections(pathToMappers, getAllAnnotatedClasses(scannedPackagePath));
	}
}
 