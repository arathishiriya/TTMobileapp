package org.cosgix.ttmobileapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * TimeEntryActivity is responsible for getting the project related information from either DB or webserver 
 * (depending on whether the user is offline or online)
 * 
 * @author Arathi 
 */
public class TimeEntryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeentry);
        
        //Need to check network connectivity
        
        //Need to call the Update Service
    }
}