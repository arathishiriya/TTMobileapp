package org.cosgix.ttmobileapp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


public class UpdateService extends Service 
{
	private static final String TAG = "UpdateService";
	
	private static Updater updater;
	// This is the object that receives interactions from clients. See
		// RemoteService for a more complete example.
		private final IBinder mBinder = new LocalBinder();

		/**
		 * Class for clients to access. Because we know this service always runs in
		 * the same process as its clients, we don't need to deal with IPC.
		 */
		public class LocalBinder extends Binder 
		{
			public UpdateService getService()
			{
				return UpdateService.this;
			}
		}
		public Updater getUpdater() {
			return updater;
		}

		public void setUpdater(Updater updater) {
			//this.updater = updater;
		}
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.d(TAG, "onCreate");
		// Tell the user we received a start command
		Toast toast=Toast.makeText(this, "Update Service Starting...", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		Log.d(TAG, "Service is starting.");
	}
	
	@Override
	public void onDestroy() 
	{
		Log.d(TAG, "onDestroy");
		// Stop the loop and interrupt the thread
		stopUpdaterThread();
		// Tell the user we stopped.
		Toast toast=Toast.makeText(this, "Update Service Stopped", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
		super.onDestroy();
	}
		
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		 
		Log.d(TAG, "onStartCommand");
		
		// Start the updater first since the phone state listener uses it.
		startUpdaterThread();	
		
		// return Service.START_NOT_STICKY;
		return Service.START_STICKY;
	}
	private synchronized void startUpdaterThread()
	{
		if (updater == null) 
		{
			updater = new Updater(this.getApplicationContext());
			updater.start();
			Log.d(TAG, "Start the updater thread: " + updater.getId());
		}
	}
	private synchronized void stopUpdaterThread()
	{
		if (updater != null)
		{
			Log.d(TAG,"Attempting to stop the updater thread: " + updater.getId());
			Thread moribund = updater;
			updater = null;
			try 
			{
				
				moribund.interrupt();
				Log.d(TAG,"Sent interrupt to moribund thread:" + moribund.getId());
			}
			catch (Exception ex)
			{
				 Log.d(TAG, "Failed to interrupt the updater thread");
			}
		}
	}
	
	
	 @Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		 
		return mBinder;
	}
	 
	 private class Updater extends Thread 
		{
		 private Context mContext=null;
		 public Updater(Context context){
				setmContext(context);
				//WE need to start the login process here
				
			}//end constructor
			
		 @Override
			public void run() 
			{
			 startProcessing();
			}//end run
		 
		 	private void startProcessing()
			{
				Log.d(TAG, "Starting to get the client,project,task details here: "
						+ Thread.currentThread().getId());
				//Need to start the login process here
			}

			public Context getmContext() {
				return mContext;
			}

			public void setmContext(Context mContext) {
				this.mContext = mContext;
			}
		}
	 
}