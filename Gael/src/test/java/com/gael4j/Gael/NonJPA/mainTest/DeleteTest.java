package com.gael4j.Gael.NonJPA.mainTest;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.gael4j.Gael.Gael;

import TableGenerator.Entities.Student;

public class DeleteTest {
	@Test
	void deleteTest() {
		Gael gaelIns1=new Gael("TableGenerator.Entities", false,null,"D:\\new_repos\\EclipeseWorkspace\\GAEL4J\\Gael\\target\\classes");
		gaelIns1.test1();
		gaelIns1.test2();
		gaelIns1.delete(Student.class, "1");
		System.out.println("================== RUNNING GAEL TEST4 ==================");
		if(gaelIns1.query(Student.class, "1").isEmpty()) {
			System.out.println("TEST 4 PASSED");
		}
	}
}
