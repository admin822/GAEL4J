package com.gael4j.DAO.NonJPA.Hibernate.Concurrency;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.hibernate.SessionFactory;


public class MultiDBConcurrency implements Runnable{
	boolean isDeletion;
	List<Object> queryResult;
	SessionFactory sessionFactory;
	List<String> tables;
	String id;
	String idName;
	ExecutorService threadPool;
	List<Future<?>> futures;
	public MultiDBConcurrency(boolean isDeletion,List<Object> resultList,SessionFactory sessionFactory, List<String> tables,String idName,String id) {
		this.isDeletion=isDeletion;
		this.queryResult=resultList;
		this.sessionFactory=sessionFactory;
		this.tables=tables;
		this.threadPool= Executors.newFixedThreadPool(Math.min(5, tables.size()));
		this.idName=idName;
		this.id=id;
		futures=new LinkedList<>();
	}
	@Override
	public void run() {
		if(isDeletion) {// delete user privacy
			for(String table:tables) {
				futures.add(threadPool.submit(new MultiTableDeletion(table, id,sessionFactory.openSession(), idName)));
			}
		}
		else { // query user privacy
			for(String table:tables) {
				futures.add(threadPool.submit(new MultiTableQuery(table, id, queryResult, sessionFactory.openSession(), idName)));
			}
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
	
}
