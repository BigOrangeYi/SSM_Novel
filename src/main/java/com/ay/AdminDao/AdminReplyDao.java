package com.ay.AdminDao;

import com.ay.model.ReplyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminReplyDao {
    List<ReplyVo> ASelectReplys(@Param("PageNum") int PageNum, @Param("PageSize") int PageSize );//查询所有评论
    int ADeleteReplys(ReplyVo replyVo);//删除评论
    int AUpdateReplys(ReplyVo replyVo);//修改评论
    List<ReplyVo>ASelectOneReplys(ReplyVo replyVo);//模糊查询评论
}
