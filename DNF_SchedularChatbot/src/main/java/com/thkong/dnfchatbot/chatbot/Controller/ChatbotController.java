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
import com.thkong.dnfchatbot.chatbot.vo.Items;
import com.thkong.dnfchatbot.chatbot.vo.TodayRating;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.Action;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.DetailParam;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.KakaoReq;
import com.thkong.dnfchatbot.common.httpConnection;

@RestController
@RequestMapping(value="/chat", produces = "application/json; charset=UTF-8")
public class ChatbotController {

	@Autowired
	ChatbotService service;
	
	@RequestMapping(value="/rating")
	public String toDayRating() {
		
		httpConnection conn = httpConnection.getInstance();
		ObjectMapper objmap = new ObjectMapper();
		
		String apiurl = "https://api.neople.co.kr/df/items/ff3bdb021bcf73864005e78316dd961c/shop?apikey=secretkey";
		String responseMsg;
		TodayRating eq = null;
		try {
			responseMsg = conn.HttpGetConnection(apiurl).toString();
			eq = objmap.readValue(responseMsg, TodayRating.class);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return new ResponseTemplate().addSimpleText(eq.getItemGradeName()).build();
	}
	
	@RequestMapping(value="/equipment")
	public String equipment(@RequestBody String req) {
		
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
		
		List<Items> items = new ArrayList<Items>();
		
		for(int i=0; i<3; i++) {
			Items item = new Items();
			item.setTitle(i+"");
			item.setDescription("설명");
			item.setImageUrl("이미지");
			items.add(item);
		}
		
		String res = new ResponseTemplate()
						.addListItem("제목","제목이미지", items)
						.addSimpleText("테스트입니다.")
						.build();
		
		return res;
	}
	
}
