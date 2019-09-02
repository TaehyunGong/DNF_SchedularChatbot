package com.thkong.dnfchatbot.chatbot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thkong.dnfchatbot.chatbot.service.ChatbotService;

@RestController
@RequestMapping(value="/chat", produces = "application/json; charset=UTF-8")
public class ChatbotController {

	@Autowired
	ChatbotService service;
	
	/**
	 * @date 2019. 9. 2.
	 * @description rest통신으로 바로 오늘날 등급을 가져온다. ('미래를 보는 지혜'를 기준으로 가져온다.)
	 */
	@RequestMapping(value="/rating")
	public String toDayRating() {
		String res = null;
		
		try {
			res = service.toDayRating();
		} catch (Exception e) {
			// 어떠한 에러라도 발생시 에러 메세지 발송
			res = service.errorMessage();
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * @date 2019. 9. 2.
	 * @description 오늘날의 장비 등급을 가져온다.
	 */
	@RequestMapping(value="/equipment")
	public String equipment(@RequestBody String req) {
		String res = null;
		
		try {
			res = service.toDayEquipment(req);
		} catch (Exception e) {
			res = service.errorMessage();
			e.printStackTrace();
		}
		
		return res;
	}
	
}
