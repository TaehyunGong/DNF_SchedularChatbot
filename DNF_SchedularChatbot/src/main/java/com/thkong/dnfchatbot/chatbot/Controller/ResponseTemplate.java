package com.thkong.dnfchatbot.chatbot.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.thkong.dnfchatbot.chatbot.vo.Items;
import com.thkong.dnfchatbot.chatbot.vo.KakaoRes;
import com.thkong.dnfchatbot.chatbot.vo.SimpleText;
import com.thkong.dnfchatbot.chatbot.vo.Template;

public class ResponseTemplate {

	JSONArray outputs;
	
	public ResponseTemplate() {
		this.outputs = new JSONArray();
	}

	/**
	 * @param text
	 * @return
	 * https://i.kakao.com/docs/skill-response-format#simpletext
	 */
	public ResponseTemplate addSimpleText(String text) {
		
		JSONObject json = new JSONObject();
		JSONObject value = new JSONObject();
		value.put("text", text);
		
		json.put("simpleText", value);
		outputs.add(json);
		
		return this;
	}
	
	/**
	 * @return
	 * https://i.kakao.com/docs/skill-response-format#listitem-%EC%83%81%EC%84%B8-%ED%95%84%EB%93%9C
	 */
	public ResponseTemplate addListItem(String title, String imageUrl, List<Items> itemList) {
		
		JSONObject header = new JSONObject();
		JSONObject item = new JSONObject();
		item.put("title", title);
		item.put("imageUrl", imageUrl);
		
		header.put("header", item);
		
		//////////////////
		
		JSONArray items = new JSONArray();
		JSONObject temp = new JSONObject();
		for(Items content : itemList) {
			temp = new JSONObject();
			temp.put("title", content.getTitle());
			temp.put("description", content.getDescription());
			temp.put("imageUrl", content.getImageUrl());
			
			items.add(temp);
		}
		
		JSONObject output = new JSONObject();
		output.put("listCard", header);
		output.put("items", items);
		
		outputs.add(output);
		
		return this;
	}
	
	public String build() {
		
		JSONObject res = new JSONObject();
		res.put("version", "2.0");
		
		JSONObject template = new JSONObject();
		template.put("outputs", outputs);
		
		res.put("template", template);
		
		return res.toJSONString();
	}
}
