package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 * This class provides the database helper instance and provides the table operation
 * @author Sanjib
 *
 */
public class WorkTypeTableRepository {
	
	private DatabaseHelper databaseHelper;
	
	private Dao<WorkTypeTable, String> projectTableDao = null;
	
	/**
	 * constructor for getting the database helper instance
	 * @param context
	 */
	public WorkTypeTableRepository(Context context) {
		
		try {
	 	    databaseHelper = DatabaseManager.getHelper(context);
	 	    projectTableDao = databaseHelper.getWorkTypeTableDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * creating the table
	 * @param workTypeTable
	 * @return
	 */
	public int create(WorkTypeTable workTypeTable) {
		
		try {
			return projectTableDao.create(workTypeTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
