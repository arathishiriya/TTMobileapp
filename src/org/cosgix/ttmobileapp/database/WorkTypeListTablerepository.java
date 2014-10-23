package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 * This class provides the database helper instance and provides the table operation
 * @author Sanjib
 *
 */
public class WorkTypeListTablerepository {
	
	private DatabaseHelper databaseHelper;
	
	private Dao<WorkTypeListTable, String> workTypeListTableDao = null;
	
	/**
	 * constructor for getting the database helper instance
	 * @param context
	 */
	public WorkTypeListTablerepository(Context context) {
		
		try {
	 	    databaseHelper = DatabaseManager.getHelper(context);
	 	    workTypeListTableDao = databaseHelper.getWorkTypeListTableDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * creating the table
	 * @param projectTable
	 * @return
	 */
	public int create(WorkTypeListTable workTypeListTable) {
		
		try {
			return workTypeListTableDao.create(workTypeListTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
