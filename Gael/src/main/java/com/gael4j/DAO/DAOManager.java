package com.gael4j.DAO;

import java.util.List;


public interface DAOManager {
	public List<Object> query(Class<?> entityClass, String primaryKeyValue);
	public void delete(Class<?> entityClass, String primaryKeyValue);
}
