package com.mac.android.goalmania.model;

import java.util.List;
import java.util.UUID;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Order {
	@Attribute
	private UUID ref;
	@Attribute
	private int id;
	@Element
	private String description;
	@ElementList(inline=true)
	private List<OrderItem> items;

	
	public Order() {
		ref = UUID.randomUUID();
	}
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
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public UUID getRef() {
		return ref;
	}
}