package com.thkong.dnfchatbot.chatbot.service;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thkong.dnfchatbot.chatbot.Controller.ResponseTemplate;
import com.thkong.dnfchatbot.chatbot.dao.ChatbotDao;
import com.thkong.dnfchatbot.chatbot.vo.ItemGrade;
import com.thkong.dnfchatbot.chatbot.vo.TodayRating;
import com.thkong.dnfchatbot.common.httpConnection;

@Service
public class ChatbotServiceImpl implements ChatbotService {

	@Autowired
	ChatbotDao dao;

	/* 
	 * @see com.thkong.dnfchatbot.chatbot.service.ChatbotService#toDayRating()
	 * @date 2019. 9. 2.
	 * @description rest통신으로 바로 오늘날 등급을 가져온다. ('미래를 보는 지혜'를 기준으로 가져온다.)
	 */
	@Override
	public String toDayRating() {
		httpConnection conn = httpConnection.getInstance();
		ObjectMapper objmap = new ObjectMapper();
		
		String apiurl = "https://api.neople.co.kr/df/items/ff3bdb021bcf73864005e78316dd961c/shop?apikey=P4GiGs1KtJyD3VoMB3jkgzDsMI4tDNGi";
		String res = null;
		String message = null; 
		
		try {
			String responseMsg = conn.HttpGetConnection(apiurl).toString();
			TodayRating eq = objmap.readValue(responseMsg, TodayRating.class);
			message = eq.getItemGradeName();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(res==null) {
			res = "";
		}
		
		if(message==null) {
			message = "점검중이거나 일시적인 오류입니다. 잠시후 다시 시도해주세요.";
		}
		
		res = new ResponseTemplate()
				.addSimpleText(message)
				.build();
		
		return res;
	}

	
	
}
