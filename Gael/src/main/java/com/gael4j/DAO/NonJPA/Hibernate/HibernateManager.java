package com.gael4j.DAO.NonJPA.Hibernate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import org.hibernate.service.ServiceRegistry;

import com.gael4j.DAO.DAOManager;
import com.gael4j.DAO.NonJPA.Hibernate.Concurrency.MultiDBConcurrency;
import com.gael4j.DAO.NonJPA.Hibernate.Concurrency.MultiTableDeletion;
import com.gael4j.DAO.NonJPA.Hibernate.Concurrency.MultiTableQuery;
import com.gael4j.Entity.DBConfig;
import java.util.concurrent.Executors;

public class HibernateManager implements DAOManager{
	private Map<String, SessionFactory> db2SessionFactory;
	private Map<String, List<String>> db2Tables;
	private ExecutorService threadPool;
	public HibernateManager(List<DBConfig> dbConfigList, String resourcePath) {
		Utils.generateMapperFiles(dbConfigList,resourcePath);
		threadPool=Executors.newFixedThreadPool(Math.min(10, dbConfigList.size())); // consistent with hibernate's connection pool
		db2Tables=new HashMap<>();
		db2SessionFactory=new HashMap<>();
		for(DBConfig dbConfig:dbConfigList) {
			String dbName=dbConfig.getDatabaseName();
			List<String> tables=db2Tables.getOrDefault(dbName, new LinkedList<>());
			tables.add(dbConfig.getClassName());
			db2Tables.put(dbName, tables);
			if(db2SessionFactory.containsKey(dbName)) {
				continue;
			}
			Properties dbProps=Utils.createDBProperties(dbConfig);
			Configuration config=new Configuration().setProperties(dbProps);
			
			SessionFactory sessionFactory = config.addResource("mappers.hbm.xml").buildSessionFactory();	
			db2SessionFactory.put(dbName, sessionFactory);
		}
	}
	public List<Object> query(DBConfig dbConfig, String primaryKeyValue){
//		return slowQuery(id);
//		return fastQuery(id);
		return complexQuery(dbConfig, primaryKeyValue);
	}
	public void delete(DBConfig dbConfig, String primaryKeyValue) {
//		fastDeletion(id);
		complexDeletion(dbConfig, primaryKeyValue);
	}
	private void complexDeletion(DBConfig dbConfig, String primaryKeyValue) {
		List<Future<?>> workers=new LinkedList<>();
		workers.add(singleTableDeletion(dbConfig, primaryKeyValue));
		for(Future<?> worker:workers) { // !!! Consider do we really need to wait here????
			try {
				worker.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private List<Object> complexQuery(DBConfig dbConfig, String primaryKeyValue) {
		List<Object> result=new LinkedList<>();
		List<Object> threadSafeResult=Collections.synchronizedList(result);
		List<Future<?>> workers=new LinkedList<>();
		// for now we only worry about query the starting table
		
		workers.add(singleTableQuery(dbConfig, primaryKeyValue, threadSafeResult));
		for(Future<?> worker:workers) {
			try {
				worker.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	private Future<?> singleTableQuery(DBConfig dbConfig, String primaryKeyValue, List<Object> queryResult){
		String tableName=dbConfig.getTableName();
		Session session=db2SessionFactory.get(dbConfig.getDatabaseName()).openSession();
		String idName=dbConfig.getPrimaryKey();
		return threadPool.submit(new MultiTableQuery(tableName, primaryKeyValue, queryResult, session, idName));
	}
	private Future<?> singleTableDeletion(DBConfig dbConfig, String primaryKeyValue){
		String tableName=dbConfig.getTableName();
		Session session=db2SessionFactory.get(dbConfig.getDatabaseName()).openSession();
		String idName=dbConfig.getPrimaryKey();
		return threadPool.submit(new MultiTableDeletion(tableName, primaryKeyValue, session, idName));
	}
	
	@Deprecated
	private List<Object> slowQuery(String id){
		List<Object> result=new LinkedList<>();
		for(String db:db2Tables.keySet()) {
			SessionFactory currentFac=db2SessionFactory.get(db);
			for(String table:db2Tables.get(db)) {
				Session session = currentFac.openSession();
				result.addAll(Utils.handleQuery(session, id,table,"userID"));
			}
		}
		return result;
	}
	
	@Deprecated
	private void fastDeletion(String id){
		ExecutorService threadPool=Executors.newFixedThreadPool(Math.min(3, db2Tables.keySet().size()));
		List<Future<?>> futures=new LinkedList<Future<?>>();
		for(String db:db2Tables.keySet()) {
			SessionFactory currentFac=db2SessionFactory.get(db);
			List<String> tables=db2Tables.get(db);
			futures.add(threadPool.submit(new MultiDBConcurrency(true, null, currentFac, tables, "userID",id)));
		}
		for(Future<?> future:futures) {
			try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Deprecated
	private List<Object> fastQuery(String id){
		List<Object> result=new LinkedList<>();
		List<Object> threadSafeResult=Collections.synchronizedList(result);
		ExecutorService threadPool=Executors.newFixedThreadPool(Math.min(3, db2Tables.keySet().size()));
		List<Future<?>> futures=new LinkedList<Future<?>>();
		for(String db:db2Tables.keySet()) {
			SessionFactory currentFac=db2SessionFactory.get(db);
			List<String> tables=db2Tables.get(db);
			futures.add(threadPool.submit(new MultiDBConcurrency(false, threadSafeResult, currentFac, tables, "userID",id)));
		}
		for(Future<?> future:futures) {
			try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
