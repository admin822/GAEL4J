package com.gael4j.Gael.Annotations;

import java.lang.annotation.*;

/***
 *
 * @author Hugo Huang
 * Cutome annotation indicating what is the private data in annotated class.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PrivateData {
    String schema() default "";
    String primaryKey() default "";
}
