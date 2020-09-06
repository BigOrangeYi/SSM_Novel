package com.ay.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//编码过滤
public class EncodingFilter implements Filter {
    private String newCharSet;
    public void setNewCharSet(String newCharSet){
    	this.newCharSet = newCharSet;
        } 
	public void destroy() {}

	public void doFilter(ServletRequest request,ServletResponse response, FilterChain chain) throws IOException,ServletException {
		HttpServletRequest Request = (HttpServletRequest)request;
		HttpServletResponse Response = (HttpServletResponse)response;
		String contenType = Request.getHeader("Accept");
		Request.setCharacterEncoding(newCharSet);
		Response.setCharacterEncoding(newCharSet);
		response.setContentType(contenType.contains("text/html") ? "text/html;charset=utf-8" :   "text/css;charset=utf-8");
		//response.setContentType("text/html;charset=utf-8");
		
		chain.doFilter(request,response); 
		
		}
	public void init(FilterConfig arg0) throws ServletException {
	
    	if(arg0.getInitParameter("newcharset")!=null){
            setNewCharSet(arg0.getInitParameter("newcharset"));}
   		else  setNewCharSet("utf-8");
		}
	}


