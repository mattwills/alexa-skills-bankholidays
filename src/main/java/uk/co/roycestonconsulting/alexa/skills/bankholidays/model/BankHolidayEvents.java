package uk.co.roycestonconsulting.alexa.skills.bankholidays.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BankHolidayEvents {

	private final String division;

	private final List<BankHoliday> events;

	@JsonCreator
	public BankHolidayEvents(@JsonProperty("division") String division, @JsonProperty("events") List<BankHoliday> events) {
		this.division = division;
		this.events = events;
	}
}
