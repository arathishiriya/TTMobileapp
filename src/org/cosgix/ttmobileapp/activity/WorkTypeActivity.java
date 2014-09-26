package org.cosgix.ttmobileapp.activity;

import org.cosgix.ttmobileapp.R;
import org.cosgix.ttmobileapp.data.Projects;
import org.cosgix.ttmobileapp.data.WorkTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * This class provides the Project list to display
 * @author Sanjib
 *
 */
public class WorkTypeActivity extends Activity {

	// variables declaration
	private static final String TAG = "WorkTypeActivity";
	
	private int position;
	String[] WORKTYPES;
	String[] ALPHABETS_LIST = new String[] {"A","B","C","D","E","F","G",
			"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V",
			"W","X","Y","Z"};

	ListView listview;
	private GestureDetector mGestureDetector;

	// x and y coordinates within our side index
	private static float sideIndexX;
	private static float sideIndexY;

	// height of side index
	private int sideIndexHeight;

	// number of items in the side index
	private int indexListSize;

	// list with items for side index
	private ArrayList<Object[]> indexList = null;

	int tmpIndexListSize;
	String tmpLetter = null;
	Object[] tmpIndexItem = null;
	double delta;
	TextView tmpTV;
	LinearLayout sideIndex ;
	List<Projects> projectsList;
	
	List<WorkTypes> workTypeList;

