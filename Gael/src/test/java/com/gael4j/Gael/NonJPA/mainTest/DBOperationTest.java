package com.gael4j.Gael.NonJPA.mainTest;



import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gael4j.Gael.Gael;
import com.gael4j.TableGenerator.Entities.Student;
import com.gael4j.TableGenerator.Entities.TableGenerator;

public class DBOperationTest {
	protected Gael gaelIns2=new Gael("com.gael4j.TableGenerator.Entities", false,null,System.getProperty("user.dir"));
	static protected int studentID=8; // !!!! this has to be incremented every time this test suite  is run
	@BeforeClass
	public static void setUp() throws Exception {
		TableGenerator tg=new TableGenerator("db.properties", "mappers.hbm.xml");
		tg.createTableandInsert(studentID); // this has to be incremented every time this test suite  is run
	}
	@Test
	public void queryTest() {
		List<Object> result=gaelIns2.query(Student.class, Integer.toString(studentID));
		System.out.println("================== RUNNING GAEL TEST3 ==================");
		if(result.isEmpty()) {
			System.out.println("No row matches the given id");
		}
		else {
			for(Object obj:result) {
				System.out.println(obj.toString());
			}
		}
	}
	@Test
	public void deleteTest() {
		gaelIns2.delete(Student.class, Integer.toString(studentID));
		System.out.println("================== RUNNING GAEL TEST4 ==================");
		if(gaelIns2.query(Student.class, Integer.toString(studentID)).isEmpty()) {
			System.out.println("TEST 4 PASSED");
		}
	}


}
