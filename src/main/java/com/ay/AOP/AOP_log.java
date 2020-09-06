package com.ay.AOP;

import com.ay.dao.LogDao;
import com.ay.model.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
public class AOP_log {
    @Autowired
    private LogDao logDao;
    /*用户日志*/
    @Around(value="execution(* com.ay.service.impl.UserServiceImpl.Login(..))")//登录日志
    public Object login_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        UserVo userVo=(UserVo)obj;
        StringBuffer stringBuffer=new StringBuffer("用户："+userVo.getPhone()+"使用密码："+userVo.getPassword()+"开始登录;");
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("登录结果:登录成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("登录结果:登录失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("users_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.UserServiceImpl.Register(..))")//注册日志
    public Object register_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        UserVo userVo=(UserVo)obj;
        StringBuffer stringBuffer=new StringBuffer("用户："+userVo.getPhone()+"密码："+userVo.getPassword()+"开始注册;");
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("注册结果:注册成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("注册结果:注册失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("users_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.UserServiceImpl.DeleteUser(..))")//注销日志
    public Object DeleteUser_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        UserVo userVo=(UserVo)obj;
        StringBuffer stringBuffer=new StringBuffer("用户："+userVo.getPhone()+"密码："+userVo.getPassword()+"开始注销;");
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("注销结果:注销成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("注销结果:注销失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("users_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.UserServiceImpl.AlterPassword())")//修改日志
    public Object AlterPassword_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        UserVo userVo=(UserVo)obj;
        StringBuffer stringBuffer=new StringBuffer("用户："+userVo.getPhone()+"新密码："+userVo.getPassword()+"新昵称："+userVo.getReadername());
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("修改结果:修改成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("修改结果:修改失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("users_log");
        logDao.AddLog(logVo);
        return result;
    }

    /*作者日志*/
    @Around(value="execution(* com.ay.service.impl.AuthorServiceImpl.AddAuthor(..))")//成为作者日志
    public Object AddAuthor_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        AuthorVo authorVo=(AuthorVo)obj;
        StringBuffer stringBuffer=new StringBuffer("用户："+authorVo.getPhone()+"使用笔名："+authorVo.getAuthorname()+"开始注册为作者;");
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("注册结果:注册成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("注册结果:注册失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("authors_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.AuthorServiceImpl.DeleteAuthor(..))")//注销作者日志
    public Object DeleteAuthor_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        AuthorVo authorVo=(AuthorVo)obj;
        StringBuffer stringBuffer=new StringBuffer("用户："+authorVo.getPhone()+" 笔名："+authorVo.getAuthorname()+"开始注销作者;");
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("注销结果:注销成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("注销结果:注销失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("authors_log");
        logDao.AddLog(logVo);
        return result;
    }

    @Around(value="execution(* com.ay.service.impl.BookServiceImpl.addbook(..))")//加入书架日志
    public Object addbookshelf_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        BookShelfVo bookShelfVo=(BookShelfVo)obj;
        StringBuffer stringBuffer=new StringBuffer("用户："+bookShelfVo.getPhone()+"添加书籍："+bookShelfVo.getBookname()+"进书架;");
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("添加结果:添加成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("添加结果:添加失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("bookshelfs_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.BookServiceImpl.DeleteBookShelf(..))")//删除书架日志
    public Object DeleteBookShelf_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        int bookShelfno=(int)obj;
        StringBuffer stringBuffer=new StringBuffer("移除书架编号："+bookShelfno+" from书架;");
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("移除结果:移除成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("移除结果:移除失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("bookshelfs_log");
        logDao.AddLog(logVo);
        return result;
    }

    /*书籍日志*/
    @Around(value="execution(* com.ay.service.impl.BookServiceImpl.ADDBook(..))")//增加书籍日志
    public Object ADDBook_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        BookVo bookVo=(BookVo)obj;
        StringBuffer stringBuffer=new StringBuffer("增加书籍："+bookVo);
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("增加结果:增加成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("增加结果:增加失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("books_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.BookServiceImpl.DeleteBook(..))")//删除书籍日志
    public Object DeleteBook_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        BookVo bookVo=(BookVo)obj;
        StringBuffer stringBuffer=new StringBuffer("删除书籍："+bookVo);
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("删除结果:删除成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("删除结果:删除失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("books_log");
        logDao.AddLog(logVo);
        return result;
    }

    /*评论日志*/
    @Around(value="execution(* com.ay.service.impl.ReplyServiceImpl.AddComment(..))")//添加评论日志
    public Object AddComment_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        ReplyVo replyVo=(ReplyVo)obj;
        StringBuffer stringBuffer=new StringBuffer("添加评论："+replyVo);
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("添加结果:添加成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("添加结果:添加失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("comments_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.ReplyServiceImpl.Replycmt(..))")//添加回复日志
    public Object Replycmt_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        ReplyVo replyVo=(ReplyVo)obj;
        StringBuffer stringBuffer=new StringBuffer("添加回复："+replyVo);
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("添加结果:添加成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("添加结果:添加失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("comments_log");
        logDao.AddLog(logVo);
        return result;
    }

    /*章节日志*/
    @Around(value="execution(* com.ay.service.impl.CharacterServiceImpl.ADDCharacter(..))")//添加章节日志
    public Object ADDCharacter_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        CharacterVo characterVo=(CharacterVo)obj;
        StringBuffer stringBuffer=new StringBuffer("添加章节："+characterVo);
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("添加结果:添加成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("添加结果:添加失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("chapters_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.CharacterServiceImpl.DeleteCharacter(..))")//删除章节日志
    public Object DeleteCharacter_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        CharacterVo characterVo=(CharacterVo)obj;
        StringBuffer stringBuffer=new StringBuffer("删除的章节号："+characterVo.getCharacterno()+"书号："+characterVo.getBookno());
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("删除结果:删除成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("删除结果:删除失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("chapters_log");
        logDao.AddLog(logVo);
        return result;
    }
    @Around(value="execution(* com.ay.service.impl.CharacterServiceImpl.UpdateCharacter(..))")//修改章节日志
    public Object UpdateCharacter_log(ProceedingJoinPoint point)throws IllegalAccessException, Throwable{
        LogVo logVo=new LogVo();
        Object obj = point.getArgs()[0];//获取（object）入参
        CharacterVo characterVo=(CharacterVo)obj;
        StringBuffer stringBuffer=new StringBuffer("修改章节："+characterVo);
        Object result=point.proceed();
        if (Integer.parseInt(result.toString())==1){
            stringBuffer.append("修改结果:修改成功");
            System.out.println(stringBuffer);
        }
        else {
            stringBuffer.append("修改结果:修改失败");
            System.out.println(stringBuffer);
        }
        logVo.setAactions(stringBuffer.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logVo.setLogdate(df.format(new Date()));
        logVo.setTablename("chapters_log");
        logDao.AddLog(logVo);
        return result;
    }





}
