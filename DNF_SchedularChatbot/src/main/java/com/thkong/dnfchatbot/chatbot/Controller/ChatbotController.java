package com.thkong.dnfchatbot.chatbot.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thkong.dnfchatbot.chatbot.service.ChatbotService;
import com.thkong.dnfchatbot.chatbot.vo.KakaoRes;
import com.thkong.dnfchatbot.chatbot.vo.SimpleText;
import com.thkong.dnfchatbot.chatbot.vo.Template;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.Action;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.DetailParam;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.KakaoReq;

@RestController
public class ChatbotController {

	@Autowired
	ChatbotService service;
	
	@RequestMapping(value="/test")
	public KakaoRes test(@RequestBody String req) {
		
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
		
		SimpleText simpletext = new SimpleText();

		List<SimpleText> list = new ArrayList<SimpleText>();
		list.add(simpletext);
		
		Template<SimpleText> template = new Template<SimpleText>();
		template.setOutputs(list);
		
		KakaoRes res = new KakaoRes();
		res.setVersion("2.0");
		res.setTemplate(template);
		
		return res;
	}
	
}
