package aps.view.innerview;

import javafx.beans.property.SimpleStringProperty;

public class Subject {
	private final SimpleStringProperty code; //SimpleStringProperty
	private final SimpleStringProperty name;
	private final SimpleStringProperty unit;
	private final SimpleStringProperty category;
	public Subject(String code, String name, String unit,
			String category) {
		super();
		this.code =new SimpleStringProperty(code);
		this.name = new SimpleStringProperty(name);
		this.unit = new SimpleStringProperty(unit);
		this.category = new SimpleStringProperty(category);
	
	}
	public String getCode() {
		return code.get();
	}
	public String getName() {
		return name.get();
	}
	public String getUnit() {
		return unit.get();
	}
	public String getCategory() {
		return category.get();
	}
	
}
