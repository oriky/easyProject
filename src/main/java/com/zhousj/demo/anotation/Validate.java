package com.zhousj.demo.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主要用于实体类校验
 *
 * @author zhousj
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Validate {

    int maxLen() default 32;

    String name() default "";

    String msg() default "";

    boolean allowNull() default true;
}
