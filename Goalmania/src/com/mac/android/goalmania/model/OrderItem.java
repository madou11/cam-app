package com.mac.android.goalmania.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;


public class OrderItem {
	@Attribute(required=false)
	private int id;
	@Attribute(required=false)
	private String description;
	@Element
	private Jersey jersey;

	@Element
	private JerseyType type;
	
	@Element
	private JerseySize size;
	@Element
	private String flocking;
	@Element
	private int number;
	@Element
	private boolean patched = false;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public JerseyType getType() {
		return type;
	}
	public void setType(JerseyType type) {
		this.type = type;
	}
	public Jersey getJersey() {
		return jersey;
	}
	public void setJersey(Jersey jersey) {
		this.jersey = jersey;
	};
	public JerseySize getSize() {
		return size;
	}
	public void setSize(JerseySize size) {
		this.size = size;
	}
	public String getFlocking() {
		return flocking;
	}
	public void setFlocking(String flocking) {
		this.flocking = flocking;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean isPatched() {
		return patched;
	}
	public void setPatched(boolean patched) {
		this.patched = patched;
	}

}