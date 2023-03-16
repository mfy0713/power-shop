package com.powernode.ann;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志注解
 */
@Target(ElementType.METHOD) //注解应用在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时生效
public @interface MyLog {
    String operation() default ""; //操作说明
}
