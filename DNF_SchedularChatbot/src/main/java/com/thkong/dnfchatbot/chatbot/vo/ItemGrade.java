package com.thkong.dnfchatbot.chatbot.vo;

public class ItemGrade {

	private String yyyymmdd;
	private String itemId;
	private String itemGradeName;
	private String itemGradeValue;
	public ItemGrade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getYyyymmdd() {
		return yyyymmdd;
	}
	public void setYyyymmdd(String yyyymmdd) {
		this.yyyymmdd = yyyymmdd;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	@Override
	public String toString() {
		return "ItemGrade [yyyymmdd=" + yyyymmdd + ", itemId=" + itemId + ", itemGradeName=" + itemGradeName
				+ ", itemGradeValue=" + itemGradeValue + "]";
	}
	
	
	
}
