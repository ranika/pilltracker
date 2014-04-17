package database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class docDatabase extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "doctors";
	private static final String TABLE = "doc_details";
	private static final String[] COLUMNS = {"id", "name", "address", "phone"};
	
	public docDatabase(Context context, String name, CursorFactory factory,
			int version) {
		// nothing special needs to be done
		// just use default super constructor
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("docDatabase", "onCreate");
		// create reminders database
		StringBuilder CREATE_TABLE = new StringBuilder();
		CREATE_TABLE.append("CREATE TABLE " + TABLE);
		CREATE_TABLE.append(" ( id INTEGER PRIMARY KEY, ");
		// name
		CREATE_TABLE.append(COLUMNS[1] + " text, ");
		// address
		CREATE_TABLE.append(COLUMNS[2] + " text, ");
		// phone
		CREATE_TABLE.append(COLUMNS[3] + " text");
		CREATE_TABLE.append(" )");
		db.execSQL(CREATE_TABLE.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// delete and re-create both tables
		String UPGRADE = "DROP TABLE IF EXISTS " + TABLE;
		db.execSQL(UPGRADE);
		this.onCreate(db);
	}
	
	// CRUD operations for doctors follow
	
	public void createDoctor(int id, String name, String address, String phone) {
		Log.d("docDatabase", "createDoctor");
		// enter into database
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues vals = new ContentValues();
		vals.put(COLUMNS[0], id);
		vals.put(COLUMNS[1], name);
		vals.put(COLUMNS[2], address);
		vals.put(COLUMNS[3], phone);
		long error = db.insert(TABLE, null, vals);
		db.close();
	}
	
	// returns in the following format
	// [name, address, phone]
	public ArrayList<String> readDoctor(int id) {
		Log.d("docDatebase", "readDoctor");
		SQLiteDatabase db = this.getReadableDatabase();
		String[] idStr = {String.valueOf(id)};
		Cursor query = db.query(TABLE, COLUMNS, "id = ?", idStr, null, null, null, null);
		ArrayList<String> results = new ArrayList<String>();
		results.add(query.getString(1));
		results.add(query.getString(2));
		results.add(query.getString(3));
		return results;
	}
	
	public void updateDoctor(int id, String name, String address, String phone) {
		Log.d("docDatabase", "updateDoctor");
	    SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues vals = new ContentValues();
	    vals.put(COLUMNS[1], name);
		vals.put(COLUMNS[2], address);
		vals.put(COLUMNS[3], phone);
	    int error = db.update(TABLE, vals, 
	            "id = ?", new String[] { String.valueOf(id) });
	    db.close();
	}
	
	public void deleteDoctor(int id) {
		Log.d("docDatabase", "deleteDoctor");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, "id = ?", new String[] {String.valueOf(id)}); 
        db.close();
	}

}
