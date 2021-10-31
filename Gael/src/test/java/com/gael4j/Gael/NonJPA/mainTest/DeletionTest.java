package com.gael4j.Gael.NonJPA.mainTest;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.gael4j.Gael.Gael;

public class DeletionTest {
	@Test
	void deletionTest() {
		Gael gaelIns1=new Gael("com.gael4j.Gael.NonJPA.Entity", false);
		gaelIns1.delete("2");
	}
}
