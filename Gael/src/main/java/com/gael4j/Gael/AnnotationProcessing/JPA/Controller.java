package com.gael4j.Gael.AnnotationProcessing.JPA;

import static org.reflections.scanners.Scanners.TypesAnnotated;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import javax.persistence.*;

import com.gael4j.Entity.ChildNode;
import com.gael4j.Entity.DBConfig;
import org.reflections.Reflections;

import com.gael4j.Gael.Annotations.PrivateData;


public class Controller {
    /***
     * Function scan all classes with the 'packagePrefix' and aggregate those
     * classes with our custom annotation.
     *
     * @param packagePrefix Prefix indicate the scope of which package should be scan.
     * @return FKConnections is a map representing relations between table (one-to-many; many-to-one; one-to-one).
     *          The key is the "one" side; The value is a list of "many" side.
     */
    public static Map<Class<?>, Set<ChildNode>> scan(String packagePrefix) {
        Reflections reflections = new Reflections(packagePrefix);
        Set<Class<?>> personalClasses = reflections.get(TypesAnnotated.with(PrivateData.class).asClass());
        Map<Class<?>, Set<ChildNode>> FKConnections=new HashMap<>();

        for (Class<?> personalClass: personalClasses) {
            Field[] fields = personalClass.getDeclaredFields();
            for (Field field: fields) {
                if (field.isAnnotationPresent(ManyToOne.class)) {

                    if (!FKConnections.containsKey(field.getType())) {
                        FKConnections.put(field.getType(), new HashSet<>());
                    }
                    Set<ChildNode> childNodes = FKConnections.get(field.getType());
                    checkNode: {
                        for (ChildNode childNode: childNodes) {
                            if (childNode.getNodeClass() == personalClass){
                                childNode.setBidirectional(true);
                                childNode.setTargetFieldName(field.getName());
                                break checkNode;
                            }
                        }
                        ChildNode curChildNode = new ChildNode();
                        curChildNode.setNodeClass(personalClass);
                        curChildNode.setTargetFieldName(field.getName());
                        childNodes.add(curChildNode);
                    }
                } else if (field.isAnnotationPresent(OneToMany.class)) {
                    if (!FKConnections.containsKey(personalClass)) {
                        FKConnections.put(personalClass, new HashSet<>());
                    }
                    Set<ChildNode> childNodeSet = FKConnections.get(personalClass);
                    checkNode: {
                        Type type = field.getGenericType();
                        Type[] genericArguments = ((ParameterizedType) type).getActualTypeArguments();
                        Class fieldClass = (Class) genericArguments[0];
                        for (ChildNode childNode : childNodeSet) {
                            if (childNode.getNodeClass() == fieldClass) {
                                childNode.setBidirectional(true);
                                break checkNode;
                            }
                        }
                        ChildNode curChildNode = new ChildNode();
                        curChildNode.setNodeClass(fieldClass);
                        curChildNode.setBidirectional(true);
                        childNodeSet.add(curChildNode);
                    }
                } else if (field.isAnnotationPresent(ManyToMany.class)) {
                    // TODO: more research on this.
                    continue;
                }
            }
        }
        return FKConnections;
    }

    public static Map<String, DBConfig> constructDBConfigMap(String packagePrefix) {
        Reflections reflections = new Reflections(packagePrefix);
        Set<Class<?>> personalClasses = reflections.get(TypesAnnotated.with(PrivateData.class).asClass());
        Map<String, DBConfig> tableMap = new HashMap<>();
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
            tableMap.put(personalClass.getSimpleName(),
                    new DBConfig(databaseName, tableName, personalClass.getName(), primaryKey, fieldList, columnList));
        }
        return tableMap;
    }
}