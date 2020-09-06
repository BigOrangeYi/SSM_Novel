package com.ay.AdminDao;

import com.ay.model.BookVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminBooksDao {
    List<BookVo> ASelectBooks(@Param("PageNum") int PageNum, @Param("PageSize") int PageSize );//查询所有书籍
    int ADeleteBooks(BookVo bookVo);//删除书籍
    int AUpdateBooks(BookVo bookVo);//修改书籍
    List<BookVo>ASelectOneBooks(BookVo bookVo);//模糊查询书籍
}
