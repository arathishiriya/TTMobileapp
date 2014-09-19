package org.cosgix.ttmobileapp.adapter;

import java.util.List;

import org.cosgix.ttmobileapp.R;
import org.cosgix.ttmobileapp.activity.TimeEntry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class provides the view for TimeEntry
 * 
 * @author Sanjib
 *
 */
public class TimeEntryArrayAdapter extends ArrayAdapter<TimeEntry> {

	// variables declaration
	private static final String TAG = "TimeEntryArrayAdapter";
	
	Context context;
	int resourceId;
	List<TimeEntry> data = null;
	LayoutInflater l_Inflater;
	
	/**
	 * Constructor used for initialization
	 * @param context
	 * @param resource
	 * @param data
	 */
	public TimeEntryArrayAdapter(Context context, int resource, List<TimeEntry> data) {
		super(context, resource, data);
		this.context = context;
		resourceId = resource;
		this.data = data;
		Log.d(TAG, "shareConnect------>" + data.size());
		l_Inflater = LayoutInflater.from(context);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		// get the view
		if (convertView == null) {
			convertView = l_Inflater.inflate(resourceId, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// widget initialization
		holder.imageView = (ImageView) convertView
				.findViewById(R.id.shareImages);
		holder.textShare = (TextView) convertView
				.findViewById(R.id.shareText);
		
		// get the time entry data
		TimeEntry timeEntry = data.get(position);
		holder.textShare.setText(timeEntry.getTimeEntryName());
		if (timeEntry.getTimeEntryImage() != 0) {
			holder.imageView.setVisibility(View.VISIBLE);
			holder.imageView.setImageResource(timeEntry.getTimeEntryImage());
		} else {
			holder.imageView.setVisibility(View.GONE);
		}

		return convertView;
	}

	/**
	 * This class provides the widget
	 * 
	 * @author Sanjib
	 *
	 */
	class ViewHolder {
		ImageView imageView;
		TextView textShare;

	}

	@Override
	public int getCount() {
		int size = 0;
		if (data != null) {
			size = data.size();
		}
		return size;
	}

	@Override
	public TimeEntry getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
