package com.gael4j.Gael.NonJPA.mainTest;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.gael4j.Gael.Gael;

import TableGenerator.Entities.Student;

public class QueryTest {
	@Test
	void queryTest() {
		Gael gaelIns1=new Gael("TableGenerator.Entities", false,null,"D:\\new_repos\\EclipeseWorkspace\\GAEL4J\\Gael\\target\\classes");
		gaelIns1.test1();
		gaelIns1.test2();
		List<Object> result=gaelIns1.query(Student.class, "2");
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
}
