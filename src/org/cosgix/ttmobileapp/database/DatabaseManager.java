package org.cosgix.ttmobileapp.database;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.content.Context;

/**
 * This class contains helper methods which returns a singleton object of the connection source object of the database.
 * 
 * @author Sanjib
 */
public class DatabaseManager {
	
    private static DatabaseHelper databaseHelper = null;

    /**
     * This static helper method creates an object if it doesn't exist and returns that object to the caller. It also
     * ensures to return the same object if it exists.
     * 
     * @param context
     *            - application context
     * 
     * @return CoreDbHelper
     */
    public static DatabaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
            databaseHelper.getWritableDatabase();
        }
        return databaseHelper;
    }

    /**
     * This static helper method used to release the connection of the database source.
     * 
     * @param helper
     *            - core db helper object
     * 
     * @return void
     */
    public static void releaseHelper(DatabaseHelper helper) {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
