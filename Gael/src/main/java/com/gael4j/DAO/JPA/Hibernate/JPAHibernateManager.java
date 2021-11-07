package com.gael4j.DAO.JPA.Hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gael4j.DAO.DAOManager;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Util.JPAUtils;


public class JPAHibernateManager implements DAOManager{

    public JPAHibernateManager() {}

    public List<Object> query(DBConfig dbConfig, String primaryKeyValue) {
        EntityManager manager = JPAUtils.getEntityManger();
        String queryString = "FROM " + dbConfig.getClassName() + " WHERE "+ dbConfig.getPrimaryKey() + "= :primaryKey";
        Query query = manager.createQuery(queryString);
        query.setParameter("primaryKey", primaryKeyValue);
        return query.getResultList();
    }

    @Override
    public void delete(DBConfig dbConfig, String primaryKeyValue) {
        EntityManager manager = JPAUtils.getEntityManger();
        String queryString = "DELETE FROM " + dbConfig.getTableName() + " WHERE " + dbConfig.getPrimaryKey() + "=:primaryKey";
        Query query = manager.createQuery(queryString);
        query.setParameter("primaryKey", primaryKeyValue);
    }
}