	HashMap<String, String> hashmap = new HashMap<String, String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worktypelist);
		
		showActionBar();
		getOverflowMenu();

		// don't forget to sort our array (in case it's not sorted)
		workTypeList = UpdateActivity.getWorkTypeList();
		WORKTYPES = new String[workTypeList.size()];
		int i = 0;
		for(WorkTypes workTypes : workTypeList) {
			WORKTYPES[i++] = workTypes.getWorktypeName();
			//tempProjectid = projects.getProjectId();
		}
		
		Arrays.sort(WORKTYPES,String.CASE_INSENSITIVE_ORDER);
		
		listview = (ListView) findViewById(R.id.ListView01);

		listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, WORKTYPES));
		mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());

		listItemClickEvent();

	}
	
	/**
	 * method used to display the action bar
	 */
	private void showActionBar() {

		ActionBar actionBar = getActionBar();
		// Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getResources().getString(R.string.selectaworktype)); 
		actionBar.setIcon(R.drawable.ic_time);
		//actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_blue_btn));
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightskyblue)));

	}

	/**
	 * method used to display list item click event
	 */
	private void listItemClickEvent() {

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int pos,
					long id) {

				position = pos;

				Intent intent = new Intent();  
				intent.putExtra("WORKTYPE_MESSAGE",adapterView.getItemAtPosition(pos).toString());
				intent.putExtra("WORKTYPE_SELECTED", position);
				setResult(2,intent);  

				finish();//finishing activity  

			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mGestureDetector.onTouchEvent(event)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method used to create the side index list
	 */
	private ArrayList<Object[]> createIndex(String[] strArr) {

		ArrayList<Object[]> tmpIndexList = new ArrayList<Object[]>();
		Object[] tmpIndexItem = null;

		int tmpPos = 0;
		String tmpLetter = "";
		String currentLetter = null;
		String strItem = null;

		for (int j = 0; j < strArr.length; j++)	{

			strItem = strArr[j];
			currentLetter = strItem.substring(0, 1);

			// every time new letters comes
			// save it to index list
			if (!currentLetter.equals(tmpLetter)) {

				tmpIndexItem = new Object[3];
				tmpIndexItem[0] = tmpLetter;
				tmpIndexItem[1] = tmpPos - 1;
				tmpIndexItem[2] = j - 1;

				tmpLetter = currentLetter;
				tmpPos = j + 1;

				tmpIndexList.add(tmpIndexItem);
			}
		}

		// save also last letter
		tmpIndexItem = new Object[3];
		tmpIndexItem[0] = tmpLetter;
		tmpIndexItem[1] = tmpPos - 1;
		tmpIndexItem[2] = strArr.length - 1;
		tmpIndexList.add(tmpIndexItem);

		// and remove first temporary empty entry
		if (tmpIndexList != null && tmpIndexList.size() > 0) {
			tmpIndexList.remove(0);
		}

		return tmpIndexList;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);

		sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
		sideIndexHeight = sideIndex.getHeight();
		sideIndex.removeAllViews();

		// TextView for every visible item
		tmpTV = null;

		// we'll create the index list
		//indexList = createIndex(WORKTYPES);
		indexList = createIndex(ALPHABETS_LIST);

		// number of items in the index List
		indexListSize = indexList.size();

		// maximal number of item, which could be displayed
		int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);

		tmpIndexListSize = indexListSize;

		// handling that case when indexListSize > indexMaxSize
		while (tmpIndexListSize > indexMaxSize) {

			tmpIndexListSize = tmpIndexListSize / 2;
		}

		// computing delta (only a part of items will be displayed to save a
		// place)
		delta = indexListSize / tmpIndexListSize;

		// show every m-th letter
		for (double i = 1; i <= indexListSize; i = i + delta) {

			tmpIndexItem = indexList.get((int) i - 1);
			tmpLetter = tmpIndexItem[0].toString();

			Log.d(TAG, "tmpLetter" + tmpLetter);

			hashmap.put("key", tmpLetter);

			tmpTV = new TextView(this);
			tmpTV.setText(tmpLetter);
			tmpTV.setGravity(Gravity.CENTER);
			tmpTV.setTextSize(17);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
			tmpTV.setLayoutParams(params);
			//			sideIndex.addView(tmpTV);
			displaySideIndex(true);

		}

		// and set a touch listener for it
		sideIndex.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				try {
					// now you know coordinates of touch
					sideIndexX = event.getX();
					sideIndexY = event.getY();

					Log.d(TAG, "sideIndexX=1" + sideIndexX);
					Log.d(TAG, "sideIndexY=1" + sideIndexY);

					// and can display a proper item it project list
					displayListItem();

				} catch(Exception e) {
					e.printStackTrace();
				}

				return false;
			}
		});

	}

	/**
	 * method used to display the side index
	 */
	public void displaySideIndex(boolean flag) {

		if(flag == true) {			
			sideIndex.setVisibility(View.VISIBLE);
			sideIndex.addView(tmpTV);
		}
		else if(flag == false) {
			sideIndex.setVisibility(View.GONE);
			sideIndex.removeAllViews();
		}

	}

	/**
	 * This class provides the side index scroll event
	 * 
	 * @author Sanjib
	 *
	 */
	class SideIndexGestureListener extends
	GestureDetector.SimpleOnGestureListener	{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			try {
				// we know already coordinates of first touch
				// we know as well a scroll distance
				sideIndexX = sideIndexX - distanceX;
				sideIndexY = sideIndexY - distanceY;
				
				Log.d(TAG, "sideIndexX=2" + sideIndexX);
				Log.d(TAG, "sideIndexY=2" + sideIndexY);

				// when the user scrolls within our side index
				// we can show for every position in it a proper
				// item in the country list
				if (sideIndexX >= 0 && sideIndexY >= 0) {

					displayListItem();
				}

			} catch(Exception e) {
				e.printStackTrace();
			}

			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}

	/**
	 * method used to display the list item
	 */
	public void displayListItem() {

		try {
			// compute number of pixels for every side index item
			double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

			// compute the item index for given event position belongs to
			int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

			// compute minimal position for the item in the list
			int minPosition = (int) (itemPosition * pixelPerIndexItem);

			// get the item (we can do it since we know item index)
			Object[] indexItem = indexList.get(itemPosition);
			
			Log.d(TAG, "pixelPerIndexItem" + pixelPerIndexItem);
			Log.d(TAG, "itemPosition" + itemPosition);
			Log.d(TAG, "minPosition" + minPosition);
			Log.d(TAG, "indexItem" + indexItem);

			// and compute the proper item in the country list
			int indexMin = Integer.parseInt(indexItem[1].toString());
			int indexMax = Integer.parseInt(indexItem[2].toString());
			int indexDelta = Math.max(1, indexMax - indexMin);

			Log.d(TAG, "indexMin & indexMax & indexDelta" + indexMin + "\n" 
					+ "\n" + indexMax + "\n" + indexDelta);

			double pixelPerSubitem = pixelPerIndexItem / indexDelta;
			int subitemPosition = (int) (indexMin + (sideIndexY - minPosition) / pixelPerSubitem);

			Log.d(TAG, "pixelPerSubitem & subitemPosition" + pixelPerSubitem + "\n" + subitemPosition);
			
			ListView listView = (ListView) findViewById(R.id.ListView01);
			listView.setSelection(subitemPosition);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// inflate for action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// put the other menu
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
