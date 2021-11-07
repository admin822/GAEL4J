package com.gael4j.Gael.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
    // TODO: change this at instance initialization.
    public static String persistenceUnitName = "mysql-2390finalsample";
    public static EntityManagerFactory emf = createEntityManagerFactory();

    private static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        return emf;
    }

    public static EntityManager getEntityManger(){
        EntityManager entityManager = emf.createEntityManager();
        return entityManager;
    }
}
