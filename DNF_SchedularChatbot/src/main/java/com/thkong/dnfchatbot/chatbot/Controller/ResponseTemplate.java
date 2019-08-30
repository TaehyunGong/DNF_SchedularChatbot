package com.thkong.dnfchatbot.chatbot.Controller;

import java.util.ArrayList;
import java.util.List;

import com.thkong.dnfchatbot.chatbot.vo.KakaoRes;
import com.thkong.dnfchatbot.chatbot.vo.SimpleText;
import com.thkong.dnfchatbot.chatbot.vo.Template;

public class ResponseTemplate {

	List outputs;
	
	public ResponseTemplate() {
		this.outputs = new ArrayList();
	}

	/**
	 * @param text
	 * @return
	 * https://i.kakao.com/docs/skill-response-format#simpletext
	 */
	public ResponseTemplate addSimpleText(String text) {
		
		SimpleText simpleText = new SimpleText();
		simpleText.setText(text);
		this.outputs.add(simpleText);
		
		return this;
	}
	
	/**
	 * @return
	 * https://i.kakao.com/docs/skill-response-format#listitem-%EC%83%81%EC%84%B8-%ED%95%84%EB%93%9C
	 */
	public ResponseTemplate addListItem() {
		
		return this;
	}
	
	public KakaoRes build() {
		
		Template temp = new Template();
		temp.setOutputs(outputs);
		
		KakaoRes res = new KakaoRes();
		res.setVersion("2.0");
		res.setTemplate(temp);
		
		return res;
	}
}
