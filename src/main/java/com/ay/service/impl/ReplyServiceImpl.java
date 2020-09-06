package com.ay.service.impl;

import com.ay.dao.ReplyDao;
import com.ay.model.ReplyVo;
import com.ay.service.ReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ReplyServiceImpl implements ReplyService {
    @Resource
    private ReplyDao replyDao;
   public List<ReplyVo> QueryComments1(int bookno){
        return replyDao.QueryComments1(bookno);
    }//查询一级评论

    @Override
    public List<ReplyVo> QueryComments2(int commentid) {
        return replyDao.QueryComments2(commentid);
    }//查询二级评论

    public int AddComment(ReplyVo replyVo) {
return replyDao.AddComment(replyVo);
    }//添加评论

    @Override
    public int Replycmt(ReplyVo replyVo) {
        return replyDao.Replycmt(replyVo);
    }
}
