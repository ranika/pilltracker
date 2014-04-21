package reminders;

import java.util.Calendar;

import businessLogic.mDb;

import com.example.pilltracker.R;
import com.example.pilltracker.R.id;
import com.example.pilltracker.R.layout;
import com.example.pilltracker.R.menu;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MedLogActivity extends Activity implements OnCheckedChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_reminders_medlogactivity);
		
		// get layout
		RelativeLayout l = (RelativeLayout) findViewById(R.id.mla_l);
		// add reminders for today
		mDb medFunctions = new mDb();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK); // sun = 1; sat = 7
		if (day == 1)
			day = 7;
		else
			day -= 1;
		String[] todaysMeds = medFunctions.daysMeds(this, day);
		Log.d("MedLogOnCreate", day + " " + todaysMeds.length);
		// generate checkboxes
		for (String s : todaysMeds) {
			CheckBox cb = new CheckBox(this);
			cb.setText(s);
			l.addView(cb);
		}

	}
	
	public void onResume() {
		super.onResume();
		// get layout
		LinearLayout l = (LinearLayout) findViewById(R.id.mla_l);
		// add reminders for today
		mDb medFunctions = new mDb();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK); // sun = 1; sat = 7
		if (day == 1)
			day = 6;
		else
			day -= 2;
		String[] todaysMeds = medFunctions.daysMeds(this, day);
		Log.d("MedLogOnCreate", day + " " + todaysMeds.length);
		// generate checkboxes
		for (String s : todaysMeds) {
			CheckBox cb = new CheckBox(this);
			cb.setText(s);
			cb.setOnCheckedChangeListener(this);
			l.addView(cb);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.med_log, menu);
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
			View rootView = inflater.inflate(R.layout.f_reminders_medlogactivity,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked)
			buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		else
			buttonView.setPaintFlags(buttonView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
	}

}
