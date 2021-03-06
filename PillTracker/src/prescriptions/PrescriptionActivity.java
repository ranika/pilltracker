package prescriptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import businessLogic.mDb;

import com.example.pilltracker.R;

import entities.Medication;
import entities.MedicationImpl;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PrescriptionActivity extends Activity {
	
	private final Context thisC = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_prescriptions_prescriptionactivity);
		
		// add listView
		final mDb medDatabase = new mDb();
		ArrayList<Medication> list = medDatabase.readFromDatabase(this);
		final MedArrayAdapter<Medication> maa = new MedArrayAdapter<Medication>(this, R.layout.listitem, list);
		final ListView fpa_lv = (ListView) findViewById(R.id.fpa_lv);
		fpa_lv.setAdapter(maa);
		// long click to delete doctor information
		/* TODO Implement
		fpa_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String name = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
					public void run() {
						int id = medDatabase.medNameToId(name);
						medDatabase.remove(thisC, id);
						maa.notifyDataSetChanged();
						view.setAlpha(1);
					}
				});
				return true;
			}
		});	*/	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prescription, menu);
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
			View rootView = inflater.inflate(R.layout.f_prescriptions_prescriptionactivity,
					container, false);
			return rootView;
		}
	}
	
	public void editPrescription(View view)
	{
		Intent intent = new Intent(this, EditPrescriptionActivity.class); 
		startActivity(intent);
	}
	
		// private class for managing listView
		private class MedArrayAdapter<MedicationImpl> extends ArrayAdapter<MedicationImpl> {

			HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
			
			public MedArrayAdapter(Context context, int resource,
					List<MedicationImpl> objects) {
				super(context, resource, objects);
				Medication m = null;
				// add doctors' names to list view adapter
				for (int i = 0; i < objects.size(); i++) {
					m = (Medication) objects.get(i);
					mIdMap.put(m.toString(), i);
				}
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) thisC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.listitem, parent, false);
			TextView mainText = (TextView) rowView.findViewById(R.id.listitemheader);
			TextView subText = (TextView) rowView.findViewById(R.id.listitemsub);
			String completeStr = ((Medication) getItem(position)).toString();
			String[] splitStr = completeStr.split("\\|");
			String mainTextContent = splitStr[0];
			String subTextContent = splitStr[1];
			mainText.setText(mainTextContent);
			subText.setText(subTextContent);
			Log.d("prescriptionOriginal", completeStr);
			Log.d("prescriptionSplit", splitStr.length + splitStr[0] + splitStr[1]);
		    Log.d("prescriptionAdapter", mainTextContent + "|" + subTextContent);
		    return rowView;
		}
		
		@Override
		public long getItemId(int position) {
			String item = ((Medication) getItem(position)).toString();
			return mIdMap.get(item);
		}
		
		// IDs not guaranteed to stay consistent
		@Override
		public boolean hasStableIds() {
			return false;
		}
		
	}
	
	public void startEdit(View view)
	{
		Intent intent = new Intent(this, EditPrescriptionActivity.class); 
		startActivity(intent); 
	}

}
