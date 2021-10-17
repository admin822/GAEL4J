package com.gael4j.Gael.Model;

import java.util.List;

public class DatabaseConfig {
	private String databaseName;
	private String tableName;
	private List<String> columns;
	public DatabaseConfig(String databaseName, String tableName, List<String> columns) {
		super();
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.columns = columns;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	@Override
	public String toString() {
		return "DatabaseConfig [databaseName=" + databaseName + ", tableName=" + tableName + ", columns=" + columns
				+ "]";
	}
	
}	
