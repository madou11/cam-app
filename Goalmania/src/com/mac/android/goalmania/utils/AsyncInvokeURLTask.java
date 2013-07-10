package com.mac.android.goalmania.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class AsyncInvokeURLTask extends AsyncTask<String, Void, String> {
    private final String                 mNoteItWebUrl = "your-url.com";
    private ArrayList<NameValuePair>     mParams;
    private OnPostExecuteListener        mPostExecuteListener = null;
 
    public static interface OnPostExecuteListener{
        void onPostExecute(String result);
        void onPreExecute();
        void onDoingBackground(String result);
    }
 
    public AsyncInvokeURLTask(
        ArrayList<NameValuePair> nameValuePairs,
        AsyncInvokeURLTask.OnPostExecuteListener postExecuteListener) throws Exception {
 
        mParams = nameValuePairs;
        mPostExecuteListener = postExecuteListener;
        if (mPostExecuteListener == null)
            throw new Exception("Param cannot be null.");
    }
 
    @Override
    protected void onPreExecute() {
    	super.onPreExecute();
    	mPostExecuteListener.onPreExecute();
    }
    
    @Override
    protected String doInBackground(String... params) {
 
        String result = "";
 
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(params[0]);
 
        try {
            // Add parameters
            httppost.setEntity(new UrlEncodedFormEntity(mParams));
 
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null){
                InputStream inStream = entity.getContent();
                result = convertStreamToString(inStream);
            }
            mPostExecuteListener.onDoingBackground(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return result;
    }
 
    @Override
    protected void onPostExecute(String result) {
        if (mPostExecuteListener != null){
            try {
                JSONObject json = new JSONObject(result);
                mPostExecuteListener.onPostExecute(json.toString(2));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
 
    private static String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
 
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
} // AsyncInvokeURLTask