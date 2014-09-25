package org.cosgix.ttmobileapp.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

import android.util.Log;

/**
 * TimeEntryData is responsible for creating json data and write to a file
 * 
 * @author Sanjib
 *
 */
public class TimeEntryData {

	// variables declaration
	private int projectId;
	private int taskId;
	private int worktypeId;
	private String descriptionText;
	private String mDate;
	private String timeIn;
	private String timeOut;

	JSONObject parent = new JSONObject();
	JSONObject jsonObject = new JSONObject();

	/**
	 * Method is used as a constructor
	 * @param projectId
	 * @param taskId
	 * @param worktypeId
	 * @param descriptionText
	 * @param mDate
	 * @param timeIn
	 * @param timeOut
	 */
	public TimeEntryData(int projectId,int taskId,int worktypeId,String descriptionText,
			String mDate,String timeIn,String timeOut) {

		this.projectId = projectId;
		this.taskId = taskId;
		this.worktypeId = worktypeId;
		this.descriptionText = descriptionText;
		this.mDate = mDate;
		this.timeIn = timeIn;
		this.timeOut = timeOut;

	}

    /**
     * method is used for creating the json data
     */
	public void createJsonData() {

		try {
			jsonObject.put("ProjectId", projectId);
			jsonObject.put("TaskId", taskId);
			jsonObject.put("WorkTypeId", worktypeId);
			jsonObject.put("Description", descriptionText);
			jsonObject.put("Date", mDate);
			jsonObject.put("StartTime", timeIn);
			jsonObject.put("EndTime", timeOut);

			parent.put("TimeEntry", jsonObject);

			Log.d("output", parent.toString(2));

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * method is used for writing the json data to a file
	 */
	public void writeJsonDatatoFile() {

		try {
			String fpath = "/sdcard/"+"timeentrydata.txt";
			File file = new File(fpath);
			// If file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(parent.toString());
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
