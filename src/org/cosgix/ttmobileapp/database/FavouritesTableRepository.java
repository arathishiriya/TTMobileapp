package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 * This class provides the database helper instance and provides the table operation
 * @author Sanjib
 *
 */
public class FavouritesTableRepository {
	
	private DatabaseHelper databaseHelper;
	
	private Dao<FavouritesTable, String> favouritesTableDao = null;
	
	/**
	 * constructor for getting the database helper instance
	 * @param context
	 */
	public FavouritesTableRepository(Context context) {
		
		try {
	 	    databaseHelper = DatabaseManager.getHelper(context);
	 	   favouritesTableDao = databaseHelper.getFavouritesTableDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * creating the table
	 * @param favouritesTable
	 * @return
	 */
	public int create(FavouritesTable favouritesTable) {
		
		try {
			return favouritesTableDao.create(favouritesTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
