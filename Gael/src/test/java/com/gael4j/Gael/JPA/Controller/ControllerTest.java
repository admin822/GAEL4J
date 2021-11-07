package com.gael4j.Gael.JPA.Controller;

import com.gael4j.DAO.DAOManager;
import org.junit.Test;

import com.gael4j.DAO.JPA.Hibernate.JPAHibernateManager;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;
import com.gael4j.Entity.SampleUser;
import com.gael4j.Gael.Util.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/***
 *
 * @author Hugo Huang
 * * This is a unit test class for Controller class.
 */
public class ControllerTest {
    private final String packagePrefix = "com.gael4j";

    @Test
    public void TestRun(){
        List<DBConfig> tableList = Controller.scan(packagePrefix);

        for (DBConfig tableConfigDataClass : tableList) {
            System.out.println(tableConfigDataClass);
        }
    }
    @Test
    public void query() {
        EntityManager manager = JPAUtils.getEntityManger();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        SampleUser user = new SampleUser();
        user.setAge(12);
        user.setName("Hugo Huang");
        manager.persist(user);

        SampleUser user2 = new SampleUser();
        user2.setAge(21);
        user2.setName("QN Huang");
        manager.persist(user2);
        transaction.commit();

        manager.close();
        List<DBConfig> tableList = Controller.scan(packagePrefix);
        DAOManager daoManager = new JPAHibernateManager();
        Object queryResults = daoManager.query(tableList.get(0), "1");
        System.out.println(queryResults.toString());
    }
}