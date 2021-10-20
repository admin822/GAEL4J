package edu.brown.cs2390.util;

import java.util.List;
import java.util.Map;

/***
 *
 * @author Hugo Huang
 * Data class that used to aggregate data that collected in Controller.
 */
public class TableConfigDataClass {

    private final String tableName;
    private final String[] privateFieldList;
    private final Map<String, String> variableNameToColumnName;

    public TableConfigDataClass(String tableName, String[] privateFieldList, Map<String,String> variableNameToColumnName) {
        this.tableName = tableName;
        this.privateFieldList = privateFieldList;
        this.variableNameToColumnName = variableNameToColumnName;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, String> getVariableNameToColumnName() {
        return variableNameToColumnName;
    }

    public String getColumnName(String fieldName) {
        return variableNameToColumnName.get(fieldName);
    }

    public String[] getPrivateFieldList() {
        return privateFieldList;
    }
}
