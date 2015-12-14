package com.yicheng6.ssoauth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
	
	private Map<String, String> accounts;
	
	//初始化账号信息
	@PostConstruct
	public void initConfig() {
		accounts = new HashMap<String, String>();
		accounts.put("yicheng6", "yicheng6");
	}

	//获取登陆页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		String gotoUrl = request.getParameter("gotoUrl");
		model.addAttribute("gotoUrl", gotoUrl);
		return "login";
	}
	
	//登陆提交验证跳转
	@RequestMapping(value = "/logintoauth", method = RequestMethod.POST)
	public String loginToAuth(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		//验证用户名是否存在
		if(!accounts.containsKey(request.getParameter("username"))) {
//			转发方式
//			request.setAttribute("username-error", "username not exits!");
//			return "forward:/login";
			
			//重定向存储attribute
			redirectAttributes.addFlashAttribute("username-error", "username not exits!");
			return "redirect:/login";
		}
		return "test";
	}
	
}
