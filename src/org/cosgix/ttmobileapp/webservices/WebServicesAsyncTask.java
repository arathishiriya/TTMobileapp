package org.cosgix.ttmobileapp.webservices;


import org.cosgix.ttmobileapp.webservices.IResponseHandler;
import org.cosgix.ttmobileapp.webservices.IResponseParser;
import org.cosgix.ttmobileapp.webservices.RequestProcessor;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class WebServicesAsyncTask extends AsyncTask<String, String, Object> implements
IResponseHandler, IResponseParser {

private static final String TAG = "WebServicesAsyncTask";
	
	private RequestProcessor processor;
	private Context context;
	private Object data;
	
	public WebServicesAsyncTask(Context context, RequestProcessor processor) {
		this.processor = processor;
		this.context = context;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
	}

	@Override
	protected Object doInBackground(String... params) {
		processor.processRequest();
		return data;
	}
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	@Override
	public boolean onResponseRecieve(int statusCode, String message) {
		Log.d(TAG, "Status Code : " + statusCode);
		Log.d(TAG, "Message : " + message);
		
		if(statusCode == 200)
		{	
			return false;
		}	
		else 
		{	
			return true;
		}	
	}
	@Override
	public boolean onResponseContentReceive(String content) {
		Log.d(TAG, "Server Response : " + content);
		return false;
	}

	@Override
	public boolean onParsedDataReceive(Object obj) {
		Log.d(TAG, "Parsed Object : " + obj);
		data = obj;
		return true;
	}

	@Override
	public void onRequestFailed(Exception e) {
		
		Log.e(TAG, "Error : " + e);
		
	}
	@Override
	public Object parseContent(String content) throws Exception {
		// TODO Auto-generated method stub
		data = content;
		return content;
	}
}

