package org.cosgix.ttmobileapp.activity;

import java.io.File;

import org.cosgix.ttmobileapp.database.DatabaseHelper;
import org.cosgix.ttmobileapp.database.DatabaseManager;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Environment;

public class TimeTrackerApplication extends Application {

	private DatabaseHelper databaseHelper;

	private static TimeTrackerApplication singleton;

	public static TimeTrackerApplication getInstance() {

		return singleton;

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		databaseHelper = DatabaseManager.getHelper(TimeTrackerApplication.this);
		singleton = this;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		//databaseHelper.deleteDatabase();

//		File checkFile = new File("/sdcard/"+ "timeentryormliteandroid.db");//getting the control of sdcard files
		File checkFile = new File(Environment.getExternalStorageDirectory()+ "timeentryormliteandroid.db");
		checkFile.delete();
		//deleteDirectory(checkFile);

	}

	public static boolean deleteDirectory(File path) {
		// TODO Auto-generated method stub
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return(path.delete());
	}

}
