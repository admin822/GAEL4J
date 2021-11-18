package com.gael4j.Gael.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Barry Zhang
 * this annotation will be put on entity classes to show that this class includes
 * privacy information of the users
 * @param table
 * table indicates which table in the database is used to store this class
 * @param schema
 * name of the database that the table is located at
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface userdata {
	public String table() default "";
}

