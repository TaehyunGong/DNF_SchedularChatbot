package com.thkong.dnfchatbot.chatbot.vo;

public class Items {

	private String title;
	private String description;
	private String imageUrl;
	private Object link;
	public Items() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Object getLink() {
		return link;
	}
	public void setLink(Object link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "Items [title=" + title + ", description=" + description + ", imageUrl=" + imageUrl + ", link=" + link
				+ "]";
	}
	
	
	
}
