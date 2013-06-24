package com.mac.android.goalmania.model;

import java.io.Serializable;
import java.util.List;

public interface Collectionable extends Serializable{

	public abstract List<?> getItems();
}
