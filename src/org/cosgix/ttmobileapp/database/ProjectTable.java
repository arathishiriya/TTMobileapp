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
public class ProjectTable implements CoreDomain {

	/** 
	 * Class name will be tablename
	 * Members as a field  
	 */  
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField() 
	private int project_id;
	@DatabaseField() 
	private String project_name;
	@DatabaseField(columnName = "start_date") 
	private String start_date;
	@DatabaseField(columnName = "end_date") 
	private String end_date;
	@DatabaseField() 
	private int duration;
	
	private String tableName = "time_entry_project_table_detail";

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
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
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
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override  
	public String toString() {  

		StringBuilder sb = new StringBuilder();  
		sb.append(id);  
		sb.append(", ").append(project_id);  
		sb.append(", ").append(project_name);  
		sb.append(", ").append(start_date);  
		sb.append(", ").append(end_date);  
		sb.append(", ").append(duration);  

		return sb.toString();  

	}
	@Override
	public String toJson() throws JSONException {
		
		JSONObject jsonInnerObject = new JSONObject();
		JSONObject jsonMainObject = new JSONObject();

		jsonInnerObject.put("id", getId());
		jsonInnerObject.put("Project_id", getProject_id());
		jsonInnerObject.put("Project_name", getProject_name());
		jsonInnerObject.put("Start_date", getStart_date());
		jsonInnerObject.put("End_date", getEnd_date());
		jsonInnerObject.put("Duration", getDuration());

		jsonMainObject.put(tableName, jsonInnerObject);
		
		return jsonMainObject.toString();
	}
	@Override
	public void fromJson(JSONObject json) {

		
	}  

}
