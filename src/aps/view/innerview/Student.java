package aps.view.innerview;

import javafx.beans.property.SimpleStringProperty;

public class Student {
	private final SimpleStringProperty id; //SimpleStringProperty
	private final SimpleStringProperty fname;
	private final SimpleStringProperty mname;
	private final SimpleStringProperty lname;
	private final SimpleStringProperty clas;
	public Student(String id, String fname, String mname,
			String lname, String clas) {
		super();
		this.id =new SimpleStringProperty(id);
		this.fname = new SimpleStringProperty(fname);
		this.mname = new SimpleStringProperty(mname);
		this.lname = new SimpleStringProperty(lname);
		this.clas = new SimpleStringProperty(clas);
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
	public String getClas() {
		return clas.get();
	}


}
