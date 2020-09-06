package com.ay.dao;

import com.ay.model.ReplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyDao {
    List<ReplyVo> QueryComments1(int bookno);//查询一级评论
    List<ReplyVo> QueryComments2(int commentid);//查询二级评论
    int AddComment(ReplyVo replyVo);//添加评论
    int Replycmt(ReplyVo replyVo);//添加回复
    int DeleteReply(ReplyVo replyVo);//删除评论
}
