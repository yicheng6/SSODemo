package com.yicheng6.sso.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter implements Filter {
	
	private String authService;
	
	private String loginUrl;
	
	private String cookieName;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//跳转登陆地址参数
		String param = request.getQueryString();
		//request.getRequestURI() 不包含域名端口
		String URL = loginUrl + "?gotoUrl=" + (param != null ? request.getRequestURL().append("?").append(param).toString() : request.getRequestURL().toString());
		
		//获取指定cookie进行分发
		Cookie ticket = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					ticket = cookie;
					break;
				}	
			}
		}
		if (ticket != null) 
			authCookie(request, response, chain);
		else
			response.sendRedirect(URL);
	}
	
	public void authCookie(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		authService = filterConfig.getInitParameter("authService");
		loginUrl = filterConfig.getInitParameter("loginUrl");
		cookieName = filterConfig.getInitParameter("cookieName");
	}

}
