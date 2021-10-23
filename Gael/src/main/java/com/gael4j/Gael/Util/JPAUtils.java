package com.gael4j.Gael.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
    public static EntityManagerFactory emf = createEntityManagerFactory();

    private static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql-2390finalsample");
        return emf;
    }

    public static EntityManager getEntityManger(){
        EntityManager entityManager = emf.createEntityManager();
        return entityManager;
    }
}
