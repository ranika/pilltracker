package doctors;

import prescriptions.PrescriptionActivity;
import businessLogic.dDb;
import businessLogic.mDb;

import com.example.pilltracker.R;
import com.example.pilltracker.R.id;
import com.example.pilltracker.R.layout;
import com.example.pilltracker.R.menu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DoctorEditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_doctors_doctoreditactivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_edit, menu);
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
	
	public void goBack(View view)
	{
		dDb d = new dDb();
		RelativeLayout r = (RelativeLayout) findViewById(R.id.fdea_layout); 
		d.userInputToDatabase(r, this);
		Intent intent = new Intent(this, DoctorsActivity.class); 
		startActivity(intent);
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
			View rootView = inflater.inflate(R.layout.f_doctors_doctoreditactivity,
					container, false);
			return rootView;
		}
	}

}
