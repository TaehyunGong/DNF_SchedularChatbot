package com.thkong.dnfchatbot.chatbot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thkong.dnfchatbot.chatbot.Controller.RequestMappings;
import com.thkong.dnfchatbot.chatbot.Controller.ResponseTemplate;
import com.thkong.dnfchatbot.chatbot.dao.ChatbotDao;
import com.thkong.dnfchatbot.chatbot.vo.Items;
import com.thkong.dnfchatbot.chatbot.vo.TodayRating;
import com.thkong.dnfchatbot.chatbot.vo.Equipment.Equipment;
import com.thkong.dnfchatbot.chatbot.vo.Equipment.ItemSetOption;
import com.thkong.dnfchatbot.chatbot.vo.Equipment.ItemStatus;
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
	
	/**
	 * @date 2019. 9. 2.
	 * @description 아이템 아이디를 가지고 rest통신 전용 메소드
	 */
	public String connItemStatus(String itemId) throws IOException {
		httpConnection conn = httpConnection.getInstance();
		String apiurl = "https://api.neople.co.kr/df/items/" + itemId + "/shop?apikey=7gW7GbmqkpcFLERS0FT8S9RIK5O1257V";
		return conn.HttpGetConnection(apiurl).toString();
	}
	
	/* 
	 * @see com.thkong.dnfchatbot.chatbot.service.ChatbotService#toDayRating()
	 * @date 2019. 9. 2.
	 * @description rest통신으로 바로 오늘날 등급을 가져온다. ('미래를 보는 지혜'를 기준으로 가져온다.)
	 */
	@Override
	public String toDayRating() throws IOException {
		ObjectMapper objmap = new ObjectMapper();
		
		String responseMsg = connItemStatus("ff3bdb021bcf73864005e78316dd961c");
		TodayRating eq = objmap.readValue(responseMsg, TodayRating.class);
		String text = eq.getItemGradeName()+"("+eq.getItemGradeValue()+"%)";
		
		String res = new ResponseTemplate()
						 .addSimpleText(text)
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
		DetailParam option = action.getDetailParams().get("option");
		
		String setName = param.getValue();
		
		List<ItemSetOption> equipList = dao.getSetEquipment(setName);
		
		String responseMsg = connItemStatus("ff3bdb021bcf73864005e78316dd961c");
		TodayRating eq = new ObjectMapper().readValue(responseMsg, TodayRating.class);
		
		String title = equipList.get(0).getSetItemName().replace("세트", "("+eq.getItemGradeValue()+"%)");
		String titleImageUrl = null;
		
		//ItemList 메세지 형식을 반환시키기 위해 Item객체를 리스트로 만들어 넘겨준다.
		List<Items> items = new ArrayList<Items>();
		
		for(ItemSetOption equip : equipList) {
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
	public Items getItems(ItemSetOption equip) throws IOException {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>(); 
		String[] arr = equip.getOption().split(",");
		for(String option : arr) {
			String[] a = option.split("\\+");
			Map<String, String> m = new HashMap<String ,String>();
			m.put(a[0], a[1]);
			mapList.add(m);
		}
		Items item = new Items();
		
		ObjectMapper objmap = new ObjectMapper();
		String responseMsg = connItemStatus(equip.getItemId());
		
		Equipment eq = objmap.readValue(responseMsg, Equipment.class);

		String description = "";
		
		for(Map<String, String> map : mapList) {
			for(ItemStatus status : eq.getItemStatus()) {
				String value = map.get(status.getName());
				if(value != null) {
					description += shortName(status.getName())+" " + status.getValue() +"(+"+(Integer.parseInt(value) - Integer.parseInt(status.getValue()))+") ";
				}
			}
		}
		
		item.setTitle(equip.getItemName());
		item.setImageUrl("https://img-api.neople.co.kr/df/items/"+equip.getItemId());
		item.setDescription(description);
		
		return item;
	}
	
	/**
	 * @date 2019. 9. 2.
	 * @description 옵션명을 짧게하기 위해 별도로 메소드 생성
	 */
	public String shortName(String name) {
		
		switch(name) {
			case "물리 공격력" : name="물공"; break;
			case "마법 공격력" : name="마공"; break;
			case "독립 공격력" : name="독공"; break;
			case "모든 속성 강화" : name="모속"; break;
			case "화속성강화" : name="화강"; break;
			case "수속성강화" : name="수속"; break;
			case "명속성강화" : name="명속"; break;
			case "암속성강화" : name="암강"; break;
			case "힘" : name="힘"; break;
			case "지능" : name="지"; break;
			case "체력" : name="체"; break;
			case "정신력" : name="정"; break;
		}
		
		return name;
	}
}
