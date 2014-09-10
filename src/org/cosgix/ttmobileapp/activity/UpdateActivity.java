package org.cosgix.ttmobileapp.activity;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;




import org.cosgix.ttmobileapp.data.GetProjects;
import org.cosgix.ttmobileapp.data.GetTasks;
import org.cosgix.ttmobileapp.data.GetWorkTypes;
import org.cosgix.ttmobileapp.data.IProjects;
import org.cosgix.ttmobileapp.data.ITasks;
import org.cosgix.ttmobileapp.data.IWorkTypes;
import org.cosgix.ttmobileapp.data.Projects;
import org.cosgix.ttmobileapp.data.Tasks;
import org.cosgix.ttmobileapp.data.WorkTypes;
import org.cosgix.ttmobileapp.services.UpdateService;
import org.cosgix.ttmobileapp.webservices.IResponseParser;

import com.google.android.gms.common.SignInButton;


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
public class UpdateActivity extends Activity implements IProjects, ITasks,IWorkTypes {
	@SuppressWarnings("unused")
	private static final String TAG = "UpdateActivity";
	//private Button mExitButton ;

	GetProjects getProjects;
	List<Projects> ProjectsList;
	
	GetWorkTypes getWorkTypes;
	List<WorkTypes> workTypeList;
	
	GetTasks getTasks;
	List<Tasks> TasksList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		
		getProjects = new GetProjects(UpdateActivity.this);
		
		getWorkTypes = new GetWorkTypes(UpdateActivity.this);
	
		startUpdateService();

	}

	/**
	 * Button on click listener
	 * */
	public void onClick(View v) {
		switch (v.getId()) {
		//		case R.id.Exitbutton:
		//			// stopUpdateService
		//			stopUpdateService();
		//			break;

		default:
			break;
		}
	}
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

	private void stopUpdateService(){
		if (isUpdateServiceRunning()) {
			Intent serviceIntent=new Intent(this,UpdateService.class);
			stopService(serviceIntent);
			serviceIntent=null;
		}//end if running
	}//end stopUpdateService
	// Check to see if the Update Service is running
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
		stopUpdateService();
		return;
	}//onBackPressed

	@Override
	public boolean projectListDownloadDone(List<Projects> projectsList) {
		// TODO Auto-generated method stub
		System.out.println("Update Activity Projects details\n");
		for(Projects projects : projectsList) {
			System.out.println("\n"+ projects.getProjectName() +projects.getProjectId());
			//tempProjectid = projects.getProjectId();
		}
		getTasks = new GetTasks(UpdateActivity.this,22);
		return true;
	}

	@Override
	public boolean tasksDownloadDone(List<Tasks> tasksList) {
		// TODO Auto-generated method stub
		System.out.println("Update Activity tasks details\n");
		for(Tasks tasks: tasksList) {
			System.out.println("\n"+ tasks.getTaskName());
			//tempProjectid = projects.getProjectId();
		}
		
		return true;
	}

	@Override
	public boolean workTypesDownloadDone(List<WorkTypes> workTypeList) {
		// TODO Auto-generated method stub
		System.out.println("Update Activity workTypes details\n");
		for(WorkTypes worktypes: workTypeList) {
			System.out.println("\n"+ worktypes.getWorktypeName());
			//tempProjectid = projects.getProjectId();
		}
		return true;
	}

	

	

	

	


}
