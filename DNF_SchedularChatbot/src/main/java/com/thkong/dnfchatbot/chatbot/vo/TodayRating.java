package com.thkong.dnfchatbot.chatbot.vo;

public class TodayRating {

	private String itemId;
	private String itemName;
	private String itemGradeName;
	private String itemGradeValue;
	private Object itemStatus;
	public TodayRating() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemGradeName() {
		return itemGradeName;
	}
	public void setItemGradeName(String itemGradeName) {
		this.itemGradeName = itemGradeName;
	}
	public String getItemGradeValue() {
		return itemGradeValue;
	}
	public void setItemGradeValue(String itemGradeValue) {
		this.itemGradeValue = itemGradeValue;
	}
	public Object getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(Object itemStatus) {
		this.itemStatus = itemStatus;
	}
	@Override
	public String toString() {
		return "TodayRating [itemId=" + itemId + ", itemName=" + itemName + ", itemGradeName=" + itemGradeName
				+ ", itemGradeValue=" + itemGradeValue + ", itemStatus=" + itemStatus + "]";
	}
	
	
}
