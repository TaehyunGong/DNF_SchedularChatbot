package com.thkong.dnfchatbot.chatbot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thkong.dnfchatbot.chatbot.Controller.RequestMappings;
import com.thkong.dnfchatbot.chatbot.Controller.ResponseTemplate;
import com.thkong.dnfchatbot.chatbot.dao.ChatbotDao;
import com.thkong.dnfchatbot.chatbot.vo.Items;
import com.thkong.dnfchatbot.chatbot.vo.TodayRating;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.Action;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.DetailParam;
import com.thkong.dnfchatbot.common.httpConnection;

@Service
public class ChatbotServiceImpl implements ChatbotService {

	@Autowired
	ChatbotDao dao;

	/**
	 * @date 2019. 9. 2.
	 * @description 어떠한 에러라도 뜨면 SimpleText로 재시도 메세지 발송
	 */
	@Override
	public String errorMessage() {
		String res = new ResponseTemplate()
				.addSimpleText("점검중이거나 일시적인 오류입니다. 잠시후 다시 시도해주세요.")
				.build();
		return res;
	}
	
	/* 
	 * @see com.thkong.dnfchatbot.chatbot.service.ChatbotService#toDayRating()
	 * @date 2019. 9. 2.
	 * @description rest통신으로 바로 오늘날 등급을 가져온다. ('미래를 보는 지혜'를 기준으로 가져온다.)
	 */
	@Override
	public String toDayRating() throws IOException {
		httpConnection conn = httpConnection.getInstance();
		ObjectMapper objmap = new ObjectMapper();
		
		String apiurl = "https://api.neople.co.kr/df/items/ff3bdb021bcf73864005e78316dd961c/shop?apikey=P4GiGs1KtJyD3VoMB3jkgzDsMI4tDNGi9";
		String responseMsg = conn.HttpGetConnection(apiurl).toString();
		TodayRating eq = objmap.readValue(responseMsg, TodayRating.class);
		
		String res = new ResponseTemplate()
						 .addSimpleText(eq.getItemGradeName())
						 .build();
		
		return res;
	}

	/* 
	 * @see com.thkong.dnfchatbot.chatbot.service.ChatbotService#toDayEquipment()
	 * @date 2019. 9. 2.
	 * @description 오늘날의 장비 등급을 가져온다.
	 */
	@Override
	public String toDayEquipment(String req) throws Exception{
		
		RequestMappings reqMap = RequestMappings.getInstance();
		Action action = reqMap.getAction(req);
		
		DetailParam param = action.getDetailParams().get("equipment");
		String setName = param.getValue();
		
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
		
		return null;
	}

	
	
}
