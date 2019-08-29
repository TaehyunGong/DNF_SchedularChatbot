package com.thkong.dnfchatbot.chatbot.vo.kakaoReq;

public class User {

	private String id;
	private String type;
	private UserProperties properties;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UserProperties getProperties() {
		return properties;
	}
	public void setProperties(UserProperties properties) {
		this.properties = properties;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", type=" + type + ", properties=" + properties + "]";
	}
	
}
