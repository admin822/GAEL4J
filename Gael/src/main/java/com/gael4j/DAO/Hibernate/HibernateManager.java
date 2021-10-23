package com.gael4j.DAO.Hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Util.JPAUtils;


public class HibernateManager {
    /***
     * Using hibernate to query all filed in the table with given userid.
     * @param tableList List of table configuration that are marked as private.
     */
    public static void query(List<DBConfig> tableList, Long id) {
        EntityManager manager = JPAUtils.getEntityManger();
            String queryString = "from " + tableConfig.getClassName() + " Where id= :userid";
            Query query = manager.createQuery(queryString);
            query.setParameter("userid", id);
            List<?> res = query.getResultList();
            for (Object item : res) {
                System.out.println(item.toString());
            }
        }
}
