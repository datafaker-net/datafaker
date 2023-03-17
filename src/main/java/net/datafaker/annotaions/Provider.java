package net.datafaker.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Provider {

    String expression() default "";

    String method() default "";

    String languageTag() default "en";

    long[] seed() default {};
}
