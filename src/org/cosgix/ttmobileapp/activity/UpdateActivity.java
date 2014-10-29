package org.cosgix.ttmobileapp.activity;

import java.util.List;

import org.cosgix.ttmobileapp.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import org.cosgix.ttmobileapp.database.DatabaseHelper;
import org.cosgix.ttmobileapp.database.DatabaseManager;
import org.cosgix.ttmobileapp.database.ProjectListTable;
import org.cosgix.ttmobileapp.database.ProjectListTableRepository;
import org.cosgix.ttmobileapp.services.UpdateService;
import org.cosgix.ttmobileapp.util.BaseUtil;

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
public class UpdateActivity extends Activity implements IProjects, IWorkTypes,ITasks{

	// variables declaration
	@SuppressWarnings("unused")
	private static final String TAG = "UpdateActivity";

	ProgressDialog progressDialog;
	private Button addTimeEntryButton;

	GetProjects getProjects;
	static List<Projects> ProjectsList;

	GetWorkTypes getWorkTypes;
	static List<WorkTypes> workTypeList;

	GetTasks getTasks;
	List<Tasks>[] mTasksList;
	
	List<ProjectListTable> projectListTable = null;
	ProjectListTable [] projectListArray =null;

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
	
	private int projectListSize = 21;

	private DatabaseHelper databaseHelper;
	private boolean dbExist;
	private boolean flagExist = false;

	UpdateActivity context;

	String emailid ;
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_update);

		context = this;

		databaseHelper = DatabaseManager.getHelper(context);

		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		emailid = sharedpreferences.getString("email", "");

		dbExist = databaseHelper.checkdatabase();

		boolean flag = sharedpreferences.getBoolean("flagExist", false);

		if(!flag){
			showProgressDialog();
			getDataAfterInterval(context);
			getProjects = new GetProjects(UpdateActivity.this);

			getWorkTypes = new GetWorkTypes(UpdateActivity.this);

		}
		else {
			// cancel dialog
			getDataAfterInterval(context);
			getProjects = new GetProjects(UpdateActivity.this);

			getWorkTypes = new GetWorkTypes(UpdateActivity.this);
		}

		//		showProgressDialog();
		//		getDataAfterInterval(context);

		getWidgetId();

		//		getProjects = new GetProjects(UpdateActivity.this);
		//
		//		getWorkTypes = new GetWorkTypes(UpdateActivity.this);

		//startUpdateService();

		addTimeEntryButtonClickEvent();

	}

	private void getWidgetId() {

		addTimeEntryButton = (Button)findViewById(R.id.addtimeentrybutton);

		addTimeEntryButton.setVisibility(View.VISIBLE);

	}

	public void getDataAfterInterval(Context context) {

		Intent downloader = new Intent(UpdateActivity.this, MyStartServiceReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, downloader, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5000, pendingIntent);

	}

	@Override
	protected void onResume() {
		super.onResume();
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
		//finish();
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
	public boolean tasksDownloadDone(List<Tasks>[] tasksList) {
		// get the tasks list if not null 
		if(tasksList != null) {
			mTasksList = tasksList;
		}
		
		PutToDB();
		return false;
	}

	@Override
	public boolean workTypesDownloadDone(List<WorkTypes> worktypeList) {

		// get the work type list if not null and invoke time entry activity
		if(worktypeList != null) {
			workTypeList = worktypeList;
			cancelProgressDialog();
			//invokeTimeEntryActivity();
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
		startActivityForResult(intent, 5);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(data != null) {

			// check if the request code is same as what is passed  here it is 5  
			if(requestCode == 5) { 

				addTimeEntryButton.setVisibility(View.VISIBLE);
				addTimeEntryButtonClickEvent();

			}
		}
	}	

	/**
	 * Add time Entry button event
	 */
	private void addTimeEntryButtonClickEvent() {

		addTimeEntryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				invokeTimeEntryActivity() ;

			}
		});
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

		saveServerDataToDatabase();
		saveTasksDataToDatabase();

		flagExist = true;
		SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		editor.putBoolean("flagExist", flagExist);

		editor.commit(); 
	}

	public void saveServerDataToDatabase() {

		String [] PROJECTS = new String[getProjectsList().size()];
		String projectName = "";
		int i = 0;
		Projects proj = new Projects();
		for(Projects projects : getProjectsList()) {
			PROJECTS[i++] = projects.getProjectName();
			projectName = projects.getProjectName();
			BaseUtil.insertProjectListTableData(UpdateActivity.this, projects.getProjectId(), projectName);
		}

		String [] WORKTYPES = new String[getWorkTypeList().size()];
		String worktype = "";
		int j = 0;
		for(WorkTypes workTypes : getWorkTypeList()) {
			WORKTYPES[j++] = workTypes.getWorktypeName();
			worktype = workTypes.getWorktypeName();
			BaseUtil.insertWorkTypeListTableData(UpdateActivity.this, 1, worktype);
		}

		//		String [] TASKS = new String[TimeEntryActivity.getTasksList().size()];
		//		String task = "";
		//		int k = 0;
		//		for(Tasks tasks : TimeEntryActivity.getTasksList()) {
		//			TASKS[k++] = tasks.getTaskName();
		//			task = tasks.getTaskName();
		//			BaseUtil.insertTaskListTableData(UpdateActivity.this, 2, task);
		//		}

	}

	public void saveTasksDataToDatabase() {

		ProjectListTableRepository projectListTableRepository = new ProjectListTableRepository(UpdateActivity.this);

		projectListTable = projectListTableRepository.getProjectListData();

		projectListArray = projectListTable.toArray(new ProjectListTable[projectListTable.size()]);
		int [] projectIds = null;
		projectIds = new int[projectListArray.length];
		for(int i = 0; i < projectListArray.length; i++) {
			projectIds[i]=projectListArray[i].getProject_id();
		}
	
		@SuppressWarnings("unused")
		GetTasks getTasks = new GetTasks(UpdateActivity.this,projectIds);


	}
	
	public void PutToDB()
	{
		for(int i = 0; i < projectListArray.length; i++) {
			for(Tasks task : mTasksList[i]) {
				if(task!=null)
				{
					BaseUtil.insertTaskListTableData(UpdateActivity.this, projectListArray[i].getProject_id(), task.getTaskName());
				}
			}
			
		}
	
	}
}
