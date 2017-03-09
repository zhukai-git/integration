package com.zhukai.framework.spring.integration.annotation.web;

import java.lang.annotation.*;

/**
 * Created by zhukai on 17-1-12.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
}