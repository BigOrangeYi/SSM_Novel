package com.ay.AdminController;

import com.ay.AdminService.AdminService;
import com.ay.model.AuthorVo;
import com.ay.model.LogVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class AdminLogs {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ObjectMapper mapper;
    @RequestMapping("ASelectLogs.do")
    public void ASelectLogs(HttpServletResponse response, @RequestBody Map maps) throws Exception {
        int PageSize=Integer.parseInt(maps.get("PageSize").toString());
        int PageNum=(Integer.parseInt(maps.get("PageNum").toString())-1)*PageSize;
        Map map=new HashMap();
        map.put("PageSize",PageSize);
        map.put("PageNum",PageNum);
        map.put("tablename",maps.get("tablename").toString());
        List<LogVo> logVos=adminService.ASelectLogs(map);
        mapper.writeValue(response.getWriter(),logVos );
    }
    @RequestMapping("ADeleteLogs.do")
    public void ADeleteLogs(HttpServletResponse response,@RequestBody Map maps) throws Exception {
        Map map=new HashMap();
        map.put("tablename",maps.get("tablename").toString());
        map.put("logid",Integer.parseInt(maps.get("logid").toString()));
        mapper.writeValue(response.getWriter(), adminService.ADeleteLogs(map));
    }
}
