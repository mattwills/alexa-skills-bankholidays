package uk.co.roycestonconsulting.alexa.skills.bankholidays.service;

/**
 * Represents a region of the UK which have some specific bank holidays.
 */
public enum Division {

	ENGLAND_AND_WALES("england-and-wales"),

	SCOTLAND("scotland"),

	NORTHERN_IRELAND("northern-ireland");

	String value;

	Division(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
