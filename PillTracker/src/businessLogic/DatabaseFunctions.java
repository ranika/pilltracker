package businessLogic;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;

public interface DatabaseFunctions {
	
	// use this to update database
	// based on newest user input
	public void userInputToDatabase(View v, Context c);
	
	// use this to get information
	// from database that will be
	// displayed on screen
	public ArrayList<?> readFromDatabase(Context c);

}
