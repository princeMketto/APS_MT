package aps.view;

import javafx.beans.property.SimpleStringProperty;

public class UploadDetail {
	private final SimpleStringProperty subject; //SimpleStringProperty
	private final SimpleStringProperty Sclass;
	private final SimpleStringProperty type;
	private final SimpleStringProperty year;
	public UploadDetail(String subject, String sclass, String type,
			String year) {
		super();
		this.subject = new SimpleStringProperty(subject);
		Sclass = new SimpleStringProperty(sclass);
		this.type = new SimpleStringProperty(type);
		this.year = new SimpleStringProperty(year);
	}
	public String getSubject() {
		return subject.get();
	}
	public String getSclass() {
		return Sclass.get();
	}
	public String getType() {
		return type.get();
	}
	public String getYear() {
		return year.get();
	}
	
	
}
