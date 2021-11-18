package TableGenerator;

import org.hibernate.Session;

import TableGenerator.Entities.Grade;
import TableGenerator.Entities.Presentation;
import TableGenerator.Entities.Project;
import TableGenerator.Entities.Student;
import TableGenerator.Entities.Submission;

public class CreateTableAndInsert {
	public static void createTableandInsert(Session session) {
		Student firstStudent=new Student("1");
		Submission submission1=new Submission("1", firstStudent, "answer1");
		Submission submission2=new Submission("2", firstStudent, "answer2");
		Grade grade1=new Grade("1", "1", 90, submission1);
		Grade grade2=new Grade("2", "2", 95, submission1);
		Grade grade3=new Grade("3", "1", 91, submission2);
		Project project1=new Project("1", firstStudent, "project1");
		Project project2=new Project("2", firstStudent, "project2");
		Presentation presentation1=new Presentation("1", 15, project1);
		Presentation presentation2=new Presentation("2", 12, project2);
		Presentation presentation3=new Presentation("3", 10, project2);
		
		
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
}
