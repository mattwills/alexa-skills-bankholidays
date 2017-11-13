package uk.co.roycestonconsulting.alexa.skills.bankholidays.model;

import java.util.List;

public class BankHolidayEvents {

	private String division;

	private List<BankHoliday> events;

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public List<BankHoliday> getEvents() {
		return events;
	}

	public void setEvents(List<BankHoliday> events) {
		this.events = events;
	}
}
