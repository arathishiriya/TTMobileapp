package org.cosgix.ttmobileapp.activity;

import java.util.Calendar;
import java.util.List;

import org.cosgix.ttmobileapp.R;

import org.cosgix.ttmobileapp.adapter.TimeEntryArrayAdapter;
import org.cosgix.ttmobileapp.data.GetTasks;
import org.cosgix.ttmobileapp.data.ITasks;
import org.cosgix.ttmobileapp.data.Tasks;
import android.app.ActionBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TimePicker;

/**
 * TimeEntryActivity is responsible for getting the project related information from either DB or webserver 
 * (depending on whether the user is offline or online)
 * 
 * @author Arathi 
 * @author Sanjib
 */
public class TimeEntryActivity extends Activity implements ITasks {

	// variables declaration
	private static final String TAG = "TimeEntryActivity";

	private ListView listView;
	private EditText descriptionEditText;
	private TextView dateText;
	private TextView startTimeText;
	private TextView endTimeText;
	private Button addEntrybutton;

	String message;
	String worktype_message;
	String addentry_message;
	
	String taskName;
	
	private boolean  mTasksListLoaded = false;

	String descriptionText;

	private int year;
	private int month;
	private int day;

	static final int DATE_PICKER_ID = 1; 

	Calendar calendar;
	private int hour;
	private int minute;

	public static final int TIME_PICKER_INTERVAL=10;

	static final int START_TIME_PICKER_ID = 2;
	static final int END_TIME_PICKER_ID = 3;

	InputMethodManager inputMethodManager;

	TimeEntry timeEntry;
	List<TimeEntry> timeEntryList;
	TimeEntryArrayAdapter shareAdapter;
	GetTasks getTasks;
	List<Tasks> mTasksList;
	static List<Tasks> TasksList;

	public static List<Tasks> getTasksList() {
		return TasksList;
	}

	public void setTasksList(List<Tasks> tasksList) {
		TasksList = tasksList;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeentry);

		getWidgetId();

		showActionBar();
		getOverflowMenu();

		timeEntryList = TimeEntryUtil.getTimeEntryListDeatils(this);

		// time entry array adapter to display the items
		shareAdapter = new TimeEntryArrayAdapter(this,R.layout.activity_timeentryrow, timeEntryList);
		shareAdapter.notifyDataSetChanged();

		displayListItems();

		inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		getDescriptionText();

		getCurrentDateTimeByCalendar();
		showCurrentDate();
		showDatePickerDialog();

