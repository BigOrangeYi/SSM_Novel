package com.ay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jms.Destination;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class PraiseController {//点赞

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private com.ay.praiseProducer praiseProducer;

    private static Destination destination=new ActiveMQQueue("ay.queue.high.concurrency.praise");//ActiveMQ队列存储

    @RequestMapping("praise.do")
    public void praise(@RequestBody Map maps, HttpServletResponse response, HttpSession session) throws Exception {
        if (maps.get("bookno")!=null&&maps.get("bookno")!=""&&session.getAttribute("userphone")!=null&&session.getAttribute("userphone")!=""){
            praiseProducer.sendMessage(destination,maps.get("bookno").toString());//发送消息(操作ActiveMQ)
            mapper.writeValue(response.getWriter(), true);
        }else {
            mapper.writeValue(response.getWriter(), false);
        }
    }

}
