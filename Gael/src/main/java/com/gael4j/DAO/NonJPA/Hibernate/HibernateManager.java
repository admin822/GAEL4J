package com.gael4j.DAO.NonJPA.Hibernate;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import com.gael4j.DAO.DAOManager;
import com.gael4j.DAO.NonJPA.Hibernate.Concurrency.MultiDBConcurrency;
import com.gael4j.DAO.NonJPA.Hibernate.Concurrency.MultiTableDeletion;
import com.gael4j.DAO.NonJPA.Hibernate.Concurrency.MultiTableQuery;
import com.gael4j.Entity.ChildNode;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Util.Util;

import net.bytebuddy.asm.Advice.This;
import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

import java.util.concurrent.Executors;

public class HibernateManager implements DAOManager{
	private SessionFactory sessionFactory;
	private Map<Class<?>, Map<Class<?>, String>> child2Parent2ForeignKey;
	private Map<Class<?>,Set<ChildNode>> directedTableGraph;
	public HibernateManager(String pathToMapperFiles, String pathToDBProps,
							Map<Class<?>,Set<ChildNode>> directedTableGraph,
							Map<Class<?>, Map<Class<?>, String>> child2Parent2ForeignKey) {
		System.out.println("================== BUILDING HIBERNATE MANAGER ==================");
		List<String> allMappers =Util.getAllMappers(pathToMapperFiles);
		System.out.println("Detected "+allMappers.size()+" mapper files in "+pathToMapperFiles);
		Properties Hibernateprop = Util.createHibernateProps(pathToDBProps);
		Configuration config=new Configuration().setProperties(Hibernateprop);
		for(String mapperFile:allMappers) {
			File temp=new File(mapperFile);
			config.addResource(temp.getName());
		}
		this.sessionFactory=config.buildSessionFactory();
		this.directedTableGraph=directedTableGraph;
		this.child2Parent2ForeignKey=child2Parent2ForeignKey;
	}
	@Override
	public List<Object> query(Class<?> entityClass, String primaryKeyValue) {
		// TODO Auto-generated method stub
		System.out.println("================== QUERYING USER PRIVACY DATA ==================");
		List<Object> result=new LinkedList<>();
		List<String> pks=new LinkedList<>();
		pks.add(primaryKeyValue);
		handleQuery(result, entityClass, pks , false);
		return result;
	}
	
	@Override
	public void delete(Class<?> entityClass, String primaryKeyValue) {
		// TODO Auto-generated method stub
		
	}
	private boolean hasChildNode(Class<?> currentClass) {
		if(directedTableGraph.containsKey(currentClass)==false||directedTableGraph.get(currentClass).isEmpty()) {
			// this is a leaf node, no other node descends from it
			return false;
		}
		return true;
	}
	private void handleQuery(List<Object> results,Class<?> currentClass, List<String> primaryKeys,boolean hasLoaded) {
		System.out.println("Currently getting data from class: "+currentClass.getName()+" "+primaryKeys.size()+" records of this class will be retrieved");
//		String verbose="The primary keys are [";
//		for(String pk:primaryKeys) {
//			verbose+=" "+pk;
//		}
//		verbose+="]";
//		System.out.println(verbose);
		if(!hasLoaded) {
			for(String pk:primaryKeys) {
				results.add(sessionFactory.openSession().load(currentClass, pk));
			}
		}
		if(hasChildNode(currentClass)==false) {
			return;
		}
		for(ChildNode cn:directedTableGraph.get(currentClass)) {
			handleQuery(results, cn.getNodeClass(), getChildrenPrimaryKeys(currentClass, cn.getNodeClass(), primaryKeys), cn.isBidirectional());
		}
	}
	private List<String> getChildrenPrimaryKeys(Class<?> currentClass, Class<?> childClass, List<String> primaryKeys){
		List<String> childrenPrimaryKeys=new LinkedList<>();
		String childPrimaryKeyName=sessionFactory.getClassMetadata(childClass).getIdentifierPropertyName();
		String childForeignKeyName=child2Parent2ForeignKey.get(childClass).get(currentClass);
		String parentPrimaryKeyName=sessionFactory.getClassMetadata(currentClass).getIdentifierPropertyName();
		String[] strings=childClass.getName().split("\\.");
		String childName=strings[strings.length-1];
		Session session=sessionFactory.openSession();
		for(String parentPrimaryKey:primaryKeys) {
			String hql = "SELECT  CHILD." + childPrimaryKeyName+
					 " FROM "+childName+" AS CHILD "+
					 "WHERE CHILD."+childForeignKeyName+"."+parentPrimaryKeyName+"="+parentPrimaryKey;
			Query query = session.createQuery(hql);
			List<Object> results = query.list();
			for(Object result:results) {
				childrenPrimaryKeys.add(result.toString());
			}
		}
		return childrenPrimaryKeys;
	}
}
	
