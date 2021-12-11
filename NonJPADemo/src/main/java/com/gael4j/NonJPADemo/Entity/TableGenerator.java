package com.gael4j.NonJPADemo.Entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class TableGenerator {
	private SessionFactory sessionFactory;
	public TableGenerator(String pathToConnectionInfo,String pathToMapper) {
		//String pathToMappers=System.getProperty("user.dir")+"/src/main/java/TableGenerator/mappers.hbm.xml";
		Properties dbProperties=new Properties();
		try (InputStream input = new FileInputStream(pathToConnectionInfo)) {
			dbProperties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		Properties Hibernateprop= new Properties();
		//generic form: jdbc:mysql://<hostname>:<port>/dbname
		Hibernateprop.setProperty("hibernate.connection.url", dbProperties.getProperty("url"));
        //You can use any database you want, I had it configured for Postgres
		Hibernateprop.setProperty("dialect", "org.hibernate.dialect.MySQ8LDialect");
		Hibernateprop.setProperty("hibernate.connection.username", dbProperties.getProperty("username"));
		Hibernateprop.setProperty("hibernate.connection.password", dbProperties.getProperty("password"));
		Hibernateprop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		Hibernateprop.setProperty("hibernate.hbm2ddl.auto","update");
		Hibernateprop.setProperty("show_sql", "true"); //If you wish to see the generated sql query
		
		Configuration config=new Configuration().setProperties(Hibernateprop);
		this.sessionFactory = config.addResource(pathToMapper).buildSessionFactory();
	}
	public void createTableandInsert(int studentID) {
		Session session=this.sessionFactory.openSession();
		Student firstStudent=new Student(Integer.toString(studentID));
		Submission submission1=new Submission(Integer.toString(studentID*2-1), firstStudent, "answer1");
		Submission submission2=new Submission(Integer.toString(studentID*2), firstStudent, "answer2");
		Grade grade1=new Grade(Integer.toString(studentID*3-2), "1", 90, submission1);
		Grade grade2=new Grade(Integer.toString(studentID*3-1), "2", 95, submission1);
		Grade grade3=new Grade(Integer.toString(studentID*3), "1", 91, submission2);
		Project project1=new Project(Integer.toString(studentID*2-1), firstStudent, "project1");
		Project project2=new Project(Integer.toString(studentID*2), firstStudent, "project2");
		Presentation presentation1=new Presentation(Integer.toString(studentID*3-2), 15, project1);
		Presentation presentation2=new Presentation(Integer.toString(studentID*3-1), 12, project2);
		Presentation presentation3=new Presentation(Integer.toString(studentID*3), 10, project2);
		
		
		session.beginTransaction();
		session.save(firstStudent);
		session.save(submission1);
		session.save(submission2);
		session.save(grade1);
		session.save(grade2);
		session.save(grade3);
		session.save(project1);
		session.save(project2);
		session.save(presentation1);
		session.save(presentation2);
		session.save(presentation3);
		session.getTransaction().commit();
	}
	public Session getSession() {
		return this.sessionFactory.openSession();
	}
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
}
