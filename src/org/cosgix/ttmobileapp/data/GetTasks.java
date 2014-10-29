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

	
	Boolean threadProcessing= false;
	int currentItem=0;
	int[] projectids = null;
	List<Tasks>[] taskList = null;
	AsyncTask<String, String, Object>task[] = null;
	protected  ITasks iTasks;
	JSONArray jsonArray;
	JSONObject jsonObj;

	public String response;
	

	public RequestProcessor processor = null;
	

	
	private HashMap<String, String> map = null;

	{
		//map = new HashMap<String, String>();
		//map.put("callback", "showIP");		
	}

	
	
	/**
	 * method used as a constructor to initialize with project id
	 * @param context
	 * @param projectid
	 */
	public GetTasks(Context context,int[] projectids) {

		this.context = context;
		this.iTasks = (ITasks) context;

		taskList = (List<Tasks>[])new ArrayList[projectids.length];
		task = new AsyncTask[projectids.length];
		
		this.projectids = projectids;
		
		for(int i=0;i<projectids.length;i++)
		{
			
				processor = new GETRequestProcessor(Const.SERVER_URL,Const.GET_TASK_PATH+projectids[i], WebServicesConst.TT_HTTP_GET,this , map, this);
				task[i] =new WebServicesAsyncTask(context,processor);				
				task[i].execute("Fetch Response");

		}

		
	}


	@Override
	public boolean onResponseRecieve(int statusCode, String message) {

		Log.i(TAG,"GetTasks Status Code : " + statusCode);
		Log.i(TAG,"GetTasks Message : " + message);

		if(statusCode == 200) {	

			return false;
		}	
		else if(statusCode == 404) {	
			taskList[currentItem]=new ArrayList<Tasks>();
			Tasks tasks = new Tasks();
			tasks.setProjectId(this.projectids[currentItem]);
			tasks.setTaskName("");
			tasks.setTaskId(0);
			taskList[currentItem].add(tasks);
			currentItem++; //This is for incrementing to the next element
			Log.i(TAG,"GetTasks returned 404 but is valid to have no tasks for a particular project " );
			
			return true;
		}
		else
		{
			return true;
		}
	}

	@Override
	public boolean onResponseContentReceive(String content,int statusCode) {
		if(statusCode == 200) {
			

			Log.i(TAG,"GetTasks Server Response : " + content);
			if(content != null) {
				response = content;
				downloadTasksStatus = true;
				taskList[currentItem] = getListOfTasks();
				currentItem++;
				if (projectids.length == currentItem)
				{
					if (this.iTasks != null) {
						iTasks.tasksDownloadDone(taskList);
					}
				}
			}
	
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
			    
				taskList[currentItem]=new ArrayList<Tasks>();
				
				for(int i = 0; i < jsonArray.length(); i++) {

					Tasks tasks = new Tasks();
					jsonObj = jsonArray.getJSONObject(i);
					tasks.setTaskId(jsonObj.getInt("id"));
					tasks.setTaskName(jsonObj.getString("name"));
					tasks.setProjectId(this.projectids[currentItem]);
					Log.i(TAG,"Tasks details" + tasks.getprojectId() + tasks.getTaskName());
					taskList[currentItem].add(tasks);
				}

			} catch(Exception e) {
				Log.i(TAG,"Json Tasks error");
			}
		}
		return taskList[currentItem];

	}

	@Override
	public Object parseContent(String content) throws Exception {
		return null;
	}

}
