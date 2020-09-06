package com.ay.AdminController;

import com.ay.AdminService.AdminService;
import com.ay.model.ReplyVo;
import com.ay.model.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AdminReply {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ObjectMapper mapper;

    @RequestMapping("ASelectReplys.do")//管理评论：查询所有评论功能
    public void ASelectReplys(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        int PageSize = Integer.parseInt(maps.get("PageSize").toString());
        int PageNum = (Integer.parseInt(maps.get("PageNum").toString()) - 1) * PageSize;
        mapper.writeValue(response.getWriter(), adminService.ASelectReplys(PageNum, PageSize));
    }

    @RequestMapping("ASelectOneReplys.do")//管理评论：模糊查询功能
    public void ASelectOneReplys(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        ReplyVo replyVo = new ReplyVo();
        replyVo.setReadername(maps.get("readername").toString());
        mapper.writeValue(response.getWriter(), adminService.ASelectOneReplys(replyVo));
    }

    @RequestMapping("ADeleteReplys.do")//管理评论：删除评论功能
    public void ADeleteReplys(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        mapper.writeValue(response.getWriter(), adminService.ADeleteReplys(new ReplyVo(Integer.parseInt(maps.get("commentid").toString()))));
    }

    @RequestMapping("AUpdateReplys.do")//管理评论：修改评论功能
    public void AUpdateReplys(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        ReplyVo replyVo = new ReplyVo();
        replyVo.setBookno(Integer.parseInt(maps.get("bookno").toString()));
        replyVo.setReplyid(Integer.parseInt(maps.get("replyid").toString()));
        replyVo.setCommentid(Integer.parseInt(maps.get("commentid").toString()));
        replyVo.setCommentdate(maps.get("commentdate").toString());
        replyVo.setCommenttext(maps.get("commenttext").toString());
        replyVo.setHeadimg(maps.get("headimg").toString());
        replyVo.setReadername(maps.get("readername").toString());
        replyVo.setReplyname(maps.get("replyname").toString());
        mapper.writeValue(response.getWriter(), adminService.AUpdateReplys(replyVo));
    }
}
