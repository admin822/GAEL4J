package com.gael4j.Gael.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * for the time being, we consider user are using the same variable names in both Java and Database
 * So class name is considered as table name
 * field names are considered as column names
 * Potential improvement in the future*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface userdata {
	public String schema() default "";

}
