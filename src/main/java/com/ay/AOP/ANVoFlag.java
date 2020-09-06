package com.ay.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ANVoFlag {//注册校验注解
    /** 参数名称 */
    String name();
    /** 是否必填 */
    boolean required() default false;
    /** 正则匹配 */
    String regular() default "";

}
