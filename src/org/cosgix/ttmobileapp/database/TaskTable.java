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
public class TaskTable implements CoreDomain {

	/** 
	 * Class name will be tablename
	 * Members as a field  
	 */  
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField() 
	private int task_id;
	@DatabaseField() 
	private String task_name;
	@DatabaseField(columnName = "start_date") 
	private String start_date;
	
	private String tableName = "time_entry_task_table_detail";

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	@Override  
	public String toString() {  
		
		StringBuilder sb = new StringBuilder();  
		sb.append(id);  
		sb.append(", ").append(task_id);  
		sb.append(", ").append(task_name);  
		sb.append(", ").append(start_date);  

		return sb.toString();  
		
	}
	@Override
	public String toJson() throws JSONException {

		JSONObject jsonInnerObject = new JSONObject();
		JSONObject jsonMainObject = new JSONObject();

		jsonInnerObject.put("id", getId());
		jsonInnerObject.put("worktype_id", getTask_id());
		jsonInnerObject.put("worktype_name", getTask_name());
		jsonInnerObject.put("description", getStart_date());

		jsonMainObject.put(tableName, jsonInnerObject);
		
		return jsonMainObject.toString();
	}
	@Override
	public void fromJson(JSONObject json) {

		
	}  

}
