package com.ay.dao;

import com.ay.model.BookVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PraiseDao {
    int UpdatePraiseNumber(@Param("bookno") int bookno, @Param("totalNum") double totalNum);//更新书籍点赞数量
    int ClearBookPosition();//清空书籍位置信息
    int ResetBookPosition(@Param("bookno") int bookno, @Param("bookposition") String bookposition);//重新设置书籍位置信息
    List<BookVo> IndexBooks();//查询首页书籍（前22本书籍）

}
