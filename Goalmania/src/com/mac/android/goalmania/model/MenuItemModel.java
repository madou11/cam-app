package com.mac.android.goalmania.model;

public class MenuItemModel {
	private int imageId;
	private String title;
	private String description;
	
	public MenuItemModel() {
	}
	
	public MenuItemModel(int imageId, String title, String description) {
		super();
		this.imageId = imageId;
		this.title = title;
		this.description = description;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
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
}
