package com.gael4j.Gael.JPA.Sample;

import org.junit.Test;

import com.gael4j.Entity.SampleUser;
import com.gael4j.Gael.Util.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/***
 * Create database using hibernate for testing.
 */
public class SampleUserDAOTest {

    @Test
    public void persist() {
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
    }
}
