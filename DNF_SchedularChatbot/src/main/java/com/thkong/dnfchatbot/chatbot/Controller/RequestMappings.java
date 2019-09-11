package com.thkong.dnfchatbot.chatbot.Controller;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.Action;
import com.thkong.dnfchatbot.chatbot.vo.kakaoReq.KakaoReq;

public class RequestMappings {

	private RequestMappings() {}

	public static class RequestMappings_Singleton{
		private final static RequestMappings instance = new RequestMappings();
	}
	
	public static RequestMappings getInstance() {
		return RequestMappings_Singleton.instance;
	}
	
	/**
	 * @date 2019. 9. 2.
	 * @description Kakao Request로 받은 json 문자열을 파싱 후 Action만 반환
	 */
	public Action getAction(String req) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		KakaoReq ob = mapper.readValue(req, KakaoReq.class);
		Action action = ob.getAction();
		return action;
	}
	
}
