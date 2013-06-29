package com.mac.android.goalmania.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;


public class Jersey extends AbstractImageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3373399979339930926L;

	@Attribute
	private String year;
	
	@Attribute
	private JerseyType type;
	
	@Override
	public List<?> getItems() {
		// TODO Auto-generated method stub
		return new ArrayList();
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public JerseyType getType() {
		return type;
	}

	public void setType(JerseyType type) {
		this.type = type;
	}

}