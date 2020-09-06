package com.ay.AdminDao;

import com.ay.model.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface AdminUsersDao {
    List<UserVo> ASelectUsers(@Param("PageNum") int PageNum, @Param("PageSize") int PageSize );//查询所有用户
    int ADeleteUsers(UserVo userVo);//删除用户
    int AUpdateUsers(UserVo userVo);//修改用户
    List<UserVo>ASelectOneUsers(UserVo userVo);//模糊查询用户
}
