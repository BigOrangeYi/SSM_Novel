package com.ay.dao;

import com.ay.model.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int Login(UserVo loginuser);//登录
    int Register( UserVo newuser);//注册
    List<UserVo> FindUserByPhone(String phone);//查询手机号是否存在
    List<UserVo> navsession(String userphone);//查询导航栏session信息（头像，昵称）
    List<String> AutoComplete1(@Param("inputtext") String inputtext);//自动补全功能（模糊查找）
    List<String> AutoComplete2(@Param("inputtext")String inputtext);//自动补全功能（模糊查找）
    int AlterHeadimg(UserVo userVo);//更换头像
    int AlterPassword(UserVo userVo);//更换密码
    int DeleteUser(UserVo userVo);//注销用户
    List<UserVo> FindPassword(UserVo userVo);//找回密码
}
