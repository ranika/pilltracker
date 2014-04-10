package businessLogic;

import java.util.ArrayList;

public interface DatabaseFunctions {
	
	// use this to update database
	// based on newest user input
	public void userInputToDatabase();
	
	// use this to get information
	// from database that will be
	// displayed on screen
	public ArrayList<?> readFromDatabase();

}
