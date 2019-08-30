package com.thkong.dnfchatbot.chatbot.Controller;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thkong.dnfchatbot.chatbot.service.ChatbotService;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.Action;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.DetailParam;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.KakaoReq;

@RestController
public class ChatbotController {

	@Autowired
	ChatbotService service;
	
	@RequestMapping(value="/test", produces = "application/json; charset=UTF-8")
	public String test(@RequestBody String req) {
		
		ObjectMapper mapper = new ObjectMapper();
		KakaoReq ob = null;
		try {
			ob = mapper.readValue(req, KakaoReq.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Action action = ob.getAction();
		DetailParam param = action.getDetailParams().get("armor");
		
		String setName = param.getValue();
		
		//----------------------------------------------------------
		
		String res = new ResponseTemplate().addSimpleText("추가").build();
		
		return res;
	}
	
}
