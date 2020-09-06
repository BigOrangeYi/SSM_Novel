package com.ay.service;

import com.ay.model.BookShelfVo;
import com.ay.model.BookVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookService {
    List<BookVo> FindBookByParam(BookVo books)throws Exception;//根据位置参数查询书籍
    List<BookVo> AllBooks(int PageNum, int PageSize,String type)throws Exception;//分页查询所有书籍
    int addbook(BookShelfVo book)throws Exception;//加入书架
    int DeleteBookShelf(int shelfno)throws Exception;//移出书架
    List<BookShelfVo> Mybookshelf(String phone)throws Exception;//我的书架

    int ADDBook(BookVo bookVo)throws Exception;//增加书籍
    int DeleteBook(BookVo bookVo)throws Exception;//删除书籍
    List<BookVo> QueryMybook(int authorno)throws Exception;//查询我的书籍
    int UpdateBook(BookVo bookVo)throws Exception;//更新书籍信息(封面和介绍)
    List<BookVo> QueryBookName(BookVo bookVo)throws Exception;//查询书名
}
