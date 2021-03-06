package businessLogic;

import java.util.ArrayList;

import android.content.Context;

public interface MedicationFunctions extends DatabaseFunctions{
	
	// conversion functions between
	// user displayed information
	// and database key
	
	public int medNameToId(String name);
	
	public String idToMedName(int id);
	
	// returns list of medications to be taken on given day
	public String[] daysMeds(Context c, int day);
	
	// returns list of all possible medications user can take
	// format: [name precriber comments]
	public String[] allMeds(Context c);
	
	// get times a given medication is to be taken
	// gets all reminders
	// returns in the following format
	// [hour0, min0, day0, on0, hour1, min1, day1, on1, ...]
	public ArrayList<String> getTimes(Context c, int id);
}
