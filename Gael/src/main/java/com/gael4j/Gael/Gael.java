package com.gael4j.Gael;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice.Info;

import com.gael4j.Gael.Util.JPAUtils;

import net.bytebuddy.asm.Advice.This;

import org.hibernate.query.criteria.internal.expression.function.LengthFunction;

import com.gael4j.DAO.DAOManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateManager;
import com.gael4j.DAO.NonJPA.Hibernate.HibernateMappingFileGenerator;
import com.gael4j.DAO.JPA.Hibernate.JPAHibernateManager;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;
import com.gael4j.Gael.AnnotationProcessing.NonJPA.ReflectionProcessing;

public class Gael {
	Map<Class<?>,Set<Class<?>>> directedTableGraph;
	boolean useJPA;
	DAOManager daoManager;
	final String NONJPA_RSC_PATH="./target/classes/mappers.hbm.xml";

	public void delete(Class<?> entityClass, String primaryKeyValue) {
		daoManager.delete(entityClass, primaryKeyValue);
	}
	public List<Object> query(Class<?> entityClass, String primaryKeyValue){
		return daoManager.query(entityClass, primaryKeyValue);
	}
	
	private Gael(String packageScanPath,boolean useJPA,String JPAPersistenceUnitName,String pathToMapperFiles) {
		this.useJPA=useJPA;
		if(this.useJPA) {
			JPAUtils.persistenceUnitName = JPAPersistenceUnitName;
			//TODO: get directedTableGraph
		} else {
			//TODO: get directedTableGraph
		}
		if (useJPA) {
			//TODO: get daoManager
		} else {
			//TODO: get daoManager
		}
	}

}
