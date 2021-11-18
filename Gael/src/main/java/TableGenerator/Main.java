package TableGenerator;

import org.hibernate.Session;


public class Main {
	public static void main(String[] args) {
		String pathToProperties=System.getProperty("user.dir")+"/src/main/java/TableGenerator/db.properties";
		TableGenerator tableGenerator=new TableGenerator(pathToProperties);
		Session session=tableGenerator.getSession();
		//CreateTableAndInsert.createTableandInsert(session);
		QueryData.query(session);
		session.close();
	}
}
