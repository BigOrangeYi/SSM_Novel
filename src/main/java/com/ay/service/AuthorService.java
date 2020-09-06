package com.ay.service;

import com.ay.model.AuthorVo;
import com.ay.model.BookVo;

import java.util.List;

public interface AuthorService {
    int AddAuthor(AuthorVo authorVo)throws Exception;//增加作者
    List<AuthorVo> QueryAuthorByPhone(AuthorVo authorVo)throws Exception;//根据参数查询作者
    List<AuthorVo> QueryAuthorByName(AuthorVo authorVo)throws Exception;//根据笔名查询作者
    int DeleteAuthor(AuthorVo authorVo)throws Exception;//删除作者
    int AlterBookImg(BookVo bookVo)throws Exception;//修改封面
    int UDeleteAuthor(int authorno)throws Exception;//删除作者
    int DeleteWork(int bookno)throws Exception;//删除作品
}
