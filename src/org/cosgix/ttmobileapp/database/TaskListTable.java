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
public class TaskListTable {
	
	/** 
	 * Class name will be tablename
	 * Members as a field  
	 */  
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField() 
	private int project_id;
	@DatabaseField() 
	private String task_name;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int task_id) {
		this.project_id = task_id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	
	@Override  
	public String toString() {  
		
		StringBuilder sb = new StringBuilder();  
		sb.append(id);  
		sb.append(", ").append(project_id);  
		sb.append(", ").append(task_name);  

		return sb.toString();  
		
	}

}
