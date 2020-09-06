package com.ay.AdminController;

import com.ay.AdminService.AdminService;
import com.ay.model.AuthorVo;
import com.ay.model.BookVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AdminBooks {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ObjectMapper mapper;
    @RequestMapping("ASelectBooks.do")//管理书籍：查询所有书籍功能
    public void ASelectBooks(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        int PageSize=Integer.parseInt(maps.get("PageSize").toString());
        int PageNum=(Integer.parseInt(maps.get("PageNum").toString())-1)*PageSize;
        mapper.writeValue(response.getWriter(), adminService.ASelectBooks(PageNum,PageSize));
    }
    @RequestMapping("ASelectOneBooks.do")//管理书籍：模糊查询功能
    public void ASelectOneBooks(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        BookVo bookVo=new BookVo();bookVo.setBookname(maps.get("bookname").toString());bookVo.setAuthorname(maps.get("authorname").toString());
        mapper.writeValue(response.getWriter(), adminService.ASelectOneBooks(bookVo));
    }
    @RequestMapping("ADeleteBooks.do")//管理书籍：删除书籍功能
    public void ADeleteBooks(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        mapper.writeValue(response.getWriter(), adminService.ADeleteBooks(new BookVo(Integer.parseInt(maps.get("bookno").toString()))));
    }
    @RequestMapping("AUpdateBooks.do")//管理书籍：修改书籍功能
    public void AUpdateBooks(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        BookVo bookVo=new BookVo();
        bookVo.setBookname(maps.get("bookname").toString());
        bookVo.setBookno(Integer.parseInt(maps.get("bookno").toString()));
        bookVo.setBookimg(maps.get("bookimg").toString());
        bookVo.setAuthorno(Integer.parseInt(maps.get("authorno").toString()));
        bookVo.setAuthorname(maps.get("bookname").toString());
        bookVo.setBookpopularity(Integer.parseInt(maps.get("bookpopularity").toString()));
        bookVo.setBookbirthday(maps.get("bookbirthday").toString());
        bookVo.setBookposition(maps.get("bookposition").toString());
        bookVo.setBookintro(maps.get("bookintro").toString());
        bookVo.setBooktype(maps.get("booktype").toString());
        bookVo.setBookpublish(Integer.parseInt(maps.get("bookpublish").toString()));
        mapper.writeValue(response.getWriter(),adminService.AUpdateBooks(bookVo));
    }
}
