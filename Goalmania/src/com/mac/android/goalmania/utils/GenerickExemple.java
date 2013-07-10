package com.mac.android.goalmania.utils;

import com.mac.android.goalmania.utils.Commander.MyWsCommand1;
import com.mac.android.goalmania.utils.GenericAsyncTaskCommande.GenericAsyncTask;

public class GenerickExemple {
	public static void main(String[] args) {

		GenericAsyncTask<Object, Object, Object> myAsyncTask1;
		GenericAsyncTask<Object, Object, Object> myAsyncTask2;

		myAsyncTask1 = new GenericAsyncTask<Object, Object, Object>(
				new MyWsCommand1());
		myAsyncTask1.execute();
	}
}
