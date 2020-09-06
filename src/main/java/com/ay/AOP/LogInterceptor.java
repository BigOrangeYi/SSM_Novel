package com.ay.AOP;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.jms.Session;
import javax.servlet.http.HttpSession;

@Aspect
public class LogInterceptor {

    @Before(value = "execution(* com.ay.controller.UserController.Register(..))")
    public void before(){
        System.out.println("进入方法时间为:" + System.currentTimeMillis());
    }

    @After(value = "execution(* com.ay.controller.UserController.Register(..))")
    public void after(){
        System.out.println("退出方法时间为:" + System.currentTimeMillis());
    }


}
