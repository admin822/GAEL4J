package com.gael4j.Gael;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.MidiDevice.Info;

import org.hibernate.query.criteria.internal.expression.function.LengthFunction;

import com.gael4j.DAO.DAOManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateMappingFileGenerator;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;
import com.gael4j.Gael.AnnotationProcessing.NonJPA.ReflectionProcessing;

public class Gael {
	List<DBConfig> DBConfigList;
	boolean useJPA;
	DAOManager daoManager=null;
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
	
	private List<Object> JPAQuery(){
		//TODO
		return new ArrayList<>();
	}
	private List<Object> nonJpaQuery(String id){
		if(daoManager==null) {
			this.daoManager=new HibernateManager(this.DBConfigList,NONJPA_RSC_PATH);
		}
		return daoManager.query(id);
	}
	public List<Object> query(String id){
		if(useJPA) {
			return JPAQuery();
		}
		return nonJpaQuery(id);
	}
	
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
