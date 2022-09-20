package model.entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Log {

	private Timestamp date;
	private String field;

	public Log(Timestamp date, String field) {
		this.date = date;
		this.field = field;
	}

	public Log() {

	}

	public String getDate() {
		if (date != null) {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date.getTime());
		}
		
		return null;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
