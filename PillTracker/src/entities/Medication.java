package entities;

public interface Medication {
	
	// fields: name, id, prescriber, comments, times

	// used to get fields from user input on screen
	
	public String getName();
	public int getId();
	public String getPrescriber();
	public String getComments();
	// gets all reminders
	// returns in the following format
	// [hour0, min0, day0, on0, hour1, min1, day1, on1, ...]
	public int[] getTimes();
	
}
