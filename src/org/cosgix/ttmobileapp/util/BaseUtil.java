package org.cosgix.ttmobileapp.util;

import java.util.List;

import org.cosgix.ttmobileapp.database.FavouritesTable;
import org.cosgix.ttmobileapp.database.FavouritesTableRepository;
import org.cosgix.ttmobileapp.database.ProjectListTable;
import org.cosgix.ttmobileapp.database.ProjectListTableRepository;
import org.cosgix.ttmobileapp.database.ProjectTable;
import org.cosgix.ttmobileapp.database.ProjectTableRepository;
import org.cosgix.ttmobileapp.database.TaskListTable;
import org.cosgix.ttmobileapp.database.TaskListTableRepository;
import org.cosgix.ttmobileapp.database.TaskTable;
import org.cosgix.ttmobileapp.database.TaskTableRepository;
import org.cosgix.ttmobileapp.database.TimeEntryTable;
import org.cosgix.ttmobileapp.database.TimeEntryTableRepository;
import org.cosgix.ttmobileapp.database.WorkTypeListTable;
import org.cosgix.ttmobileapp.database.WorkTypeListTablerepository;
import org.cosgix.ttmobileapp.database.WorkTypeTable;
import org.cosgix.ttmobileapp.database.WorkTypeTableRepository;

import android.app.Activity;

/**
 * 
 * This class provides all the application related data
 * storing data in db
 * @author Sanjib
 *
 */
public class BaseUtil {
	
	public static boolean insertProjectFlag = false;
	public static boolean insertWorkTypeFlag = false;
	public static boolean insertTaskFlag = false;
	
	List<ProjectListTable> projectList;
	
	/**
	 * This method inserting the time entry table data to the database
	 * @param activity
	 * @param selected_projectId
	 * @param selected_worktypeId
	 * @param selected_taskId
	 * @param descriptionText
	 * @param timeIn
	 * @param timeOut
	 * @param created_date_time
	 * @param updated_date_time
	 * @param user_id
	 */
	public static void insertTimeEntryTableData(Activity activity,int selected_projectId,
			int selected_worktypeId,int selected_taskId,String descriptionText,String timeIn,
			String timeOut,String created_date_time, String updated_date_time, String user_id) {
		
		TimeEntryTableRepository timeEntryTableRepository = new TimeEntryTableRepository(activity);
		
		TimeEntryTable timeEntryTable = new TimeEntryTable();
		timeEntryTable.setProject_id(selected_projectId);
		timeEntryTable.setWorktype_id(selected_worktypeId);
		timeEntryTable.setTask_id(selected_taskId);
		timeEntryTable.setDescription(descriptionText);
		timeEntryTable.setStart_date(timeIn);
		timeEntryTable.setEnd_date(timeOut);
		timeEntryTable.setCreated_date_time(created_date_time);
		timeEntryTable.setUpdated_date_time(updated_date_time);
		timeEntryTable.setUser_id(user_id);
		
		timeEntryTableRepository.create(timeEntryTable);
		
	}
	
	public static void insertProjectTableData(Activity activity,int selected_projectId,
			String project_name,String timeIn,String timeOut,int duration) {
		
		ProjectTableRepository projectTableRepository = new ProjectTableRepository(activity);
		
		ProjectTable projectTable = new ProjectTable();
		projectTable.setProject_id(selected_projectId);
		projectTable.setProject_name(project_name);
		projectTable.setStart_date(timeIn);
		projectTable.setEnd_date(timeOut);
		projectTable.setDuration(duration);
		
		projectTableRepository.create(projectTable);
		
	}
	
	public static void insertWorkTypeTableData(Activity activity,int worktype_id,
			String worktype_name,String description) {
		
		WorkTypeTableRepository workTypeTableRepository = new WorkTypeTableRepository(activity);
		
		WorkTypeTable workTypeTable = new WorkTypeTable();
		workTypeTable.setWorktype_id(worktype_id);
		workTypeTable.setWorktype_name(worktype_name);
		workTypeTable.setDescription(description);
		
		workTypeTableRepository.create(workTypeTable);
		
	}
	
	public static void insertTaskTableData(Activity activity,int task_id,
			String task_name,String start_date) {
		
		TaskTableRepository TaskTableRepository = new TaskTableRepository(activity);
		
		TaskTable taskTable = new TaskTable();
		taskTable.setTask_id(task_id);
		taskTable.setTask_name(task_name);
		taskTable.setStart_date(start_date);
		
		TaskTableRepository.create(taskTable);
		
	}
	
	public static void insertProjectListTableData(Activity activity,int selected_projectId,
			String project_name) {
		
		ProjectListTableRepository projectListTableRepository = new ProjectListTableRepository(activity);
		
		ProjectListTable projectListTable = new ProjectListTable();
		projectListTable.setProject_id(selected_projectId);
		projectListTable.setProject_name(project_name);
		
		projectListTableRepository.create(projectListTable);
		
		insertProjectFlag = true;
		
	}
	
	public static void insertWorkTypeListTableData(Activity activity,int worktype_id,
			String worktype_name) {
		
		WorkTypeListTablerepository workTypeListTableRepository = new WorkTypeListTablerepository(activity);
		
		WorkTypeListTable workTypeListTable = new WorkTypeListTable();
		workTypeListTable.setWorktype_id(worktype_id);
		workTypeListTable.setWorktype_name(worktype_name);
		
		workTypeListTableRepository.create(workTypeListTable);
		
		insertWorkTypeFlag = true;
		
	}
	
	public static void insertTaskListTableData(Activity activity,int project_id,
			String task_name) {
		
		TaskListTableRepository taskListTableRepository = new TaskListTableRepository(activity);
		
		TaskListTable taskListTable = new TaskListTable();
		taskListTable.setProject_id(project_id);
		taskListTable.setTask_name(task_name);
		
		taskListTableRepository.create(taskListTable);
		
		insertTaskFlag = true;
		
	}
	
	public static void insertFavouritesTableData(Activity activity,int project_id,
			String project_name,int worktype_id,String worktype_name,int task_id,
			String task_name,String user_id,String created_date_time,String updated_date_time,
			String nickName,String colour) {
		
		FavouritesTableRepository favouritesTableRepository = new FavouritesTableRepository(activity);
		
		FavouritesTable favouritesTable = new FavouritesTable();
		favouritesTable.setProject_id(project_id);
		favouritesTable.setProject_name(project_name);
		favouritesTable.setWorktype_id(worktype_id);
		favouritesTable.setWorktype_name(worktype_name);
		favouritesTable.setTask_id(task_id);
		favouritesTable.setTask_name(task_name);
		favouritesTable.setUser_id(user_id);
		favouritesTable.setCreated_date_time(created_date_time);
		favouritesTable.setUpdated_date_time(updated_date_time);
		favouritesTable.setNickName(nickName);
		favouritesTable.setColour(colour);
		
		favouritesTableRepository.create(favouritesTable);
		
	}
	
	public static void getProjectListData(Activity activity) {
		
		ProjectListTableRepository projectListTableRepository = new ProjectListTableRepository(activity);
		projectListTableRepository.getProjectListData();
		
	}
	
	public void deleteDatabase() {
		
		
	}
	
}
