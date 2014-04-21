package database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class medDatabase extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 5;
	private static final String DATABASE_NAME = "medications.db";
	private static final String TABLE_R = "reminder";
	private static final String TABLE_D = "details";
	private static final String[] COLUMNS_R = {"id", "time_h", "time_m", "day", "turntUp"};
	private static final String[] COLUMNS_D = {"id", "name", "prescriber", "comments"};
	
	public medDatabase(Context context, String name, CursorFactory factory,
			int version) {
		// nothing special needs to be done
		// just use default super constructor
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("medDatabase", "onCreate");
		// create reminders database
		StringBuilder CREATE_TABLE_R = new StringBuilder();
		CREATE_TABLE_R.append("CREATE TABLE " + TABLE_R);
		CREATE_TABLE_R.append(" ( id INTEGER PRIMARY KEY, ");
		// hours: 0-23
		CREATE_TABLE_R.append(COLUMNS_R[1] + " int, ");
		// minutes: 0-59
		CREATE_TABLE_R.append(COLUMNS_R[2] + " int, ");
		// day: 0-6
		CREATE_TABLE_R.append(COLUMNS_R[3] + " int, ");
		// boolean turntUp: 0-1
		CREATE_TABLE_R.append(COLUMNS_R[4] + " int");
		CREATE_TABLE_R.append(" )");
		db.execSQL(CREATE_TABLE_R.toString());
		// create details database
		StringBuilder CREATE_TABLE_D = new StringBuilder();
		CREATE_TABLE_D.append("CREATE TABLE " + TABLE_D);
		CREATE_TABLE_D.append(" ( id INTEGER PRIMARY KEY, ");
		// name
		CREATE_TABLE_D.append(COLUMNS_D[1] + " text, ");
		// prescriber
		CREATE_TABLE_D.append(COLUMNS_D[2] + " text, ");
		// comments
		CREATE_TABLE_D.append(COLUMNS_D[3] + " text");
		CREATE_TABLE_D.append(" )");
		db.execSQL(CREATE_TABLE_D.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// delete and re-create both tables
		String UPGRADE = "DROP TABLE IF EXISTS " + TABLE_R;
		db.execSQL(UPGRADE);
		UPGRADE = "DROP TABLE IF EXISTS " + TABLE_D;
		db.execSQL(UPGRADE);
		this.onCreate(db);
	}
	
	// CRUD operations for reminders database follow
	
	// helper function used to check only valid values are input
	private boolean errorCheck(int hour, int min, int day) {
		if ( (hour < 0) || (hour > 23) ) {
			System.out.println("medDatabase createReminder error : invalid input");
			return false;
		}
		if ( (min < 0) || (min > 59) ) {
			System.out.println("medDatabase createReminder error : invalid input");
			return false;
		}
		if ( (day < 0) || (day > 6) ) {
			System.out.println("medDatabase createReminder error : invalid input");
			return false;
		}
		return true;
	}
	
	public void createReminder(int med_id, int hour, int min, int day, boolean on) {
		Log.d("medDatabase", "createReminder");
		// error checking
		if (errorCheck(hour, min, day) == false)
			return;
		// enter into database
		SQLiteDatabase db = this.getWritableDatabase();
		// create query
		int onint = (on) ? 1 : 0;
		ContentValues vals = new ContentValues();
		vals.put(COLUMNS_R[0], med_id);
		vals.put(COLUMNS_R[1], hour);
		vals.put(COLUMNS_R[2], min);
		vals.put(COLUMNS_R[3], day);
		vals.put(COLUMNS_R[4], onint);
		long error = db.insertWithOnConflict(TABLE_R, null, vals, SQLiteDatabase.CONFLICT_REPLACE);
		db.close();
	}
	
	// returns in the following format
	// [hour0, min0, dayAll, on0]]
	// read in increments of 4
	public ArrayList<Integer> readReminder(int med_id) {
		Log.d("medDatebase", "readReminder");
		SQLiteDatabase db = this.getReadableDatabase();
		String[] idStr = {String.valueOf(med_id)};
		Cursor query = db.query(TABLE_R, COLUMNS_R, "id = ?", idStr, null, null, null, null);
		ArrayList<Integer> results = new ArrayList<Integer>();
		if (query.moveToFirst()) {
			results.add(Integer.parseInt(query.getString(1)));
			results.add(Integer.parseInt(query.getString(2)));
			results.add(Integer.parseInt(query.getString(3)));
			results.add(Integer.parseInt(query.getString(4)));
		}
		db.close(); 
		return results;
	}
	
	public void updateReminder() {
		// this will never be used, as when we update reminders, 
		// we will delete all previous reminders
		// and then add all new ones
	}
	
	// note: this deletes all reminders for the given med_id
	public void deleteReminder(int med_id) {
		Log.d("medDatabase", "deleteReminder");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_R, "id = ?", new String[] {String.valueOf(med_id)}); 
        db.close();
	}
	
	// CRUD operations for details database follow
	
	public void createDetails(int med_id, String name, String prescriber, String comments) {
		Log.d("medDatabase", "createDetail");
		// enter into database
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues vals = new ContentValues();
		vals.put(COLUMNS_D[0], med_id);
		vals.put(COLUMNS_D[1], name);
		vals.put(COLUMNS_D[2], prescriber);
		vals.put(COLUMNS_D[3], comments);
		long error = db.insertWithOnConflict(TABLE_D,null,vals,SQLiteDatabase.CONFLICT_REPLACE);
		db.close();
	}
	
	// returns in the following format
	// [name, prescriber, comments]
	public String[] readDetails(int med_id) {
		Log.d("medDatabase", "readDetails");
		SQLiteDatabase db = this.getReadableDatabase();
		String[] idStr = {String.valueOf(med_id)};
		Cursor query = db.query(TABLE_D, COLUMNS_D, "id = ?", idStr, null, null, null, null);
		String[] results = new String[3];
		if(query.moveToFirst())
		{
			results[0] = query.getString(1);
			results[1] = query.getString(2);
			results[2] = query.getString(3);
		}
		else
			Log.d("medDatabase", "were fucked"); 
		db.close(); 
		return results;
	}
	
	public void updateDetails(int med_id, String name, String prescriber, String comments) {
		Log.d("medDatabase", "updateDetails");
	    SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues vals = new ContentValues();
	    vals.put(COLUMNS_D[1], name);
		vals.put(COLUMNS_D[2], prescriber);
		vals.put(COLUMNS_D[3], comments);
	    int error = db.update(TABLE_D, vals, 
	            "id = ?", new String[] { String.valueOf(med_id) });
	    db.close();
	}
	
	public void deleteDetails(int med_id) {
		Log.d("medDatabase", "deleteDetails");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_D, "id = ?", new String[] {String.valueOf(med_id)}); 
        db.close();
	}

}
