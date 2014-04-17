package entities;

// class that stores information about doctors
public class DoctorImpl implements Doctor {
	
	private int id;
	private static int idCounter = 0;
	private String name;
	private String phone;
	private String email;
	private String address;
	
	public DoctorImpl(String n, String p, String e, String a) {
		this.name = n;
		this.phone = p;
		this.email = e;
		this.address = a;
		this.id = DoctorImpl.getNextId();
	}

	public DoctorImpl(int id2, String n, String p, String e, String a) {
		this.name = n;
		this.phone = p;
		this.email = e;
		this.address = a;
		this.id = id;
	}
	
	public String toString() {
		StringBuilder details = new StringBuilder();
		details.append(this.getName());
		details.append("| EMAIL: ");
		details.append(this.getEmail());
		details.append(" ADDRESS: ");
		details.append(this.getAddress());
		details.append(" PHONE: ");
		details.append(this.getPhone());
		return details.toString();
	}

	// all doctor objects get the next consecutive int as ID
	private static int getNextId() {
		DoctorImpl.idCounter++;
		return DoctorImpl.idCounter;
	}

	// generic getters/setters below
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
