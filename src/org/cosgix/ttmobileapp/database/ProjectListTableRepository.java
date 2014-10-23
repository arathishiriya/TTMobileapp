package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 * This class provides the database helper instance and provides the table operation
 * @author Sanjib
 *
 */
public class ProjectListTableRepository {
	
	private DatabaseHelper databaseHelper;
	
	private Dao<ProjectListTable, String> projectListTableDao = null;
	
	/**
	 * constructor for getting the database helper instance
	 * @param context
	 */
	public ProjectListTableRepository(Context context) {
		
		try {
	 	    databaseHelper = DatabaseManager.getHelper(context);
	 	    projectListTableDao = databaseHelper.getProjectListTableDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * creating the table
	 * @param projectTable
	 * @return
	 */
	public int create(ProjectListTable projectListTable) {
		
		try {
			return projectListTableDao.create(projectListTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
