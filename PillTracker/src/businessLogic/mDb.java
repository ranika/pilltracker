package businessLogic;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;


import com.example.pilltracker.R;
import database.medDatabase;
import entities.AlarmService;
import entities.Medication;
import entities.MedicationImpl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TimePicker;

public class mDb implements MedicationFunctions {
	
	private static HashMap<String, Integer> idMap = new HashMap<String, Integer>();
	private static int idGenerator = 0;
	
	@Override
	public boolean userInputToDatabase(View v, Context c) {
		int id = mDb.idGenerator++;
		// find text information
		String name = ((EditText)v.findViewById(R.id.fepa_et1)).getText().toString();
		String prescriber = ((EditText)v.findViewById(R.id.fepa_et2)).getText().toString();
		String comments = ((EditText)v.findViewById(R.id.fepa_et3)).getText().toString();
		boolean on = ((CheckBox)v.findViewById(R.id.fepa_s1)).isChecked();
		//ArrayList<Integer> days = new ArrayList<Integer>();
		StringBuffer days = new StringBuffer(); 
		// check days
		if (((CheckBox)v.findViewById(R.id.fepa_cb1)).isChecked())
			days.append(1);
		if (((CheckBox)v.findViewById(R.id.fepa_cb2)).isChecked())
			days.append(2);
		if (((CheckBox)v.findViewById(R.id.fepa_cb3)).isChecked())
			days.append(3);
		if (((CheckBox)v.findViewById(R.id.fepa_cb4)).isChecked())
			days.append(4);
		if (((CheckBox)v.findViewById(R.id.fepa_cb5)).isChecked())
			days.append(5);
		if (((CheckBox)v.findViewById(R.id.fepa_cb6)).isChecked())
			days.append(6);
		if (((CheckBox)v.findViewById(R.id.fepa_cb0)).isChecked())
			days.append(0);
		TimePicker t = ((TimePicker)v.findViewById(R.id.fepa_tp1)); 
		int time_h = t.getCurrentHour();
		int time_m = t.getCurrentMinute();
		if (days.length() != 0) {
			idMap.put(name, id);
			// add to database
			medDatabase mDb = new medDatabase(c, null, null, 0);
			mDb.createDetails(id, name, prescriber, comments);
			mDb.createReminder(id, time_h, time_m, days.toString(), on);
			Log.d("mDb userInputToDatabase", days.toString());
			if(on)
			{
				Log.d("mDb", "reminders are on"); 
				AlarmService as = new AlarmService(); 
				as.createAlarms(id, days.toString(), time_h, time_m, c); 
			}
			return true;
		}
		else {
			return false;
		}
	}
	

	@Override
	public ArrayList<Medication> readFromDatabase(Context c) {
		ArrayList<Medication> results = new ArrayList<Medication>();
		medDatabase mDb = new medDatabase(c, null, null, 0);
		Integer[] allIds = idMap.values().toArray(new Integer[idMap.values().size()]);
		for (Integer i : allIds) {
			// [hour0, min0, day0, on0]
			ArrayList<String> resultList = mDb.readReminder(i);
			// [name, prescriber, comments]
			String[] medNames = mDb.readDetails(i);
			String name = medNames[0];
			String prescriber = medNames[1];
			String comments = medNames[2];
			int numReminders = resultList.size() / 4;
			int[] time_h = new int[numReminders];
			int[] time_m = new int[numReminders];
			int[] days = new int[numReminders];
			// only check db if reminders exist
			int on = (numReminders > 0) ? Integer.parseInt(resultList.get(3)) : 0;
			Medication m = new MedicationImpl(name, prescriber, comments, time_h, time_m, days, on, i);
			results.add(m);
		}
		return results;
	}

	@Override
	public int medNameToId(String name) {
		return idMap.get(name);
	}

	@Override
	public String idToMedName(int id) {
		for (Entry<String, Integer> entry : idMap.entrySet()) {
			if (id == entry.getValue()) {
				return entry.getKey();
			}
		}
		// could not find name
		return null;
	}

	@Override
	public String[] daysMeds(Context c, int day) {
		ArrayList<String> result = new ArrayList<String>();
		medDatabase mDb = new medDatabase(c, null, null, 0);
		Integer[] allIds = idMap.values().toArray(new Integer[idMap.values().size()]);
		StringBuffer sb = null;
		for (Integer i : allIds) {
			// [hour0, min0, day0, on0]
			ArrayList<String> resultList = mDb.readReminder(i);
			Log.d("daysMeds", resultList.get(2).toString());
			if (resultList.get(2).contains(String.valueOf(day))) {
				sb = new StringBuffer();
				sb.append(resultList.get(0));
				sb.append(":");
				sb.append(resultList.get(1));
				sb.append(" | ");
				sb.append(this.idToMedName(i));
				result.add(sb.toString());
			}
		}
		Collections.sort(result);
		return result.toArray(new String[result.size()]);
	}

	@Override
	public String[] allMeds(Context c) {
		ArrayList<String> results = new ArrayList<String>();
		medDatabase mDb = new medDatabase(c, null, null, 0);
		Integer[] allIds = idMap.values().toArray(new Integer[idMap.values().size()]);
		for (Integer id : allIds) {
			String[] medNames = mDb.readDetails(id);
			for (String s : medNames) {
				results.add(s);
			}
		}
		return results.toArray(new String[results.size()]);
	}

	@Override
	public ArrayList<String> getTimes(Context c, int id) {
		medDatabase mDb = new medDatabase(c, null, null, 0);
		ArrayList<String> resultList = mDb.readReminder(id);
		int[] result = new int[resultList.size()];
		//trying to debug error 
		for(int j = 0; j < result.length; j++) 
		{
			Log.d("getting times", resultList.get(j)); 
		}
		for (int i = 0; i < result.length; i++) {
			result[i] = Integer.parseInt(resultList.get(i));
		}
		return resultList;
	}
	
	public void setTimes(Context c, int id, int hour, int min, String day, boolean on) {
		medDatabase mDb = new medDatabase(c, null, null, 0);
		mDb.createReminder(id, hour, min, day, on);
	}

	public void remove(Context c, int id) {
		medDatabase mDb = new medDatabase(c, null, null, 0);
		mDb.deleteDetails(id);
	}

	public void clear(Context c) {
		medDatabase mDb = new medDatabase(c, null, null, 0);
		mDb.clear();
	}


}
