package com.thkong.dnfchatbot.chatbot.dao;

import java.util.List;

import com.thkong.dnfchatbot.chatbot.vo.Equipment.ItemSetOption;

public interface ChatbotDao {

	public List<ItemSetOption> getSetEquipment(String setName);
	
}
