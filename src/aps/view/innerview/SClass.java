package aps.view.innerview;

import javafx.beans.property.SimpleStringProperty;

public class SClass {
 //SimpleStringProperty
	private final SimpleStringProperty id;
	private final SimpleStringProperty stream;
	
	public SClass(String id,String stream) {
		super();
		this.id = new SimpleStringProperty(id);
		this.stream = new SimpleStringProperty(stream);
	}

	public String getId() {
		return id.get();
	}

	public String getStream() {
		return stream.get();
	}
	
}
