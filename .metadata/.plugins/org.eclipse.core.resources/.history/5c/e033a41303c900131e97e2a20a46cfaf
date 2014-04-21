package doctors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import businessLogic.dDb;

import com.example.pilltracker.R;
import com.example.pilltracker.R.id;
import com.example.pilltracker.R.layout;
import com.example.pilltracker.R.menu;

import entities.Doctor;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DoctorsActivity extends Activity {
	
	private final Context thisC = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_doctors_doctorsactivity);
		
		// add listView
		final dDb doctorDatabase = new dDb();
		ArrayList<Doctor> list = doctorDatabase.readFromDatabase(this);
		final DoctorArrayAdapter<Doctor> daa = new DoctorArrayAdapter<Doctor>(this, R.layout.listitem, list);
		final ListView fda_lv = (ListView) findViewById(R.id.fda_lv);
		fda_lv.setAdapter(daa);
		// long click to delete doctor information
		fda_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String name = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
					public void run() {
						doctorDatabase.remove(thisC, name);
						daa.notifyDataSetChanged();
						view.setAlpha(1);
					}
				});
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctors, menu);
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
			View rootView = inflater.inflate(R.layout.f_doctors_doctorsactivity,
					container, false);
			return rootView;
		}
	}
	
	public void editDoctor(View view)
	{
		Intent intent = new Intent(this, DoctorEditActivity.class);
		startActivity(intent); 
	}
	
	// private class for managing listView
	private class DoctorArrayAdapter<DoctorImpl> extends ArrayAdapter<DoctorImpl> {

		HashMap<String, Integer> dIdMap = new HashMap<String, Integer>();
		
		public DoctorArrayAdapter(Context context, int resource,
				List<DoctorImpl> objects) {
			super(context, resource, objects);
			Doctor d = null;
			// add doctors' names to list view adapter
			for (int i = 0; i < objects.size(); i++) {
				d = (Doctor) objects.get(i);
				dIdMap.put(d.toString(), i);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) thisC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.listitem, parent, false);
		    TextView mainText = (TextView) rowView.findViewById(R.id.listitemheader);
		    TextView subText = (TextView) rowView.findViewById(R.id.listitemsub);
		    String completeStr = ((Doctor) getItem(position)).toString();
		    String[] splitStr = completeStr.split("|");
		    String mainTextContent = splitStr[0];
		    String subTextContent = splitStr[1];
		    mainText.setText(mainTextContent);
		    subText.setText(subTextContent);
		    return rowView;
		  }
		
		@Override
		public long getItemId(int position) {
			String item = ((Doctor) getItem(position)).toString();
			return dIdMap.get(item);
		}
		
		// IDs not guaranteed to stay consistent
		@Override
		public boolean hasStableIds() {
			return false;
		}
		
	}

}


