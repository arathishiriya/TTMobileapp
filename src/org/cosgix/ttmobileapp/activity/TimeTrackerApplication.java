package org.cosgix.ttmobileapp.activity;

import android.app.Application;
import android.content.res.Configuration;

public class TimeTrackerApplication extends Application {
	
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
		singleton = this;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
