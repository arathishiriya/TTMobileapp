package org.cosgix.ttmobileapp.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;




import org.cosgix.ttmobileapp.services.UpdateService;


/**
 * Update Activity is responsible to fetch all the project related data from the webserver 
 * And 
 * 
 * @param none 
 * 
 * 
 * @param 
 * 
 */
public class UpdateActivity extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "UpdateActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update);
        
      final ProgressDialog ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setTitle("Updating From Server :) ");
        ringProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ringProgressDialog.setIndeterminate(true);
     
        
        ringProgressDialog.setCancelable(true);
        ringProgressDialog.show();
        
        
        startUpdateService();
 
        for(int i = 0;i<1000000;i++)
        {
        	//Do nothing...Just testing 
        }
        stopUpdateService();
        ringProgressDialog.dismiss();

		
    }

	
	 private void startUpdateService(){
	    	if (!isUpdateServiceRunning()) {
	    		// use this to start and trigger a service
	    		Intent serviceIntent= new Intent(this, UpdateService.class);
	    		// potentially add data to the intent
	    		    		
	    		startService(serviceIntent);
	    		serviceIntent=null;;
	    		//testing the dismiss progress Dialog
	    		
	    	}//end if not running
	    }//end startUpdateService
	    
	    private void stopUpdateService(){
	    	if (isUpdateServiceRunning()) {
	    	    Intent serviceIntent=new Intent(this,UpdateService.class);
	    	    stopService(serviceIntent);
	    	    serviceIntent=null;
	    	}//end if running
	    }//end stopUpdateService
	    // Check to see if the Update Service is running
	    private boolean isUpdateServiceRunning() {
	        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	            if ("org.cosgix.ttmobileapp.services.UpdateService".equals(service.service.getClassName())) {
	                return true;
	            }
	        }
	        return false;
	    }
	
}
