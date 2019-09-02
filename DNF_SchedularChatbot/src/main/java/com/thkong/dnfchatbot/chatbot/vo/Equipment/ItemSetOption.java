package com.thkong.dnfchatbot.chatbot.vo.Equipment;

public class ItemSetOption {

	private String itemId;
	private String itemName;
	private String setItemName;
	private String Option;
	public ItemSetOption() {
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
	public String getSetItemName() {
		return setItemName;
	}
	public void setSetItemName(String setItemName) {
		this.setItemName = setItemName;
	}
	public String getOption() {
		return Option;
	}
	public void setOption(String option) {
		Option = option;
	}
	@Override
	public String toString() {
		return "ItemSetOption [itemId=" + itemId + ", itemName=" + itemName + ", setItemName=" + setItemName
				+ ", Option=" + Option + "]";
	}
	
	
	
}
