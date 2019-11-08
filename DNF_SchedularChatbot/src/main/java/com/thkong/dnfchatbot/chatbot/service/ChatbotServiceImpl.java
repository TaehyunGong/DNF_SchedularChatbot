package com.thkong.dnfchatbot.chatbot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thkong.dnfchatbot.chatbot.Controller.ResponseTemplate;
import com.thkong.dnfchatbot.chatbot.dao.ChatbotDao;
import com.thkong.dnfchatbot.chatbot.vo.Items;
import com.thkong.dnfchatbot.chatbot.vo.TodayRating;
import com.thkong.dnfchatbot.chatbot.vo.Equipment.Equipment;
import com.thkong.dnfchatbot.chatbot.vo.Equipment.ItemSetOption;
import com.thkong.dnfchatbot.chatbot.vo.Equipment.ItemStatus;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.Action;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.DetailParam;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.KakaoReq;
import com.thkong.dnfchatbot.common.httpConnection;

@Service
public class ChatbotServiceImpl implements ChatbotService {

	@Autowired
	ChatbotDao dao;

	ObjectMapper objmap;
	
	public ChatbotServiceImpl() {
		objmap = new ObjectMapper();
		objmap.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

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
		//미래를 보는 지혜 = ff3bdb021bcf73864005e78316dd961c
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
		KakaoReq ob = objmap.readValue(req, KakaoReq.class);
		Action action = ob.getAction();
		DetailParam param = action.getDetailParams().get("equipment");
		DetailParam option = action.getDetailParams().get("option");
		
		String setName = param.getValue();
		
		List<ItemSetOption> equipList = dao.getSetEquipment(setName);
		
		//해당 세트 아이템의 가장 첫번째 아이템의 등급을 가져온다.
		String responseMsg = connItemStatus(equipList.get(0).getItemId());
		TodayRating eq = new ObjectMapper().readValue(responseMsg, TodayRating.class);
		
		String title = equipList.get(0).getSetItemName().replace("세트", "("+eq.getItemGradeValue()+"%)");
		String titleImageUrl = null;
		
		//ItemList 메세지 형식을 반환시키기 위해 Item객체를 리스트로 만들어 넘겨준다.
		List<Items> items = new ArrayList<Items>();
		
		for(ItemSetOption equip : equipList) {
			items.add(getItems(equip, option));
		}
		
		String res = new ResponseTemplate()
						.addListItem(title, titleImageUrl, items)
						.build();
		
		return res;
	}

	/**
	 * @throws IOException 
	 * @date 2019. 9. 2.
	 * @description 아이템 Id를 받아 오늘날의 등급정보를 가져온다.
	 */
	public Items getItems(ItemSetOption equip, DetailParam option) throws IOException {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>(); 
		String[] arr = equip.getOption().split(",");
		/*
		 * '지능+254,체력+246,정신력+134,힘+45' 와같이 데이터가 넘어오기때문에 
		 * split으로 ","로 분열 후 다시 "+"로 분해 후 map으로 키=옵션명, 값=옵션값 으로 넣는다.
		 */
		for(String splitOne : arr) {
			String[] splitTwo = splitOne.split("\\+");
			Map<String, String> splitMap = new HashMap<String ,String>();
			splitMap.put(splitTwo[0], splitTwo[1]);
			mapList.add(splitMap);
		}
		Items item = new Items();
		
		String responseMsg = connItemStatus(equip.getItemId());
		
		Equipment eq = objmap.readValue(responseMsg, Equipment.class);

		String description = "";
		
		//만약에 사용자가 "영광 모속" 이라고 입력하면 모속이 option으로 들어가지만 "영광"일경우 null로 들어가기때문에 조건을 부여
		if(option != null) {
			String opt = option.getValue();
			String maxStat = "";
			String stat = "";
			
			//DB에서 가져온 max 옵션을 반복문으로 돌려서 사용자가 입력한 옵션을 찾아 maxStat으로 넣어준다.
			for(Map<String, String> map : mapList) {
				if(map.get(option.getValue()) != null) {
					maxStat = map.get(opt);
					break;
				}
			}
			
			//rest로 가져온 스탯에서 위와 동일하게 사용자가 입력한 옵션을 찾고 값을 가져온다.
			for(ItemStatus status : eq.getItemStatus()) {
				if(status.getName().equals(opt)) {
					stat = status.getValue();
					break;
				}
			}
			
			//DB에서 가져온값과 REST로 가져온 값이 모든 있을경우만 description을 추가해준다.
			if(maxStat.length() != 0 && stat.length() != 0) {
				description += opt + " " + stat +"(+"+(Integer.parseInt(maxStat) - Integer.parseInt(stat))+") ";
			}
			
		}else {
			//사용자가 아이템만 입력했을때는 모든 옵션을 다 뿌려준다.
			for(Map<String, String> map : mapList) {
				for(ItemStatus status : eq.getItemStatus()) {
					String value = map.get(status.getName());
					if(value != null) {
						description += shortName(status.getName())+" " + status.getValue() +"(+"+(Integer.parseInt(value) - Integer.parseInt(status.getValue()))+") ";
					}
				}
			}
		}
		
		//만약 사용자가 입력한 옵션이 없을경우 "옵션 없을"으로 보여준다.
		if(description.length() == 0)
			description="옵션 없음";
		
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
