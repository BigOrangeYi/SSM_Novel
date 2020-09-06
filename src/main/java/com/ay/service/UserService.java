package com.ay.service;

import com.ay.model.UserVo;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.validation.Valid;

public interface UserService {
    int Login(UserVo loginuser)throws Exception;//登录
    int Register(UserVo newuser)throws Exception;//注册
    int FindUserByPhone(String phone)throws Exception;//查询手机号是否存在
    List<UserVo> navsession(String userphone)throws Exception;//查询导航栏session信息（头像，昵称）
    List<String> AutoComplete1(String inputtext);//自动补全功能（模糊查找）
    List<String> AutoComplete2(String inputtext);//自动补全功能（模糊查找）
    int AlterHeadimg(UserVo userVo)throws Exception;//更换头像
    int AlterPassword(UserVo userVo)throws Exception;//更换密码

    int DeleteUser(UserVo userVo)throws Exception;//注销用户
    List<UserVo> FindPassword(UserVo userVo);//找回密码
}
