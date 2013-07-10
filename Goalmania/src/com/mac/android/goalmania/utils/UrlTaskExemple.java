package com.mac.android.goalmania.utils;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;

import com.mac.android.goalmania.utils.AsyncInvokeURLTask.OnPostExecuteListener;

public class UrlTaskExemple extends Activity {

	public static void main(String[] args) throws Exception {
		UrlTaskExemple main = new UrlTaskExemple();
		main.run();
	}

	public void run(){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("command", "do_get_shop_list"));
		nameValuePairs.add(new BasicNameValuePair("arg1", String.valueOf(1)));
		
    	final ProgressDialog simpleWaitDialog = new ProgressDialog(UrlTaskExemple.this); 

		AsyncInvokeURLTask task = null;
		try {
			task = new AsyncInvokeURLTask(
			    nameValuePairs, new OnPostExecuteListener() {
					@Override
					public void onPostExecute(String result) {
						// TODO Auto-generated method stub
						simpleWaitDialog.dismiss();
					}

					@Override
					public void onDoingBackground(String result) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onPreExecute() {
						simpleWaitDialog.show(UrlTaskExemple.this,"Wait", "Downloading Image");

					}
				});
		} catch (Exception e) {
			e.printStackTrace();
		}
		task.execute();
	}
}
