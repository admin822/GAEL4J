package edu.brown.cs2390.controller;

import edu.brown.cs2390.sampleClass.SampleUser;
import edu.brown.cs2390.sampleClass.SampleUserDAOTest;
import edu.brown.cs2390.util.JPAUtils;
import edu.brown.cs2390.util.TableConfigDataClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;

/***
 *
 * @author Hugo Huang
 * * This is a unit test class for Controller class.
 */
public class ControllerTest {

    private final Controller controllerTest = new Controller();

    @Test
    public void TestRun(){
        List<TableConfigDataClass> tableList = controllerTest.scan("edu.brown.cs2390");
        for (TableConfigDataClass tableConfigDataClass : tableList) {
            System.out.println(tableConfigDataClass.getTableName() + " " +
                    tableConfigDataClass.getVariableNameToColumnName().toString() + " " +
                    Arrays.toString(tableConfigDataClass.getPrivateFieldList()));
        }
    }
    @Test
    public void query() {
        EntityManager manager = JPAUtils.getEntityManger();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        SampleUser user = new SampleUser();
        user.setAge(12);
        user.setUserName("Hugo Huang");
        manager.persist(user);

        SampleUser user2 = new SampleUser();
        user2.setAge(21);
        user2.setUserName("QN Huang");
        manager.persist(user2);
        transaction.commit();

        manager.close();
        List<TableConfigDataClass> tableList = controllerTest.scan("edu.brown.cs2390");
        controllerTest.query(tableList, (long) 1);
    }
}