package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 * This class provides the database helper instance and provides the table operation
 * @author Sanjib
 *
 */
public class TaskTableRepository {
	
	private DatabaseHelper databaseHelper;
	
	private Dao<TaskTable, String> projectTableDao = null;
	
	/**
	 * constructor for getting the database helper instance
	 * @param context
	 */
	public TaskTableRepository(Context context) {
		
		try {
	 	    databaseHelper = DatabaseManager.getHelper(context);
	 	    projectTableDao = databaseHelper.getTaskTableDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * creating the table
	 * @param taskTable
	 * @return
	 */
	public int create(TaskTable taskTable) {
		
		try {
			return projectTableDao.create(taskTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
