package com.gael4j.DAO;

import java.util.List;

public interface DAOManager {
	public List<Object> query(String id);
	public void delete(String id);
}
