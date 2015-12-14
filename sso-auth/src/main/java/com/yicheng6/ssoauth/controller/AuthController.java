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
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		String gotoUrl = request.getParameter("gotoUrl");
		model.addAttribute("gotoUrl", gotoUrl);
		return "login";
	}
	
	//登陆提交验证跳转
	@RequestMapping(value = "/logintoauth", method = RequestMethod.POST)
	public String loginToAuth(HttpServletRequest request, Model model) {
		//验证用户名是否存在
		if (!accounts.containsKey(request.getParameter("username"))) {
			//转发方式（核实清楚bug：login方法不能限定为GET方式，request对象不会匹配）
			model.addAttribute("username-error", "用户名不存在！");
			return "forward:/login";
			
			//重定向存储attribute
			//redirectAttributes.addFlashAttribute("username-error", "username not exits!");
			//return "redirect:/login";
		}
		if (!accounts.get(request.getParameter("username")).equals(request.getParameter("password"))) {
			model.addAttribute("password-error", "密码不匹配！");
			return "forward:/login";
		}
		return "test";
	}
	
}
