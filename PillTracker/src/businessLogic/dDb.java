package businessLogic;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.example.pilltracker.R;

import database.docDatabase;

import entities.Doctor;
import entities.DoctorImpl;

public class dDb implements doctorFunctions{

	@Override
	public boolean userInputToDatabase(View v, Context c) {
		String name = ((EditText)v.findViewById(R.id.fdea_et1)).getText().toString();
		String phone = ((EditText)v.findViewById(R.id.fdea_et2)).getText().toString();
		String email = ((EditText)v.findViewById(R.id.fdea_et3)).getText().toString();
		String address = ((EditText)v.findViewById(R.id.fdea_et4)).getText().toString();
		Doctor d = new DoctorImpl(name, phone, email, address);
		int id = d.getId();
		docDatabase db = new docDatabase(c, null, null, 0);
		db.createDoctor(id, name, phone, email, address);
		return true;
	}

	@Override
	public ArrayList<Doctor> readFromDatabase(Context c) {
		docDatabase db = new docDatabase(c, null, null, 0);
		return db.readAllDoctors();
	}

	public void remove(Context c, String name) {
		docDatabase db = new docDatabase(c, null, null, 0);
		db.removeName(name);
	}

	public void clear(Context c) {
		docDatabase db = new docDatabase(c, null, null, 0);
		db.clear();
	}
	
	

}
