package org.kadirov.core.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD,
        ElementType.TYPE,
        ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Scope {
    String value() default "singleton";
}
