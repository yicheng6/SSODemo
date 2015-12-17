package com.yicheng6.sso.app.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.yicheng6.sso.app.model.ReceiveMessage;

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

		// 跳转登陆地址参数
		String param = request.getQueryString();
		// request.getRequestURI() 不包含域名端口
		String URL = loginUrl
				+ "?gotoUrl="
				+ (param != null ? request.getRequestURL().append("?")
						.append(param).toString() : request.getRequestURL()
						.toString());

		// 获取指定cookie进行分发
		Cookie tokenCookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					tokenCookie = cookie;
					break;
				}
			}
		}
		if (tokenCookie != null)
			authCookie(request, response, chain, tokenCookie.getValue(), URL);
		else
			response.sendRedirect(URL);
	}

	public void authCookie(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, String tokenKey, String URL)
			throws IOException, ServletException {
		String param = "tokenKey=" + tokenKey;
		String message = sendPost(param);
		System.out.println(message);
		ReceiveMessage receiveMessage = new ObjectMapper().readValue(message, ReceiveMessage.class);
		if ("200".equals(receiveMessage.getCode())) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(URL);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		authService = filterConfig.getInitParameter("authService");
		loginUrl = filterConfig.getInitParameter("loginUrl");
		cookieName = filterConfig.getInitParameter("cookieName");
	}

	private String sendPost(String params) {
		OutputStreamWriter writer = null;
		BufferedReader reader = null;
		StringBuffer result = new StringBuffer();
		
		try {
			URL postUrl = new URL(authService);
			URLConnection conn = postUrl.openConnection();
			
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(params);
			writer.flush();
			
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result.toString();
	}
}
