package com.gael4j.Gael;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice.Info;

import com.gael4j.Gael.Util.JPAUtils;

import net.bytebuddy.asm.Advice.This;

import org.hibernate.query.criteria.internal.expression.function.LengthFunction;

import com.gael4j.DAO.DAOManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateMappingFileGenerator;
import com.gael4j.DAO.JPA.Hibernate.JPAHibernateManager;
import com.gael4j.Entity.ChildNode;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;
import com.gael4j.Gael.AnnotationProcessing.NonJPA.RelationMapping;

public class Gael {
	Map<Class<?>,Set<ChildNode>> directedTableGraph;
	Map<Class<?>, Map<Class<?>, String>> child2Parent2ForeignKey;
	Map<String, DBConfig> name2dbConfigMap;
	boolean useJPA;
	DAOManager daoManager;
	final String DB_PROPS_PATH="./target/classes/db.properties";

	public void delete(Class<?> entityClass, String primaryKeyValue) {
		daoManager.delete(entityClass, primaryKeyValue);
	}
	public List<Object> query(Class<?> entityClass, String primaryKeyValue){
		return daoManager.query(entityClass, primaryKeyValue);
	}
	
	/*        test         function              */
	public void test1() {
		System.out.println("================== RUNNING GAEL TEST1 ==================");
		for(Class<?> c:this.directedTableGraph.keySet()) {
			System.out.println("Current Class is "+c.getName());
			for(ChildNode cn:directedTableGraph.get(c)) {
				System.out.println("\tchild class: "+cn.getNodeClass().getName()+"\tis bidirectional: "+cn.isBidirectional());
			}
		}
	}
	public void test2() {
		System.out.println("================== RUNNING GAEL TEST2 ==================");
		for(Class<?> c:child2Parent2ForeignKey.keySet()) {
			System.out.println("Child Class is "+c.getName());
			Map<Class<?>, String> parentClass2Keys=child2Parent2ForeignKey.get(c);
			for(Class<?> parentClass:parentClass2Keys.keySet()) {
				System.out.println("\t parent class is "+parentClass.getName()+"\tforeign key is "+parentClass2Keys.get(parentClass));
			}
		}
	}
	/*        test         function              */
	
	
	public Gael(String packageScanPath,boolean useJPA,String JPAPersistenceUnitName,String pathToMapperFiles) {
		this.useJPA=useJPA;
		if(this.useJPA) {
			JPAUtils.persistenceUnitName = JPAPersistenceUnitName;
			directedTableGraph = Controller.scan(packageScanPath);
			name2dbConfigMap = Controller.constructDBConfigMap(packageScanPath);

			//TODO: get directedTableGraph
		} else {
			//TODO: get directedTableGraph
			Map[] result=RelationMapping.relationMapping(packageScanPath, pathToMapperFiles);
			directedTableGraph=result[0];
			child2Parent2ForeignKey=result[1];
		}
		if (useJPA) {
			//TODO: get daoManager
			daoManager = new JPAHibernateManager(directedTableGraph, name2dbConfigMap);
		} else {
			//TODO: get daoManager
			daoManager=new HibernateManager(pathToMapperFiles, DB_PROPS_PATH,this.directedTableGraph,this.child2Parent2ForeignKey);
		}
	}

}
