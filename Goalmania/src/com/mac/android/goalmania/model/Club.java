package com.mac.android.goalmania.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class Club extends AbstractImageModel implements Collectionable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4276550763505608446L;
	
	@ElementList(inline = true, name = "maillots")
	private List<Jersey> jerseys;

	public Club() {
		jerseys = new ArrayList<Jersey>();
	}
	
	public List<Jersey> getJerseys() {
		return jerseys;
	}

	public void setJerseys(List<Jersey> jerseys) {
		this.jerseys = jerseys;
	}

	@Override
	public List<?> getItems() {
		return jerseys;
	}

	
}