package com.ay.controller;

import com.ay.model.AuthorVo;
import com.ay.model.BookVo;
import com.ay.service.impl.AuthorServiceImpl;
import com.ay.service.impl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
public class AuthorController {
    @Resource
    private AuthorServiceImpl authorService;
    @Resource
    private BookServiceImpl bookService;

    @Resource
    private ObjectMapper mapper;

    @RequestMapping("DeleteAuthor.do")//删除作者
    public void DeleteAuthor(@RequestBody Map maps,HttpServletResponse response, HttpSession session) throws Exception {//删除作者
    if (maps.get("authorno")!=null){
        if (authorService.UDeleteAuthor(Integer.parseInt(maps.get("authorno").toString()))==1){
            mapper.writeValue(response.getWriter(), true);
        }
        else {
            mapper.writeValue(response.getWriter(), false);
        }
    }
    }

    @RequestMapping("DeleteWork.do")//删除作品
    public void DeleteWork(@RequestBody Map maps,HttpServletResponse response, HttpSession session) throws Exception {//删除作者
        if (maps.get("bookno")!=null){
            if (authorService.DeleteWork(Integer.parseInt(maps.get("bookno").toString()))==1){

                mapper.writeValue(response.getWriter(), true);
            }
            else {

                mapper.writeValue(response.getWriter(), false);
            }
        }
    }
    @RequestMapping("authorexist.do")//查询作者
    public void authorexist(HttpServletResponse response, HttpSession session)throws Exception {
        AuthorVo authorVo=new AuthorVo();


        if (session.getAttribute("userphone")!=null&&session.getAttribute("userphone")!="") {
            authorVo.setPhone(session.getAttribute("userphone").toString());
            System.out.println(session.getAttribute("userphone"));
            System.out.println(authorVo.toString());
            List<AuthorVo> author = authorService.QueryAuthorByPhone(authorVo);
            if (author.size() > 0) {

                response.getWriter().write("<script>alert('欢迎作者');window.location.href='author.html';</script>");
                mapper.writeValue(response.getWriter(), true);//是作者
            }else {
                response.getWriter().write("<script>alert('请先去个人中心成为作者');window.location.href='Mydetail.html';</script>");
      /*          mapper.writeValue(response.getWriter(), false);*///不是作者
            }
        }
        else {
            response.getWriter().write("<script>alert('请先登录');window.location.href='LoginAndRegister.html';</script>");
           /* mapper.writeValue(response.getWriter(), "未登录");*///未登录
        }
    }
    @RequestMapping("authorexist2.do")//查询作者
    public void authorexist2(HttpServletResponse response, HttpSession session)throws Exception {
        AuthorVo authorVo=new AuthorVo();
        if (session.getAttribute("userphone")!=null&&session.getAttribute("userphone")!="") {
            authorVo.setPhone(session.getAttribute("userphone").toString());
            System.out.println(session.getAttribute("userphone"));
            System.out.println(authorVo.toString());
            List<AuthorVo> author = authorService.QueryAuthorByPhone(authorVo);
            if (author.size() > 0) {

                mapper.writeValue(response.getWriter(), true);//是作者
            }else {
                response.getWriter().write("<script>alert('请先去个人中心成为作者');window.location.href='Mydetail.html';</script>");
                /*          mapper.writeValue(response.getWriter(), false);*///不是作者
            }
        }
        else {
            response.getWriter().write("<script>alert('请先登录');window.location.href='LoginAndRegister.html';</script>");
            /* mapper.writeValue(response.getWriter(), "未登录");*///未登录
        }
    }
    @RequestMapping("addauthor.do")//增加作者
    public void addauthor(@RequestBody Map maps,HttpServletResponse response, HttpSession session)throws Exception {
        AuthorVo authorVo=new AuthorVo();
        if (session.getAttribute("userphone").toString()!=null&&session.getAttribute("userphone").toString()!="")
            authorVo.setPhone(session.getAttribute("userphone").toString());
        if(maps.get("authorname")!=null&&maps.get("authorname")!="")
            authorVo.setAuthorname(maps.get("authorname").toString());
        try{
            if(authorService.AddAuthor(authorVo)==1){
                mapper.writeValue(response.getWriter(), true);//增加成功
            }
        }catch (Exception e){
            mapper.writeValue(response.getWriter(), false);//增加失败

        }


    }
    @RequestMapping("bnameexist.do")//查询笔名
    public void bnameexist(@RequestBody Map maps,HttpServletResponse response, HttpSession session)throws Exception {
        AuthorVo authorVo=new AuthorVo();


        if (session.getAttribute("userphone")!=null&&session.getAttribute("userphone")!="") {
            if(maps.get("authorname")!=null&&maps.get("authorname")!="") {
                authorVo.setAuthorname(maps.get("authorname").toString());

                System.out.println(authorVo.toString());
                List<AuthorVo> author = authorService.QueryAuthorByName(authorVo);
                if (author.size() > 0) {
                    mapper.writeValue(response.getWriter(), true);//是笔名存在
                }
            }
        }
    }
    @RequestMapping("SelectAuthor.do")
    public void SelectAuthor(@RequestBody Map maps,HttpServletResponse response, HttpSession session)throws Exception {
        AuthorVo authorVo=new AuthorVo();
        authorVo.setPhone(session.getAttribute("userphone").toString());
        List<AuthorVo> authorVoList=authorService.QueryAuthorByPhone(authorVo);
        mapper.writeValue(response.getWriter(), authorVoList);
    }
    @RequestMapping("MyBooks.do")
    public void MyBooks(@RequestBody Map maps,HttpServletResponse response, HttpSession session)throws Exception {
        if(maps.get("authorno")!=null&&maps.get("authorno")!=""){
            System.out.println(maps.get("authorno"));
        List<BookVo> myBooksList=bookService.QueryMybook(Integer.parseInt(maps.get("authorno").toString()));
            System.out.println(myBooksList.size());
        mapper.writeValue(response.getWriter(), myBooksList);
        }
    }
    @RequestMapping("AlterBookImg.do")
    public void AlterBookimg(@RequestParam("bookno") int bookno, MultipartFile photo, HttpSession session, HttpServletRequest request, HttpServletResponse response)throws Exception {
        if (!photo.isEmpty()) {
            //String path=session.getServletContext().getRealPath("\\images");
            String path = "D:\\bookimg\\images";
            System.out.println(path);
            String filename = photo.getOriginalFilename();
            filename = bookno + ".jpg";
            if (filename.endsWith(".jpg")) {
                File file = new File(path, filename);
                if (file.exists()) file.delete();
                photo.transferTo(file);
                filename = "/bookimg/images/" + filename;
                BookVo bookVo = new BookVo();
                bookVo.setBookimg(filename);
                bookVo.setBookno(bookno);
                if (authorService.AlterBookImg(bookVo) == 1) {
                    response.getWriter().write("<script>alert('上传成功');window.location.href='author.html';</script>");
                } else response.getWriter().write("<script>alert('上传成功');window.location.href='author.html';</script>");
            }
        }
    }
}
