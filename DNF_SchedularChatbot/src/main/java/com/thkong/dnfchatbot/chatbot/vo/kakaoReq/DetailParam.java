package com.thkong.dnfchatbot.chatbot.vo.kakaoReq;

public class DetailParam {

	private String origin;
	private String value;
	private String groupName;
	public DetailParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Override
	public String toString() {
		return "DetailParam [origin=" + origin + ", value=" + value + ", groupName=" + groupName + "]";
	}
	
	
	
}
