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
public class FavouritesTable {
	
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
	@DatabaseField() 
	private int worktype_id;
	@DatabaseField() 
	private String worktype_name;
	@DatabaseField() 
	private int task_id;
	@DatabaseField() 
	private String task_name;
	@DatabaseField(columnName = "user_id") 
	private String user_id;
	@DatabaseField(columnName = "created_date_time") 
	private String created_date_time;
	@DatabaseField(columnName = "updated_date_time") 
	private String updated_date_time;
	@DatabaseField() 
	private String nickName;
	@DatabaseField() 
	private String colour;
	
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}

}
