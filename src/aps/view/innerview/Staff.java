package aps.view.innerview;

import javafx.beans.property.SimpleStringProperty;

public class Staff {
	private final SimpleStringProperty id; //SimpleStringProperty
	private final SimpleStringProperty fname;
	private final SimpleStringProperty mname;
	private final SimpleStringProperty lname;
	private final SimpleStringProperty status;
	public Staff(String id, String fname, String mname,
			String lname, String status) {
		super();
		this.id =new SimpleStringProperty(id);
		this.fname = new SimpleStringProperty(fname);
		this.mname = new SimpleStringProperty(mname);
		this.lname = new SimpleStringProperty(lname);
		this.status = new SimpleStringProperty(status);
	}
	public String getId() {
		return id.get();
	}
	public String getFname() {
		return fname.get();
	}
	public String getMname() {
		return mname.get();
	}
	public String getLname() {
		return lname.get();
	}
	public String getStatus() {
		return status.get();
	}
	
}
