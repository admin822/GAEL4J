package com.gael4j.Gael.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 *
 * @author Hugo Huang
 * Cutome annotation indicating what is the private data in annotated class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PrivateData {
    String[] column();
}
