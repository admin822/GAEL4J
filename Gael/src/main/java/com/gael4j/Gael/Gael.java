package com.gael4j.Gael;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice.Info;

import com.gael4j.Gael.Util.JPAUtils;
import org.hibernate.query.criteria.internal.expression.function.LengthFunction;

import com.gael4j.DAO.DAOManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateMappingFileGenerator;
import com.gael4j.DAO.JPA.Hibernate.JPAHibernateManager;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;
import com.gael4j.Gael.AnnotationProcessing.NonJPA.ReflectionProcessing;

public class Gael {
	List<DBConfig> DBConfigList;
	Map<String, DBConfig> tableName2DBConfig;
	boolean useJPA;
	DAOManager daoManager;
	final String NONJPA_RSC_PATH="./target/classes/mappers.hbm.xml";
	/** THIS IS A TEST FUNCTION, DELETE IT BEFORE PUBLISH!
	 * this function prints all entries in the privateInfoMap to the console
	 */
	//#####################################
	public void testFunction() {
		for(DBConfig config:DBConfigList) {
			System.out.println(config.toString());
		}
		HibernateMappingFileGenerator.generateMappers(DBConfigList,NONJPA_RSC_PATH);
	}
	public void testHibernateManagerInit() {
		this.daoManager=new HibernateManager(this.DBConfigList,NONJPA_RSC_PATH);
	}
	//#####################################

	public void delete(String tableName, String primaryKeyValue) {
		daoManager.delete(tableName2DBConfig.get(tableName), primaryKeyValue);
	}
	public List<Object> query(String tableName, String primaryKeyValue){
		return daoManager.query(tableName2DBConfig.get(tableName), primaryKeyValue);
	}
	
	public Gael(String packageScanPath,boolean useJPA) {
		this.useJPA=useJPA;
		List<DBConfig> dbConfigList;
		if(this.useJPA) {
			dbConfigList=Controller.scan(packageScanPath);
		} else {
			dbConfigList=ReflectionProcessing.reflectionProcessing(packageScanPath);
		}
		if (dbConfigList != null) {
			tableName2DBConfig = dbConfigList.stream().collect(
					Collectors.toMap(DBConfig::getClassName, dbConfig->dbConfig));
		}
		if (useJPA) {
			daoManager = new JPAHibernateManager();
		} else {
			daoManager = new HibernateManager(dbConfigList, NONJPA_RSC_PATH);
		}
	}

	public Gael(String packageScanPath,boolean useJPA, String JPAPersistenceUnitName) {
		this(packageScanPath, useJPA);
		JPAUtils.persistenceUnitName = JPAPersistenceUnitName;
	}
}
