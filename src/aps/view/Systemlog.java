package aps.view;

import javafx.beans.property.SimpleStringProperty;

public class Systemlog {
	private final SimpleStringProperty user; //SimpleStringProperty
	private final SimpleStringProperty action;
	private final SimpleStringProperty time;
	
	public Systemlog(String user, String action, String time) {
		super();
		this.user = new SimpleStringProperty(user);
		this.action = new SimpleStringProperty(action);
		this.time = new SimpleStringProperty(time);
	}

	public String getUser() {
		return user.get();
	}

	public String getAction() {
		return action.get();
	}

	public String getTime() {
		return time.get();
	}
	
}
