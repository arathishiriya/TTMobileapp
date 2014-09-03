package org.cosgix.ttmobileapp.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import org.cosgix.ttmobileapp.webservices.*;


/**
 * Login Activity initiates the Google login using G+ 
 * 
 * 
 * @param none 
 * 
 * 
 * @param 
 * 
 */
public class LoginActivity extends Activity {
	private Button mLoginButton;
	private OnClickListener mLoginButtonListener;

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
				Intent mintent = new Intent(LoginActivity.this, UpdateActivity.class);
				startActivity(mintent);
				//LoginActivity.this.startActivity(new Intent(LoginActivity.this, UpdateActivity.class));
			}
		};
		mLoginButton.setOnClickListener(mLoginButtonListener);
    }

	

	
}
