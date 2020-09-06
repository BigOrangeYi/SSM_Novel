package com.ay.service.impl;

import com.ay.AOP.AN;
import com.ay.dao.AuthorDao;
import com.ay.dao.BookDao;
import com.ay.model.AuthorVo;
import com.ay.model.BookVo;
import com.ay.model.CharacterVo;
import com.ay.service.AuthorService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Resource
    private AuthorDao authorDao;
    @Resource
    private BookDao bookDao;
    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private ObjectMapper mapper;
    @Resource
    private BookServiceImpl bookService;

    @AN
    public int AddAuthor(AuthorVo authorVo)throws Exception {
        if (authorDao.AddAuthor(authorVo)==1){
            System.out.println("MySQL增加作者成功");
            System.out.println("开始向Redis增加");
           if ( jedisCluster.zadd("AUTHORS",authorVo.getAuthorno(),mapper.writeValueAsString(authorVo))==1){
               System.out.println("Redis增加作者成功");
           }
           else {
               System.out.println("Redis增加作者失败");
           }
           return 1;
        }else {
            System.out.println("MySQL增加作者失败");return 0;
        }

    }//增加作者

    public List<AuthorVo> QueryAuthorByPhone(AuthorVo authorVo) throws Exception{
        List<AuthorVo> authorVos=new ArrayList<>();
        List<AuthorVo>result=new ArrayList<>();
        authorVos=mapper.readValue(jedisCluster.zrange("AUTHORS",0,-1).toString(),authorVos.getClass());
        authorVos=mapper.convertValue(authorVos,new TypeReference<List<AuthorVo>>(){});
        if (authorVos.size()>0){
            for(AuthorVo author:authorVos){
                if (author.getPhone().equals(authorVo.getPhone())){
                    System.out.println("Redis查询到该作者");
                    result.add(author);
                    return result;
                }
            }
        }else {
            System.out.println("Redis找不到数据");
        }
        System.out.println("开始从MySQL查找数据");
        result=authorDao.QueryAuthorByPhone(authorVo);
        if (result.size()>0){
            System.out.println("MySQL查找到了作者数据");
            for (int i = 0; i <result.size() ; i++) {
                if (jedisCluster.zadd("AUTHORS",result.get(i).getAuthorno(),mapper.writeValueAsString(result.get(i)))==1)
                {
                    System.out.println("Redis增加作者数据完成");
                    return result;
                }
            }

        }else {
            System.out.println("MySQL也找不到数据");
        }
return result;
    }//根据参数查询作者

    public List<AuthorVo> QueryAuthorByName(AuthorVo authorVo)throws Exception {
        List<AuthorVo> authorVos = new ArrayList<>();
        List<AuthorVo> result = new ArrayList<>();
        authorVos = mapper.readValue(jedisCluster.zrange("AUTHORS", 0, -1).toString(), authorVos.getClass());
        authorVos = mapper.convertValue(authorVos, new TypeReference<List<AuthorVo>>() {
        });
        if (authorVos.size() > 0) {
            for (AuthorVo author : authorVos) {
                if (author.getAuthorname().equals(authorVo.getAuthorname())) {
                    System.out.println("Redis查询到该作者");
                    result.add(author);
                    return result;
                }
            }
        } else {
            System.out.println("Redis找不到数据");
        }
        System.out.println("开始从MySQL查找数据");
        result = authorDao.QueryAuthorByName(authorVo);
        if (result.size() > 0) {
            System.out.println("MySQL查找到了作者数据");
            for (int i = 0; i < result.size(); i++) {
                if (jedisCluster.zadd("AUTHORS", result.get(i).getAuthorno(), mapper.writeValueAsString(result.get(i))) == 1) {
                    System.out.println("Redis增加作者数据完成");
                    return result;
                }
            }
        }
        return result;
    }//根据笔名查询作者
    public int DeleteAuthor(AuthorVo authorVo)throws Exception {
        return authorDao.DeleteAuthor(authorVo);
    }//删除作者

    public int AlterBookImg(BookVo bookVo) throws Exception {
        if (authorDao.AlterBookImg(bookVo) == 1) {
            System.out.println("MySQL封面修改完毕");
            List<BookVo> bookVos = new ArrayList<>();
            List<BookVo> result = new ArrayList<>();
            bookVos = mapper.readValue(jedisCluster.zrange("BOOKS", 0, -1).toString(), bookVos.getClass());
            bookVos = mapper.convertValue(bookVos, new TypeReference<List<BookVo>>() {
            });
            if (bookVos.size() > 0) {
                System.out.println("开始从Redis修改书籍封面");
                for (BookVo book : bookVos) {

                    if (book.getBookno() == bookVo.getBookno()) {
                        BookVo newBook=null;
                        try {
                           newBook = (BookVo)book.clone();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        newBook.setBookimg(bookVo.getBookimg());
                        jedisCluster.zrem("BOOKS", mapper.writeValueAsString(book));
                        jedisCluster.zadd("BOOKS", newBook.getBookpopularity(), mapper.writeValueAsString(newBook));
                        System.out.println("Redis封面修改完毕");
                        return 1;
                    }
                }

            }
            System.out.println("Redis封面修改失败");
            return 1;
        }
        return 0;

    }//修改封面

    @Override
    public int UDeleteAuthor(int authorno) throws Exception{//删除作者
      int  DeleteResult=authorDao.UDeleteAuthor(authorno);
      if (DeleteResult==1){
          System.out.println("MySQL删除作者成功");
          List<AuthorVo> authorVos = new ArrayList<>();
          List<AuthorVo> result = new ArrayList<>();
          authorVos = mapper.readValue(jedisCluster.zrange("AUTHORS", 0, -1).toString(), authorVos.getClass());
          authorVos = mapper.convertValue(authorVos, new TypeReference<List<AuthorVo>>() {
          });
          if (authorVos.size() > 0) {
              for (AuthorVo author : authorVos) {
                  if (author.getAuthorno()==authorno)
                        if (jedisCluster.zrem("AUTHORS",mapper.writeValueAsString(author))==1){
                            System.out.println("Redis删除作者成功");
                            List<BookVo> bookVos = new ArrayList<>();
                            bookVos=mapper.readValue(jedisCluster.zrange("BOOKS", 0, -1).toString(), bookVos.getClass());
                            bookVos = mapper.convertValue(bookVos, new TypeReference<List<BookVo>>() {});
                            for (BookVo bookVo:bookVos) {
                                if (bookVo.getAuthorno()==authorno){
                                    DeleteWork(bookVo.getBookno());
                                }
                            }

                            return 1;
                        }
                  }
              }
          }


        return DeleteResult;
    }

    @Override
    public int DeleteWork(int bookno) throws Exception{//删除作品
        int  DeleteResult=authorDao.DeleteWork(bookno);
        if (DeleteResult>=0){
            System.out.println("MySQL删除作品章节表成功");
            BookVo b=new BookVo();b.setBookno(bookno);
            if (bookService.DeleteBook(b)==1)
            System.out.println("MySQL删除作品成功");
            List<BookVo> bookVos = new ArrayList<>();
            List<BookVo> result = new ArrayList<>();
            bookVos = mapper.readValue(jedisCluster.zrange("BOOKS", 0, -1).toString(), bookVos.getClass());
            bookVos = mapper.convertValue(bookVos, new TypeReference<List<BookVo>>() {
            });
            if (bookVos.size() > 0) {
                for (BookVo book : bookVos) {
                    if (bookno==book.getBookno())
                        if (jedisCluster.zrem("BOOKS",mapper.writeValueAsString(book))==1){
                            System.out.println("Redis删除作品成功");
                            List<CharacterVo> characterVos = new ArrayList<>();
                            characterVos = mapper.readValue(jedisCluster.zrange(bookno+"", 0, -1).toString(), characterVos.getClass());
                            characterVos = mapper.convertValue(characterVos, new TypeReference<List<CharacterVo>>() {
                            });
                            if (characterVos.size()>=0){
                               for (CharacterVo characterVo:characterVos){
                                   if (bookno==characterVo.getBookno()){
                                       if (jedisCluster.zrem(bookno+"",mapper.writeValueAsString(characterVo))==1){
                                           System.out.println("Redis删除作品章节表成功");
                                           return 1;
                                       }
                                   }
                               }
                            }

                        }
                }
            }
        }




        return 1;
    }
}
