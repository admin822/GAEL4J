package com.gael4j.NonJPADemo;

import java.util.List;

import com.gael4j.Gael.Gael;
import com.gael4j.NonJPADemo.Entity.Student;
import com.gael4j.NonJPADemo.Entity.TableGenerator;

/**
 * Hello world!
 *
 */
public class App 
{
	private static int STUDENT_ID=10;
    public static void main( String[] args )
    {
//        TableGenerator tg=new TableGenerator("db.properties", "mappers.hbm.xml");
//        tg.createTableandInsert(STUDENT_ID);
        Gael gael=new Gael("com.gael4j.NonJPADemo.Entity", false,null,System.getProperty("user.dir"));
        List<Object> result=gael.query(Student.class, Integer.toString(STUDENT_ID));
        if(result.isEmpty()) {
			System.out.println("No row matches the given id");
		}
		else {
			for(Object obj:result) {
				System.out.println(obj.toString());
			}
		}
        gael.delete(Student.class, Integer.toString(STUDENT_ID));
		if(gael.query(Student.class, Integer.toString(STUDENT_ID)).isEmpty()) {
			System.out.println("successfully deleted");
		}
    }
}
