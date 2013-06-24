package com.mac.android.goalmania.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Football extends AbstractImageModel implements Collectionable{
/**
	 * 
	 */
	private static final long serialVersionUID = 3652371741058045686L;
	
@ElementList(inline = true, name = "championats")
	private List<Championship> championships;

public Football() {
	championships = new ArrayList<Championship>();
}

	public List<Championship> getChampionships() {
		return championships;
	}

	public void setChampionships(List<Championship> championships) {
		this.championships = championships;
	}

	@Override
	public List<?> getItems() {
		return this.championships;
	}
	
}