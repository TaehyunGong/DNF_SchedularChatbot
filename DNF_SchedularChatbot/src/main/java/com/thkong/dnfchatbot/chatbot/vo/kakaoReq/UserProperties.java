package com.thkong.dnfchatbot.chatbot.vo.kakaoReq;

public class UserProperties {

	private String plusfriendUserKey;
	private String botUserKey;
	public UserProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPlusfriendUserKey() {
		return plusfriendUserKey;
	}
	public void setPlusfriendUserKey(String plusfriendUserKey) {
		this.plusfriendUserKey = plusfriendUserKey;
	}
	public String getBotUserKey() {
		return botUserKey;
	}
	public void setBotUserKey(String botUserKey) {
		this.botUserKey = botUserKey;
	}
	@Override
	public String toString() {
		return "UserProperties [plusfriendUserKey=" + plusfriendUserKey + ", botUserKey=" + botUserKey + "]";
	}
	
	
	
}
