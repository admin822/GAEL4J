package com.gael4j.DAO.Hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Util.JPAUtils;


public class HibernateManager {
    /***
     * Using hibernate to query all filed in the table with given userid.
     * @param tableList List of table configuration that are marked as private.
     * @return List of result from query with given id.
     */
    public static List<Object> query(List<DBConfig> tableList, Long id) {
        EntityManager manager = JPAUtils.getEntityManger();
        List<Object> queryResult = new ArrayList<>();
        for (DBConfig dbConfig: tableList) {
            String queryString = "FROM " + dbConfig.getClassName() + " WHERE id= :userid";
            Query query = manager.createQuery(queryString);
            query.setParameter("userid", id);
            List<?> curResult = query.getResultList();
            queryResult.addAll(curResult);
        }
        return queryResult;
    }
}
