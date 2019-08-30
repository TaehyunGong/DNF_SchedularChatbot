package com.thkong.dnfchatbot.chatbot.vo;

public class KakaoRes<T> {

	public String version;
	public Template<T> template;
	
	public KakaoRes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Template<T> getTemplate() {
		return template;
	}
	public void setTemplate(Template<T> template) {
		this.template = template;
	}
	
	
	
}
