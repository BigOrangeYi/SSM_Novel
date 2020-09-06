package com.ay.service;

import com.ay.model.BookVo;

import java.util.List;

public interface PraiseService {
    int UpdatePraiseNumber(int bookno,double totalNum);//更新书籍点赞数量

    int ClearBookPosition();//清空书籍位置信息

    int ResetBookPosition( int bookno, String bookposition);//重新设置书籍位置信息

    List<BookVo> IndexBooks();//查询首页书籍（前22本书籍）
}
