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
public class WorkTypeTable implements CoreDomain {

	/** 
	 * Class name will be tablename
	 * Members as a field  
	 */  
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField() 
	private int worktype_id;
	@DatabaseField() 
	private String worktype_name;
	@DatabaseField() 
	private String description;
	
	private String tableName = "time_entry_worktype_table_detail";

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWorktype_id() {
		return worktype_id;
	}
	public void setWorktype_id(int worktype_id) {
		this.worktype_id = worktype_id;
	}
	public String getWorktype_name() {
		return worktype_name;
	}
	public void setWorktype_name(String worktype_name) {
		this.worktype_name = worktype_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override  
	public String toString() {  
		
		StringBuilder sb = new StringBuilder();  
		sb.append(id);  
		sb.append(", ").append(worktype_id);  
		sb.append(", ").append(worktype_name);  
		sb.append(", ").append(description);  

		return sb.toString();  
		
	}
	@Override
	public String toJson() throws JSONException {

		JSONObject jsonInnerObject = new JSONObject();
		JSONObject jsonMainObject = new JSONObject();

		jsonInnerObject.put("id", getId());
		jsonInnerObject.put("worktype_id", getWorktype_id());
		jsonInnerObject.put("worktype_name", getWorktype_name());
		jsonInnerObject.put("description", getDescription());

		jsonMainObject.put(tableName, jsonInnerObject);
		
		return jsonMainObject.toString();
	}
	@Override
	public void fromJson(JSONObject json) {

		
	}  

}
