package com.gael4j.Gael.JPA.Controller;

import org.junit.Test;

import com.gael4j.DAO.Hibernate.HibernateManager;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;
import com.gael4j.Gael.JPA.Entity.SampleUser;
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

    @Test
    public void TestRun(){
        List<DBConfig> tableList = Controller.scan("edu.brown.cs2390");
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
        user.setUserName("Hugo Huang");
        manager.persist(user);

        SampleUser user2 = new SampleUser();
        user2.setAge(21);
        user2.setUserName("QN Huang");
        manager.persist(user2);
        transaction.commit();

        manager.close();
        List<DBConfig> tableList = Controller.scan("edu.brown.cs2390");
        HibernateManager.query(tableList, (long) 1);
    }
}