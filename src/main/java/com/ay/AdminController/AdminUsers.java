package com.ay.AdminController;

import com.ay.AdminDao.AdminUsersDao;
import com.ay.AdminService.AdminService;
import com.ay.model.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AdminUsers {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ObjectMapper mapper;
    @RequestMapping("ASelectAllUsers.do")//管理用户：查询所有用户功能
    public void ASelectAllUsers(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        int PageSize=Integer.parseInt(maps.get("PageSize").toString());
        int PageNum=(Integer.parseInt(maps.get("PageNum").toString())-1)*PageSize;
        mapper.writeValue(response.getWriter(), adminService.ASelectUsers(PageNum,PageSize));
    }
    @RequestMapping("ASelectOneUsers.do")//管理用户：模糊查询功能
    public void ASelectOneUsers(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        UserVo userVo=new UserVo();userVo.setPhone(maps.get("phone").toString());userVo.setReadername(maps.get("readername").toString());
        mapper.writeValue(response.getWriter(), adminService.ASelectOneUsers(userVo));
    }
    @RequestMapping("ADeleteUsers.do")//管理用户：删除用户功能
    public void ADeleteUsers(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        mapper.writeValue(response.getWriter(), adminService.ADeleteUsers(new UserVo(Integer.parseInt(maps.get("id").toString()))));
    }
    @RequestMapping("AUpdateUsers.do")//管理用户：修改用户功能
    public void AUpdateUsers(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        UserVo userVo=new UserVo(Integer.parseInt(maps.get("id").toString()),maps.get("phone").toString(),maps.get("password").toString(),maps.get("readername").toString(),maps.get("headimg").toString());
        mapper.writeValue(response.getWriter(),adminService.AUpdateUsers(userVo));
    }
}
