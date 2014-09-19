package org.cosgix.ttmobileapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cosgix.ttmobileapp.webservices.*;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This class provides WorkTypes details from work types
 * @author Sanjib
 *
 */
public class GetWorkTypes implements IResponseHandler,IResponseParser{

	// variables declaration
	private static final String TAG = "GetWorkTypes";
	private Context context;
	private boolean downloadStatus=false;
	protected IWorkTypes iWorkTypes;

	List<WorkTypes> workTypeList;

	JSONArray jsonArray;
	JSONObject jsonObj;

	public String response;

	public RequestProcessor processor = null;

	private HashMap<String, String> map = null;

	{
		//map = new HashMap<String, String>();
		//map.put("callback", "showIP");		
	}

	public boolean isDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(boolean downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	/**
	 * method used as a constructor to initialize
	 * @param context
	 */
	public GetWorkTypes(Context context) {

		this.context = context;
		this.iWorkTypes = (IWorkTypes) context;
		workTypeList = new ArrayList<WorkTypes>();

		processor = new GETRequestProcessor(Const.SERVER_URL,Const.GET_WORK_TYPE_PATH, WebServicesConst.TT_HTTP_GET,this , map, this);

		AsyncTask<String, String, Object> worktypetask = 
				new WebServicesAsyncTask(context,processor);
		worktypetask.execute("Fetch Response");

	}

	@Override
	public boolean onResponseRecieve(int statusCode, String message) {

		Log.i(TAG,"Status Code : " + statusCode);
		Log.i(TAG,"Message : " + message);

		if(statusCode == 200) {	
			return false;
		}	
		else {	
			return true;
		}
	}

	@Override
	public boolean onResponseContentReceive(String content) {

		Log.i(TAG,"Server Response : " + content);
		if(content != null) {
			response = content;
			downloadStatus=true;
		}
		workTypeList = getListOfWorkTypes();

		if (iWorkTypes != null) {
			iWorkTypes.workTypesDownloadDone(workTypeList);

		}

		return false;
	}

	@Override
	public boolean onParsedDataReceive(Object obj) {
		Log.e(TAG,"Parsed Object : " + obj);
		return false;

	}

	@Override
	public void onRequestFailed(Exception e) {
		Log.e(TAG,"Error : " + e);
	}

	/**
	 * method used to get the list of work types
	 * @return workTypeList
	 */
	public List<WorkTypes> getListOfWorkTypes() {

		if((response!=null) && (isDownloadStatus())){

			try {

				jsonArray = new JSONArray(response);

				for(int i = 0; i < jsonArray.length(); i++) {

					WorkTypes wokTypes = new WorkTypes();

					jsonObj = jsonArray.getJSONObject(i);

					int worktypeId = jsonObj.getInt("id");
					String worktypeName = jsonObj.getString("name");
					String worktypeDescription = jsonObj.getString("description");


					wokTypes.setWorktypeId(worktypeId);
					wokTypes.setWorktypeName(worktypeName);
					wokTypes.setWorktypeDescription(worktypeDescription);

					//Log.i(TAG,"WorkTypes details"+wokTypes.getWorktypeId() +wokTypes.getWorktypeName() +wokTypes.getWorktypeDescription());

					workTypeList.add(wokTypes);

				}


			} catch(Exception e) {
				Log.e(TAG,"JsonWorkTypes error");
			}
		}
		return workTypeList;

	}

	@Override
	public Object parseContent(String content) throws Exception {
		return null;
	}

}
