package TableGenerator;

import org.hibernate.Session;

import TableGenerator.Entities.Student;
import TableGenerator.Entities.Submission;


public class Main {
	public static void main(String[] args) {
		String pathToProperties="./target/classes/db.properties";
		TableGenerator tableGenerator=new TableGenerator(pathToProperties);
		Session session=tableGenerator.getSession();
		System.out.println(session.load(Student.class, "2"));;
		//CreateTableAndInsert.createTableandInsert(session);
//		QueryData.query(session);
//		DeleteData.delete(session, Student.class, "1");
		session.close();
	}
}
