package com.gael4j.Gael.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * @author Barry Zhang
 * this annotation should be put on fields that are NOT included in the database.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface excludeColumn {
	
}
