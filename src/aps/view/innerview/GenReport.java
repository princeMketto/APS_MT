package aps.view.innerview;

import javafx.beans.property.SimpleStringProperty;

public class GenReport {

	private final SimpleStringProperty id; //SimpleStringProperty
	private final SimpleStringProperty name;
	private final SimpleStringProperty average;
	private final SimpleStringProperty grade;
	private final SimpleStringProperty clas;
	private final SimpleStringProperty sect;
	private final SimpleStringProperty posit;
	private final SimpleStringProperty division;
	
	public GenReport(String id, String name, String clas,String sect,String posit,
			String average, String grade, String division) {
		super();
		this.id =new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.clas = new SimpleStringProperty(clas);
		this.sect = new SimpleStringProperty(sect);
		this.posit = new SimpleStringProperty(posit);
		this.average = new SimpleStringProperty(average);
		this.grade = new SimpleStringProperty(grade);
		this.division = new SimpleStringProperty(division);
	}

	public String getPosit() {
		return posit.get();
	}

	public String getSect() {
		return sect.get();
	}

	public String getId() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public String getAverage() {
		return average.get();
	}

	public String getGrade() {
		return grade.get();
	}

	public String getClas() {
		return clas.get();
	}

	public String getDivision() {
		return division.get();
	}



}
