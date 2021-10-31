package com.gael4j.DAO.NonJPA.Hibernate.Concurrency;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class MultiTableDeletion implements Runnable{
	String tableName;
	String id;
	Session session;
	String idName;
	public MultiTableDeletion(String tableName,String id,List<Object> queryResult, Session session,String idName) {
		this.tableName=tableName;
		this.id=id;
		this.session=session;
		this.idName=idName;
	}
	@Override
	public void run() {
		try {
			 session.beginTransaction();
	            String hql = "delete FROM "+tableName+" T where T."+idName+"="+id;  
	            System.out.println(hql);
	            Query query = session.createQuery(hql);
	            query.executeUpdate();
	            session.getTransaction().commit();
	    } catch (HibernateException e) {
	    	System.out.println("Deletion Error!");
	    	e.printStackTrace();
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	            System.out.println("sucessful rollback");
	        }
	    } finally {
	    	if(session!=null&&session.isOpen())
	    		session.close();
	    }
	}
}
