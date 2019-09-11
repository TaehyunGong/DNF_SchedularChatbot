package com.thkong.dnfchatbot.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

	@RequestMapping(value="/")
	public String index() {
		return "index";
	}
	
}
