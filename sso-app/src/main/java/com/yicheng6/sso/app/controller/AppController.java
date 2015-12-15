package com.yicheng6.sso.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
	
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
}
