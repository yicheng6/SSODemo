package com.yicheng6.sso.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yicheng6.sso.auth.model.AuthToken;
import com.yicheng6.sso.auth.util.AuthIdManager;

@Controller
public class AuthController {

	private Map<String, String> accounts;

	private Map<String, AuthToken> tokens;

	// 获取sso.properties参数
	@Value("cookieName")
	private String cookieName;

	@Value("domainName")
	private String domainName;

	private AuthIdManager authIdManager;

	// 初始化账号信息
	@PostConstruct
	public void initConfig() {
		accounts = new HashMap<String, String>();
		accounts.put("yicheng6", "yicheng6");

		tokens = new HashMap<String, AuthToken>();
		authIdManager = new AuthIdManager();
	}

	// 获取登陆页面
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		String gotoUrl = request.getParameter("gotoUrl");
		model.addAttribute("gotoUrl", gotoUrl);
		return "login";
	}

	// 登陆提交验证跳转
	@RequestMapping(value = "/logintoauth", method = RequestMethod.POST)
	public String loginToAuth(HttpServletRequest request, Model model,
			HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 验证用户名是否存在
		if (!accounts.containsKey(username)) {
			// 转发方式（核实清楚bug：login方法不能限定为GET方式，request对象不会匹配）
			model.addAttribute("username-error", "用户名不存在！");
			return "forward:/login";

			// 重定向存储attribute
			// redirectAttributes.addFlashAttribute("username-error",
			// "username not exits!");
			// return "redirect:/login";
		}
		if (!accounts.get(username).equals(password)) {
			model.addAttribute("password-error", "密码不匹配！");
			return "forward:/login";
		}

		// 取得唯一随机Id
		String tokenKey = null;
		do {
			tokenKey = authIdManager.generateAuthId(10);
		} while (tokens.containsKey(tokenKey));

		AuthToken token = new AuthToken();
		token.setUsername(username);

		// 将cookie内容以令牌放入全局内存变量中
		tokens.put(tokenKey, token);

		// 返回response对象中添加cookie
		Cookie cookie = new Cookie(cookieName, tokenKey);
		cookie.setDomain(domainName);
		cookie.setPath("/");
		response.addCookie(cookie);

		String gotoURL = request.getParameter("gotoURL");
		return "redirect:" + gotoURL;
	}

}
