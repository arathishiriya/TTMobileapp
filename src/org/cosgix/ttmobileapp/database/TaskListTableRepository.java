package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 *  We just annotate our class as a table and its members as fields and
 *  we' re almost done with creating a table
 *  The second thing to notice is that ORMLite handles all of the basic data types
 *  (integers, strings, floats, dates, and more).
 *  @author Sanjib
 *
 */
public class TaskListTableRepository {
	
	private DatabaseHelper databaseHelper;
	
	private Dao<TaskListTable, String> taskListTableDao = null;
	
	/**
	 * constructor for getting the database helper instance
	 * @param context
	 */
	public TaskListTableRepository(Context context) {
		
		try {
	 	    databaseHelper = DatabaseManager.getHelper(context);
	 	   taskListTableDao = databaseHelper.getTaskListTableDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * creating the table
	 * @param projectTable
	 * @return
	 */
	public int create(TaskListTable taskListTable) {
		
		try {
			return taskListTableDao.create(taskListTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
