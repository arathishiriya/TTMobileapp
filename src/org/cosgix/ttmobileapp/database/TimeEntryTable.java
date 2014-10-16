package org.cosgix.ttmobileapp.database;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;

/**
 *  We just annotate our class as a table and its members as fields and
 *  we' re almost done with creating a table
 *  The second thing to notice is that ORMLite handles all of the basic data types
 *  (integers, strings, floats, dates, and more).
 *  @author Sanjib
 *
 */
public class TimeEntryTable implements CoreDomain {

	/** 
	 * Class name will be tablename
	 * Members as a field  
	 */  
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField() 
	private int project_id;
	@DatabaseField() 
	private int worktype_id;
	@DatabaseField() 
	private int task_id;
	@DatabaseField() 
	private String description;
	@DatabaseField(columnName = "start_date") 
	private String start_date;
	@DatabaseField(columnName = "end_date") 
	private String end_date;
	@DatabaseField(columnName = "created_date_time") 
	private String created_date_time;
	@DatabaseField(columnName = "updated_date_time") 
	private String updated_date_time;
	@DatabaseField(columnName = "user_id") 
	private String user_id;

	private String tableName = "time_entry_table_result_detail";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public int getWorktype_id() {
		return worktype_id;
	}

	public void setWorktype_id(int worktype_id) {
		this.worktype_id = worktype_id;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getCreated_date_time() {
		return created_date_time;
	}

	public void setCreated_date_time(String created_date_time) {
		this.created_date_time = created_date_time;
	}

	public String getUpdated_date_time() {
		return updated_date_time;
	}

	public void setUpdated_date_time(String updated_date_time) {
		this.updated_date_time = updated_date_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override  
	public String toString() {  

		StringBuilder sb = new StringBuilder();  
		sb.append(id);  
		sb.append(", ").append(project_id);  
		sb.append(", ").append(worktype_id);  
		sb.append(", ").append(task_id);  
		sb.append(", ").append(description);  
		sb.append(", ").append(start_date);  
		sb.append(", ").append(end_date);  
		sb.append(", ").append(created_date_time);  
		sb.append(", ").append(updated_date_time);  
		sb.append(", ").append(user_id);  

		return sb.toString();  

	}

	@Override
	public String toJson() throws JSONException {

		JSONObject jsonInnerObject = new JSONObject();
		JSONObject jsonMainObject = new JSONObject();

		jsonInnerObject.put("id", getId());
		jsonInnerObject.put("Project_id", getProject_id());
		jsonInnerObject.put("Worktype_id", getWorktype_id());
		jsonInnerObject.put("Task_id", getTask_id());
		jsonInnerObject.put("Description", getDescription());
		jsonInnerObject.put("Start_date", getStart_date());
		jsonInnerObject.put("End_date", getEnd_date());
		jsonInnerObject.put("Created_date_time", getCreated_date_time());
		jsonInnerObject.put("Updated_date_time", getUpdated_date_time());
		jsonInnerObject.put("User_id", getUser_id());

		jsonMainObject.put(tableName, jsonInnerObject);

		return jsonMainObject.toString();
	}

	@Override
	public void fromJson(JSONObject json) {



	}  

}
