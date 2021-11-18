package TableGenerator;

import org.hibernate.Session;

import TableGenerator.Entities.Student;


public class Main {
	public static void main(String[] args) {
		String pathToProperties="./target/classes/db.properties";
		TableGenerator tableGenerator=new TableGenerator(pathToProperties);

		
//		Session session=tableGenerator.getSession();
		//CreateTableAndInsert.createTableandInsert(session);
//		QueryData.query(session);
//		session.close();
	}
}
