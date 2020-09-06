package com.ay.controller;

import com.ay.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AutoCompleteController {//自动补全
    @Resource
    private UserServiceImpl userService;

    @RequestMapping("AutoComplete.do")
    public void AutoComplete(HttpServletResponse response, HttpServletRequest request, HttpSession session ,
                      @RequestBody Map maps)throws IOException {
        List<String> list1 =userService.AutoComplete1(maps.get("inputtext").toString());
        List<String> list2 =userService.AutoComplete2(maps.get("inputtext").toString());
        list1.addAll(list2);
        List<String>list=list1.stream().distinct().collect(Collectors.toList());
        //java8 stream api去重， Collectors.toList方法是获取list类型的收集器  distinct方法进行去重  collect进行转换
        System.out.println(list);
        String str = "";
        if(list.size()>0) {
            for(int i=0;i<list.size();i++) {
                if(i>0) {
                    str+=",";
                }
                str+=list.get(i);
            }
          /*  str+=",";
            for(int i=0;i<list2.size();i++) {//找书
                if(i>0) {
                    str+=",";
                }
                str+=list2.get(i);
            }*/
            //将处理好的数据传回给客户端
            response.getWriter().write(str);
        }




    }

}
