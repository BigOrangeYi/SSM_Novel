package com.ay.AdminDao;

import com.ay.model.AuthorVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdminAuthorsDao {
    List<AuthorVo> ASelectAuthors(@Param("PageNum") int PageNum, @Param("PageSize") int PageSize );//查询所有作者
    int ADeleteAuthors(AuthorVo authorVo);//删除作者
    int AUpdateAuthors(AuthorVo authorVo);//修改作者
    List<AuthorVo>ASelectOneAuthors(AuthorVo authorVo);//模糊查询作者
}
