package entities;

import java.util.ArrayList;

import android.content.Context;
import database.medDatabase;

public class MedicationImpl implements Medication {

	private String name;
	private String prescriber;
	private String comments;
	private int[] time_h;
	private int[] time_m;
	private int[] day;
	private int on;
	private int id;
	
	public MedicationImpl(String name, String prescriber, String comments,
			int[] time_h, int[] time_m, int[] day, int on, int id) {
		super();
		this.name = name;
		this.prescriber = prescriber;
		this.comments = comments;
		this.time_h = time_h;
		this.time_m = time_m;
		this.day = day;
		this.on = on;
		this.id = id;
	}
	
	public String toString() {
		StringBuilder m = new StringBuilder();
		m.append(name);
		m.append("|");
		m.append(" PRESCRIBER: ");
		m.append(prescriber);
		m.append(" COMMENTS: ");
		m.append(comments);
		return m.toString();
	}
	
	// generic getters/setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrescriber() {
		return prescriber;
	}
	public void setPrescriber(String prescriber) {
		this.prescriber = prescriber;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public boolean getOn() {
		if (on == 0)
			return false;
		else
			return true;
	}
	public void setOn(boolean on) {
		if (on)
			this.on = 1;
		else
			this.on = 0;
	}

}
