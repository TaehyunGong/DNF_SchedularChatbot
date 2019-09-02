package com.thkong.dnfchatbot.chatbot.service;

import java.io.IOException;

public interface ChatbotService {

	public String errorMessage();
	
	public String toDayRating() throws IOException;
	
	public String toDayEquipment(String req) throws Exception;
}
