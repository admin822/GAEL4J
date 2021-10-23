package com.gael4j.Gael.AnnotationProcessing.JPA;

import static org.reflections.scanners.Scanners.TypesAnnotated;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;

import org.reflections.Reflections;
import org.reflections.Reflections.*;

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

        for (Class<?> personalClass: personalClasses) {
            PrivateData privateDataAnnotation = personalClass.getAnnotation(PrivateData.class);
            String[] privateFiledList = privateDataAnnotation.column();
            Table tableAnnotation = personalClass.getAnnotation(Table.class); // FIXME
            String tableName = tableAnnotation.name();
            Map<String, String> variableNameToColumnName = new HashMap<>();

            Field[] fields = personalClass.getDeclaredFields();
            for (Field field: fields) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                variableNameToColumnName.put(field.getName(), columnAnnotation.name()); // FIXME
            }
            tableList.add(new DBConfig(tableName, personalClass.getName(), privateFiledList, variableNameToColumnName));
        }
        return tableList;
    }
}