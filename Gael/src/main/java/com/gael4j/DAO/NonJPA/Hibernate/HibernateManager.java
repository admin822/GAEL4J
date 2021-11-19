package com.gael4j.DAO.NonJPA.Hibernate;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
import java.util.concurrent.TimeoutException;import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
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

import javassist.tools.rmi.ObjectNotFoundException;
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
		System.out.println("================== QUERYING USER PRIVACY DATA ==================");
		List<Object> result=new LinkedList<>();
		Set<String> pks=new HashSet<>();
		pks.add(primaryKeyValue);
		handleQuery(result, entityClass, pks , false);
		return result;
	}
	
	@Override
	public void delete(Class<?> entityClass, String primaryKeyValue) {
		System.out.println("================== DELETING USER PRIVACY DATA ==================");
		Set<String> pks=new HashSet<>();
		pks.add(primaryKeyValue);
		handleDelete(entityClass, pks);
	}
	
	private boolean hasChildNode(Class<?> currentClass) {
		if(directedTableGraph.containsKey(currentClass)==false||directedTableGraph.get(currentClass).isEmpty()) {
			// this is a leaf node, no other node descends from it
			return false;
		}
		return true;
	}
	private void handleDelete(Class<?> currentClass, Set<String> primaryKeys) {
		int counter=0;
		if(hasChildNode(currentClass)) {
			for(ChildNode cn:directedTableGraph.get(currentClass)) {
				handleDelete(cn.getNodeClass(), getChildrenPrimaryKeys(currentClass, cn.getNodeClass(), primaryKeys));
			}
		}
		Session session=sessionFactory.openSession();
		try {
			for(String pk:primaryKeys) {
				Object obj=session.get(currentClass, pk);
				if(obj==null) {
					continue;
				}
				session.beginTransaction();
				session.delete(obj);
				session.getTransaction().commit();
				counter++;
			}
			System.out.println("For class "+currentClass.getName()+" "+counter+" rows are deleted");
		}
		catch (HibernateException e) {
	    	System.out.println("Deletion Error occured when trying to delete from class "+currentClass.getName());
	    	e.printStackTrace();
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	            System.out.println("sucessful rollback");
	        }
	    }finally {
	    	if(session!=null&&session.isOpen())
	    		session.close();
	    }
	}
	private void handleQuery(List<Object> results,Class<?> currentClass, Set<String> primaryKeys,boolean hasLoaded) {
		System.out.println("Currently getting data from class: "+currentClass.getName()+" "+primaryKeys.size()+" records of this class will be retrieved");
//		String verbose="The primary keys are [";
//		for(String pk:primaryKeys) {
//			verbose+=" "+pk;
//		}
//		verbose+="]";
//		System.out.println(verbose);
		Set<String> validPrimaryKeySet=new HashSet<String>(primaryKeys);
		if(!hasLoaded) {
			for(String pk:primaryKeys) {
				Object obj=sessionFactory.openSession().get(currentClass, pk);
				if(obj==null) {
					validPrimaryKeySet.remove(pk);
					continue;
				}
				results.add(obj);
			}
		}
		if(hasChildNode(currentClass)==false||validPrimaryKeySet.isEmpty()) {
			return;
		}
		for(ChildNode cn:directedTableGraph.get(currentClass)) {
			handleQuery(results, cn.getNodeClass(), getChildrenPrimaryKeys(currentClass, cn.getNodeClass(), validPrimaryKeySet), cn.isBidirectional());
		}
	}
	private Set<String> getChildrenPrimaryKeys(Class<?> currentClass, Class<?> childClass, Set<String> primaryKeys){
		Set<String> childrenPrimaryKeys=new HashSet();
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
	
