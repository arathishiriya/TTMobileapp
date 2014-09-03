package org.cosgix.ttmobileapp.activity;

import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import org.cosgix.ttmobileapp.webservices.*;


/**
 * Update Activity is responsible to fetch all the project related data from the webserver 
 * And 
 * 
 * @param none 
 * 
 * 
 * @param 
 * 
 */
public class UpdateActivity extends Activity implements IResponseHandler, IResponseParser{
	private static final String TAG = null;
	private Button mLoginButton;
	private OnClickListener mLoginButtonListener;
	private String url = "http://firefly.new.cosdevx.com";
	private String path = "api/v1/clients";
	private RequestProcessor processor = null;
	
	private HashMap<String, String> map = null;
	
	{
		//map = new HashMap<String, String>();
		//map.put("callback", "showIP");		
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //
        mLoginButton = (Button) this.findViewById(R.id.SignInButton);
        mLoginButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				processor = new GETRequestProcessor(url,path, WebServicesConst.TT_HTTP_GET,UpdateActivity.this, map, UpdateActivity.this);
		
				AsyncTask<String, String, Object> task = 
				new WebServicesAsyncTask(UpdateActivity.this,UpdateActivity.this.processor);
				task.execute("Fetch Response");
			}
		};
		mLoginButton.setOnClickListener(mLoginButtonListener);
    }

	@Override
	public Object parseContent(String content) throws Exception {
		// TODO Auto-generated method stub
		return content;
	}

	@Override
	public boolean onResponseRecieve(int statusCode, String message) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Log.d(TAG, "Server Response : " + content);
		return false;
	}

	@Override
	public boolean onParsedDataReceive(Object obj) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Parsed Object : " + obj);
		return false;
		
	}

	@Override
	public void onRequestFailed(Exception e) {
		// TODO Auto-generated method stub
		Log.e(TAG, "Error : " + e);
	}
	
	
}
