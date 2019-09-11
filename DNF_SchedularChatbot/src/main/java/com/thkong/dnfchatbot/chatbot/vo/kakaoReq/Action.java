package com.thkong.dnfchatbot.chatbot.vo.kakaoReq;

import java.util.Map;

public class Action {

	private String name;
	private String clientExtra;
	private Map<String, String> params;
	private String id;
	private Map<String, DetailParam> detailParams;
	public Action() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClientExtra() {
		return clientExtra;
	}
	public void setClientExtra(String clientExtra) {
		this.clientExtra = clientExtra;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, DetailParam> getDetailParams() {
		return detailParams;
	}
	public void setDetailParams(Map<String, DetailParam> detailParams) {
		this.detailParams = detailParams;
	}
	@Override
	public String toString() {
		return "Action [name=" + name + ", clientExtra=" + clientExtra + ", params=" + params + ", id=" + id
				+ ", detailParams=" + detailParams + "]";
	}
	
	
}
