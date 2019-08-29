package com.thkong.dnfchatbot.chatbot.vo.kakaoReq;

public class Block {

	private String id;
	private String name;
	public Block() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Block [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
