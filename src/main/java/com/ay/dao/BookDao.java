package com.ay.dao;

import com.ay.model.BookShelfVo;
import com.ay.model.BookVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BookDao {
    List<BookVo> FindBookByParam(BookVo books);//根据位置参数查询书籍
    List<BookVo> AllBooks(@Param("PageNum") int PageNum,@Param("PageSize")int PageSize,@Param("Type") String type);//分页查询所有书籍
    int addbook(BookShelfVo book);//加入书架
    int DeleteBookShelf(int shelfno);//移出书架
    List<BookShelfVo> Mybookshelf(String phone);//我的书架


    int ADDBook(BookVo bookVo);/*增加书籍*/void ADDChapterTable(@Param("ChapterName")String ChapterName);/*调用存储过程建立章节表*/
    int DeleteBook(BookVo bookVo)throws Exception;//删除书籍
    List<BookVo> QueryMybook(int authorno);//查询我的书籍
    int UpdateBook(BookVo bookVo);//更新书籍信息(封面和介绍)
    List<BookVo> QueryBookName(BookVo bookVo);//查询书名
    int QueryMyBookShelf(BookShelfVo book);
}
