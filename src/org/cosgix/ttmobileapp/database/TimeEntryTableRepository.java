package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 * This class provides the database helper instance and provides the table operation
 * @author Sanjib
 *
 */
public class TimeEntryTableRepository {
	
	private DatabaseHelper databaseHelper;
	
	private Dao<TimeEntryTable, String> timeEntryTableDao = null;
	
	/**
	 * constructor for getting the database helper instance
	 * @param context
	 */
	public TimeEntryTableRepository(Context context) {
		
		try {
	 	    databaseHelper = DatabaseManager.getHelper(context);
			timeEntryTableDao = databaseHelper.getTimeEntryTableDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * creating the table
	 * @param timeEntryTable
	 * @return
	 */
	public int create(TimeEntryTable timeEntryTable) {
		
		try {
			return timeEntryTableDao.create(timeEntryTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
