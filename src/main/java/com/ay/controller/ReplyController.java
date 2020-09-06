package com.ay.controller;

import com.ay.model.ReplyVo;
import com.ay.service.ReplyService;
import com.ay.service.impl.ReplyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ReplyController {
    @Resource
    private ReplyServiceImpl replyService;
    @Resource
    private ObjectMapper mapper;

    @RequestMapping("querycomments1.do")//查询一级评论
    public void Querycomments1(HttpServletResponse response, @RequestBody Map maps)throws IOException {
        List<ReplyVo> replyVos =replyService.QueryComments1(Integer.parseInt(maps.get("bookno").toString()));
        mapper.writeValue(response.getWriter(), replyVos);

    }
    @RequestMapping("querycomments2.do")//查询二级评论
    public void Querycomments2(HttpServletResponse response, @RequestBody Map maps)throws IOException {
        List<ReplyVo> replyVos =replyService.QueryComments2(Integer.parseInt(maps.get("commentid").toString()));
        mapper.writeValue(response.getWriter(), replyVos);

    }

    @RequestMapping("addcomment.do")//添加评论
    public void ADDCOMMENT(HttpSession session,HttpServletResponse response, @RequestBody Map maps)throws IOException{
        ReplyVo reply=new ReplyVo();
        reply.setBookno(Integer.parseInt(maps.get("bookno").toString()));
        String commenttext=maps.get("commenttext").toString();
        if(commenttext!=null&&commenttext!="") {
            if(session.getAttribute("userphone")!=null&&session.getAttribute("userphone")!="") {
                reply.setCommenttext(commenttext);
                reply.setHeadimg(maps.get("headimg").toString());
                reply.setReadername(maps.get("readername").toString());
                Calendar now = Calendar.getInstance();
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateNowStr = sdf.format(d);
                reply.setCommentdate(dateNowStr);
                int temp = 0;
                temp = replyService.AddComment(reply);
                if (temp == 1) {

                    mapper.writeValue(response.getWriter(), "添加成功");
                } else {

                    mapper.writeValue(response.getWriter(), "添加失败");
                }
            }
            else{

                mapper.writeValue(response.getWriter(), "未登录");
            }
            }

    }


    @RequestMapping("addreply.do")//添加回复
    public void ADDReply(HttpSession session,HttpServletResponse response, @RequestBody Map maps)throws IOException{
        ReplyVo reply=new ReplyVo();
        reply.setBookno(Integer.parseInt(maps.get("bookno").toString()));
        String commenttext=maps.get("commenttext").toString();
        int replyid=Integer.parseInt(maps.get("replyid").toString());
        String replyname=maps.get("replyname").toString();
        if(commenttext!=null&&commenttext!="") {
            if (replyid>0&&replyname!=""&&replyname!=null)
            if(session.getAttribute("userphone")!=null&&session.getAttribute("userphone")!="") {
                reply.setReplyid(replyid);
                reply.setReplyname(replyname);
                reply.setCommenttext(commenttext);
                reply.setHeadimg(maps.get("headimg").toString());
                reply.setReadername(maps.get("readername").toString());
                Calendar now = Calendar.getInstance();
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateNowStr = sdf.format(d);
                reply.setCommentdate(dateNowStr);
                int temp = 0;
                temp = replyService.Replycmt(reply);
                if (temp == 1) {

                    mapper.writeValue(response.getWriter(), "添加成功");
                } else {

                    mapper.writeValue(response.getWriter(), "添加失败");
                }
            }
            else{

                mapper.writeValue(response.getWriter(), "未登录");
            }
        }

    }
}
