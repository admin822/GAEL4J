package com.gael4j.Gael.NonJPA.mainTest;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.gael4j.Gael.Gael;

public class QueryTest {
	@Test
	void initializationTest() {
		Gael gaelIns1=new Gael("com.gael4j.Gael.NonJPA.Entity", false);
		List<Object> list=gaelIns1.query("1");
		for(Object obj:list) {
			System.out.println(obj.toString());
		}
	}
}
