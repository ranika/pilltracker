package businessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import com.example.pilltracker.R;

import database.medDatabase;
import entities.Medication;
import entities.MedicationImpl;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.CheckBox;
import android.widget.TimePicker;

public class mDb implements MedicationFunctions {
	
	private static HashMap<String, Integer> idMap = new HashMap<String, Integer>();
	private static int idGenerator = 0;
	
	@Override
	public void userInputToDatabase(View v, Context c) {
		int id = mDb.idGenerator++;
		// find text information
		String name = ((EditText)v.findViewById(R.id.fepa_et1)).getText().toString();
		String prescriber = ((EditText)v.findViewById(R.id.fepa_et2)).getText().toString();
		String comments = ((EditText)v.findViewById(R.id.fepa_et3)).getText().toString();
		boolean on = ((Switch)v.findViewById(R.id.fepa_s1)).isChecked();
		//ArrayList<Integer> days = new ArrayList<Integer>();
		StringBuffer days = new StringBuffer(); 
		// check days
		if (((CheckBox)v.findViewById(R.id.fepa_cb0)).isChecked())
			days.append(0);
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
		TimePicker t = ((TimePicker)v.findViewById(R.id.fepa_tp1)); 
		int time_h = t.getCurrentHour();
		int time_m = t.getCurrentMinute();
		idMap.put(name, id);
		// add to database
		medDatabase mDb = new medDatabase(c, null, null, 0);
		mDb.createDetails(id, name, prescriber, comments);
		mDb.createReminder(id, time_h, time_m, Integer.parseInt(days.toString()), on);
	}

	@Override
	public ArrayList<Medication> readFromDatabase(Context c) {
		ArrayList<Medication> results = new ArrayList<Medication>();
		medDatabase mDb = new medDatabase(c, null, null, 0);
		Integer[] allIds = idMap.values().toArray(new Integer[idMap.values().size()]);
		for (Integer i : allIds) {
			// [hour0, min0, day0, on0, hour1, min1, day1, on1, ...]
			ArrayList<Integer> resultList = mDb.readReminder(i);
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
			int on = (numReminders > 0) ? resultList.get(3) : 0;
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
			// [hour0, min0, day0, on0, hour1, min1, day1, on1, ...]
			ArrayList<Integer> resultList = mDb.readReminder(i);
			for (int j = 0; j < resultList.size(); j += 4) {
				if (resultList.get(j+2) == day)
					sb = new StringBuffer();
					sb.append(resultList.get(j));
					sb.append(":");
					sb.append(resultList.get(j+1));
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
	public int[] getTimes(Context c, int id) {
		medDatabase mDb = new medDatabase(c, null, null, 0);
		ArrayList<Integer> resultList = mDb.readReminder(id);
		int[] result = new int[resultList.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = resultList.get(i);
		}
		return result;
	}

	public void remove(Context c, int id) {
		medDatabase mDb = new medDatabase(c, null, null, 0);
		mDb.deleteDetails(id);
	}


}
