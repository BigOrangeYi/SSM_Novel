package com.ay.AdminController;

import com.ay.AdminService.AdminService;
import com.ay.model.AuthorVo;
import com.ay.model.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@Controller
public class AdminAuthors {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ObjectMapper mapper;
    @RequestMapping("ASelectAuthors.do")//管理作者：查询所有作者功能
    public void ASelectAuthors(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        int PageSize=Integer.parseInt(maps.get("PageSize").toString());
        int PageNum=(Integer.parseInt(maps.get("PageNum").toString())-1)*PageSize;
        mapper.writeValue(response.getWriter(), adminService.ASelectAuthors(PageNum,PageSize));
    }
    @RequestMapping("ASelectOneAuthors.do")//管理作者：模糊查询功能
    public void ASelectOneAuthors(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        AuthorVo authorVo=new AuthorVo();authorVo.setPhone(maps.get("phone").toString());authorVo.setAuthorname(maps.get("authorname").toString());
        mapper.writeValue(response.getWriter(), adminService.ASelectOneAuthors(authorVo));
    }
    @RequestMapping("ADeleteAuthors.do")//管理作者：删除作者功能
    public void ADeleteAuthors(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        mapper.writeValue(response.getWriter(), adminService.ADeleteAuthors(new AuthorVo(Integer.parseInt(maps.get("authorno").toString()))));
    }
    @RequestMapping("AUpdateAuthors.do")//管理作者：修改作者功能
    public void AUpdateAuthors(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        AuthorVo authorVo=new AuthorVo(Integer.parseInt(maps.get("authorno").toString()),maps.get("phone").toString(),maps.get("authorname").toString());
        mapper.writeValue(response.getWriter(),adminService.AUpdateAuthors(authorVo));
    }
}
