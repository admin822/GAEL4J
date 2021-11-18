package TableGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class TableGenerator {
	private SessionFactory sessionFactory;
	public TableGenerator(String pathToConnectionInfo) {
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
		this.sessionFactory = config.addResource("levelOne.hbm.xml")
									.addResource("levelTwo.hbm.xml")
									.addResource("levelThree.hbm.xml")
									.buildSessionFactory();
	}
	public Session getSession() {
		return this.sessionFactory.openSession();
	}
}
