package com.gael4j.Gael.JPA;

import com.gael4j.Entity.ChildNode;
import org.junit.Test;

import com.gael4j.Gael.AnnotationProcessing.JPA.Controller;

import java.util.Map;
import java.util.Set;

/***
 *
 * @author Hugo Huang
 * * This is a unit test class for Controller class.
 */
public class ControllerTest {

    @Test
    public void TestRun(){
        String packagePrefix = "TableGenerator.JPAEntities";
        Map<Class<?>, Set<ChildNode>> FKConnections = Controller.scan(packagePrefix);
        prettyPrint(FKConnections);
    }

    private void prettyPrint(Map<Class<?>, Set<ChildNode>> FKConnections) {
        for (Map.Entry<Class<?>, Set<ChildNode>> entry: FKConnections.entrySet()) {
            System.out.println("Key=" + entry.getKey().getSimpleName() + ";");
            System.out.print("Value=[");
            entry.getValue().forEach(node -> System.out.print(node.toString()+";"));
            System.out.println("]");
        }
    }
}