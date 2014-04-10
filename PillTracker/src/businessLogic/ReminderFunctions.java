package businessLogic;

import java.util.concurrent.TimeUnit;

public interface ReminderFunctions extends DatabaseFunctions{
	
	// returns the time of the next reminder
	// after the next reminder happens, 
	// call this function again and set up internal
	// timer for the next reminder
	public TimeUnit getNextReminder();
	
}
