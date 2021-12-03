package com.gael4j.Gael.NonJPA.mainTest;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gael4j.Entity.ChildNode;
import com.gael4j.Gael.Gael;

public class InitializationTest {
	// This test case will always pass as long as the gael instance can be initialized
	protected Gael gaelIns1=new Gael("com.gael4j.TableGenerator.Entities", false,null,System.getProperty("user.dir"));
	@Test
	public void test1() {
		System.out.println("================== RUNNING GAEL TEST1 ==================");
		Map<Class<?>,Set<ChildNode>> directedGraph=gaelIns1.getDirectedGraph();
		for(Class<?> c:directedGraph.keySet()) {
			System.out.println("Current Class is "+c.getName());
			for(ChildNode cn:directedGraph.get(c)) {
				System.out.println("\tchild class: "+cn.getNodeClass().getName()+"\tis bidirectional: "+cn.isBidirectional());
			}
		}
		return;
	}
	@Test
	public void test2() {
		System.out.println("================== RUNNING GAEL TEST2 ==================");
		Map<Class<?>, Map<Class<?>, String>> ForeignKeyMap=gaelIns1.getForeignKeyMap();
		for(Class<?> c:ForeignKeyMap.keySet()) {
			System.out.println("Child Class is "+c.getName());
			Map<Class<?>, String> parentClass2Keys=ForeignKeyMap.get(c);
			for(Class<?> parentClass:parentClass2Keys.keySet()) {
				System.out.println("\t parent class is "+parentClass.getName()+"\tforeign key is "+parentClass2Keys.get(parentClass));
			}
		}
		return;
	}

}
