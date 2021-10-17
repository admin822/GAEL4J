package com.gael4j.Gael;
import java.util.Map;

import com.gael4j.Gael.AnnotationProcessing.ReflectionProcessing;
import com.gael4j.Gael.AnnotationProcessing.usernameProcessing;
import com.gael4j.Gael.Model.DatabaseConfig;

public class Gael {
	Map<String, DatabaseConfig> privateInfoMap;
	
	/** THIS IS A TEST FUNCTION, DELETE IT BEFORE PUBLISH!
	 * this function prints all entries in the privateInfoMap to the console
	 */
	//#####################################
	private void testFunction() {
		for(String key:privateInfoMap.keySet()) {
			System.out.println("For class "+key+" , its DB configuration is as follows:");
			System.out.println("    "+privateInfoMap.get(key).toString());
		}
	}
	//#####################################
	
	public Gael(String packageScanPath) {
		privateInfoMap=ReflectionProcessing.reflectionProcessing(packageScanPath);
		testFunction();
	}
}
