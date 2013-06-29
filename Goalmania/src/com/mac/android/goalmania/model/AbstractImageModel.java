package com.mac.android.goalmania.model;

import org.simpleframework.xml.Attribute;


public abstract class AbstractImageModel implements Collectionable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6980039928464903600L;
	
	@Attribute
	String imageName;
	@Attribute
	String imageTitle;
	@Attribute
	int imageId;
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
}