package com.mac.android.goalmania.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;


public class Championship extends AbstractImageModel implements Collectionable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6720058632531866432L;
	
	@ElementList(inline = true, name = "clubs") 
	private List<Club> clubs;

	public Championship() {
		clubs = new ArrayList<Club>();
	}
	
	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	@Override
	public List<?> getItems() {
		return this.clubs;
	}
}