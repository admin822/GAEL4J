package com.gael4j.Gael.NonJPA.mainTest;

import org.junit.jupiter.api.Test;

import com.gael4j.Gael.Gael;

public class InitTest {
	@Test
	void initializationTest() {
		Gael gaelIns1=new Gael("com.gael4j.Gael.NonJPA.Entity", false);
		gaelIns1.testFunction();
		System.out.flush();
	}
}