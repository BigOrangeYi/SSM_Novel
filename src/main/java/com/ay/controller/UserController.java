package com.ay.controller;

import com.ay.model.UserVo;
import com.ay.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Resource
    private UserServiceImpl userService;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private ObjectMapper mapper;
    @RequestMapping("Login.do")//登录功能
    public void Login(HttpServletResponse response, HttpServletRequest request, HttpSession session, @RequestBody Map maps) throws IOException {
        UserVo loginuser = new UserVo(maps.get("phone").toString(), maps.get("password").toString());
        System.out.println(session.getId());
        if (userService.Login(loginuser) == 1) {
            session.setAttribute("userphone", maps.get("phone").toString());
/*            Cookie userLoginer = new Cookie("UserLoginSessionId",session.getId() );
            userLoginer.setMaxAge(-1);
            response.addCookie(userLoginer);
            jedisCluster.hset("UserLoginSessionId",session.getId(),maps.get("phone").toString());
            jedisCluster.expire("UserLoginSessionId",60*30);*/

            Map map = new HashMap();
            map.put("loginresult", true);
            mapper.writeValue(response.getWriter(), map);
        }


    }

    @RequestMapping("Register.do")//注册功能
    public void Register(HttpServletResponse response, HttpServletRequest request, HttpSession session, @RequestBody Map maps) throws IOException {
        String headimg = "images/mr.jpg";
        UserVo newuser = new UserVo(maps.get("p0").toString(), maps.get("p1").toString(), maps.get("p0").toString(), headimg);
        Map map = new HashMap();
        if (session.getAttribute("C").toString().equals(maps.get("code").toString().toString()) == false)//验证码验证失败
        {
            map.put("tip", "验证码错误");
        } else if (userService.Register(newuser) != 1) {
            map.put("tip", "注册失败");
        } else {
            map.put("tip", "注册成功");
        }
        mapper.writeValue(response.getWriter(), map);
    }

    @RequestMapping("FindUserByPhone.do")//手机号检查
    public void FindUserByPhone(@RequestBody Map maps, HttpServletResponse response) throws Exception {//检查用户手机号是否已经被注册
        if (userService.FindUserByPhone(maps.get("phone").toString()) == 1) {
            Map map = new HashMap();
            map.put("R", "已存在");
            mapper.writeValue(response.getWriter(), map);
        }
    }

    @RequestMapping("navsession.do")
    public void navsession(HttpServletResponse response, HttpSession session) throws IOException {//导航栏信息
        if ((session.getAttribute("userphone") != null) && (session.getAttribute("userphone") != "")) {
            try {
                List<UserVo> userVo = userService.navsession(session.getAttribute("userphone").toString());
                if (userVo.size() != 0) {
                    Map<String, String> maps = new HashMap<>();
                    for (UserVo user : userVo) {
                        maps.put("readername", user.getReadername().toString());
                        maps.put("headimg", user.getHeadimg().toString());
                    }

                    mapper.writeValue(response.getWriter(), maps);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    @RequestMapping("upload.do")//更改头像
    public void headimg(MultipartFile photo, HttpSession session, HttpServletResponse response) throws Exception {
        if (!photo.isEmpty()) {
            //String path=session.getServletContext().getRealPath("\\images");
            String path = "D:\\images";
            System.out.println(path);
            String filename = photo.getOriginalFilename();
            filename = session.getAttribute("userphone").toString() + ".jpg";
            if (filename.endsWith(".jpg")) {
                File file = new File(path, filename);
                if (file.exists()) file.delete();
                photo.transferTo(file);
                filename = "/images/" + filename;
                UserVo userVo = new UserVo();
                userVo.setHeadimg(filename);
                userVo.setPhone(session.getAttribute("userphone").toString());
                if (userService.AlterHeadimg(userVo) == 1) {
                    response.getWriter().write("<script>alert('更换成功');window.location.href='Mydetail.html';</script>");
                } else response.getWriter().write("<script>alert('更换失败');window.location.href='Mydetail.html';</script>");
            }

        }
    }

    @RequestMapping("alterpassword.do")//更改昵称密码
    public void alterpassword(@RequestBody Map maps, HttpServletResponse response, HttpSession session) throws Exception {
        UserVo userVo = new UserVo();
        if (maps.get("newreadername") != null && maps.get("newreadername") != "" && maps.get("newpassword") != null && maps.get("newpassword") != "") {
            userVo.setReadername(maps.get("newreadername").toString());
            userVo.setPassword(maps.get("newpassword").toString());
        }

        userVo.setPhone(session.getAttribute("userphone").toString());

        int temp = userService.AlterPassword(userVo);
        if (temp == 1) {

            mapper.writeValue(response.getWriter(), "修改成功");
        } else {

            mapper.writeValue(response.getWriter(), "修改失败");
        }
    }

    @RequestMapping("back.do")//退出登录
    public void back(@RequestBody Map maps, HttpServletResponse response, HttpSession session) throws IOException {
        System.out.println("执行退出");
        session.invalidate();
        mapper.writeValue(response.getWriter(), true);


    }
    @RequestMapping("DeleteUser.do")//注销用户
    public void DeleteUser(@RequestBody Map maps, HttpServletResponse response, HttpSession session)throws Exception {
        if (session.getAttribute("userphone")!=null){
            UserVo userVo=new UserVo();
            userVo.setPhone(session.getAttribute("userphone").toString());
            if (userService.DeleteUser(userVo)==1){
                mapper.writeValue(response.getWriter(), true);
            }else {
                mapper.writeValue(response.getWriter(), false);
            }
        }
    }
    @RequestMapping("FindPassword.do")//找回密码
    public void FindPassword(@RequestBody Map maps, HttpServletResponse response, HttpSession session)throws IOException {
        if (maps.get("phone")!=null&&maps.get("password")!=null) {
            if (maps.get("password").toString().length() >= 3) {
                UserVo userVo = new UserVo();
                userVo.setPhone(maps.get("phone").toString());
                userVo.setPassword(maps.get("password").toString());
                List<UserVo> userVos=userService.FindPassword(userVo);
                if (userVos.size()==1) {

                    mapper.writeValue(response.getWriter(), "找回成功！您的密码："+userVos.get(0).getPassword());
                } else {

                    mapper.writeValue(response.getWriter(), "您的模糊密码查询失败："+maps.get("password").toString());
                }
            }else {

                mapper.writeValue(response.getWriter(), "请检查您的输入");
            }
        }
    }

}
