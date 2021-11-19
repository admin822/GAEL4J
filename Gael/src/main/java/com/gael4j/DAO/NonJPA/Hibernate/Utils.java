package com.gael4j.DAO.NonJPA.Hibernate;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.tools.Diagnostic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.gael4j.Entity.DBConfig;

public class Utils {
	
	// THIS NEEDS TO BE GENERIC BEFORE PUBLISH!

	
	static List<Object> handleQuery(Session session, String id,String className, String idName){
		 try {
			 session.beginTransaction();
	            String hql = "FROM "+className+" T where T."+idName+"="+id;  
	            System.out.println(hql);
	            Query query = session.createQuery(hql);
	            List<Object> result = query.list();
	            session.getTransaction().commit();
	            return result;
	    } catch (HibernateException e) {
	    	System.out.println("Query Error!");
	    	e.printStackTrace();
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	            System.out.println("sucessful rollback");
	        }
	        return null;
	    } finally {
	    	if(session!=null&&session.isOpen())
	    		session.close();
	    }
	}
}
