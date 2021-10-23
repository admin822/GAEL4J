package com.gael4j.Gael;
import java.util.List;
import java.util.Map;

import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;
import com.gael4j.Gael.AnnotationProcessing.NonJPA.ReflectionProcessing;

public class Gael {
	List<DBConfig> DBConfigList;
	boolean useJPA;
	/** THIS IS A TEST FUNCTION, DELETE IT BEFORE PUBLISH!
	 * this function prints all entries in the privateInfoMap to the console
	 */
	//#####################################
	private void testFunction() {
		for(DBConfig config:DBConfigList) {
			System.out.println(config.toString());
		}
	}
	//#####################################
	
	public Gael(String packageScanPath,boolean useJPA) {
		this.useJPA=useJPA;
		if(this.useJPA) {
			DBConfigList=Controller.scan(packageScanPath);
		}
		else {
			DBConfigList=ReflectionProcessing.reflectionProcessing(packageScanPath);
		}
	}
}
