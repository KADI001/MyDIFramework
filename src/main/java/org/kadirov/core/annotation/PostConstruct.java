package org.kadirov.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * Component class should use this annotation for additional obj initialization
 */
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostConstruct {
}
