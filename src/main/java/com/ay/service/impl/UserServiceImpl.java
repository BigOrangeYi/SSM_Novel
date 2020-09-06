package com.ay.service.impl;

import com.ay.AOP.AN;
import com.ay.dao.UserDao;
import com.ay.model.UserVo;
import com.ay.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private  ObjectMapper mapper;


    public int Login(UserVo loginuser) {
        if (jedisCluster.hget("login", loginuser.getPhone()) != null) {
            if (jedisCluster.hget("login", loginuser.getPhone()).equals(loginuser.getPassword())) {
                System.out.println("用Redis集群登录");
                return 1;
            } else {
                return 0;
            }
        } else {
            int result = userDao.Login(loginuser);
            if (result == 1) {//登录成功，向redis中插入值
                System.out.println("用MYSQL登录");
                jedisCluster.hset("login", loginuser.getPhone(), loginuser.getPassword());
                return 1;
            } else {
                return 0;
            }
        }


    }//登录

    @AN
    public int Register(UserVo newuser) {//注册
        if (userDao.Register(newuser) == 1) {
            jedisCluster.hset("login", newuser.getPhone(), newuser.getPassword());//插入redis用于登录
            return 1;
        } else {
            return 0;
        }
    }//注册

    public int FindUserByPhone(String phone) throws Exception{//查询手机号是否存在
        List<UserVo> userVos= new ArrayList<>();
        List<UserVo> result=new ArrayList<>();
        try{
            System.out.println("从Redis中中查询手机号是否存在");
            userVos=mapper.readValue(jedisCluster.lrange("USERS",0,-1).toString(),userVos.getClass());
            if(userVos.size()>0){
                userVos=mapper.convertValue(userVos,new TypeReference<List<UserVo>>() {});/*linkedhashmap转为自定义list*/
                for(UserVo temp:userVos){
                    if(temp.getPhone().equals(phone)){
                        System.out.println("在Redis中查询的结果：存在该手机号");
                        return 1;
                    }
                }
            }
            System.out.println("在Redis中无查询结果，在MySQL中查询");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("从MySQL中查询手机号是否存在");
        result=userDao.FindUserByPhone(phone);
        if (result.size()>0){
            System.out.println("在MySQL中查询的结果：存在该手机号");
            for (UserVo u:result){
                jedisCluster.lpush("USERS",mapper.writeValueAsString(u));
            }
            return 1;
        }else {
            System.out.println("在MySQL中查询的结果：不存在该手机号");
            return 0;
        }


    }//查询手机号是否存在

    public List<UserVo> navsession(String userphone)throws Exception {
        List<UserVo> userVos= new ArrayList<>();
        List<UserVo> result=new ArrayList<>();
        try{
            userVos=mapper.readValue(jedisCluster.lrange("USERS",0,-1).toString(),userVos.getClass());
            if(userVos.size()>0){
                userVos=mapper.convertValue(userVos,new TypeReference<List<UserVo>>() {});/*linkedhashmap转为自定义list*/
                for(UserVo temp:userVos){
                    if(temp.getPhone().equals(userphone)){
                        result.add(temp);
                        System.out.println("从Redis中返回navsession信息");
                        return result;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("从MySQL中返回navsession信息");
        result=userDao.navsession(userphone);
        for (UserVo u:result){//将navsession信息插入Redis
            jedisCluster.lpush("USERS",mapper.writeValueAsString(u));
        }
        return result;

    }//查询导航栏session信息（头像，昵称）

    public List<String> AutoComplete1(String inputtext) {//自动补全功能（模糊查找）
        return userDao.AutoComplete1(inputtext);
    }

    public List<String> AutoComplete2(String inputtext) {//自动补全功能（模糊查找）
        return userDao.AutoComplete2(inputtext);
    }

    public int AlterHeadimg(UserVo userVo) throws Exception{
        if (userDao.AlterHeadimg(userVo)== 1) {
            System.out.println("MYSQL的头像已修改");
            List<UserVo> userVos= new ArrayList<>();
            userVos=mapper.readValue(jedisCluster.lrange("USERS",0,-1).toString(),userVos.getClass());
            if(userVos.size()>0){
                userVos=mapper.convertValue(userVos,new TypeReference<List<UserVo>>() {});/*linkedhashmap转为自定义list*/
                for (int i = 0; i <userVos.size() ; i++) {
                    if(userVo.getPhone().equals(userVos.get(i).getPhone().toString())){
                        UserVo u=(UserVo) userVos.get(i).clone();
                        u.setHeadimg(userVo.getHeadimg());
                        jedisCluster.lset("USERS",i,mapper.writeValueAsString(u));
                        System.out.println("Redis的头像修改");
                        break;
                    }
                }
            }
            return 1;
        }
        else {
            return 0;
        }

    }//改头像

    public int AlterPassword(UserVo userVo)throws Exception {
        if (userDao.AlterPassword(userVo) == 1) {
            System.out.println("MYSQL的user密码和昵称已修改");
            jedisCluster.hset("login",userVo.getPhone(),userVo.getPassword());
            List<UserVo> userVos= new ArrayList<>();
            userVos=mapper.readValue(jedisCluster.lrange("USERS",0,-1).toString(),userVos.getClass());
            if(userVos.size()>0){
                userVos=mapper.convertValue(userVos,new TypeReference<List<UserVo>>() {});/*linkedhashmap转为自定义list*/
                for (int i = 0; i <userVos.size() ; i++) {
                    if(userVo.getPhone().equals(userVos.get(i).getPhone().toString())){
                        UserVo u=userVos.get(i);
                        u.setPassword(userVo.getPassword());
                        u.setReadername(userVo.getReadername());
                        jedisCluster.lset("USERS",i,mapper.writeValueAsString(u));
                        if (jedisCluster.lset("USERS",i,mapper.writeValueAsString(u)).equals("OK"))
                        System.out.println("Redis的user密码和昵称已修改");
                        break;
                    }
                }
            }
            return 1;
        }
        else {
            return 0;
        }

    }//改昵称和密码

    public int DeleteUser(UserVo userVo)throws Exception{
        List<UserVo> userVos= new ArrayList<>();
        List<UserVo> result=new ArrayList<>();
        int DeleteResult=userDao.DeleteUser(userVo);
        if (DeleteResult==1)
        try{
            System.out.println("MySQL中已删除该用户");
            userVos=mapper.readValue(jedisCluster.lrange("USERS",0,-1).toString(),userVos.getClass());
            if(userVos.size()>0){
                userVos=mapper.convertValue(userVos,new TypeReference<List<UserVo>>() {});/*linkedhashmap转为自定义list*/
                for (int i = 0; i <userVos.size() ; i++) {
                    if (userVos.get(i).getPhone().equals(userVo.getPhone())){
                        if(jedisCluster.lrem("USERS",i,mapper.writeValueAsString(userVos.get(i)))==1){
                            System.out.println("Redis中已删除该用户");
                            jedisCluster.hdel("login",userVo.getPhone());
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return DeleteResult;
    }//注销用户
    public List<UserVo> FindPassword(UserVo userVo){
        return userDao.FindPassword(userVo);
    }//找回密码

}
