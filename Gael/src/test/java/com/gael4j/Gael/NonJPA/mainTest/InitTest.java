package com.gael4j.Gael.NonJPA.mainTest;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.gael4j.Gael.Gael;

public class InitTest {
	@Test
	void initializationTest() {
		Gael gaelIns1=new Gael("TableGenerator.Entities", false,null,"D:\\new_repos\\EclipeseWorkspace\\GAEL4J\\Gael\\target\\classes");
		gaelIns1.test1();
		gaelIns1.test2();
		
	}
}
