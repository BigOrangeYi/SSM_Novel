package com.ay.controller;


import com.ay.model.BookShelfVo;
import com.ay.model.BookVo;
import com.ay.service.impl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BookController {
    @Resource
    private BookServiceImpl bookService;
    @Resource
    private ObjectMapper mapper;

    @RequestMapping("zztjs.do")//站长推荐
    public void ZZTJ(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        BookVo books = new BookVo();
        books.setBookposition("站长推荐");
        QueryBookByPosition(response, books);

    }

    @RequestMapping("dstjs.do")//大神推荐
    public void DSTJ(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        BookVo books = new BookVo();
        books.setBookposition("大神推荐");
        QueryBookByPosition(response, books);
    }

    @RequestMapping("xstjs.do")//新书推荐
    public void XSTJ(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        BookVo books = new BookVo();
        books.setBookposition("新书推荐");
        QueryBookByPosition(response, books);
    }

    @RequestMapping("jptjs.do")//精品推荐
    public void JPTJ(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        BookVo books = new BookVo();
        books.setBookposition("精品推荐");
        QueryBookByPosition(response, books);
    }

    @RequestMapping("bookdetail.do")
    public void BOKOKDETAIL(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        BookVo books = new BookVo();
        books.setBookno(Integer.parseInt(maps.get("bookno").toString()));
        QueryBookByPosition(response, books);
    }

    public void QueryBookByPosition(HttpServletResponse response, BookVo book) throws Exception {
        List<BookVo> books = bookService.FindBookByParam(book);
        mapper.writeValue(response.getWriter(), books);

    }

    @RequestMapping("addbook.do")//加入书架
    public int ADDBOOK(HttpServletResponse response, @RequestBody Map maps, HttpSession session) throws IOException {
        if (session.getAttribute("userphone") == null || session.getAttribute("userphone") == "") {
            System.out.println("未登录");
            return 0;
        }
        BookShelfVo book = new BookShelfVo();
        book.setAuthorname(maps.get("authorname").toString());
        book.setBookimg(maps.get("bookimg").toString());
        book.setBookname(maps.get("bookname").toString());
        book.setBookno(Integer.parseInt(maps.get("bookno").toString()));
        book.setPhone(session.getAttribute("userphone").toString());
        int temp = 0;

        try {
            temp = bookService.addbook(book);
        } catch (Exception e) {
            temp = 0;
            System.out.println("书架已经有这本书了");
        }

        if (temp == 1) {

            mapper.writeValue(response.getWriter(), true);
        } else {

            mapper.writeValue(response.getWriter(), false);
        }
        return 0;
    }

    @RequestMapping("Mybookshelf.do")//我的书架
    public void Mybookshelf(HttpServletResponse response, HttpSession session) throws Exception {
        if (session.getAttribute("userphone") != null) {
            List<BookShelfVo> list = bookService.Mybookshelf(session.getAttribute("userphone").toString());
            mapper.writeValue(response.getWriter(), list);
        }

    }
    @RequestMapping("deletebookshelf.do")//移出书架
    public void DeleteBookShelf(HttpServletResponse response, HttpSession session,@RequestBody Map maps) throws Exception {
        if (maps.get("shelfno")!=null&&maps.get("shelfno")!=""){
            System.out.println(maps.get("shelfno").toString());
            int T= bookService.DeleteBookShelf(Integer.parseInt(maps.get("shelfno").toString()));
            if (T==1){
                mapper.writeValue(response.getWriter(), true);
            }
        }

    }
    @RequestMapping("AllBooks.do")
    public void AllBooks(HttpServletResponse response, HttpSession session,@RequestBody Map maps) throws Exception {
        if(maps.get("PageNum")!=null&&maps.get("PageNum")!=""&&maps.get("PageSize")!=null&&maps.get("PageSize")!=""){

            int PageSize=Integer.parseInt(maps.get("PageSize").toString());
            int PageNum=(Integer.parseInt(maps.get("PageNum").toString())-1)*PageSize;
            String Type="";
            if(maps.get("type")!=null&&maps.get("type")!="")
                Type=maps.get("type").toString();
            List<BookVo> booklist=bookService.AllBooks(PageNum,PageSize,Type);

            mapper.writeValue(response.getWriter(), booklist);
        }

    }
    @RequestMapping("newbook.do")
    public void addbook(HttpServletResponse response, HttpSession session,@RequestBody Map maps) throws Exception {
       BookVo bookVo=new BookVo();
       if(maps.get("booktype")!=null&&maps.get("booktype")!=""&&maps.get("bookname")!=null&&maps.get("bookname")!=""&&maps.get("bookintro")!=""&&maps.get("bookintro")!=null) {
           String booktype = maps.get("booktype").toString();
           String bookname = maps.get("bookname").toString();
           String bookintro = maps.get("bookintro").toString();
           String authorname = maps.get("authorname").toString();
           int authorno = Integer.parseInt(maps.get("authorno").toString());
           bookVo.setAuthorno(authorno);
           bookVo.setAuthorname(authorname);
           bookVo.setBookname(bookname);
           bookVo.setBooktype(booktype);
           bookVo.setBookintro(bookintro);
           bookVo.setBookposition("无");
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
           bookVo.setBookbirthday(df.format(new Date()).toString());
           bookVo.setBookpopularity(0);
           bookVo.setBookpublish(0);
           bookVo.setBookimg("images/mrbookimg.jpg");
           if (bookService.ADDBook(bookVo) == 1) {
               mapper.writeValue(response.getWriter(), true);
           } else {
               mapper.writeValue(response.getWriter(), false);
           }
       }
    }
    @RequestMapping("SS.do")
    public void SS(HttpServletResponse response, HttpSession session,@RequestBody Map maps) throws Exception {
        if(maps.get("inputtext")!=null&&maps.get("inputtext")!=""){
            BookVo bookVo=new BookVo();

            bookVo.setAuthorname(maps.get("inputtext").toString());
            List<BookVo> list1=bookService.QueryBookName(bookVo);
            BookVo bookVo2=new BookVo();
            bookVo2.setBookname(maps.get("inputtext").toString());
            List<BookVo> list2=bookService.QueryBookName(bookVo2);
            list1.addAll(list2);

            list1=list1.stream().distinct().collect(Collectors.toList());

            mapper.writeValue(response.getWriter(), list1);
        }

    }
    @RequestMapping("CheckBookName.do")
    public void CheckBookName(HttpServletResponse response, HttpSession session,@RequestBody Map maps) throws Exception {
        if(maps.get("BookName")!=null&&maps.get("BookName")!=""){
            BookVo bookVo=new BookVo();
            bookVo.setBookname(maps.get("BookName").toString());
            List<BookVo> list1=bookService.QueryBookName(bookVo);
            if (list1.size()>0){
                mapper.writeValue(response.getWriter(), true);
            }else {
                mapper.writeValue(response.getWriter(), false);
            }


        }

    }
}

