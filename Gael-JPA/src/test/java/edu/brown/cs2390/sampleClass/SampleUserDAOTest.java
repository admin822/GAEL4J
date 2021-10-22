package edu.brown.cs2390.sampleClass;

import edu.brown.cs2390.util.JPAUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SampleUserDAOTest {

    @Test
    public void persist() {
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
    }
}