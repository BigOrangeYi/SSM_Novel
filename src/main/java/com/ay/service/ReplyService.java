package com.ay.service;

import com.ay.model.ReplyVo;

import java.util.List;

public interface ReplyService  {
    List<ReplyVo> QueryComments1(int bookno);//查询一级评论
    List<ReplyVo> QueryComments2(int commentid);//查询二级评论
    int AddComment(ReplyVo replyVo);//添加评论
    int Replycmt(ReplyVo replyVo);//添加回复
}
