package aps.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Marks {
	private final SimpleStringProperty id; 
	private final SimpleStringProperty name ;
	private final SimpleStringProperty test1 ;
	private final SimpleStringProperty test2 ;
	private final SimpleStringProperty test3 ;
	private final SimpleStringProperty test4 ;
	private final SimpleStringProperty test5 ;
	private final SimpleStringProperty mazoezi ; //SimpleStringProperty
	private final SimpleIntegerProperty midterm;
	private final SimpleIntegerProperty exams;
	public Marks(String id,String name,String test1,String test2,String test3,String test4,String test5, String mazoezi, Integer midterm, Integer exams) {
		super();
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.test1 = new SimpleStringProperty(test1);
		this.test2 = new SimpleStringProperty(test2);
		this.test3 = new SimpleStringProperty(test3);
		this.test4 = new SimpleStringProperty(test4);
		this.test5 = new SimpleStringProperty(test5);
		this.mazoezi = new SimpleStringProperty(mazoezi);
		this.midterm = new SimpleIntegerProperty(midterm);
		this.exams = new SimpleIntegerProperty(exams);
	}
	public String getId() {
		return id.get();
	}
	public String getName() {
		return name.get();
	}
	public String getTest1() {
		return test1.get();
	}
	public String getTest2() {
		return test2.get();
	}
	public String getTest3() {
		return test3.get();
	}
	public String getTest4() {
		return test4.get();
	}
	public String getTest5() {
		return test5.get();
	}
	public String getMazoezi() {
		return mazoezi.get();
	}
	public int getMidterm() {
		return midterm.get();
	}
	public int getExams() {
		return exams.get();
	}
	
}
