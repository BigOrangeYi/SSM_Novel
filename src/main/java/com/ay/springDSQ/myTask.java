package com.ay.springDSQ;

import com.ay.model.BookVo;
import com.ay.praiseProducer;
import com.ay.service.impl.BookServiceImpl;
import com.ay.service.impl.PraiseServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import javax.jms.Destination;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class myTask {
@Autowired
private praiseProducer praiseProducer;
    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PraiseServiceImpl praiseService;

    @Autowired
    private BookServiceImpl bookService;

    private static Destination destination=new ActiveMQQueue("ay.queue.high.concurrency.praise");
    //定时任务执行的方法:
    //1。从Redis集群中读取点赞相关数据
    //2.更新MySQL点赞数据
    //3.清空书籍位置信息
    //4.查询并更新前22本书籍在首页的位置
    public void execute() throws Exception{
        int flag=0;
        List<BookVo> bookVos = new ArrayList<>();
        bookVos = mapper.readValue(jedisCluster.zrange("BOOKS", 0, -1).toString(), bookVos.getClass());
        bookVos = mapper.convertValue(bookVos, new TypeReference<List<BookVo>>() {});/*转为自定义list*/
        for (BookVo book : bookVos) {//遍历Redis书籍数据并更新MySQL中的书籍点赞量！
            praiseService.UpdatePraiseNumber(book.getBookno(), book.getBookpopularity());
        }
        praiseService.ClearBookPosition();//清空MySQL书籍位置信息
        List<BookVo> books=praiseService.IndexBooks();//查询首页书籍（前22本）
        Collections.sort(books);

        for(BookVo bookVo:books){
            flag++;
            if (flag>0&&flag<=6){
                bookVo.setBookposition("站长推荐");
                praiseService.ResetBookPosition(bookVo.getBookno(), bookVo.getBookposition());
            }else if (flag>6&&flag<=11){
                bookVo.setBookposition("大神推荐");
                praiseService.ResetBookPosition(bookVo.getBookno(), bookVo.getBookposition());
            }
            else if (flag>11&&flag<=17){
                bookVo.setBookposition("新书推荐");
                praiseService.ResetBookPosition(bookVo.getBookno(), bookVo.getBookposition());
            }
            else if (flag>17&&flag<=22){
                bookVo.setBookposition("精品推荐");
                praiseService.ResetBookPosition(bookVo.getBookno(), bookVo.getBookposition());
            }
        }

            flag=0;
                jedisCluster.del("BOOKS");//清空redis书籍信息

                List<BookVo> bookVoList=new ArrayList<>();
                String bookposition=null;
        for (int i = 0; i <4 ; i++) {
          switch (i){
              case 0:bookposition="站长推荐";break;
              case 1:bookposition="大神推荐";break;
              case 2:bookposition="新书推荐";break;
              case 3:bookposition="精品推荐";break;
              default:break;
          }
            BookVo bookVo=new BookVo();
            bookVo.setBookposition(bookposition);
            bookVoList.addAll(bookService.FindBookByParam(bookVo));
        }


                for (BookVo bookVo1:bookVoList){
                    jedisCluster.zadd("BOOKS", bookVo1.getBookpopularity(),mapper.writeValueAsString(bookVo1));
                }


    }

}
