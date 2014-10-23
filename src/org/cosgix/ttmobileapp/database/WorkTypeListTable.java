package org.cosgix.ttmobileapp.database;

import com.j256.ormlite.field.DatabaseField;

/**
 *  We just annotate our class as a table and its members as fields and
 *  we' re almost done with creating a table
 *  The second thing to notice is that ORMLite handles all of the basic data types
 *  (integers, strings, floats, dates, and more).
 *  @author Sanjib
 *
 */
public class WorkTypeListTable {
	
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
	
	@Override  
	public String toString() {  
		
		StringBuilder sb = new StringBuilder();  
		sb.append(id);  
		sb.append(", ").append(worktype_id);  
		sb.append(", ").append(worktype_name);  

		return sb.toString();  
		
	}

}
