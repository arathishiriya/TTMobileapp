package org.cosgix.ttmobileapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cosgix.ttmobileapp.webservices.Const;
import org.cosgix.ttmobileapp.webservices.GETRequestProcessor;
import org.cosgix.ttmobileapp.webservices.IResponseHandler;
import org.cosgix.ttmobileapp.webservices.IResponseParser;
import org.cosgix.ttmobileapp.webservices.RequestProcessor;
import org.cosgix.ttmobileapp.webservices.WebServicesAsyncTask;
import org.cosgix.ttmobileapp.webservices.WebServicesConst;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This class provides the tasks from server
 * 
 * @author Sanjib
 *
 */
public class GetTasks implements IResponseHandler,IResponseParser{

	// variables declaration
	private Context context;
	private boolean downloadTasksStatus = false;
	private static final String TAG = "GetTasks";

	List<Tasks> taskList;
	protected  ITasks iTasks;
	JSONArray jsonArray;
	JSONObject jsonObj;

	public String response;
	
	public int taskId;
	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String taskName;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public RequestProcessor processor = null;
	int Projectid;

	// getter method for project id
	public int getProjectid() {
		return Projectid;
	}

	// setter method for project id
	public void setProjectid(int projectid) {
		Projectid = projectid;
	}

	private HashMap<String, String> map = null;

	{
		//map = new HashMap<String, String>();
		//map.put("callback", "showIP");		
	}

	/**
	 * method used as a constructor to initialize
	 * @param context
	 */
	public GetTasks(Context context) {

		this.context = context;
		this.iTasks = (ITasks) context;
		taskList = new ArrayList<Tasks>();

		processor = new GETRequestProcessor(Const.SERVER_URL,Const.GET_TASK_PATH+Projectid, WebServicesConst.TT_HTTP_GET,this , map, this);

		AsyncTask<String, String, Object> task = 
				new WebServicesAsyncTask(context,processor);
		task.execute("Fetch Response");

	}

	/**
	 * method used as a constructor to initialize with project id
	 * @param context
	 * @param projectid
	 */
	public GetTasks(Context context,int projectid) {

		this.context = context;
		this.iTasks = (ITasks) context;
		taskList = new ArrayList<Tasks>();

		processor = new GETRequestProcessor(Const.SERVER_URL,Const.GET_TASK_PATH+projectid, WebServicesConst.TT_HTTP_GET,this , map, this);

		AsyncTask<String, String, Object> task = 
				new WebServicesAsyncTask(context,processor);
		task.execute("Fetch Response");

	}


	@Override
	public boolean onResponseRecieve(int statusCode, String message) {

		Log.i(TAG,"GetTasks Status Code : " + statusCode);
		Log.i(TAG,"GetTasks Message : " + message);

		if(statusCode == 200) {	

			return false;
		}	
		else {	

			return true;
		}
	}

	@Override
	public boolean onResponseContentReceive(String content) {

		Log.i(TAG,"GetTasks Server Response : " + content);
		if(content != null) {
			response = content;
			downloadTasksStatus = true;
		}
		taskList = getListOfTasks();

		if (this.iTasks != null) {
			iTasks.tasksDownloadDone(taskList);
		}
		return false;
	}

	@Override
	public boolean onParsedDataReceive(Object obj) {
		Log.i(TAG,"GetTasks Parsed Object : " + obj);
		return false;

	}

	@Override
	public void onRequestFailed(Exception e) {
		Log.e(TAG,"GetTasks Error : " + e);
	}

	/**
	 * method used to get the tasks download status
	 * @return downloadTasksStatus
	 */
	public boolean isDownloadTasksStatus() {
		return downloadTasksStatus;
	}

	/**
	 * method used to set tasks download status
	 * @param downloadTasksStatus
	 */
	public void setDownloadTasksStatus(boolean downloadTasksStatus) {
		this.downloadTasksStatus = downloadTasksStatus;
	}

	/**
	 * method used to get the list of tasks
	 * @return taskList
	 */
	public List<Tasks> getListOfTasks() {

		if((response!=null) && (isDownloadTasksStatus())){

			try {

				jsonArray = new JSONArray(response);

				for(int i = 0; i < jsonArray.length(); i++) {

					Tasks tasks = new Tasks();

					jsonObj = jsonArray.getJSONObject(i);

					taskId = jsonObj.getInt("id");
					taskName = jsonObj.getString("name");


					tasks.setTaskId(taskId);
					tasks.setTaskName(taskName);

					Log.i(TAG,"Tasks details" + tasks.getTaskId() + tasks.getTaskName());

					taskList.add(tasks);

				}

			} catch(Exception e) {
				Log.i(TAG,"Json Tasks error");
			}
		}
		return taskList;

	}

	@Override
	public Object parseContent(String content) throws Exception {
		return null;
	}

}
