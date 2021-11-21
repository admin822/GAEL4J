package com.gael4j.DAO.JPA.Hibernate;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.gael4j.DAO.DAOManager;
import com.gael4j.Entity.ChildNode;
import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Util.JPAUtils;


public class JPAHibernateManager implements DAOManager{

    Map<Class<?>, Set<ChildNode>> directedTableGraph;
    Map<String, DBConfig> name2dbConfigMap;
    public JPAHibernateManager(Map<Class<?>, Set<ChildNode>> directedTableGraph, Map<String, DBConfig> name2dbConfigMap) {
        this.directedTableGraph = directedTableGraph;
        this.name2dbConfigMap = name2dbConfigMap;
    }

    @Override
    public List<Object> query(Class<?> entityClass, String primaryKeyValue) {
        EntityManager manager = JPAUtils.getEntityManger();
        Object instance = manager.find(entityClass, primaryKeyValue);
        List<Object> result = new ArrayList<>();
        result.add(instance);
        for (ChildNode childNode: directedTableGraph.getOrDefault(entityClass, new HashSet<>())) {
            String primaryKeyName = name2dbConfigMap.get(entityClass.getSimpleName()).getPrimaryKey();
            List childResult = recursiveQuery(childNode.getNodeClass(), childNode.getTargetFieldName(), primaryKeyName, Collections.singletonList(primaryKeyValue));
            result.addAll(childResult);
        }
        manager.close();
        return result;
    }

    private List<Object> recursiveQuery(Class<?> curClass, String targetField, String foreignKeyName, List<String> foreignKeyValues) {
        EntityManager manager = JPAUtils.getEntityManger();
        String queryString = "FROM " + curClass.getSimpleName() + " t WHERE t." + targetField + "." + foreignKeyName + " IN (:foreignKeyValues)";
        Query query = manager.createQuery(queryString);
        query.setParameter("foreignKeyValues", foreignKeyValues);
        List result = query.getResultList();

        if (directedTableGraph.containsKey(curClass)) {
            String primaryKeyName = name2dbConfigMap.get(curClass.getSimpleName()).getPrimaryKey();
            String primaryKeyQueryString = "SELECT t." + primaryKeyName + " FROM " + curClass.getSimpleName() +
                    " t WHERE t." +  targetField + "." + foreignKeyName + " IN (:foreignKeyValues)";
            Query primaryKeyQuery = manager.createQuery(primaryKeyQueryString);
            primaryKeyQuery.setParameter("foreignKeyValues", foreignKeyValues);
            List primaryKeyValues = primaryKeyQuery.getResultList();
            for(ChildNode childNode: directedTableGraph.get(curClass)) {
                List childResult = recursiveQuery(childNode.getNodeClass(), childNode.getTargetFieldName(), primaryKeyName, primaryKeyValues);
                result.addAll(childResult);
            }
        }
        manager.close();
        return result;
    }

    @Override
    public void delete(Class<?> entityClass, String primaryKeyValue) {
        EntityManager manager = JPAUtils.getEntityManger();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        List<Object> instances = query(entityClass, primaryKeyValue);
        for (Object object: instances) {
            manager.remove(manager.contains(object) ? object : manager.merge(object));
        }
        transaction.commit();
    }

}
