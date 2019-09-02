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
import com.thkong.dnfchatbot.chatbot.vo.Equipment.Equipment;
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
		
		String apiurl = "https://api.neople.co.kr/df/items/ff3bdb021bcf73864005e78316dd961c/shop?apikey=7gW7GbmqkpcFLERS0FT8S9RIK5O1257V";
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
		
		//Kakao에서 받은 request json을 Action 객체로 파싱 후 setName를 가져온다. 
		RequestMappings reqMap = RequestMappings.getInstance();
		Action action = reqMap.getAction(req);
		DetailParam param = action.getDetailParams().get("equipment");
		String setName = param.getValue();
		
		List<Equipment> equipList = dao.getSetEquipment(setName);
		
		String title = equipList.get(0).getSetItemName();
		String titleImageUrl = equipList.get(0).getSetItemId();
		
		//ItemList 메세지 형식을 반환시키기 위해 Item객체를 리스트로 만들어 넘겨준다.
		List<Items> items = new ArrayList<Items>();
		
		for(Equipment equip : equipList) {
			items.add(getItems(equip));
		}
		
		String res = new ResponseTemplate()
						.addListItem(title,titleImageUrl, items)
						.build();
		
		return res;
	}

	/**
	 * @throws IOException 
	 * @date 2019. 9. 2.
	 * @description 아이템 Id를 받아 오늘날의 등급정보를 가져온다.
	 */
	public Items getItems(Equipment equip) throws IOException {
		Items item = new Items();
		
		ObjectMapper objmap = new ObjectMapper();
		httpConnection conn = httpConnection.getInstance();
		String responseMsg = conn.HttpGetConnection("https://api.neople.co.kr/df/items/" + equip.getItemId() + "/shop?apikey=7gW7GbmqkpcFLERS0FT8S9RIK5O1257V").toString();
		Equipment eq = objmap.readValue(responseMsg, Equipment.class);
		
		item.setTitle(equip.getItemName());
		item.setImageUrl("https://img-api.neople.co.kr/df/items/"+equip.getItemId());
		item.setDescription(equip.getItemExplain());
		
		return item;
	}
	
}
