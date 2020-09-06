package com.ay.service.impl;

import com.ay.dao.PraiseDao;
import com.ay.model.BookVo;
import com.ay.service.PraiseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PraiseServiceImpl  implements PraiseService {
    @Resource
    private PraiseDao praiseDao;
    @Override
    public int UpdatePraiseNumber(int bookno, double totalNum) {//更新点赞数量
        return praiseDao.UpdatePraiseNumber(bookno, totalNum);
    }

    @Override
    public int ClearBookPosition() {//清空书籍位置信息
        return praiseDao.ClearBookPosition();
    }

    @Override
    public int ResetBookPosition(int bookno, String bookposition) {
        return praiseDao.ResetBookPosition(bookno, bookposition);
    }

    @Override
    public List<BookVo> IndexBooks() {
        return praiseDao.IndexBooks();
    }

}
