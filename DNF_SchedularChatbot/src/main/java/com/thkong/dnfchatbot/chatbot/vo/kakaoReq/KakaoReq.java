package com.thkong.dnfchatbot.chatbot.vo.kakaoReq;

public class KakaoReq {

	private Object intent;
	
	private UserRequest userRequest;
	private Object contexts;
	
	private Object bot;
	private Action action;
	
	public KakaoReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Object getIntent() {
		return intent;
	}

	public void setIntent(Object intent) {
		this.intent = intent;
	}

	public UserRequest getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(UserRequest userRequest) {
		this.userRequest = userRequest;
	}

	public Object getContexts() {
		return contexts;
	}

	public void setContexts(Object contexts) {
		this.contexts = contexts;
	}

	public Object getBot() {
		return bot;
	}

	public void setBot(Object bot) {
		this.bot = bot;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "KakaoReq [intent=" + intent + ", userRequest=" + userRequest + ", contexts=" + contexts + ", bot=" + bot
				+ ", action=" + action + "]";
	}
	
	
}
