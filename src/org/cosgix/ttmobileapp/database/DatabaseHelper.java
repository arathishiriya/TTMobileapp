package org.cosgix.ttmobileapp.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private Context context;
	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "/sdcard/"+ "timeentryormliteandroid.db";
	// any time you make changes to your database, you may have to increase the
	// database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the any table
	private Dao<TimeEntryTable, String> simpleDao = null;
	private RuntimeExceptionDao<TimeEntryTable, String> simpleRuntimeDao = null;
	
	private Dao<ProjectTable, String> projectTableDao = null;
	private Dao<WorkTypeTable, String> workTypeTableDao = null;
	private Dao<TaskTable, String> taskTableDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, TimeEntryTable.class);
			TableUtils.createTable(connectionSource, ProjectTable.class);
			TableUtils.createTable(connectionSource, WorkTypeTable.class);
			TableUtils.createTable(connectionSource, TaskTable.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {

		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, TimeEntryTable.class, true);
			TableUtils.dropTable(connectionSource, ProjectTable.class, true);
			TableUtils.dropTable(connectionSource, WorkTypeTable.class, true);
			TableUtils.dropTable(connectionSource, TaskTable.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * Returns the Database Access Object (DAO) for our Time Entry Table Data class. It
	 * will create it or just give the cached value.
	 */
	public Dao<TimeEntryTable, String> getTimeEntryTableDao() throws SQLException {
		if (simpleDao == null) {
			simpleDao = getDao(TimeEntryTable.class);
		}
		return simpleDao;
	}

	public RuntimeExceptionDao<TimeEntryTable, String> getSimpleDataDao() {
		if (simpleRuntimeDao == null) {
			simpleRuntimeDao = getRuntimeExceptionDao(TimeEntryTable.class);
		}
		return simpleRuntimeDao;
	}
	
	/**
	 * Returns the Database Access Object (DAO) for our Project Table Data class. It
	 * will create it or just give the cached value.
	 */
	public Dao<ProjectTable, String> getProjectTableDao() throws SQLException {
		if (projectTableDao == null) {
			projectTableDao = getDao(ProjectTable.class);
		}
		return projectTableDao;
	}
	
	/**
	 * Returns the Database Access Object (DAO) for our WorkTypeTable data class. It
	 * will create it or just give the cached value.
	 */
	public Dao<WorkTypeTable, String> getWorkTypeTableDao() throws SQLException {
		if (workTypeTableDao == null) {
			workTypeTableDao = getDao(WorkTypeTable.class);
		}
		return workTypeTableDao;
	}
	
	/**
	 * Returns the Database Access Object (DAO) for our TaskTable data class. It
	 * will create it or just give the cached value.
	 */
	public Dao<TaskTable, String> getTaskTableDao() throws SQLException {
		if (taskTableDao == null) {
			taskTableDao = getDao(TaskTable.class);
		}
		return taskTableDao;
	}
	
	// method for insert data if no design done
	public int addData(TimeEntryTable timeEntryTable) {
		Log.d("addData","Test 1---");
		int i = 0;
		try {
			RuntimeExceptionDao<TimeEntryTable, String> dao = getSimpleDataDao();
			i = dao.create(timeEntryTable);

		} catch (Exception e) {
			Log.e("addData","ERROR",e);
		}
		return i;
	}
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		simpleRuntimeDao = null;
	}

}
