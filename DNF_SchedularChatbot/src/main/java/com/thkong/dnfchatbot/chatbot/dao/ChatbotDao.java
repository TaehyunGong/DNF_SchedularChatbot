package com.thkong.dnfchatbot.chatbot.dao;

import java.util.List;

import com.thkong.dnfchatbot.chatbot.vo.Equipment.Equipment;

public interface ChatbotDao {

	public List<Equipment> getSetEquipment(String setName);
	
}
