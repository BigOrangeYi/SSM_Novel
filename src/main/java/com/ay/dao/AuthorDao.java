package com.ay.dao;

import com.ay.model.AuthorVo;
import com.ay.model.BookVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorDao {
    int AddAuthor(AuthorVo authorVo);//增加作者
    List<AuthorVo> QueryAuthorByPhone(AuthorVo authorVo);//根据phone查询作者
    List<AuthorVo> QueryAuthorByName(AuthorVo authorVo);//根据笔名查询作者
    int DeleteAuthor(AuthorVo authorVo);//删除作者
    int AlterBookImg(BookVo bookVo);//修改封面
    int UDeleteAuthor(int authorno);//删除作者
    int DeleteWork(@Param("bookno") int bookno);//删除作品
}
