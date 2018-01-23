package aps.view.innerview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Progress {
	private final SimpleStringProperty id; //SimpleStringProperty
	private final SimpleStringProperty name;
	private final SimpleStringProperty Sclass;
	private final SimpleStringProperty gender;
	private final SimpleStringProperty  mazoezi;
	private final SimpleStringProperty midterm;
	private final SimpleStringProperty exam;
	public Progress(String id, String name,String gender, String Sclass, String mazoezi,
			String midterm, String exam) {
		super();
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.gender = new SimpleStringProperty(gender);
		this.Sclass = new SimpleStringProperty(Sclass);
		this.mazoezi = new SimpleStringProperty(mazoezi);
		this.midterm =new SimpleStringProperty(midterm);
		this.exam = new SimpleStringProperty(exam);
	}
	public String getId() {
		return id.get();
	}
	public String getName() {
		return name.get();
	}
	public String getGender() {
		return gender.get();
	}
	public String getSclass() {
		return Sclass.get();
	}
	public String getMazoezi() {
		return mazoezi.get();
	}
	public String getMidterm() {
		return midterm.get();
	}
	public String getExam() {
		return exam.get();
	}
	

}
