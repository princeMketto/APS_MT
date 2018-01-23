package aps.view.innerview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Continous {
	private final SimpleStringProperty id; //SimpleStringProperty
	private final SimpleStringProperty name;
	private final SimpleStringProperty sclass;
	private final SimpleStringProperty gender;
	private final SimpleStringProperty tes1;
	private final SimpleStringProperty tes2;
	private final SimpleStringProperty tes3;
	private final SimpleStringProperty tes4;
	private final SimpleStringProperty tes5;
	private final SimpleStringProperty average;
	private final SimpleStringProperty mid;
	private final SimpleStringProperty exam;
	public Continous(String id, String name,String sclass,String gender, String tes1,
			String tes2, String tes3, String tes4,
			String tes5, String average, String mid,
			String exam) {
		super();
		this.id = new SimpleStringProperty(id);
		this.name =new SimpleStringProperty(name);
		this.sclass =new SimpleStringProperty(sclass);
		this.gender =new SimpleStringProperty(gender);
		this.tes1 = new SimpleStringProperty(tes1);
		this.tes2 =  new SimpleStringProperty(tes2);
		this.tes3 =  new SimpleStringProperty(tes3);
		this.tes4 =  new SimpleStringProperty(tes4);
		this.tes5 =  new SimpleStringProperty(tes5);
		this.average =new SimpleStringProperty( average);
		this.mid = new SimpleStringProperty(mid);
		this.exam = new SimpleStringProperty(exam);
	}
	public String getId() {
		return id.get();
	}
	public String getName() {
		return name.get();
	}
	public String getSclass() {
		return sclass.get();
	}
	public String getGender() {
		return gender.get();
	}
	public String getTes1() {
		return tes1.get();
	}
	public String getTes2() {
		return tes2.get();
	}
	public String getTes3() {
		return tes3.get();
	}
	public String getTes4() {
		return tes4.get();
	}
	public String getTes5() {
		return tes5.get();
	}
	public String getAverage() {
		return average.get();
	}
	public String getMid() {
		return mid.get();
	}
	public String getExam() {
		return exam.get();
	}
	
}
