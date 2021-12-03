package com.gael4j.Gael;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gael4j.Gael.Util.JPAUtils;

import com.gael4j.DAO.DAOManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateManager;
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
	final String DB_PROPS_PATH="db.properties";
    public Map<Class<?>,Set<ChildNode>> getDirectedGraph(){
    	return directedTableGraph;
    }
    public Map<Class<?>, Map<Class<?>, String>> getForeignKeyMap(){
    	return child2Parent2ForeignKey;
    }
	public void delete(Class<?> entityClass, String primaryKeyValue) {
		daoManager.delete(entityClass, primaryKeyValue);
	}
	public List<Object> query(Class<?> entityClass, String primaryKeyValue){
		return daoManager.query(entityClass, primaryKeyValue);
	}
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
