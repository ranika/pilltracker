package reminders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import businessLogic.mDb;

import com.example.pilltracker.R;

import entities.AlarmService;
import entities.Medication;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.support.v4.app.Fragment;

public class RemindersActivity extends Activity {
	
	private final Context thisC = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_reminders_remindersactivity);
	}
	
	protected void onResume() {
		super.onResume();
		// add listView
		mDb medDatabase = new mDb();
		ArrayList<Medication> list = medDatabase.readFromDatabase(this);
		RemArrayAdapter<Medication> raa = new RemArrayAdapter<Medication>(this, R.layout.remitem, list);
		ListView rem_lv = (ListView) findViewById(R.id.rem_lv);
		rem_lv.setAdapter(raa);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reminders, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.f_reminders_remindersactivity,
					container, false);
			return rootView;
		}
	}
	
	// private class for managing listView
	private class RemArrayAdapter<MedicationImpl> extends ArrayAdapter<MedicationImpl> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
		
		public RemArrayAdapter(Context context, int resource,
				List<MedicationImpl> objects) {
			super(context, resource, objects);
			Medication m = null;
			// add medication names to list view adapter
			for (int i = 0; i < objects.size(); i++) {
				m = (Medication) objects.get(i);
				mIdMap.put(m.getName(), i);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) thisC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.remitem, parent, false);
			CheckBox mainCb = (CheckBox) rowView.findViewById(R.id.cb);
			String completeStr = ((Medication) getItem(position)).getName();
			mDb medDatabase = new mDb();
			int id = medDatabase.medNameToId(completeStr);
			ArrayList<String> times = medDatabase.getTimes(thisC, id);
			// hour, min, days, on
			boolean isChecked = (Integer.parseInt(times.get(3)) != 0);
			mainCb.setText(completeStr);
			mainCb.setChecked(isChecked);
			if (!isChecked) {
				mainCb.setPaintFlags(mainCb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			}
			mainCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				int hour;
				int min;
				int id;
				boolean checked;
				ArrayList<String> times;
				
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
					String completeStr = buttonView.getText().toString();
					Log.d("rem checkbox changed", completeStr + " " + isChecked);
					mDb medDatabase = new mDb();
					id = medDatabase.medNameToId(completeStr);
					times = medDatabase.getTimes(thisC, id);
					hour = Integer.parseInt(times.get(0));
					min = Integer.parseInt(times.get(1));
					checked = isChecked;
					// off > on
					if (isChecked) {
						buttonView.setPaintFlags(buttonView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
						// pop up new time window
						Dialog d= new TimePickerDialog(thisC, timeSetListener, hour, min, true);
						d.show();
					}
					// on > off
					else {
						Log.d("rem alarm off", "cancelling an alarm");
						AlarmService as = new AlarmService(); 
						as.cancel(thisC, id); 
						buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					}
				}
				
				private TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						Log.d("rem alarm on", "turning alarm on");
						AlarmService as = new AlarmService(); 
						hour = hourOfDay;
						min = minute;
						// store in database
						Log.d("remListenerStoring", times.get(2) + " " + checked);
						mDb medDatabase = new mDb();
						medDatabase.setTimes(thisC, id, hour, min, times.get(2), checked);
						as.createAlarms(id, times.get(2), hour, min, thisC); 
					}

				};
			});
			Log.d("reminderText", completeStr + " " + isChecked);
		    return rowView;
		}
		
		
		
		@Override
		public long getItemId(int position) {
			String item = ((Medication) getItem(position)).getName();
			return mIdMap.get(item);
		}
			
		// IDs not guaranteed to stay consistent
		@Override
		public boolean hasStableIds() {
			return false;
		}
			
	} // private class ends

}
