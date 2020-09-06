package com.ay;

import com.ay.model.BookVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class praiseConsumer implements MessageListener {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onMessage(Message message) {

        ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
        int bookno= 0;
        try {
            bookno = Integer.parseInt(activeMQTextMessage.getText().toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("书籍号是："+bookno);


        List<BookVo> bookVos = new ArrayList<>();
        try {
            bookVos = mapper.readValue(jedisCluster.zrange("BOOKS", 0, -1).toString(), bookVos.getClass());
            bookVos = mapper.convertValue(bookVos, new TypeReference<List<BookVo>>() {});/*转为自定义list*/
            for (BookVo book : bookVos) {//遍历Redis书籍数据
                if (book.getBookno() == bookno) {
                    try {
                        //更新Redis中的书籍点赞数据
                        BookVo newBook = (BookVo)book.clone();
                        jedisCluster.zrem("BOOKS", mapper.writeValueAsString(book));
                        newBook.setBookpopularity(newBook.getBookpopularity()+1);
                        jedisCluster.zadd("BOOKS", newBook.getBookno(), mapper.writeValueAsString(newBook));
                        //更新完成
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
