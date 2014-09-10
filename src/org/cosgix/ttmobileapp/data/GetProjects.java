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

public class GetProjects implements IResponseHandler,IResponseParser{
	
	private Context context;
	private boolean downloadProjectsStatus = false;
	private static final String TAG = "GetProjects";
	protected  IProjects iProjects;
	List<Projects> projectList;
	
	JSONArray jsonArray;
	JSONObject jsonObj;
	

	public String response;
	
	public RequestProcessor processor = null;

	private HashMap<String, String> map = null;

	{
		//map = new HashMap<String, String>();
		//map.put("callback", "showIP");		
	}
	
	public GetProjects(Context context) {
		
		this.context = context;
		this.iProjects= (IProjects) context; 
		projectList = new ArrayList<Projects>();
		
		processor = new GETRequestProcessor(Const.SERVER_URL,Const.GET_PROJECT_PATH, WebServicesConst.TT_HTTP_GET,this , map,   this);
		
		AsyncTask<String, String, Object> projectstask = 
				new WebServicesAsyncTask(context,processor);
		projectstask.execute("Fetch Response");
		
	}
	

	
	@Override
	public boolean onResponseRecieve(int statusCode, String message) {

		Log.i(TAG,"GetProjects Status Code : " + statusCode);
		Log.i(TAG,"GetProjects Message : " + message);
		
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
		//Log.i(TAG,"GetProjects Server Response : " + content);
		if(content != null)
		{
			response = content;
			downloadProjectsStatus = true;
		}
		projectList = getListOfProjects();
		
	
		if (iProjects != null)
		{
			iProjects.projectListDownloadDone(projectList);
		}
		
		return false;
	}

	@Override
	public boolean onParsedDataReceive(Object obj) {
		//Log.i(TAG,"GetProjects Parsed Object : " + obj);
		return false;

	}

	@Override
	public void onRequestFailed(Exception e) {
		Log.e(TAG,"GetProjects Error : " + e);
	}
	
	public boolean isDownloadProjectsStatus() {
		return downloadProjectsStatus;
	}


	public void setDownloadProjectsStatus(boolean downloadProjectsStatus) {
		this.downloadProjectsStatus = downloadProjectsStatus;
	}
	
	public List<Projects> getListOfProjects() {
		
		if((response != null) && (isDownloadProjectsStatus())){
	
		try {
			
			jsonArray = new JSONArray(response);
			
			for(int i = 0; i < jsonArray.length(); i++) {
				
				Projects projects = new Projects();
				
				jsonObj = jsonArray.getJSONObject(i);
				
				int projectId = jsonObj.getInt("id");
				int projectClientId = jsonObj.getInt("client_id");
				String projectName = jsonObj.getString("name");
				
				
				projects.setProjectId(projectId);
				projects.setProjectClientId(projectClientId);
				projects.setProjectName(projectName);

				//Log.i(TAG,"Projects details" + projects.getProjectId() + projects.getProjectClientId() + projects.getProjectName());
				
				projectList.add(projects);
				
			}
	

		} catch(Exception e) {
			Log.i(TAG,"Json Projects error");
		}
		}
		return projectList;

	}



	@Override
	public Object parseContent(String content) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
