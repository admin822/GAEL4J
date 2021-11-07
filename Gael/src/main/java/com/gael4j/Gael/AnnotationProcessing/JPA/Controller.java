package com.gael4j.Gael.AnnotationProcessing.JPA;

import static org.reflections.scanners.Scanners.TypesAnnotated;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.reflections.Reflections;

import com.gael4j.Entity.DBConfig;
import com.gael4j.Gael.Annotations.PrivateData;


public class Controller {
    /***
     * Function scan all classes with the 'packagePrefix' and aggregate those
     * classes with our custom annotation.
     *
     * @param packagePrefix Prefix indicate the scope of which package should be scan.
     * @return A list of TableConfigDataClass that contains information about table name and
     *         field names with its column name.
     */
    public static List<DBConfig> scan(String packagePrefix) {
        Reflections reflections = new Reflections(packagePrefix);
        Set<Class<?>> personalClasses = reflections.get(TypesAnnotated.with(PrivateData.class).asClass());
        List<DBConfig> tableList = new ArrayList<>();
        List<String> fieldList = new ArrayList<>();
        List<String> columnList = new ArrayList<>();

        for (Class<?> personalClass: personalClasses) {
            PrivateData privateDataAnnotation = personalClass.getAnnotation(PrivateData.class);
            String databaseName = privateDataAnnotation.schema();
            String primaryKey = privateDataAnnotation.primaryKey();
            Table tableAnnotation = personalClass.getAnnotation(Table.class);
            String tableName = tableAnnotation.name();

            Field[] fields = personalClass.getDeclaredFields();
            for (Field field: fields) {
                if (primaryKey.equals("")) {
                    if (field.isAnnotationPresent(GeneratedValue.class)) {
                        primaryKey = field.getName();
                    }
                }
                Column columnAnnotation = field.getAnnotation(Column.class);
                fieldList.add(field.getName());
                if (columnAnnotation != null) {
                    columnList.add(columnAnnotation.name());
                } else {
                    columnList.add(field.getName());
                }
            }
            tableList.add(new DBConfig(databaseName, tableName, personalClass.getName(), primaryKey, fieldList, columnList));
        }
        return tableList;
    }
}