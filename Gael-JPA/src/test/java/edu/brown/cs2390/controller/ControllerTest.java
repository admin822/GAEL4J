package edu.brown.cs2390.controller;

import edu.brown.cs2390.util.TableConfigDataClass;
import org.junit.Test;

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
}