package model.entities;

public class Log {

	private String date;
	private String field;

	public Log(String date, String field) {
		this.date = date;
		this.field = field;
	}

	public Log() {

	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
