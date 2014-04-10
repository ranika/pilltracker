package businessLogic;

public interface MedicationFunctions extends DatabaseFunctions{
	
	// conversion functions between
	// user displayed information
	// and database key
	
	public int medNameToId(String name);
	
	public String idToMedName(int id);
	
	// returns list of medications to be taken on given day
	public String[] daysMeds(int day);
	
	// returns list of all possible medications user can take
	public String[] allMeds();
}