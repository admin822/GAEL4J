package com.gael4j.Entity;

import java.util.List;

public class DBConfig {
	private final String databaseName; 
	private final String tableName;
    private final String className; 
    private final String primaryKey;
    private final List<String> fieldList; // names of all included fields in the class
	private final List<String> columns; // names of all columns that correspond to the fields
	public DBConfig(String databaseName, String tableName, String className, String primaryKey, List<String> fieldList,
			List<String> columns) {
		super();
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.className = className;
		this.primaryKey = primaryKey;
		this.fieldList = fieldList;
		this.columns = columns;
	}
	
	public String getPrimaryKey() {
		return primaryKey;
	}

	public String getDatabaseName() {
		return databaseName;
	}
	public String getTableName() {
		return tableName;
	}
	public String getClassName() {
		return className;
	}
	public List<String> getfieldList() {
		return fieldList;
	}
	public List<String> getColumns() {
		return columns;
	}

	@Override
	public String toString() {
		return "DBConfig [databaseName=" + databaseName + ", tableName=" + tableName + ", className=" + className
				+ ", primaryKey=" + primaryKey + ", fieldList=" + fieldList + ", columns=" + columns + "]";
	}
	
    
	
	
}	
