package aps.view.innerview;

import javafx.beans.property.SimpleStringProperty;

public class Announce {
	public final SimpleStringProperty Aclass; //SimpleStringProperty
	public final SimpleStringProperty head;
	public final SimpleStringProperty teacher;
	public final SimpleStringProperty gradeA;
	public final SimpleStringProperty atype;
	public final SimpleStringProperty ayear;
	
	
	public Announce(String Aclass, String gradeA, String head,String teacher,String atype,String ayear) {
		super();
		this.Aclass = new SimpleStringProperty(Aclass);
		this.head = new SimpleStringProperty(head);
		this.teacher = new SimpleStringProperty(teacher);
		this.gradeA = new SimpleStringProperty(gradeA);
		this.atype = new SimpleStringProperty(atype);
		this.ayear = new SimpleStringProperty(ayear);
		
	}


	public String getAclass() {
		return Aclass.get();
	}
   public String getHead() {
		return head.get();
	}
	
	public String getTeacher() {
		return teacher.get();
	}
	public String getGradeA() {
		return gradeA.get();
	}
	public String getAtype() {
		return atype.get();
	}
	public String getAyear() {
		return ayear.get();
	}
}
