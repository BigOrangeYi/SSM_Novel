package com.ay.AdminService;
import com.ay.AdminDao.*;
import com.ay.model.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Resource
    private AdminUsersDao adminUsersDao;
    @Resource
    private AdminAuthorsDao adminAuthorsDao;
    @Resource
    private AdminBooksDao adminBooksDao;
    @Resource
    private AdminReplyDao adminReplyDao;
    @Resource
    private AdminLogsDao adminLogsDao;
    //用户管理
    public List<UserVo> ASelectUsers(int PageNum,int PageSize){
        return adminUsersDao.ASelectUsers(PageNum,PageSize);
    };//查询所有用户
    public int ADeleteUsers(UserVo userVo){
        return adminUsersDao.ADeleteUsers(userVo);
    };//删除用户
    public int AUpdateUsers(UserVo userVo){
        return adminUsersDao.AUpdateUsers(userVo);
    };//修改用户
    public List<UserVo>ASelectOneUsers(UserVo userVo){
        return adminUsersDao.ASelectOneUsers(userVo);
    };//模糊查询用户

    //作者管理
    public List<AuthorVo> ASelectAuthors(int PageNum,int PageSize ){return adminAuthorsDao.ASelectAuthors(PageNum,PageSize);};//查询所有作者
    public int ADeleteAuthors(AuthorVo authorVo){return adminAuthorsDao.ADeleteAuthors(authorVo);};//删除作者
    public int AUpdateAuthors(AuthorVo authorVo){return adminAuthorsDao.AUpdateAuthors(authorVo);};//修改作者
    public List<AuthorVo>ASelectOneAuthors(AuthorVo authorVo){return adminAuthorsDao.ASelectOneAuthors(authorVo);};//模糊查询作者

    //书籍管理
    public List<BookVo> ASelectBooks(int PageNum, int PageSize ){return adminBooksDao.ASelectBooks(PageNum,PageSize);};//查询所有作者
    public int ADeleteBooks(BookVo bookVo){return adminBooksDao.ADeleteBooks(bookVo);};//删除作者
    public int AUpdateBooks(BookVo bookVo){return  adminBooksDao.AUpdateBooks(bookVo);};//修改作者
    public List<BookVo>ASelectOneBooks(BookVo bookVo){return  adminBooksDao.ASelectOneBooks(bookVo);};//模糊查询作者

    //评论管理
    public List<ReplyVo> ASelectReplys(int PageNum, int PageSize ){return adminReplyDao.ASelectReplys(PageNum,PageSize);};//查询所有评论
    public int ADeleteReplys(ReplyVo replyVo){return adminReplyDao.ADeleteReplys(replyVo);};//删除评论
    public int AUpdateReplys(ReplyVo replyVo){return  adminReplyDao.AUpdateReplys(replyVo);};//修改评论
    public List<ReplyVo>ASelectOneReplys(ReplyVo replyVo){return  adminReplyDao.ASelectOneReplys(replyVo);};//模糊查询评论

    //日志管理
    public List<LogVo> ASelectLogs(Map map){return adminLogsDao.ASelectLogs(map);};//查询所有评论
    public int ADeleteLogs(Map map){return adminLogsDao.ADeleteLogs(map);};//删除评论
}
