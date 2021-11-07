package com.gael4j.DAO;

import com.gael4j.Entity.DBConfig;

import java.util.List;

import com.gael4j.Entity.DBConfig;

public interface DAOManager {
	public List<Object> query(DBConfig dbConfig, String primaryKeyValue);
	public void delete(DBConfig dbConfig, String primaryKeyValue);
}