		showCurrentTime();
		showTimePickerDialog();

	}

	/**
	 * method used to display the action bar
	 */
	private void showActionBar() {

		ActionBar actionBar = getActionBar();
		actionBar.setTitle(getResources().getString(R.string.addtimeentry)); 
		actionBar.setIcon(R.drawable.ic_time);
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightskyblue)));

	}

	/**
	 * method used to get widget id
	 */
	private void getWidgetId() {

		listView = (ListView)findViewById(R.id.listView1);
		descriptionEditText = (EditText)findViewById(R.id.descriptioneditText);
		dateText = (TextView)findViewById(R.id.dateOutput);
		startTimeText = (TextView)findViewById(R.id.starttime);
		endTimeText = (TextView)findViewById(R.id.endtime);
		addEntrybutton = (Button)findViewById(R.id.addEntrybutton);

		addEntrybutton.setVisibility(View.GONE);

	}

	/**
	 * method used to display list items and list item click event
	 */
	private void displayListItems() {

		listView.setAdapter(shareAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {

				timeEntry = (TimeEntry) adapterView.getItemAtPosition(position);

				switch(position) {

				case 0:
					// project clicked
					invokeProjectListActivity();
					break;
				case 1:
					// task clicked
					if(mTasksListLoaded)
					{

						invokeTasksListActivity();
					}
					else
					{
						Log.d(TAG,"Please wait, data downloading from the server");
					}
					break;
				case 2:
					// work type clicked
					invokeWorkTypeActivity();
					break;

				default:
					break;


				}

			}
		});

	}

	/**
	 * method used for invoking ProjectListActivity 
	 */
	private void invokeProjectListActivity() {
		Log.i(TAG, "LoginActivity invoke ProjectListActivity");
		Intent intent = new Intent(TimeEntryActivity.this,ProjectListActivity.class);
		startActivityForResult(intent, 1);

	}
	
	protected void invokeTasksListActivity() {

        if(mTasksList != null) {		
		Log.i(TAG, "LoginActivity invoke TasksListActivity");
		Intent intent = new Intent(TimeEntryActivity.this,TaskListActivity.class);
		startActivityForResult(intent, 4);
        }
        else {
    		Log.i(TAG, "taskName is null");
        }
		
	}

	/**
	 * method used for invoking WorkTypeActivity 
	 */
	private void invokeWorkTypeActivity() {
		Log.i(TAG, "LoginActivity invoke invokeWorkTypeActivity");
		Intent intent = new Intent(TimeEntryActivity.this,WorkTypeActivity.class);
		startActivityForResult(intent, 2);

	}

	// Call Back method  to get the Message form other Activity  
	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		super.onActivityResult(requestCode, resultCode, data);  

		if(data != null ) {
			// check if the request code is same as what is passed  here it is 1  
			if(requestCode == 1) { 

				message = data.getStringExtra("PROJECT_MESSAGE");
				int selected_projectId =  data.getIntExtra("PROJECT_SELECTED",0);

				getTasks = new GetTasks(TimeEntryActivity.this,selected_projectId);
				TasksList = getTasks.getListOfTasks();
				taskName = getTasks.getTaskName();

				Log.d(TAG,"message" + message);
				//shareAdapter.remove(shareAdapter.getItem(0));
				timeEntryList.remove(0);
				timeEntry.setTimeEntryName(message);
				timeEntryList.add(0,timeEntry);
				shareAdapter.notifyDataSetChanged();

			}  
			if(requestCode == 2) { 

				worktype_message = data.getStringExtra("WORKTYPE_MESSAGE");
				int worktype_selected_index =  data.getIntExtra("WORKTYPE_SELECTED",0);

				Log.d(TAG,"worktype_message" + worktype_message);
				//shareAdapter.remove(shareAdapter.getItem(2));
				timeEntryList.remove(2);
				timeEntry.setTimeEntryName(worktype_message);
				timeEntryList.add(2,timeEntry);
				shareAdapter.notifyDataSetChanged();

			}  

			if(requestCode == 3) { 

				addentry_message = data.getStringExtra("ADDENTRY_MESSAGE");

				Log.d(TAG,"addentry_message" + addentry_message);

				listView.invalidateViews();
				descriptionEditText.getText().clear();
				this.recreate();

			}  
			
			if(requestCode == 4) { 

				shareAdapter.notifyDataSetChanged();
				taskName = getTasks.getTaskName();
				Log.d(TAG,"taskName" + taskName);
				timeEntryList.remove(1);
				timeEntry.setTimeEntryName(taskName);
				timeEntryList.add(1,timeEntry);
				shareAdapter.notifyDataSetChanged();

			}
		}

	}

	@Override
	public boolean tasksDownloadDone(List<Tasks> tasksList) {

		for(Tasks tasks: tasksList) {
			Log.d(TAG,"tasks" + tasks.getTaskName());

			//tempProjectid = projects.getProjectId();
		}

		// get the tasks list if not null 
		if(tasksList != null) {
			mTasksList = tasksList;
			mTasksListLoaded = true;
		}

		return false;
	}

	/**
	 * method used to get the description text
	 */
	private void getDescriptionText() {

		descriptionEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Show soft keyboard for the user to enter the value.
				inputMethodManager.showSoftInput(descriptionEditText, InputMethodManager.SHOW_FORCED);

				descriptionText = descriptionEditText.getText().toString();

			}
		});

		descriptionEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int keyCode, KeyEvent arg2) {
				if(keyCode == EditorInfo.IME_ACTION_DONE){
					//do what you want

					if(descriptionEditText.getText().length() > 0) {
						addEntrybutton.setVisibility(View.VISIBLE);
						addEntryEvent();
					}
					else {
						addEntrybutton.setVisibility(View.GONE);
					}
				}
				return false;
			}


		});

	}

	@Override
	public void onBackPressed() {

		return;
	}//onBackPressed

	/**
	 * method used to create the date and time picker dialog
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			// open datepicker dialog. 
			// set date picker for current date 
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListener, year, month,day);

		case START_TIME_PICKER_ID:
			// set start time picker as current time
			return new TimePickerDialog(this, startTimePickerListener, hour, minute,false);

		case END_TIME_PICKER_ID:
			// set end time picker as current time
			return new TimePickerDialog(this, endTimePickerListener, hour, minute,false);
		}

		return null;
	}

	// Get current date by calender
	private void getCurrentDateTimeByCalendar() {

		// Get current date by calender

		calendar = Calendar.getInstance();
		year  = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day   = calendar.get(Calendar.DAY_OF_MONTH);

		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);

	}

	// Show current date
	private void showCurrentDate() {

		dateText.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(month + 1).append("-").append(day).append("-")
		.append(year).append(" "));

	}

	// ImageView and TextView listener to show date picker dialog
	private void showDatePickerDialog() {

		dateText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// On button click show datepicker dialog 
				showDialog(DATE_PICKER_ID);

			}
		});

	}

	// set date in Date picker dialog 
	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year  = selectedYear;
			month = selectedMonth;
			day   = selectedDay;

			// Show selected date 
			dateText.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

		}
	};

	// Show current time
	private void showCurrentTime() {

		// set current time into textview
		startTimeText.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)).append(getTimeFormat()).toString());

		endTimeText.setText(new StringBuilder().append(padding_str(00)).append(":").append(padding_str(00)).append(getTimeFormat()).toString());

	}

	// ImageView and TextView listener to show time picker dialog
	private void showTimePickerDialog() {

		startTimeText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// On button click show datepicker dialog 
				showDialog(START_TIME_PICKER_ID);

			}
		});

		endTimeText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// On button click show datepicker dialog 
				showDialog(END_TIME_PICKER_ID);

			}
		});

	}

	// set start time in Time picker dialog 
	private TimePickerDialog.OnTimeSetListener startTimePickerListener =  new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			// set current time into textview
			startTimeText.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)).append(getTimeFormat()).toString());


		}
	};

	// set end time in Time picker dialog 
	private TimePickerDialog.OnTimeSetListener endTimePickerListener =  new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			// set current time into textview
			endTimeText.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)).append(getTimeFormat()).toString());


		}
	};

	// get the round minute
	public static int getRoundedMinute(int minute){
		if(minute % TIME_PICKER_INTERVAL != 0){
			int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
			minute = minuteFloor + (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
			if (minute == 60)  minute=0;
		}

		return minute;
	}

	// display time in string format
	private static String padding_str(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	// get am or pm string format
	private String getTimeFormat() {

		String timeSet = "";
		if (hour > 12) {
			hour -= 12;
			timeSet = "PM";
		} else if (hour == 0) {
			hour += 12;
			timeSet = "AM";
		} else if (hour == 12) {
			timeSet = "PM";
		} else {
			timeSet = "AM";
		}

		return timeSet;

	}

	// move to the AddEntryActivity
	private void addEntryEvent() {

		addEntrybutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(TimeEntryActivity.this,AddEntryActivity.class);
				startActivityForResult(intent,3);

			}
		});

	}

	// inflate for action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	// put the other menu on the three dots (overflow)
	private void getOverflowMenu() {

		try {

			ViewConfiguration config = ViewConfiguration.get(this);
			java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}