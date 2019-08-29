package com.thkong.dnfchatbot.chatbot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thkong.dnfchatbot.chatbot.service.ChatbotService;
import com.thkong.dnfchatbot.chatbot.vo.ItemGrade;

@RestController
public class ChatbotController {

	@Autowired
	ChatbotService service;
	
	@RequestMapping(value="/grade")
	public ItemGrade test() {
		return service.getGrade();
	}
	
}
