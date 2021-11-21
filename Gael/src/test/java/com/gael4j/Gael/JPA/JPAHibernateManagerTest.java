package com.gael4j.Gael.JPA;

import TableGenerator.JPAEntities.*;
import com.gael4j.DAO.DAOManager;
import com.gael4j.DAO.JPA.Hibernate.JPAHibernateManager;
import com.gael4j.Entity.ChildNode;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static TableGenerator.JPAEntities.TableGenerator.createTableAndInsertJPA;

public class JPAHibernateManagerTest {

    private DAOManager daoManager;

    @BeforeClass
    public static void init() {
        createTableAndInsertJPA();
    }

    @Before
    public void setUp() {
        String packagePrefix = "TableGenerator.JPAEntities";
        Map<Class<?>, Set<ChildNode>> FKConnections = Controller.scan(packagePrefix);
        Map<String, DBConfig> name2dbConfigMap = Controller.constructDBConfigMap(packagePrefix);
        daoManager = new JPAHibernateManager(FKConnections, name2dbConfigMap);
    }

    @Test
    public void testQuery() {
        List<Object> result = daoManager.query(Student.class, "1");
        result.forEach(System.out::println);
    }

    @Test
    public void testDelete() {
        daoManager.delete(Student.class, "1");
    }
}