package reminders;

import java.util.Calendar;

import businessLogic.MedicationFunctions;
import businessLogic.mDb;

import com.example.pilltracker.R;
import com.example.pilltracker.R.id;
import com.example.pilltracker.R.layout;
import com.example.pilltracker.R.menu;

import database.medDatabase;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class RemindersActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_reminders_remindersactivity);
		
		mDb medFunctions = new mDb();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK); // sun = 1; sat = 7
		if (day == 1)
			day = 7;
		else
			day -= 1;
		String[] todaysMeds = medFunctions.daysMeds(this, day);
		for (String s : todaysMeds) {
			
		}
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

}
