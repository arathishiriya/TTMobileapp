package org.cosgix.ttmobileapp.activity;

import java.util.List;

import org.cosgix.ttmobileapp.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.cosgix.ttmobileapp.data.GetProjects;
import org.cosgix.ttmobileapp.data.GetWorkTypes;
import org.cosgix.ttmobileapp.data.IProjects;
import org.cosgix.ttmobileapp.data.IWorkTypes;
import org.cosgix.ttmobileapp.data.Projects;
import org.cosgix.ttmobileapp.data.WorkTypes;
import org.cosgix.ttmobileapp.services.UpdateService;

/**
 * Update Activity is responsible to fetch all the project related data from the webserver 
 * And 
 * 
 * @param none 
 * 
 * 
 * @param 
 * 
 * @author Arathi 
 * @author Sanjib
 * 
 */
public class UpdateActivity extends Activity implements IProjects, IWorkTypes {

	// variables declaration
	@SuppressWarnings("unused")
	private static final String TAG = "UpdateActivity";

	ProgressDialog progressDialog;

	GetProjects getProjects;
	static List<Projects> ProjectsList;

	GetWorkTypes getWorkTypes;
	static List<WorkTypes> workTypeList;

	// getter method for project list
	public static List<Projects> getProjectsList() {
		return ProjectsList;
	}

	// setter method for project list
	public static void setProjectsList(List<Projects> projectsList) {
		ProjectsList = projectsList;
	}

	// getter method for work type list
	public static List<WorkTypes> getWorkTypeList() {
		return workTypeList;
	}

	// setter method for work type list
	public  void setWorkTypeList(List<WorkTypes> worktypeList) {
		workTypeList = worktypeList;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_update);

		showProgressDialog();

		getProjects = new GetProjects(UpdateActivity.this);

		getWorkTypes = new GetWorkTypes(UpdateActivity.this);

		//startUpdateService();

	}

	/**
	 * method used to start update service
	 */
	private void startUpdateService(){
		if (!isUpdateServiceRunning()) {
			// use this to start and trigger a service
			Intent serviceIntent= new Intent(this, UpdateService.class);
			// potentially add data to the intent

			startService(serviceIntent);
			serviceIntent=null;;
			//testing the dismiss progress Dialog

		}//end if not running
	}//end startUpdateService

	/**
	 * method used to stop update service
	 */
	private void stopUpdateService(){
		if (isUpdateServiceRunning()) {
			Intent serviceIntent=new Intent(this,UpdateService.class);
			stopService(serviceIntent);
			serviceIntent=null;
		}//end if running
	}//end stopUpdateService

	/**
	 * method used to Check to see if the Update Service is running
	 */
	private boolean isUpdateServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if ("org.cosgix.ttmobileapp.services.UpdateService".equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		//stopUpdateService();
		return;
	}//onBackPressed

	@Override
	public boolean projectListDownloadDone(List<Projects> projectsList) {

		// get the project list if not null 
		if(projectsList != null) {
			ProjectsList = projectsList;
			//getTasks = new GetTasks(UpdateActivity.this,22);
		}
		else {
			Log.e(TAG, "Project List is null");
		}

		return true;
	}

	@Override
	public boolean workTypesDownloadDone(List<WorkTypes> worktypeList) {

		// get the work type list if not null and invoke time entry activity
		if(worktypeList != null) {
			workTypeList = worktypeList;
			cancelProgressDialog();
			invokeTimeEntryActivity();
		}
		else {
			Log.e(TAG, "Work Type List is null");
		}

		return true;
	}

	/**
	 * method used for invoking TimeEntryActivity 
	 */
	private void invokeTimeEntryActivity() {
		Intent intent = new Intent(UpdateActivity.this,TimeEntryActivity.class);
		startActivity(intent);
	}

	/**
	 * This method is used to show the progress dialog in the UI
	 */
	public void showProgressDialog() {

		try {
			// Create a progress dialog
			progressDialog = new ProgressDialog(UpdateActivity.this);
			// Set progress dialog title
			progressDialog.setTitle("Please wait");
			// Set progress dialog message
			progressDialog.setMessage("Data loading");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);

			if(!progressDialog.isShowing()) {
				// Show progress dialog
				progressDialog.show();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to cancel the progress dialog from the UI
	 */
	public void cancelProgressDialog() {

		// Close the progress dialog
		progressDialog.dismiss();
	}

}
