package com.ay.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Aspect
public class ParameterVerify {
    @Before(value="@annotation(com.ay.AOP.AN)")//Vo校验（springAOP+反射）
    @Order(1)
    public int RegisterparameterVerify(JoinPoint point) throws IllegalAccessException {
        System.out.println("开始执行Vo校验AOP");
        //切点对象
        Object obj = point.getArgs()[0];//获取（object）入参
        Class clazz = obj.getClass();//获取入参的类对象
        Field[] fields = clazz.getDeclaredFields();//获取入参的所有属性
        for (Field field : fields){
            field.setAccessible(true);//作用：允许在用反射时访问私有变量
            //需要做校验的参数
            if(field.isAnnotationPresent(ANVoFlag.class)){// A.isAnnotationPresent(B.class)；意思就是：注解B是否在此A上。如果在则返回true；不在则返回false。
                ANVoFlag ANVoFlag = field.getAnnotation(ANVoFlag.class);
                String name = ANVoFlag.name();
                String regular = ANVoFlag.regular();
                //入参属性值
                Object fieldObj = field.get(obj);
                if(!"".equals(regular)){//正则表达式不为空串！
                    Pattern pattern = Pattern.compile(regular);
                    if (!pattern.matcher(String.valueOf(fieldObj)).matches()) {
                        throw new RuntimeException(String.format("参数【%s】的数据不符合规则",name));
                    }
                    else {
                        System.out.println("参数合法");
                    }
                }
            }
        }
return 1;
    }

    @Around(value="execution(* com.ay.service.impl.*.*(..))")//DFA校验（springAOP+反射）
    @Order(2)
    public Object DFAAOP(ProceedingJoinPoint point) throws IllegalAccessException, Throwable {
        System.out.println("开始DFA校验");
        //切点对象
        Object obj = point.getArgs()[0];//获取（object）入参
        Class clazz = obj.getClass();//获取入参的类对象
        Field[] fields = clazz.getDeclaredFields();//获取入参的所有属性
        List<String> result=new ArrayList<>();
        DFA dfa=new DFA();
        for (int i = 0; i <fields.length ; i++) {

            fields[i].setAccessible(true);//作用：允许在用反射时访问私有变量
                //入参属性值
                Object fieldObj = fields[i].get(obj);
                result=dfa.DFACheck(String.valueOf(fieldObj));
                if (result.size()>0){
                    System.out.println("开始进行屏蔽操作");
                    if (fields[i].getName().toString().equals("characterdetail")){
                        Set<String>set=new HashSet<>();
                        set.addAll(result);//去重
                        System.out.println("检测到"+fields[i].getName()+"包含敏感词："+set);
                        String afterChapter=null;
                            for(String r:set){
                               afterChapter=fieldObj.toString().replaceAll(r,"Bad");
                            }
                        fields[i].set(obj,afterChapter);
                        System.out.println("屏蔽操作完成");
                    }else {
                        fields[i].set(obj, "Bad");
                        System.out.println("屏蔽操作完成");
                    }
                }else {
                    System.out.println(fields[i].getName()+"不包含敏感词，"+"DFA校验通过");
                }
            result.clear();
        }
        Object invoke=point.proceed();
        return invoke;

    }

    @Before(value = "execution(* com.ay.service.impl.UserServiceImpl.AlterPassword(..))")//用户更改校验（springAOP+反射）
    @Order(1)
    public int UpdateUserparameterVerify(JoinPoint point) throws IllegalAccessException {
        System.out.println("开始执行更改校验AOP");
        //切点对象
        Object obj = point.getArgs()[0];//获取（object）入参
        Class clazz = obj.getClass();//获取入参的类对象
        Field[] fields = clazz.getDeclaredFields();//获取入参的所有属性
        for (Field field : fields){
            field.setAccessible(true);//作用：允许在用反射时访问私有变量
            //需要做校验的参数
            if(field.isAnnotationPresent(ANalterUser.class)){// A.isAnnotationPresent(B.class)；意思就是：注解B是否在此A上。如果在则返回true；不在则返回false。
                ANalterUser verify = field.getAnnotation(ANalterUser.class);
                String name = verify.name();
                String regular = verify.regular();
                boolean required=verify.required();
                //入参属性值
                Object fieldObj = field.get(obj);
                if(!"".equals(regular)){//正则表达式不为空串！
                    Pattern pattern = Pattern.compile(regular);
                    if (!pattern.matcher(String.valueOf(fieldObj)).matches()) {
                        throw new RuntimeException(String.format("参数【%s】的数据不符合规则",name));
                    }
                    else {
                        System.out.println("参数合法");
                    }
                }
            }
        }
        return 1;
    }



}
