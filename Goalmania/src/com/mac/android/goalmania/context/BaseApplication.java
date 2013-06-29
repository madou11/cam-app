package com.mac.android.goalmania.context;

import java.util.HashMap;

import android.app.Application;

public class BaseApplication extends Application {

	protected HashMap<String, Object> contextDatas;
	
	public BaseApplication() {
		this.contextDatas = new HashMap<String, Object>();
	}
	
	public boolean putDatas(String key, Object value){
		if(this.contextDatas != null && ! this.contextDatas.containsKey(key)){
			this.contextDatas.put(key, value);
			return true;
		}
		return false;
	}
	
	public Object getDatas(String key){
		if(this.contextDatas != null){
			return this.contextDatas.get(key);
		}
		return null;
	}

	public boolean existInDatas(String key){
		if(this.contextDatas != null){
			return this.contextDatas.containsKey(key);
		}
		return false;
	}
}
