package com.gael4j.Gael.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * @author Barry Zhang
 * this annotation will be put on fields, this annotation should only be used when 
 * field name is different from the corresponding column name.
 * @param name
 * parameter name indicates the corresponding column in the database for this field
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface columnName {
	public String name() default "";
}
