package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 * This class provides the database helper instance and provides the table operation
 * @author Sanjib
 *
 */
public class ProjectTableRepository {
	
	private DatabaseHelper databaseHelper;
	
	private Dao<ProjectTable, String> projectTableDao = null;
	
	/**
	 * constructor for getting the database helper instance
	 * @param context
	 */
	public ProjectTableRepository(Context context) {
		
		try {
	 	    databaseHelper = DatabaseManager.getHelper(context);
	 	    projectTableDao = databaseHelper.getProjectTableDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * creating the table
	 * @param projectTable
	 * @return
	 */
	public int create(ProjectTable projectTable) {
		
		try {
			return projectTableDao.create(projectTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
