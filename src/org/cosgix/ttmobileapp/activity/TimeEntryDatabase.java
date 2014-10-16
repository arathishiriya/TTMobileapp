package org.cosgix.ttmobileapp.activity;

import java.sql.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * This class provides the TimeEntry database
 * @author Sanjib
 *
 */
public class TimeEntryDatabase extends SQLiteOpenHelper {
	
	/**
     * these are the variable declaration within this class	
     */
	public static final String DATABASE_NAME     = "/sdcard/"+ "timentrydatabase";
	public static int DATABASE_VERSION           = 1;
	public static final String DATABASE_TABLE    = "timeentryrecord";
	public static final String TAG               = "SGMyDatabase.class.getSimpleName()";
	
	// time entry database columns
	public static final String ROWID             = "timeentry_id";
	
	public static final String PROJECTNAME       = "project_name";
	public static final String WORKTYPE          = "worktype";
	public static final String TASK              = "task";
	public static final String DESCRIPTION       = "description";
	public static final String DATE              = "timentry_date";
	public static final String STARTTIME         = "timentry_start_time";
	public static final String ENDTIME           = "timentry_end_time";
	
	public Context  mContext;
	TimeEntryDatabase    mDbHelper;
	SQLiteDatabase  mDb;
	Date date;
	
	// time entry table creation
	String timeentrytable = "create table " + DATABASE_TABLE + "( " + ROWID 
	+ " integer primary key autoincrement, " + PROJECTNAME + " text, "
	+ WORKTYPE + " text, " + TASK + " text, " 
	+ DESCRIPTION + " text, " + DATE + " date, " + STARTTIME + " text, " 
	+ ENDTIME + " text "+ ");";
	
	/**
	 * this method is used for constructor
	 * @param context
	 */
    public TimeEntryDatabase(Context context) {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	this.mContext = context;
    	Log.d(TAG, "Database constructor");

	}

    /**
     * This method creates the table using  sqlite database db
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
 
		Log.d(TAG, "Table is created");
		db.execSQL(timeentrytable);

	}

	/**
	 * This method upgrade the database table
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w(TAG, "Upgrading database from version" + oldVersion + "to" + newVersion + ", which will destroy all old data" );
		db.execSQL("drop table if exists tables"+DATABASE_TABLE);
		onCreate(db);

	}
	
	/**
	 * This method inserting the record in the database
	 * @param projectname
	 * @param worktype
	 * @param task
	 * @param description
	 * @param timeentrydate
	 * @param starttime
	 * @param endtime
	 */
	public void insertRecord(String projectname,String worktype,String task,
			String description,String timeentrydate,String starttime,
			String endtime) {
		
		Log.d(TAG, "insert record is called");
		
		// creating the sqlite database object for writing to the database table
		SQLiteDatabase db     = getWritableDatabase();
		
		//SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = new Date(0);
		String yDate = (String) DateFormat.format(timeentrydate, date);
		
		// content values is required for putting the time entry values
		ContentValues values  = new ContentValues();
		values.put(TimeEntryDatabase.PROJECTNAME,projectname );
		values.put(TimeEntryDatabase.WORKTYPE,worktype );
		values.put(TimeEntryDatabase.TASK, task);
		values.put(TimeEntryDatabase.DESCRIPTION, description);
		values.put(TimeEntryDatabase.DATE, timeentrydate);
		values.put(TimeEntryDatabase.STARTTIME, starttime);
		values.put(TimeEntryDatabase.ENDTIME, endtime);

		// inserting the values to the database table
		db.insert(DATABASE_TABLE, null, values);
		Log.d(TAG, "=====Time Entry Table is inserted=====");
		// close the database
		db.close();
	}

}
