package org.cosgix.ttmobileapp.activity;

import org.cosgix.ttmobileapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


/**
 * Login Activity initiates the Google login using G+ 
 * 
 * 
 * @param none 
 * 
 * 
 * @param 
 * 
 * @author sanjib
 * 
 */
public class LoginActivity extends Activity implements OnClickListener,
ConnectionCallbacks, OnConnectionFailedListener {

	// variables declaration
	private static final int RC_SIGN_IN = 0;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;

	private SignInButton mLoginButton;

	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// sign in button initialization
		mLoginButton = (SignInButton) findViewById(R.id.SignInButton);

		// Button click listeners
		mLoginButton.setOnClickListener(this);

		//Need to initiate mGoogleApiClient to use the G+ sign on
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();


	}

	/**
	 * method used to connect the google api
	 */
	protected void onStart() {
		super.onStart();
		//GEt connected to the google server
		mGoogleApiClient.connect();
	}

	/**
	 * method used to disconnect the google api
	 */
	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			//Make sure you disconnect from the server
			mGoogleApiClient.disconnect();
		}
	}

	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {

		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;

		// Update the UI after signin
		updateUI(true);
		invokeUpdateActivity();
		getProfileInformation();

	}

	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
		try {
			sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			editor = sharedpreferences.edit();

			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				editor.putString("personName", personName);
				editor.putString("email", email);

				editor.commit(); 

				Log.e("TAG", "Name: " + personName + ", email: " + email + personPhotoUrl + personGooglePlusProfile);

			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updating the UI, showing/hiding buttons and profile layout
	 * */
	private void updateUI(boolean isSignedIn) {
		if (isSignedIn) {
			mLoginButton.setVisibility(View.GONE);
		} else {
			mLoginButton.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * method used for invoking Update activity
	 */
	private void invokeUpdateActivity() {

		Intent intent = new Intent(LoginActivity.this,UpdateActivity.class);
		startActivity(intent);

	}

	@Override
	public void onConnectionSuspended(int arg0) {

		//Making sure we connect to the client in case the connection gets suspended
		mGoogleApiClient.connect();
		updateUI(false);
	}

	/**
	 * Button on click listener
	 * */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.SignInButton:
			// Signin button clicked
			signInWithGplus();
			break;

		default:
			break;
		}
	}

	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}

}
