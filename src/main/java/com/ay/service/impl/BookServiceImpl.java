package com.ay.service.impl;

import com.ay.AOP.AN;
import com.ay.dao.BookDao;
import com.ay.model.BookShelfVo;
import com.ay.model.BookVo;
import com.ay.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookDao bookDao;
    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private ObjectMapper mapper;



    public List<BookVo> AllBooks(int PageNum, int PageSize, String type) throws Exception {
        List<BookVo> bookVos = new ArrayList<>();
        List<BookVo> result = new ArrayList<>();
        try {
            System.out.println("开始从Redis中分页查询");
            if (type != "") {
                bookVos = mapper.readValue(jedisCluster.zrevrange("BOOKS", 0, -1).toString(), bookVos.getClass());
            } else
                bookVos = mapper.readValue(jedisCluster.zrevrange("BOOKS", PageNum, PageSize - 1 + PageNum).toString(), bookVos.getClass());
            if (bookVos.size() > 0) {
                System.out.println("Redis分页查询成功");
                bookVos = mapper.convertValue(bookVos, new TypeReference<List<BookVo>>() {
                });/*linkedhashmap转为自定义list*/
                if (type.equals("")) return bookVos;
                else {
                    for (int i = 0; i < bookVos.size(); i++)
                        if (type.equals(bookVos.get(i).getBooktype())) {
                            result.add(bookVos.get(i));
                        }
                    List<BookVo> typeBooks = new ArrayList<>();
                    for (int j = PageNum; j <= result.size() && j <= (PageSize - 1 + PageNum); j++) {
                        if (result.get(j) == null) break;
                        typeBooks.add(result.get(j));
                    }
                    return typeBooks;
                }
            }
            System.out.println("在Redis分页查询无结果");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("开始从MySQL中分页查询");
        result = bookDao.AllBooks(PageNum, PageSize, type);
        if (result.size() > 0) {
            System.out.println("MySQL中分页查询成功，开始注入Redis");
            for (int i = 0; i < result.size(); i++) {
                jedisCluster.zadd("BOOKS", result.get(i).getBookpopularity(), mapper.writeValueAsString(result.get(i)));
            }
            System.out.println("注入Redis完成");
        }

        return result;
    }//分页查询所有书籍


    public List<BookVo> FindBookByParam(BookVo books) throws Exception {
        List<BookVo> bookVos = new ArrayList<>();
        List<BookVo> result = new ArrayList<>();
        try {
            System.out.println("开始从Redis中查询" + (books.getBookposition() == null ? books.getBookno() : books.getBookposition()) + "的书籍是否存在");
            bookVos = mapper.readValue(jedisCluster.zrevrange("BOOKS", 0, -1).toString(), bookVos.getClass());
            if (bookVos.size() > 0) {
                bookVos = mapper.convertValue(bookVos, new TypeReference<List<BookVo>>() {
                });/*linkedhashmap转为自定义list*/
                for (BookVo book : bookVos) {
                    if (book.getBookno() == books.getBookno() && books.getBookposition() == null) {
                        result.add(book);
                    } else if (book.getBookposition().equals(books.getBookposition())) {
                        result.add(book);
                    }
                }

                if (result.size() > 0) {
                    System.out.println("已经从Redis中查询" + (books.getBookposition() == null ? books.getBookno() : books.getBookposition()) + "的书籍存在");
                    return result;
                }
            }
            System.out.println("在Redis中无" + (books.getBookposition() == null ? books.getBookno() : books.getBookposition()) + "的查询结果");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("开始从MySQL中查询" + (books.getBookposition() == null ? books.getBookno() : books.getBookposition()) + "的书籍");
        result = bookDao.FindBookByParam(books);
        if (result.size() > 0) {
            System.out.println("已经从MySQL中获得查询结果，开始向Redis中注入" + (books.getBookposition() == null ? books.getBookno() : books.getBookposition()) + "的查询结果");
            for (int i = 0; i < result.size(); i++) {
                jedisCluster.zadd("BOOKS", result.get(i).getBookpopularity(), mapper.writeValueAsString(result.get(i)));
            }
            System.out.println("已经将" + (books.getBookposition() == null ? books.getBookno() : books.getBookposition()) + "的查询结果注入到了Redis");
        } else {
            System.out.println("MySQL中没有查询到" + (books.getBookposition() == null ? books.getBookno() : books.getBookposition()) + "的书籍");
        }
        return result;

    }//根据位置参数查询书籍

    public int addbook(BookShelfVo book) throws Exception {

        if (bookDao.QueryMyBookShelf(book)==1)
            return 0;
        if (bookDao.addbook(book) == 1) {
            return 1;
        }
        return 0;
    }//加入书架

    public List<BookShelfVo> Mybookshelf(String phone) throws Exception {

        List<BookShelfVo> bookShelfVos = bookDao.Mybookshelf(phone);
        if (bookShelfVos.size() > 0) {
            System.out.println("从MySQL查询书架信息");
        }
        return bookShelfVos;

    }//我的书架

    public int DeleteBookShelf(int shelfno) throws Exception {

        if (bookDao.DeleteBookShelf(shelfno) == 1)
            return 1;
        else {
            System.out.println("移出书架失败");
            return 0;
        }
    }//移出书架

    /*作者功能区*/
    @AN
    public int ADDBook(BookVo bookVo)throws Exception  {
        try {
            int num = bookDao.ADDBook(bookVo);
            if (num == 1) {
                System.out.println("MySQL书籍增加成功,开始注入Redis");
                long redisnum = jedisCluster.zadd("BOOKS", bookVo.getBookpopularity(), mapper.writeValueAsString(bookVo));
                if (redisnum == 1)
                    System.out.println("Redis书籍增加成功");
            }
            String Bookno = String.valueOf(bookVo.getBookno());
            bookDao.ADDChapterTable(Bookno);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }//增加书籍

    public int DeleteBook(BookVo bookVo) throws Exception{
        if (bookDao.DeleteBook(bookVo)==1) {
            List<BookShelfVo> bookShelfVos = new ArrayList<>();
            bookShelfVos = mapper.readValue(jedisCluster.zrange("BOOKSHELF", 0, -1).toString(), bookShelfVos.getClass());
            if (bookShelfVos.size() > 0) {
                bookShelfVos = mapper.convertValue(bookShelfVos, new TypeReference<List<BookShelfVo>>() {
                });
                for (BookShelfVo bookShelfVo : bookShelfVos) {
                    if (bookShelfVo.getBookno() == bookVo.getBookno()) {

                        jedisCluster.zrem("BOOKSHELF", mapper.writeValueAsString(bookShelfVo));
                        System.out.println("Redis已从书架移出该书");
                    }
                }
            }

           return 1;
        }
       else return 0;
    }//删除书架书籍

    public List<BookVo> QueryMybook(int authorno) throws Exception {
        List<BookVo> bookVos=new ArrayList<>();
        List<BookVo> result=new ArrayList<>();
        bookVos=mapper.readValue(jedisCluster.zrange("BOOKS",0,-1).toString(),bookVos.getClass());
        bookVos=mapper.convertValue(bookVos,new TypeReference<List<BookVo>>(){});
        if (bookVos.size()>0){
            System.out.println("开始从Redis查询我的书籍");
            for (BookVo bookVo:bookVos){
                if (bookVo.getAuthorno()==authorno){
                    result.add(bookVo);
                }
            }
            if (result.size()>0)return result;else System.out.println("Redis中查询不到我的书籍,开始查询MySQL");
        }

        result= bookDao.QueryMybook(authorno);
        if (result.size()>0){
            for (BookVo bookVo:result){
                jedisCluster.zadd("BOOKS",bookVo.getBookno(),mapper.writeValueAsString(bookVo));
                System.out.println("Redis添加我的书籍信息完毕");
            }
        }
        return result;
    }//查询我的书籍

    public int UpdateBook(BookVo bookVo) throws Exception {
        return bookDao.UpdateBook(bookVo);
    }//更新书籍信息(封面和介绍)

    public List<BookVo> QueryBookName(BookVo bookVo) throws Exception {

        List<BookVo> result=new ArrayList<>();

        result=bookDao.QueryBookName(bookVo);
        if (result.size()>0){
            for (BookVo book:result){
                System.out.println("MySQL查询存在该书名");
                jedisCluster.zadd("BOOKS",bookVo.getBookno(),mapper.writeValueAsString(book));
                System.out.println("Redis添加信息完毕");
            }
        }else {
            System.out.println("MySQL也不存在该书名，可以创建此书");
        }
        return result;
    }//查询书名
}
