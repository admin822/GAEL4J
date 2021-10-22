package edu.brown.cs2390.controller;

import edu.brown.cs2390.annotations.PrivateData;
import edu.brown.cs2390.util.JPAUtils;
import edu.brown.cs2390.util.TableConfigDataClass;
import org.reflections.Reflections;

import javax.persistence.*;

import static org.reflections.scanners.Scanners.*;

import java.lang.reflect.Field;
import java.util.*;

/***
 *
 * @author Hugo Huang
 * * Controller class contains functions that cooperate with our own
 *   custom annotation.
 */
public class Controller {

    public Controller() {}

    /***
     * Function scan all classes with the 'packagePrefix' and aggregate those
     * classes with our custom annotation.
     *
     * @param packagePrefix Prefix indicate the scope of which package should be scan.
     * @return A list of TableConfigDataClass that contains information about table name and
     *         field names with its column name.
     */
    public List<TableConfigDataClass> scan(String packagePrefix) {
        Reflections reflections = new Reflections(packagePrefix);
        Set<Class<?>> personalClasses = reflections.get(TypesAnnotated.with(PrivateData.class).asClass());
        List<TableConfigDataClass> tableList = new ArrayList<>();

        for (Class<?> personalClass: personalClasses) {
            PrivateData privateDataAnnotation = personalClass.getAnnotation(PrivateData.class);
            String[] privateFiledList = privateDataAnnotation.column();
            Table tableAnnotation = personalClass.getAnnotation(Table.class);
            String tableName = tableAnnotation.name();
            Map<String, String> variableNameToColumnName = new HashMap<>();

            Field[] fields = personalClass.getDeclaredFields();
            for (Field field: fields) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                variableNameToColumnName.put(field.getName(), columnAnnotation.name());
            }
            tableList.add(new TableConfigDataClass(tableName, personalClass.getName(), privateFiledList, variableNameToColumnName));
        }
        return tableList;
    }

    /***
     * Using hibernate to query all filed in the table with given userid.
     * @param tableList List of table configuration that are marked as private.
     */
    public void query(List<TableConfigDataClass> tableList, Long id) {
        EntityManager manager = JPAUtils.getEntityManger();
        for (TableConfigDataClass tableConfig: tableList) {
            String queryString = "from " + tableConfig.getClassName() + " Where id= :userid";
            Query query = manager.createQuery(queryString);
            query.setParameter("userid", id);
            List<?> res = query.getResultList();
            for (Object item : res) {
                System.out.println(item.toString());
            }
        }
    }
}
