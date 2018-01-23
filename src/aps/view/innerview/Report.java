package aps.view.innerview;



import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report {
	public final SimpleStringProperty id; //SimpleStringProperty
	public final SimpleStringProperty fname;
	public final SimpleStringProperty mname;
	public final SimpleStringProperty lname;
	public final SimpleStringProperty clas;
	
	
     
	
	public Report(String id, String fname, String mname,
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
