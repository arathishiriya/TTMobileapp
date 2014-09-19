package org.cosgix.ttmobileapp.activity;

import org.cosgix.ttmobileapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * This class provides the successful time entry
 * @author Sanjib
 *
 */
public class AddEntryActivity extends Activity {
	
	// variables declaration
	AlertDialog builder;

	AddEntryActivity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_addentry);
		
		context = this;
		
		displayDialog();
		
	}

	/**
	 * method used to display the alert dialog to set the result
	 */
	private void displayDialog() {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(getResources().getString(R.string.timeentrysuccessful));
		alertDialog.setMessage(getResources().getString(R.string.wanttoaddmore));
		alertDialog.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				
				setActivityResult();

			}
		});
		
		alertDialog.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				setActivityResult();

			}
		});

		alertDialog.setCancelable(false);
		builder = alertDialog.create();

		if (!builder.isShowing()) {

			builder.show();

		}
		
	}

	/**
	 * set the result of the activity
	 */
	protected void setActivityResult() {

		Intent intent = new Intent();  
		intent.putExtra("ADDENTRY_MESSAGE","ADDENTRY");
		setResult(3,intent);  

		finish();//finishing activity 
		
	}

}
