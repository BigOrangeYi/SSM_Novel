package com.ay.filter;

import com.ay.model.BookShelfVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;
import redis.clients.jedis.JedisCluster;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoginFilter implements Filter {
private JedisCluster jedisCluster;
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest Request = (HttpServletRequest)request;
        HttpServletResponse Response = (HttpServletResponse)response;
        HttpSession session = Request.getSession(true);




        //安全过滤
        String fUrl = Request.getServletPath();
        String[] sUrl = {"/LoginAndRegister.html","/index.html","/books.html","/bookdetail.html","/chapter.html","/chapterdetail.html","/forget.html"};
        String u = ((HttpServletRequest) request).getRequestURI();
        if(u.contains(".ico")||u.contains(".do")||u.contains(".css")||u.contains(".jpg")||u.contains(".png")||(u.contains(".js")&& (!u.contains("jsp"))))
        {
            chain.doFilter(Request, Response);
            return;
        }
        for(String url : sUrl)
        {
            System.out.println("url："+url);
            if(url.equals(fUrl))
            {

                chain.doFilter(Request, Response);
                return;
            }
        }


/*        Cookie[] cookies = Request.getCookies();

        String cookieValue = "";
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("UserLoginSessionId")){
                    cookieValue = cookie.getValue();
                }
            }
        }*/
/*        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("8080cookieValue："+cookieValue);
        ApplicationContext beans = WebApplicationContextUtils.getWebApplicationContext(Request.getSession().getServletContext());
        jedisCluster=(JedisCluster)beans.getBean("jedisCluster");

        if (jedisCluster.hget("UserLoginSessionId",cookieValue)!=null){
            session.setAttribute("userphone",jedisCluster.hget("UserLoginSessionId",cookieValue));
        }
        System.out.println("redisValue:"+jedisCluster.hget("UserLoginSessionId",cookieValue));*/
        String username = (String)session.getAttribute("userphone");
        System.out.println(fUrl);
        if (fUrl.startsWith("/A")){
            if (username.equals("admin")){
                chain.doFilter(Request, Response);
                return;
            }else {
                username=null;
            }
        }
        if(username!=null){
            System.out.println("已判断用户不为空");
            chain.doFilter(Request, Response);
        }else {
            System.out.println("已判断用户为空");
            Response.getWriter().print("<script>alert('请先登录');location.href='./index.html'</script>");
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}

