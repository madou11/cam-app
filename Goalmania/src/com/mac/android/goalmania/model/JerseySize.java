package com.mac.android.goalmania.model;

import java.util.ArrayList;
import java.util.List;

public enum JerseySize {
	Y_3((String) "3 ANS"), Y_5((String) "5 ANS"), Y_7((String) "7 ANS"), Y_9(
			(String) "9 ANS"), Y_12((String) "12 ANS"), Y_14((String) "14 ANS"), S(
			(String) "S"), M((String) "M"), L((String) "L"), XL((String) "XL");

	private String value;

	private JerseySize(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public static List<String> getStringValues(){
		List<String> jerseys  = new ArrayList<String>();
		
		for (JerseySize jerseySize : JerseySize.values()) {
			jerseys.add(jerseySize.value());
		}
		return jerseys;
		
	}

	public static int indexOf(String value) throws Exception{
		List<String> val =JerseySize.getStringValues();
		for (int i = 0; i<val.size(); i++) {
			if(val.get(i).equals(value)){
				return i;
			}
		}
		throw new Exception("the value doesn't exit in this enum.");
	}
	
	public static JerseySize get(String value) {
		if (value == Y_3.value()) {
			return Y_3;
		} else if (value == Y_5.value()) {
			return Y_5;
		} else if (value == Y_7.value()) {
			return Y_7;
		} else if (value == Y_9.value()) {
			return Y_9;
		} else if (value == Y_12.value()) {
			return Y_12;
		} else if (value == Y_14.value()) {
			return Y_14;
		} else if (value == S.value()) {
			return S;
		} else if (value == M.value()) {
			return M;
		} else if (value == L.value()) {
			return L;
		} else if (value == XL.value()) {
			return XL;
		}
		return null;
	}
}
