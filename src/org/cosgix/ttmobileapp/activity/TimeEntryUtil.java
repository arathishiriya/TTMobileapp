package org.cosgix.ttmobileapp.activity;

import java.util.ArrayList;
import java.util.List;

import org.cosgix.ttmobileapp.R;

import android.content.Context;

/**
 * This class provides the Time entry list to display
 * @author Sanjib
 *
 */
public class TimeEntryUtil {
	
	public static List<TimeEntry> getTimeEntryListDeatils(Context context) {
		List<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();
		TimeEntry shareData;
		final String[] TIME_ENTRY_OPTIONS = new String[] {
				"Project Name",
				"WorkType",
				"Task" };

		Integer[] imageId = { R.drawable.ic_project,
				R.drawable.ic_worktype,
				R.drawable.ic_task};

		for (int i = 0; i < TIME_ENTRY_OPTIONS.length; i++) {
			shareData = new TimeEntry();
			shareData.setTimeEntryName(TIME_ENTRY_OPTIONS[i]);
			shareData.setTimeEntryImage(imageId[i]);
			timeEntryList.add(shareData);

		}

		return timeEntryList;

	}

}
